package models;

import models.api.SparkNode;

import javax.annotation.OverridingMethodsMustInvokeSuper;

public class GraphNode implements SparkNode {

    private String id;

    private String label;

    private Integer size = 4;

    private GraphNode() {}

    public static GraphNode from(final String id, final String label) {
        final GraphNode result = new GraphNode();
        result.id = id;
        result.label = label;

        return result;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String getName() {
        return label;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(final Integer size) {
        this.size = size;
    }
}
