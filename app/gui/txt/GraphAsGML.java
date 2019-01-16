package gui.txt;

import gui.api.TXTVisualization;
import models.GraphNode;
import models.api.PlainGraphEdge;
import play.twirl.api.Txt;

import java.util.Set;

import views.txt.renders.txt.gml;

public class GraphAsGML implements TXTVisualization {

    private Set<GraphNode> nodes;

    private Set<? extends PlainGraphEdge> edges;

    public Set<GraphNode> getNodes() {
        return nodes;
    }

    public Set<? extends PlainGraphEdge> getEdges() {
        return edges;
    }

    public GraphAsGML(final Set<GraphNode> nodes, final Set<? extends PlainGraphEdge> edges) {
        this.nodes = nodes;
        this.edges = edges;
    }

    @Override
    public Txt render() {
        return gml.render(this);
    }
}
