package models;

public class AggregateResult {

    private double value;

    private String itemCode;

    private int year;

    private AggregateResult() {}

    public static AggregateResult from(final double value, final String itemCode, final int year) {
        final AggregateResult result = new AggregateResult();
        result.value = value;
        result.itemCode = itemCode;
        result.year = year;

        return result;
    }

    public double getValue() {
        return value;
    }

    public String getItemCode() {
        return itemCode;
    }

    public int getYear() {
        return year;
    }
}
