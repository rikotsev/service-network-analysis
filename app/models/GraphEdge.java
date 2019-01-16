package models;

import models.api.PlainGraphEdge;

import java.util.UUID;

public class GraphEdge implements PlainGraphEdge {

    private static String LABEL_TEMPLATE = "[FlowType = %s] [ItemCode = %s] [Amount = %s]";

    private String reportedId;

    private String partnerId;

    private String flowType;

    private String itemCode;

    private String year;

    private String reportedValue;

    private String finalValue;

    private String finalValueMethodology;

    private String balancedValue;

    private GraphEdge() {
    }

    public static GraphEdge from(final String... values) {
        final GraphEdge result = new GraphEdge();
        result.reportedId = values[0];
        result.partnerId = values[1];
        result.flowType = values[2];
        result.itemCode = values[3];
        result.year = values[4];
        result.reportedValue = values[5];
        result.finalValue = values[6];
        result.finalValueMethodology = values[7];
        result.balancedValue = values[8];

        return result;
    }

    public static GraphEdge from(final String reportedId, final String partnerId, final String flowType, final String itemCode, final String year, final String reportedValue, final String finalValue, final String finalValueMethodology, final String balancedValue) {
        final GraphEdge result = new GraphEdge();
        result.reportedId = reportedId;
        result.partnerId = partnerId;
        result.flowType = flowType;
        result.itemCode = itemCode;
        result.year = year;
        result.reportedValue = reportedValue;
        result.finalValue = finalValue;
        result.finalValueMethodology = finalValueMethodology;
        result.balancedValue = balancedValue;

        return result;
    }

    public String getReportedId() {
        return reportedId;
    }

    public void setReportedId(String reportedId) {
        this.reportedId = reportedId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getFlowType() {
        return flowType;
    }

    public void setFlowType(String flowType) {
        this.flowType = flowType;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getReportedValue() {
        return reportedValue;
    }

    public void setReportedValue(String reportedValue) {
        this.reportedValue = reportedValue;
    }

    public String getFinalValue() {
        return finalValue;
    }

    public void setFinalValue(String finalValue) {
        this.finalValue = finalValue;
    }

    public String getFinalValueMethodology() {
        return finalValueMethodology;
    }

    public void setFinalValueMethodology(String finalValueMethodology) {
        this.finalValueMethodology = finalValueMethodology;
    }

    public String getBalancedValue() {
        return balancedValue;
    }

    public void setBalancedValue(String balancedValue) {
        this.balancedValue = balancedValue;
    }

    public String getItemCode() {
        return itemCode;
    }

    @Override
    public String getSource() {
        return reportedId;
    }

    @Override
    public String getTarget() {
        return partnerId;
    }

    @Override
    public String getLabel() {
        //return String.format(LABEL_TEMPLATE, this.getFlowType(), this.getItemCode(), this.getFinalValue());
        return this.getFinalValue();
    }

    @Override
    public Double getValue() {
        return Double.parseDouble(finalValue);
    }

    public String getIndex() {
        return UUID.randomUUID().toString();
    }

}
