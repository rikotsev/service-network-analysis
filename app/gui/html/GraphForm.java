package gui.html;

import gui.api.HTMLVisualization;
import models.PredefinedValuesContainer;
import org.apache.commons.lang3.StringUtils;
import play.twirl.api.Html;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import views.html.renders.html.graph_form.graph_form_html;
import views.html.renders.html.graph_form.graph_form_js;

public class GraphForm implements HTMLVisualization {

    public enum ActionType {
        VISUALIZATION_SIMPLE,
        VISUALIZATION_WITH_DEGREES,
        DEGREES,
        IN_CLOSENESS_CENTRALITY,
        OUT_CLOSENESS_CENTRALITY,
        BETWEENNESS_CENTRALITY,
        PAGE_RANK,
        EIGENVECTOR_CENTRALITY,
        EDGE_DENSITY,
        TRANSITIVITY_GLOBAL,
        TRANSITIVITY_AVERAGE,
        DIAMETER
    }

    public enum VisualizationType {
        HTML, JAVASCRIPT;
    }

    private String itemCode;

    private int year;

    private String flowType;

    private String actionType;

    private String postUrl;

    private VisualizationType vizType;

    public GraphForm(final String url) {
        this.postUrl = url;
        this.vizType = VisualizationType.HTML;
    }

    public GraphForm() {
        this.vizType = VisualizationType.JAVASCRIPT;
    }

    @Override
    public Html render() {
        switch(vizType) {
            case HTML:
                return graph_form_html.render(this);
            case JAVASCRIPT:
                return graph_form_js.render( this );
            default:
                throw new RuntimeException("Canno render the graph!");
        }
    }

    /**
     * SIMPLE GETTERS/SETTERS FOR MEMBERS
     **/

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getFlowType() {
        return flowType;
    }

    public void setFlowType(String flowType) {
        this.flowType = flowType;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(final String actionType) {
        this.actionType = actionType;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }
    /** END SIMPLE GETTERS/SETTERS FOR MEMBERS **/

    /**
     * METHODS USED TO VISUALIZE VALUES
     **/

    public List<String> allItemCodes() {
        return Arrays.asList(PredefinedValuesContainer.ItemCode.values()).stream().map(en -> en.toString()).collect(Collectors.toList());
    }

    public List<Integer> allYears() {
        return IntStream.of(PredefinedValuesContainer.years).mapToObj( i -> Integer.valueOf(i) ).collect(Collectors.toList());
    }

    public List<String> flowTypes() {
        final List<String> values = Arrays.asList(PredefinedValuesContainer.FlowType.values()).stream().map( en -> en.toString() ).collect(Collectors.toList());
        values.add(0, StringUtils.EMPTY);

        return values;
    }

    public List<String> actionTypes() {
        final List<String> values = Arrays.asList(ActionType.values()).stream().map( vl -> vl.toString() ).collect(Collectors.toList());

        return values;
    }

    /** END METHODS USED TO VISUALIZE VALUES **/

    /** METHODS TO ENSURE USABILITY **/

    public void setVizType(VisualizationType vizType) {
        this.vizType = vizType;
    }

    public VisualizationType getVizType() {
        return vizType;
    }

    public ActionType getActionTypeAsEnum() {
        return ActionType.valueOf(actionType);
    }

    /** END METHODS TO ENSURE RE-USABILITY **/
}
