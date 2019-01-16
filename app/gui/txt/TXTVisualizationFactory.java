package gui.txt;

import gui.api.TXTVisualization;
import models.GraphNode;
import models.api.PlainGraphEdge;

import javax.inject.Singleton;
import java.util.Set;

@Singleton
public class TXTVisualizationFactory {

    public TXTVisualization gml(final Set<GraphNode> nodes, final Set<? extends PlainGraphEdge> edges) {
        return new GraphAsGML(nodes, edges);
    }

}
