package gui.html.graphdetails;

import gui.api.HTMLVisualization;
import models.GraphNode;
import models.GraphStatContainer;
import play.twirl.api.Html;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import views.html.renders.html.graph_details.graph_details;

public class GraphDetails implements HTMLVisualization {

    private Collection<HTMLVisualization> elements = new ArrayList<>();

    private GraphStatContainer stats;

    private Map<String, String> nodesAsMap;

    public GraphDetails(final GraphStatContainer stats, final Collection<GraphNode> nodes) {
        this.stats = stats;
        this.nodesAsMap = nodes.stream().collect(Collectors.toMap(GraphNode::getId, GraphNode::getLabel));
    }

    public GraphStatContainer getStats() {
        return stats;
    }

    public Collection<HTMLVisualization> getElements() {
        return elements;
    }

    @Override
    public Html render() {

        renderDegrees();
        renderInCentrality();
        renderOutCentrality();
        renderBetweennessCentrality();
        renderPageRank();
        renderEigenvectorCentrality();

        return graph_details.render(this);
    }


    private void renderDegrees() {
        if(stats.degrees().isPresent()) {
            final TableElement element = new TableElement(stats.degrees().get(), nodesAsMap);
            element.setCaption("Degrees");
            element.setAverage(false);
            element.setColumnNames("Country Code", "# of Degrees");

            elements.add(element);
        }
    }

    private void renderInCentrality() {
        if(stats.inClosenessCentrality().isPresent()) {
            final TableElement element = new TableElement(stats.inClosenessCentrality().get(), nodesAsMap);
            element.setCaption("In Closeness Centrality");
            element.setAverage(true);
            element.setColumnNames("Country code", "Centrality");

            elements.add(element);
        }
    }

    private void renderOutCentrality() {
        if(stats.outClosenessCentrality().isPresent()) {
            final TableElement element = new TableElement(stats.outClosenessCentrality().get(), nodesAsMap);
            element.setCaption("Out Closeness Centrality");
            element.setAverage(true);
            element.setColumnNames("Country Code", "Centrality");

            elements.add(element);
        }
    }

    private void renderBetweennessCentrality() {
        if(stats.betweennessCentrality().isPresent()) {
            final TableElement element = new TableElement(stats.betweennessCentrality().get(), nodesAsMap);
            element.setCaption("Betweenness Centrality");
            element.setAverage(true);
            element.setColumnNames("Country Code", "Centrality");

            elements.add(element);
        }
    }

    private void renderPageRank() {
        if(stats.pageRank().isPresent()) {
            final TableElement element = new TableElement(stats.pageRank().get(), nodesAsMap);
            element.setCaption("Page Rank");
            element.setAverage(false);
            element.setColumnNames("Country Code", "Page Rank");

            elements.add(element);
        }
    }

    private void renderEigenvectorCentrality() {
        if(stats.eigenvectorCentrality().isPresent()) {
            final TableElement element = new TableElement(stats.eigenvectorCentrality().get(), nodesAsMap);
            element.setCaption("Eigenvector Centrality");
            element.setAverage(false);
            element.setColumnNames("Country Code", "Eigenvector Centrality");

            elements.add(element);
        }
    }

}
