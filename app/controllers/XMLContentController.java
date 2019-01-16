package controllers;

import analysis.analyzers.AnalysisFacade;
import data.db.DatabaseFilter;
import data.db.SQLDatabaseDAO;
import gui.api.XMLVisualization;
import gui.business.GuiBusinessFacade;
import gui.html.GraphForm;
import gui.xml.XMLVisualizationsFactory;
import models.AnalyzedElementsContainer;
import models.GraphNode;
import models.PredefinedValuesContainer;
import models.TypifiedGraphEdge;
import play.Logger;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.Set;

public class XMLContentController extends Controller {

    @Inject
    private FormFactory formFactory;

    @Inject
    private SQLDatabaseDAO dbDAO;

    @Inject
    private XMLVisualizationsFactory xmlVisualizations;

    @Inject
    private GuiBusinessFacade businessFacade;

    @Inject
    private AnalysisFacade analysisFacade;

    public Result simpleGraph() {
        final Set<GraphNode> nodes = dbDAO.readNodes();
        final DatabaseFilter filter = DatabaseFilter.builder(PredefinedValuesContainer.ItemCode.S200, 2012)
                .finalValueBiggerThan(600.00).build();
        final Set<TypifiedGraphEdge> edges = dbDAO.readEdges(filter);
        final XMLVisualization content = xmlVisualizations.gexf(nodes, edges);

        return ok(content.render());
    }

    public Result gexfGraph() {
        final GraphForm frm = formFactory.form(GraphForm.class).bindFromRequest().get();

        if(!businessFacade.validateForm(frm)) {
            throw new RuntimeException("Could not generate XML for an invalid form!");
        }

        AnalyzedElementsContainer result;

        switch(frm.getActionTypeAsEnum()) {
            case VISUALIZATION_SIMPLE:
                result = analysisFacade.simpleVisualization(frm);
                break;
            case VISUALIZATION_WITH_DEGREES:
                result = analysisFacade.degreeNodes(frm);
                break;
            default:
                throw new RuntimeException("Unimplemented action for enum" + frm.getActionType());
        }


        final XMLVisualization content = xmlVisualizations.gexf(result.getNodes(), result.getEdges());

        return ok(content.render());
    }

    public Result graphmlGraph() {
        try {
            final GraphForm frm = formFactory.form(GraphForm.class).bindFromRequest().get();

            if (!businessFacade.validateForm(frm)) {
                throw new RuntimeException("Could not generate XML for an invalid form!");
            }

            final AnalyzedElementsContainer result;

            if(frm.getActionTypeAsEnum().equals(GraphForm.ActionType.IN_CLOSENESS_CENTRALITY) ||
                    frm.getActionTypeAsEnum().equals(GraphForm.ActionType.OUT_CLOSENESS_CENTRALITY) ||
                        frm.getActionTypeAsEnum().equals(GraphForm.ActionType.TRANSITIVITY_AVERAGE)) {
                result = analysisFacade.excludeNodes(frm);
            }
            else if(frm.getActionTypeAsEnum().equals(GraphForm.ActionType.VISUALIZATION_WITH_DEGREES)) {
                result = analysisFacade.simpleVisualization(frm);
            }
            else {
                result = analysisFacade.all(frm);
            }

            final XMLVisualization content = xmlVisualizations.graphml(result.getNodes(), result.getEdges());

            return ok(content.render());
        }
        catch(final Exception e) {
            Logger.error("Failed to generate graphml xml with", e);
            return internalServerError();
        }
    }

}
