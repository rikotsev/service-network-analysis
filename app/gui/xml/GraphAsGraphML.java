package gui.xml;

import gui.api.XMLVisualization;
import models.GraphNode;
import models.api.PlainGraphEdge;
import play.twirl.api.Xml;

import java.util.Set;

import views.xml.renders.xml.graphml;

public class GraphAsGraphML implements XMLVisualization {

    private Set<GraphNode> nodes;

    private Set<? extends PlainGraphEdge> edges;

    public GraphAsGraphML(final Set<GraphNode> nodes, final Set<? extends PlainGraphEdge> edges) {
        this.nodes = nodes;
        this.edges = edges;
    }

    public Set<GraphNode> getNodes() {
        return nodes;
    }

    public Set<? extends PlainGraphEdge> getEdges() {
        return edges;
    }

    @Override
    public Xml render() {
        return graphml.render(this);
    }

}
