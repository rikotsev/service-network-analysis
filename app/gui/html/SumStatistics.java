package gui.html;

import gui.api.HTMLVisualization;
import models.PredefinedValuesContainer;
import play.twirl.api.Html;
import shapeless.ops.nat;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import views.html.renders.html.sum_statistics;

public class SumStatistics implements HTMLVisualization {

    private Map<String, Double> data;

    public List<String> getItemCodes() {
        return Arrays.asList(PredefinedValuesContainer.ItemCode.values()).stream().map(en -> en.toString()).collect(Collectors.toList());
    }

    public List<String> getYears() {
        return IntStream.of(PredefinedValuesContainer.years).mapToObj( year -> Integer.toString(year) ).collect(Collectors.toList());
    }

    public SumStatistics(final Map<String, Double> data) {
        this.data = data;
    }

    public Map<String, Double> getData() {
        return data;
    }

    @Override
    public Html render() {
        return sum_statistics.render(this);
    }
}
