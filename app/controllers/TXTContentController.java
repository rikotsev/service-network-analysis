package controllers;

import data.db.SQLDatabaseDAO;
import gui.api.TXTVisualization;
import gui.business.GuiBusinessFacade;
import gui.html.GraphForm;
import gui.txt.TXTVisualizationFactory;
import models.GraphNode;
import models.TypifiedGraphEdge;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.Set;

public class TXTContentController extends Controller {

    @Inject
    private FormFactory formFactory;

    @Inject
    private SQLDatabaseDAO dbDAO;

    @Inject
    private TXTVisualizationFactory txtVisualizations;

    @Inject
    private GuiBusinessFacade businessFacade;

    public Result specific() {
        final GraphForm frm = formFactory.form(GraphForm.class).bindFromRequest().get();

        if(!businessFacade.validateForm(frm)) {
            throw new RuntimeException("Could not generate XML for an invalid form!");
        }

        final Set<GraphNode> nodes = dbDAO.readNodes();
        final Set<TypifiedGraphEdge> edges = businessFacade.consume(frm);
        final TXTVisualization content = txtVisualizations.gml(nodes, edges);

        return ok(content.render());
    }

}
