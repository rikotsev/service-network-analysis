package data.db;

import models.PredefinedValuesContainer;

import java.util.Optional;
import java.util.stream.IntStream;

public class DatabaseFilter {

    private PredefinedValuesContainer.ItemCode itemCode;

    private PredefinedValuesContainer.FlowType flowType;

    private PredefinedValuesContainer.FinalValueMethodology finalValueMethodology;

    private Integer year;

    private Double finalValueBiggerThan;

    private DatabaseFilter() {
    }

    public static Builder builder(final PredefinedValuesContainer.ItemCode itemCode, final int year) {
        return new Builder(itemCode, year);
    }

    public static class Builder {

        final private PredefinedValuesContainer.ItemCode itemCode;

        final private int year;

        private PredefinedValuesContainer.FlowType flowType = null;

        private PredefinedValuesContainer.FinalValueMethodology finalValueMethodology = null;

        private Double finalValueBiggerThan = null;

        private Builder(final PredefinedValuesContainer.ItemCode itemCode, final int year) {
            this.itemCode = itemCode;
            this.year = year;
        }

        public Builder flowType(final PredefinedValuesContainer.FlowType flowType) {
            this.flowType = flowType;
            return this;
        }

        public Builder finalValueMethodology(final PredefinedValuesContainer.FinalValueMethodology finalValueMethodology) {
            this.finalValueMethodology = finalValueMethodology;
            return this;
        }

        public Builder finalValueBiggerThan(final Double value) {
            this.finalValueBiggerThan = value;
            return this;
        }

        public DatabaseFilter build() {

            if ( !IntStream.of( PredefinedValuesContainer.years ).anyMatch( val -> val == this.year ) ) {
                throw new IllegalArgumentException("The year " + Integer.toString(this.year) + " is not available in the database!");
            }

            final DatabaseFilter dbFilter = new DatabaseFilter();
            dbFilter.itemCode = this.itemCode;
            dbFilter.year = this.year;
            dbFilter.flowType = this.flowType;
            dbFilter.finalValueMethodology = this.finalValueMethodology;
            dbFilter.finalValueBiggerThan = this.finalValueBiggerThan;

            return dbFilter;

        }

    }

    public Optional<PredefinedValuesContainer.ItemCode> itemCode() {
        return Optional.ofNullable(itemCode);
    }

    public Optional<PredefinedValuesContainer.FlowType> flowType() {
        return Optional.ofNullable(flowType);
    }

    public Optional<PredefinedValuesContainer.FinalValueMethodology> finalValueMethodology() {
        return Optional.ofNullable(finalValueMethodology);
    }

    public Optional<Integer> year() {
        return Optional.ofNullable(year);
    }

    public Optional<Double> finalValueBiggerThan() {
        return Optional.ofNullable( this.finalValueBiggerThan );
    }
}
