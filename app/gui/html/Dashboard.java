package gui.html;

import gui.api.HTMLVisualization;
import play.twirl.api.Html;

import java.util.UUID;

import views.html.renders.html.dashboard;

public class Dashboard implements HTMLVisualization {

    private String urlSumStatistics;

    private String idSumStatistics = UUID.randomUUID().toString();

    public Dashboard(final String urlSumStatistics) {
        this.urlSumStatistics = urlSumStatistics;
    }

    @Override
    public Html render() {
        return dashboard.render(this);
    }

    public String getUrlSumStatistics() {
        return urlSumStatistics;
    }

    public String getIdSumStatistics() {
        return idSumStatistics;
    }
}
