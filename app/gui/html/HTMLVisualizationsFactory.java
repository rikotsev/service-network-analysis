package gui.html;

import gui.api.HTMLVisualization;
import gui.html.graphdetails.GraphDetails;
import gui.html.graphdetails.GraphDetailsFrame;
import models.GraphNode;
import models.GraphStatContainer;

import javax.inject.Singleton;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

@Singleton
public class HTMLVisualizationsFactory {

    public HTMLVisualization page(final HTMLVisualization content) {
        return new Page(content);
    }

    public HTMLVisualization page(final HTMLVisualization content, final HTMLVisualization navbar) {
        return new Page(content, navbar);
    }

    public HTMLVisualization pageWithStandardNavbar(final HTMLVisualization content) {
        return page(content, navbar());
    }

    public HTMLVisualization simpleGraph(final String url) {
        return new SimpleGraph(url);
    }

    public HTMLVisualization dashboard(final String url) {
        return new Dashboard(url);
    }

    public HTMLVisualization sumStatistics(final Map<String, Double> data) {
        return new SumStatistics(data);
    }

    public HTMLVisualization navbar() {
        return new Navbar();
    }

    public HTMLVisualization graphFormHTML(final String url) {
        return new GraphForm(url);
    }

    public HTMLVisualization graphFormJS() {
        return new GraphForm();
    }

    public HTMLVisualization graphDetailsFrameForVisualization(final String url, final HTMLVisualization graphForm) {
        return new GraphDetailsFrame(url, graphForm, GraphDetailsFrame.Type.VISUALIZATION);
    }

    public HTMLVisualization graphDetailsFrameForData(final String url, final HTMLVisualization graphForm) {
        return new GraphDetailsFrame(url, graphForm, GraphDetailsFrame.Type.DATA);
    }

    public HTMLVisualization graphDetails(final GraphStatContainer container, final Collection<GraphNode> nodes) {
        return new GraphDetails(container, nodes);
    }
}
