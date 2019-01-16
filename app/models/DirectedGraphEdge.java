package models;

import models.api.PlainGraphEdge;

public class DirectedGraphEdge implements PlainGraphEdge {

    private Integer id;

    private String source;

    private String target;

    private String itemCode;

    private Integer year;

    private Double value;

    DirectedGraphEdge() {}

    public static DirectedGraphEdge from(final Integer id, final String source, final String target, final String itemCode, final Integer year, final Double value) {
        final DirectedGraphEdge result = new DirectedGraphEdge();
        result.id = id;
        result.source = source;
        result.target = target;
        result.itemCode = itemCode;
        result.year = year;
        result.value = value;

        return result;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String getIndex() {
        return Integer.toString(id);
    }

    @Override
    public String getSource() {
        return source;
    }

    @Override
    public String getTarget() {
        return target;
    }

    public String getItemCode() {
        return itemCode;
    }

    public Integer getYear() {
        return year;
    }

    @Override
    public Double getValue() {
        return value;
    }

    @Override
    public String getLabel() {
        return String.format("%s_%d", itemCode, year);
    }


}
