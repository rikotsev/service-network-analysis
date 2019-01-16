package gui.html;

import gui.api.HTMLVisualization;
import play.twirl.api.Html;

import views.html.renders.html.simple_graph;

public class SimpleGraph implements HTMLVisualization {

    final private String url;

    public SimpleGraph(final String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public Html render() {
        return simple_graph.render(this);
    }
}
