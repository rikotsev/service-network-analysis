package controllers;

import analysis.RGraphWrapper;
import data.db.SQLDatabaseDAO;
import gui.api.HTMLVisualization;
import gui.business.GuiBusinessFacade;
import gui.html.GraphForm;
import gui.html.HTMLVisualizationsFactory;
import models.GraphNode;
import models.GraphStatContainer;
import play.Configuration;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.Set;

public class GraphGuiController extends Controller {

    @Inject
    private SQLDatabaseDAO dbDAO;

    @Inject
    private FormFactory formFactory;

    @Inject
    private HTMLVisualizationsFactory htmlVisualizations;

    @Inject
    private GuiBusinessFacade businessFacade;

    @Inject
    private Configuration config;

    public Result simpleGraph() {

        final String url = routes.XMLContentController.simpleGraph().toString();
        final HTMLVisualization pageContent = htmlVisualizations.simpleGraph(url);
        final HTMLVisualization page = htmlVisualizations.page(pageContent);

        return ok(page.render());
    }

    /**
     * <b>GET</b>
     * Renders the form
     *
     * @return
     */
    public Result graphForm() {

        final String url = routes.GraphGuiController.visualizeGraph().toString();
        final HTMLVisualization form = htmlVisualizations.graphFormHTML(url);
        final HTMLVisualization page = htmlVisualizations.pageWithStandardNavbar(form);

        return ok(page.render());
    }

    /**
     * <b>POST</b>
     * Renders the visualization of the graph selected from the form
     *
     * @return
     */
    public Result visualizeGraph() {
        final GraphForm graphForm = formFactory.form(GraphForm.class).bindFromRequest().get();

        if (!businessFacade.validateForm(graphForm)) {
            return redirect(routes.GraphGuiController.graphForm());
        }

        graphForm.setVizType(GraphForm.VisualizationType.JAVASCRIPT);

        HTMLVisualization frame;

        switch(graphForm.getActionTypeAsEnum()) {
            case VISUALIZATION_SIMPLE:
            case VISUALIZATION_WITH_DEGREES:
                final String urlViz = routes.XMLContentController.gexfGraph().toString();
                frame = htmlVisualizations.graphDetailsFrameForVisualization(urlViz, graphForm);
                break;
            case DEGREES:
            case DIAMETER:
            case IN_CLOSENESS_CENTRALITY:
            case OUT_CLOSENESS_CENTRALITY:
            case BETWEENNESS_CENTRALITY:
            case TRANSITIVITY_GLOBAL:
            case TRANSITIVITY_AVERAGE:
            case EIGENVECTOR_CENTRALITY:
            case EDGE_DENSITY:
            case PAGE_RANK:
                final String urlData = routes.GraphGuiController.graphData().toString();
                frame = htmlVisualizations.graphDetailsFrameForData(urlData, graphForm);
                break;
            default:
                throw new RuntimeException("Unimplemented visualizaton for " +  graphForm.getActionType());
        }

        final HTMLVisualization page = htmlVisualizations.pageWithStandardNavbar(frame);

        return ok(page.render());
    }

    /**
     * <b>POST</b>
     * Renders the async graph data
     * @return
     */
    public Result graphData() {
        final GraphForm graphForm = formFactory.form(GraphForm.class).bindFromRequest().get();

        if(!businessFacade.validateForm(graphForm)) {
            throw new RuntimeException("Cannot generate data for an invalid form!");
        }

        final String xmlUrl = routes.XMLContentController.graphmlGraph().absoluteURL(request());

        final RGraphWrapper wrapper = RGraphWrapper.get(xmlUrl, graphForm, config);

        switch(graphForm.getActionTypeAsEnum()) {
            case DEGREES:
                wrapper.action(RGraphWrapper.Action.DEGREES);
                break;
            case IN_CLOSENESS_CENTRALITY:
                wrapper.action(RGraphWrapper.Action.IN_CLOSENESS_CENTRALITY);
                break;
            case OUT_CLOSENESS_CENTRALITY:
                wrapper.action(RGraphWrapper.Action.OUT_CLOSENESS_CENTRALITY);
                break;
            case BETWEENNESS_CENTRALITY:
                wrapper.action(RGraphWrapper.Action.BETWEENNESS_CENTRALITY);
                break;
            case EDGE_DENSITY:
                wrapper.action(RGraphWrapper.Action.EDGE_DENSITY);
                break;
            case TRANSITIVITY_GLOBAL:
                wrapper.action(RGraphWrapper.Action.TRANSITIVITY_GLOBAL);
                break;
            case TRANSITIVITY_AVERAGE:
                wrapper.action(RGraphWrapper.Action.TRANSITIVITY_AVERAGE);
                break;
            case DIAMETER:
                wrapper.action(RGraphWrapper.Action.DIAMETER);
                break;
            case EIGENVECTOR_CENTRALITY:
                wrapper.action(RGraphWrapper.Action.EIGENVECTOR_CENTRALITY);
                break;
            case PAGE_RANK:
                wrapper.action(RGraphWrapper.Action.PAGE_RANK);
                break;
            default:
                throw new RuntimeException("Unimplemented R functionality for " + graphForm.getActionType());
        }

        wrapper.calculate();

        final GraphStatContainer container = wrapper.container();
        final Set<GraphNode> nodes = dbDAO.readNodes();
        final HTMLVisualization content = htmlVisualizations.graphDetails(container, nodes);

        return ok(content.render());
    }

}
