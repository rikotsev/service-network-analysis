package gui.html.graphdetails;

import gui.api.HTMLVisualization;
import play.twirl.api.Html;

import views.html.renders.html.graph_details.graph_details_frame;

public class GraphDetailsFrame implements HTMLVisualization {

    public enum Type {
        VISUALIZATION,
        DATA
    };

    private String url;

    private HTMLVisualization graphForm;

    private Type type;

    public GraphDetailsFrame(final String url, final HTMLVisualization graphForm, final Type type) {
        this.url = url;
        this.graphForm = graphForm;
        this.type = type;
    }

    @Override
    public Html render() {
        return graph_details_frame.render(this);
    }

    public String getUrl() {
        return url;
    }

    public HTMLVisualization getGraphForm() {
        return graphForm;
    }

    public void setGraphForm(HTMLVisualization graphForm) {
        this.graphForm = graphForm;
    }

    public boolean isVisualization() {
        return type.equals(Type.VISUALIZATION);
    }

    public boolean isData() {
        return type.equals(Type.DATA);
    }
}
