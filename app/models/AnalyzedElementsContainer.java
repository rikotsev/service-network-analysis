package models;

import models.api.PlainGraphEdge;

import java.util.List;
import java.util.Set;

public class AnalyzedElementsContainer {

    private Set<GraphNode> nodes;

    private Set<? extends PlainGraphEdge> edges;

    private AnalyzedElementsContainer() {}

    public static AnalyzedElementsContainer wrapAround(final Set<GraphNode> nodes, final Set<? extends PlainGraphEdge> edges) {
        final AnalyzedElementsContainer result = new AnalyzedElementsContainer();
        result.nodes = nodes;
        result.edges = edges;

        return result;
    }

    public Set<GraphNode> getNodes() {
        return nodes;
    }

    public Set<? extends PlainGraphEdge> getEdges() {
        return edges;
    }

}
