package data.persist;

import models.GraphEdge;
import models.GraphNode;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Collections;

/**
 * Bean class
 */
public class DatabaseImage {

    public enum PersistAction {
        NODES, EDGES;
    }

    private Collection<GraphNode> nodes;

    private Collection<GraphEdge> edges;

    private PersistAction action;

    private String nodeTableName;

    private String edgeTableName;

    private DatabaseImage() {}

    public Collection<GraphNode> getNodes() {
        return nodes;
    }

    public Collection<GraphEdge> getEdges() {
        return edges;
    }

    public PersistAction getAction() {
        return action;
    }

    public String getNodeTableName() {
        return nodeTableName;
    }

    public String getEdgeTableName() {
        return edgeTableName;
    }

    public static Builder builder(final PersistAction action) {
        return new Builder( action );
    }

    public static class Builder {
        private Collection<GraphNode> nodes = Collections.emptySet();
        private Collection<GraphEdge> edges = Collections.emptySet();
        private String nodeTableName = StringUtils.EMPTY;
        private String edgeTableName = StringUtils.EMPTY;
        final private PersistAction action;

        private Builder(final PersistAction action) {
            this.action = action;
        }

        public Builder nodes(final Collection<GraphNode> nodes) {
            this.nodes = nodes;
            return this;
        }

        public Builder edges(final Collection<GraphEdge> edges) {
            this.edges = edges;
            return this;
        }

        public Builder nodeTableName(final String nodeTableName) {
            this.nodeTableName = nodeTableName;
            return this;
        }

        public Builder edgeTableName(final String edgeTableName) {
            this.edgeTableName = edgeTableName;
            return this;
        }

        public DatabaseImage build() {
            final DatabaseImage image = new DatabaseImage();
            image.nodes = this.nodes;
            image.edges = this.edges;
            image.action = this.action;
            image.nodeTableName = this.nodeTableName;
            image.edgeTableName = this.edgeTableName;

            return image;
        }

    }

}
