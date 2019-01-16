package data.persist;

import models.GraphEdge;
import models.GraphNode;
import play.Logger;
import play.db.Database;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class DatabaseMemoryPersistence {

    private static int THREAD_COUNT = 5;

    private static String INSERT_STATEMENT_NODES = "INSERT INTO %s (id, label) VALUES (?,?)";

    private static String INSERT_STATEMENT_EDGES = "INSERT INTO %s (reported_id, partner_id, flow_type, item_code, year, reported_value, final_value, final_value_methodology, balanced_value) VALUES (?,?,?,?,?,?,?,?,?)";

    @Inject
    private Database db;

    /**
     * Returns -1 if the write was unsuccessful or 1 if it was successful
     *
     * @param dbImage
     * @return
     */
    public int persist(final DatabaseImage dbImage) {
        Logger.info("Started persistence!");
        final long startTime = System.currentTimeMillis();
        final String insertForTable;

        switch (dbImage.getAction()) {
            case NODES:
                insertForTable = String.format(INSERT_STATEMENT_NODES, dbImage.getNodeTableName());
                break;
            case EDGES:
                insertForTable = String.format(INSERT_STATEMENT_EDGES, dbImage.getEdgeTableName());
                break;
            default:
                throw new RuntimeException(String.format("There is no implementation for action = %s", dbImage.getAction().toString()));
        }

        try (final Connection conn = db.getConnection(); final PreparedStatement stmt = conn.prepareStatement(insertForTable)) {
            final Set<Integer> results;
            switch (dbImage.getAction()) {
                case NODES:
                    results = nodesToResults(dbImage.getNodes(), stmt);
                    break;
                case EDGES:
                    results = edgesToFutures(dbImage.getEdges(), stmt);
                    break;
                default:
                    throw new RuntimeException(String.format("There is no implementation for action = %s", dbImage.getAction().toString()));
            }

            //We have an error while adding to batch
            if (results.stream().anyMatch(i -> i.equals(-1))) {
                return -1;
            }

            stmt.executeBatch();

            final long endTime = System.currentTimeMillis();
            Logger.info("Finished persistence it took {} milliseconds", (endTime - startTime));
            return 1;

        } catch (final SQLException exception) {
            Logger.error("Failed to initiate the writing procedure with {}", exception);
            return -1;
        }

    }

    private Set<Integer> nodesToResults(final Collection<GraphNode> nodes, final PreparedStatement stmt) {
        final Set<Integer> results = nodes.stream().map(node -> {
            try {
                stmt.setString(1, node.getId());
                stmt.setString(2, node.getLabel());
                stmt.addBatch();
                return 1;
            } catch (final SQLException rowException) {
                Logger.error("Could not add the following row [ id = {} , label = {} ] to the batch. Will try to rollback! Exception was = {}",
                        node.getId(),
                        node.getLabel(),
                        rowException);
                return -1;
            }

        }).collect(Collectors.toSet());

        return results;
    }

    private Set<Integer> edgesToFutures(final Collection<GraphEdge> edges, final PreparedStatement stmt) {
        final Set<Integer> results = edges.stream().map(edge -> {

            try {
                stmt.setString(1, edge.getReportedId());
                stmt.setString(2, edge.getPartnerId());
                stmt.setString(3, edge.getFlowType());
                stmt.setString(4, edge.getItemCode());
                stmt.setString(5, edge.getYear());
                stmt.setString(6, edge.getReportedValue());
                stmt.setString(7, edge.getFinalValue());
                stmt.setString(8, edge.getFinalValueMethodology());
                stmt.setString(9, edge.getBalancedValue());
                stmt.addBatch();

                return 1;
            } catch (final SQLException rowException) {
                Logger.error("Could not add the following row [ reportedId = {}, partnerId = {}, year = {} ] to the batch. Will try to rollback! Exception was = {}",
                        edge.getReportedId(),
                        edge.getPartnerId(),
                        edge.getYear(),
                        rowException);
                return -1;
            }

        }).collect(Collectors.toSet());

        return results;
    }

}
