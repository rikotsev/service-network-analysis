package controllers;

import gui.api.HTMLVisualization;
import gui.business.GuiBusinessFacade;
import gui.html.HTMLVisualizationsFactory;
import play.mvc.*;

import javax.inject.Inject;
import java.util.Map;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    @Inject
    private HTMLVisualizationsFactory htmlVisualizations;

    @Inject
    private GuiBusinessFacade businessFacade;

    /**
     * <b>GET</b>
     * Renders only the frame
     * @return
     */
    public Result dashboard() {

        final String url = routes.HomeController.sumStatistics().toString();
        final HTMLVisualization content = htmlVisualizations.dashboard(url);
        final HTMLVisualization page = htmlVisualizations.pageWithStandardNavbar(content);

        return ok(page.render());
    }

    /**
     * <b>GET</b>
     * Renders an asynchronous element
     * @return
     */
    public Result sumStatistics() {

        final Map<String, Double> data = businessFacade.sumStatisticsData();
        final HTMLVisualization content = htmlVisualizations.sumStatistics(data);

        return ok(content.render());
    }

}
