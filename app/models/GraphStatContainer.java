package models;

import java.util.Map;
import java.util.Optional;

public class GraphStatContainer {

    private Map<String, Integer> degrees;

    private Map<String, Double> inClosenessCentrality;

    private Map<String, Double> outClosenessCentrality;

    private Map<String, Double> betweennessCentrality;

    private Map<String, Double> pageRank;

    private Map<String, Double> eigenvectorCentrality;

    private Double edgeDensity;

    private Double transivityGlobal;

    private Double transivityAverage;

    private Double diameter;

    private GraphStatContainer() {}

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Map<String, Integer> degrees = null;

        private Map<String, Double> inClosenessCentrality = null;

        private Map<String, Double> outClosenessCentrality = null;

        private Map<String, Double> betweennessCentrality = null;

        private Map<String, Double> pageRank = null;

        private Map<String, Double> eigenvectorCentrality = null;

        private Double edgeDensity = null;

        private Double transivityGlobal = null;

        private Double transivityAverage = null;

        private Double diameter = null;

        private Builder() {}

        public Builder degrees(final Map<String, Integer> data) {
            this.degrees = data;
            return this;
        }

        public Builder inClosenessCentrality(final Map<String, Double> data) {
            this.inClosenessCentrality = data;
            return this;
        }

        public Builder outClosenessCentrality(final Map<String, Double> data) {
            this.outClosenessCentrality = data;
            return this;
        }

        public Builder betweennessCentrality(final Map<String, Double> data) {
            this.betweennessCentrality = data;
            return this;
        }

        public Builder pageRank(final Map<String, Double> data) {
            this.pageRank = data;
            return this;
        }

        public Builder eigenvectorCentrality(final Map<String, Double> data) {
            this.eigenvectorCentrality = data;
            return this;
        }

        public Builder edgeDensity(final Double data) {
            this.edgeDensity = data;
            return this;
        }

        public Builder transivityGlobal(final Double data) {
            this.transivityGlobal = data;
            return this;
        }

        public Builder transivityAverage(final Double data) {
            this.transivityAverage = data;
            return this;
        }

        public Builder diameter(final Double data) {
            this.diameter = data;
            return this;
        }

        public GraphStatContainer build() {
            final GraphStatContainer result = new GraphStatContainer();
            result.degrees = this.degrees;
            result.inClosenessCentrality = this.inClosenessCentrality;
            result.outClosenessCentrality = this.outClosenessCentrality;
            result.betweennessCentrality = this.betweennessCentrality;
            result.pageRank = this.pageRank;
            result.eigenvectorCentrality = this.eigenvectorCentrality;
            result.edgeDensity = this.edgeDensity;
            result.transivityGlobal = this.transivityGlobal;
            result.transivityAverage = this.transivityAverage;
            result.diameter = this.diameter;

            return result;
        }
    }

    public Optional<Map<String, Integer>> degrees() {
        return Optional.ofNullable(degrees);
    }

    public Optional<Map<String, Double>> inClosenessCentrality() {
        return Optional.ofNullable(inClosenessCentrality);
    }

    public Optional<Map<String, Double>> outClosenessCentrality() {
        return Optional.ofNullable(outClosenessCentrality);
    }

    public Optional<Map<String, Double>> betweennessCentrality() {
        return Optional.ofNullable(betweennessCentrality);
    }

    public Optional<Map<String, Double>> pageRank() {
        return Optional.ofNullable(pageRank);
    }

    public Optional<Map<String, Double>> eigenvectorCentrality() {return Optional.ofNullable(eigenvectorCentrality);}

    public Optional<Double> edgeDensity() {
        return Optional.ofNullable(edgeDensity);
    }

    public Optional<Double> transivityGlobal() {
        return Optional.ofNullable(transivityGlobal);
    }

    public Optional<Double> transivityAverage() {
        return Optional.ofNullable(transivityAverage);
    }

    public Optional<Double> diameter() {return Optional.ofNullable(diameter);}

    @Override
    public String toString() {
        String result = "";

        if(degrees().isPresent()) {
            result = result + degrees.toString();
        }

        if(inClosenessCentrality().isPresent()) {
            result = result + inClosenessCentrality.toString();
        }

        if(outClosenessCentrality().isPresent()) {
            result = result + outClosenessCentrality.toString();
        }

        if(betweennessCentrality().isPresent()) {
            result = result + betweennessCentrality.toString();
        }

        if(pageRank().isPresent()) {
            result = result + pageRank.toString();
        }

        if(edgeDensity().isPresent()) {
            result = result + edgeDensity.toString();
        }

        if(transivityGlobal().isPresent()) {
            result = result + transivityGlobal.toString();
        }

        if(transivityAverage().isPresent()) {
            result = result + transivityAverage.toString();
        }

        return result;

    }
}
