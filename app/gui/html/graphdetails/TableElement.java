package gui.html.graphdetails;

import gui.api.HTMLVisualization;
import play.twirl.api.Html;

import java.util.*;

import views.html.renders.html.graph_details.table_element;

public class TableElement<T> implements HTMLVisualization {

    private Map<String, T> data;

    private Map<String,String> nodesAsMap;

    private String caption = "DEFAULT_CAPTION";

    private boolean average = false;

    private String domId = UUID.randomUUID().toString();

    private String[] columnNames = {"defaultName1","defaultName2"};

    public TableElement(final Map<String, T> data, final Map<String, String> nodesAsMap) {
        this.data = data;
        this.nodesAsMap = nodesAsMap;
    }

    public Map<String, T> getData() {
        return data;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public boolean isAverage() {
        return average;
    }

    public void setAverage(boolean average) {
        this.average = average;
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(String... columnNames) {
        this.columnNames = columnNames;
    }

    public String getDomId() {
        return domId;
    }

    public String getCountryName(final String code) {
        return nodesAsMap.get(code);
    }

    public Set<Map.Entry<String, String>> getCountries() {
        return nodesAsMap.entrySet();
    }

    public Optional<T> getDataValue(final String key) {
        return Optional.ofNullable(data.getOrDefault(key, null));
    }

    @Override
    public Html render() {
        return table_element.render(this);
    }

    public double average() {
        final Collection<T> values = data.values();
        final Optional<T> value = values.stream().findAny();

        if(!value.isPresent()) {
            throw new IllegalArgumentException("Cannot calculate the average of nothing!");
        }

        if(value.get() instanceof Double) {
            return values.stream().mapToDouble( v -> ((Double)v).doubleValue() ).sum() / values.size();
        }
        else {
            throw new IllegalArgumentException("Method is designed to calculate average on double values!");
        }
    }

}
