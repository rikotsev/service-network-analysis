package data.db;

import models.AggregateResult;
import models.DirectedGraphEdge;
import models.GraphNode;
import models.TypifiedGraphEdge;
import org.h2.command.Prepared;
import play.Logger;
import play.db.Database;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Singleton
public class SQLDatabaseDAO {

    private static String SELECT_ALL_NODES_SQL = "SELECT id, label FROM %s";

    private static String SELECT_ALL_EDGES_SQL = "SELECT id, reported_id, partner_id, flow_type, item_code, year, reported_value, final_value, final_value_methodology, balanced_value FROM %s WHERE year = ? AND item_code = ?";

    //TODO get from config
    private static String TABLE_NAME_NODES = "standard_nodes";

    //TODO get from config
    private static String TABLE_NAME_EDGES = "indexed_edges";

    //TODO get from config
    private static String TABLE_NAME_DIRECTED_EDGES = "directed_edges";

    @Inject
    private Database db;

    @Inject
    private SQLBuilder sqlBuilder;

    public Set<GraphNode> readNodes() {
        Logger.info("Started to read nodes!");
        final long startTime = System.currentTimeMillis();
        final String sqlStatement = String.format(SELECT_ALL_NODES_SQL, TABLE_NAME_NODES);

        try (final Connection conn = db.getConnection(); final PreparedStatement stmt = conn.prepareStatement(sqlStatement)) {

            final ResultSet resultSet = stmt.executeQuery();
            final Set<GraphNode> result = new HashSet<>();

            while (resultSet.next()) {
                final String id = resultSet.getString("id");
                final String label = resultSet.getString("label");
                result.add(GraphNode.from(id, label));
            }
            final long endTime = System.currentTimeMillis();
            Logger.info("Finished to read nodes. It took {}  milliseconds.", (endTime - startTime));
            return result;

        } catch (final SQLException e) {
            Logger.error("Failed to get the nodes with exc", e);
            return Collections.emptySet();
        }

    }

    public Set<TypifiedGraphEdge> readEdges(final DatabaseFilter filter) {
        Logger.info("Started to read edges!");
        final long startTime = System.currentTimeMillis();
        final String sqlTemplate = sqlBuilder.create(filter);
        final String sqlStatement = String.format(sqlTemplate, TABLE_NAME_EDGES);

        Logger.info("Query is = {}", sqlStatement);

        try (final Connection conn = db.getConnection(); final PreparedStatement stmt = conn.prepareStatement(sqlStatement)) {

            populateStatementFromFilter(stmt, filter);

            final ResultSet resultSet = stmt.executeQuery();
            final Set<TypifiedGraphEdge> result = new HashSet<>();

            while (resultSet.next()) {
                final int id = resultSet.getInt("id");
                final String reportedId = resultSet.getString("reported_id");
                final String partnerId = resultSet.getString("partner_id");
                final String flowType = resultSet.getString("flow_type");
                final String itemCode = resultSet.getString("item_code");
                final int year = resultSet.getInt("year");
                final double reportedValue = resultSet.getDouble("reported_value");
                final double finalValue = resultSet.getDouble("final_value");
                final String finalValueMethodology = resultSet.getString("final_value_methodology");
                final double balancedValue = resultSet.getDouble("balanced_value");

                result.add(TypifiedGraphEdge.from(id, reportedId, partnerId, flowType, itemCode, year, reportedValue, finalValue, finalValueMethodology, balancedValue));
            }

            final long endTime = System.currentTimeMillis();
            Logger.info("Finished reading edges. It took {} milliseconds.", (endTime - startTime));
            return result;

        } catch (final SQLException e) {
            Logger.error("Failed to get nodes with exc", e);
            return Collections.emptySet();
        }

    }

    public double readAverage(final DatabaseFilter filter) {
        Logger.info("Started to calculate average!");
        final long startTime = System.currentTimeMillis();
        final String sqlTemplate = sqlBuilder.getAvgStatement(filter);
        final String sqlStatement = String.format(sqlTemplate, TABLE_NAME_EDGES);

        try (final Connection conn = db.getConnection(); final PreparedStatement stmt = conn.prepareStatement(sqlStatement)) {
            populateStatementFromFilter(stmt, filter);
            final ResultSet resultSet = stmt.executeQuery();
            resultSet.next();
            final long endTime = System.currentTimeMillis();
            Logger.info("Finished calculating average. It took {} milliseconds", (endTime - startTime));

            return resultSet.getDouble("final_value_average");
        } catch (final SQLException e) {
            Logger.error("Failed to get average with exc", e);

            return 0.0;
        }

    }

    public Set<String> readPartners(final DatabaseFilter filter) {
        Logger.info("Started to read partners!");
        final long startTime = System.currentTimeMillis();
        final String sqlTemplate = sqlBuilder.getPartners(filter);
        final String sqlStatement = String.format(sqlTemplate, TABLE_NAME_EDGES);

        try (final Connection conn = db.getConnection(); final PreparedStatement stmt = conn.prepareStatement(sqlStatement)) {
            populateStatementFromFilter(stmt, filter);
            final ResultSet resultSet = stmt.executeQuery();
            final Set<String> results = new HashSet<>();

            while (resultSet.next()) {
                results.add(resultSet.getString("partner_id"));
            }

            final long endTime = System.currentTimeMillis();
            Logger.info("Finished reading partners. It took {} milliseconds", (endTime - startTime));
            return results;
        } catch (final SQLException e) {
            Logger.error("Failed to get partners with exc", e);

            return Collections.emptySet();
        }
    }

    public Set<String> readReporters(final DatabaseFilter filter) {
        Logger.info("Started to read reporters!");
        final long startTime = System.currentTimeMillis();
        final String sqlTemplate = sqlBuilder.getReporters(filter);
        final String sqlStatement = String.format(sqlTemplate, TABLE_NAME_EDGES);

        try (final Connection conn = db.getConnection(); final PreparedStatement stmt = conn.prepareStatement(sqlStatement)) {
            populateStatementFromFilter(stmt, filter);
            final ResultSet resultSet = stmt.executeQuery();
            final Set<String> results = new HashSet<>();

            while (resultSet.next()) {
                results.add(resultSet.getString("reported_id"));
            }

            final long endTime = System.currentTimeMillis();
            Logger.info("Finished reading reporters. It took {} milliseconds", (endTime - startTime));
            return results;
        } catch (final SQLException e) {
            Logger.error("Failed to get partners with exc", e);

            return Collections.emptySet();
        }
    }

    public Set<AggregateResult> readAllSums() {
        Logger.info("Started to calculate all sums!");
        final long startTime = System.currentTimeMillis();
        final String sqlTemplate = sqlBuilder.getAllSums();
        final String sqlStatement = String.format(sqlTemplate, TABLE_NAME_EDGES);

        try (final Connection conn = db.getConnection(); final PreparedStatement stmt = conn.prepareStatement(sqlStatement)) {

            final ResultSet resultSet = stmt.executeQuery();
            final Set<AggregateResult> results = new HashSet<>();

            while (resultSet.next()) {
                final double finalValue = resultSet.getDouble("final_value_sum");
                final String itemCode = resultSet.getString("item_code");
                final int year = resultSet.getInt("year");

                results.add(AggregateResult.from(finalValue, itemCode, year));
            }

            final long endTime = System.currentTimeMillis();
            Logger.info("Finished calculating all sums. It took {} milliseconds.", (endTime - startTime));
            return results;
        } catch (final SQLException e) {
            Logger.error("Failed to get sum with exc", e);
            return new HashSet<>();
        }
    }

    public Set<DirectedGraphEdge> readDirectedEdges(final DatabaseFilter filter) {
        Logger.info("Started to read directed edges!");
        final long startTime = System.currentTimeMillis();
        final String sqlTemplate = sqlBuilder.createDirected(filter);
        final String sqlStatement = String.format(sqlTemplate, TABLE_NAME_DIRECTED_EDGES);

        Logger.info("Query is = {}", sqlStatement);

        try (final Connection conn = db.getConnection(); final PreparedStatement stmt = conn.prepareStatement(sqlStatement)) {

            populateStatementFromFilter(stmt, filter);

            final ResultSet resultSet = stmt.executeQuery();
            final Set<DirectedGraphEdge> result = new HashSet<>();

            while (resultSet.next()) {
                final int id = resultSet.getInt("id");
                final String src = resultSet.getString("src");
                final String target = resultSet.getString("target");
                final String itemCode = resultSet.getString("item_code");
                final int year = resultSet.getInt("year");
                final double balancedValue = resultSet.getDouble("balanced_value");

                result.add(DirectedGraphEdge.from(id, src, target, itemCode, year, balancedValue));
            }

            final long endTime = System.currentTimeMillis();
            Logger.info("Finished reading directed edges. It took {} milliseconds.", (endTime - startTime));
            return result;

        } catch (final SQLException e) {
            Logger.error("Failed to get nodes with exc", e);
            return Collections.emptySet();
        }

    }


    private void populateStatementFromFilter(final PreparedStatement stmt, final DatabaseFilter filter) throws SQLException {
        int paramCounter = 1;

        stmt.setString(paramCounter++, filter.itemCode().get().toString());
        stmt.setInt(paramCounter++, filter.year().get());

        if (filter.finalValueBiggerThan().isPresent()) {
            stmt.setDouble(paramCounter++, filter.finalValueBiggerThan().get());
        }
    }

}
