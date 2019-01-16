package controllers;

import data.util.FileChunker;
import data.persist.DatabaseImage;
import data.persist.DatabaseMemoryPersistence;
import data.FileSystemDAO;
import models.GraphEdge;
import models.GraphNode;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.Collection;

public class AdminController extends Controller {

    @Inject
    private FileSystemDAO fsDAO;

    @Inject
    private DatabaseMemoryPersistence dbPersistence;

    public Result readNodesFromFileAndWriteToDB() {
        try {

            final Collection<GraphNode> nodes = fsDAO.readNodes();
            final DatabaseImage dbImage = DatabaseImage.builder(DatabaseImage.PersistAction.NODES)
                    .nodeTableName("standard_nodes")
                    .nodes(nodes)
                    .build();

            final int result = dbPersistence.persist(dbImage);

            if (result == -1) {
                return internalServerError("The operation failed! Check the logs!");
            }

            return ok("Successful write of nodes!");
        } catch (final Exception e) {
            Logger.error("Call failed with {} ", e);
            return internalServerError("The operation failed! Check the logs!");
        }
    }

    public Result readEdgesFromFileAndWriteToDB() {
        try {

            final int numberOfEdges = fsDAO.numberOfEdges();

            FileChunker.from(numberOfEdges).forEachChunk((firstLine, lastLine) -> {
                final Collection<GraphEdge> edges = fsDAO.readEdges(firstLine, lastLine);
                final DatabaseImage dbImage = DatabaseImage.builder(DatabaseImage.PersistAction.EDGES)
                        .edgeTableName("standard_edges")
                        .edges(edges)
                        .build();

                final int result = dbPersistence.persist(dbImage);

                if (result == -1) {
                    throw new RuntimeException("Chunk write failed!");
                }
            });

            return ok("Successfull write of edges!");

        } catch (final Exception e) {
            Logger.error("Call failed with {}", e);
            return internalServerError("The operation failed! Check the logs!");
        }
    }

}
