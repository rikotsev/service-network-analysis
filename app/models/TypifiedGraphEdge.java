package models;

import models.api.PlainGraphEdge;
import models.api.SparkEdge;

public class TypifiedGraphEdge implements PlainGraphEdge, SparkEdge{

    private static String LABEL_TEMPLATE = "[FlowType = %s] [ItemCode = %s] [Amount = %4.3f]";

    private int id;

    private String reportedId;

    private String partnerId;

    private String flowType;

    private String itemCode;

    private int year;

    private double reportedValue;

    private double finalValue;

    private String finalValueMethodology;

    private double balancedValue;

    private TypifiedGraphEdge() {}

    public static TypifiedGraphEdge from(
            final int id,
            final String reportedId,
            final String partnerId,
            final String flowType,
            final String itemCode,
            final int year,
            final double reportedValue,
            final double finalValue,
            final String finalValueMethodology,
            final double balancedValue
    ) {
        final TypifiedGraphEdge result = new TypifiedGraphEdge();
        result.id = id;
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

    public int getId() {
        return id;
    }

    public String getReportedId() {
        return reportedId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public String getFlowType() {
        return flowType;
    }

    public String getItemCode() {
        return itemCode;
    }

    public int getYear() {
        return year;
    }

    public double getReportedValue() {
        return reportedValue;
    }

    public double getFinalValue() {
        return finalValue;
    }

    public String getFinalValueMethodology() {
        return finalValueMethodology;
    }

    public double getBalancedValue() {
        return balancedValue;
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
        //return String.format( LABEL_TEMPLATE, this.getFlowType(), this.getItemCode(), this.getFinalValue() );
        return this.getFlowType();
    }

    @Override
    public Double getValue() {
        return finalValue;
    }

    @Override
    public String getSrc() {
        return reportedId;
    }

    @Override
    public String getDst() {
        return partnerId;
    }

    @Override
    public Double getAmount() {
        return finalValue;
    }

    @Override
    public String getIndex() {
        return Integer.toString(id);
    }
}
