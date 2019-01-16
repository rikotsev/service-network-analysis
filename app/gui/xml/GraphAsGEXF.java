package gui.xml;

import models.GraphNode;
import models.api.PlainGraphEdge;
import play.twirl.api.Xml;
import gui.api.XMLVisualization;

import views.xml.renders.xml.gexf;

import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Should be private but because it cannot be accessed from scala we keep it public
 */
public class GraphAsGEXF implements XMLVisualization {

    final private Set<GraphNode> nodes;

    final private Set<? extends PlainGraphEdge> edges;

    public Set<GraphNode> getNodes() {
        return nodes;
    }

    public Set<? extends PlainGraphEdge> getEdges() {
        return edges;
    }

    public GraphAsGEXF(final Set<GraphNode> nodes, final Set<? extends PlainGraphEdge> edges) {
        this.nodes = nodes;
        this.edges = edges;
    }

    @Override
    public Xml render() {
        return gexf.render(this);
    }

    public int getRandomInt() {
        return ThreadLocalRandom.current().nextInt(0, 3000 + 1);
    }
}
