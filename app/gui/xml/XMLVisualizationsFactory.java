package gui.xml;

import models.GraphNode;
import models.api.PlainGraphEdge;
import gui.api.XMLVisualization;

import javax.inject.Singleton;
import java.util.Set;

@Singleton
public class XMLVisualizationsFactory {

    public XMLVisualization gexf(final Set<GraphNode> nodes, final Set<? extends PlainGraphEdge> edges) {
        return new GraphAsGEXF(nodes, edges);
    }

    public XMLVisualization graphml(final Set<GraphNode> nodes, final Set<? extends PlainGraphEdge> edges) {
        return new GraphAsGraphML(nodes, edges);
    }

}
