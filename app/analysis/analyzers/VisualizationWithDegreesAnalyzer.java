package analysis.analyzers;

import analysis.RGraphWrapper;
import data.db.SQLDatabaseDAO;
import gui.html.GraphForm;
import models.AnalyzedElementsContainer;
import models.GraphStatContainer;
import play.Configuration;
import play.mvc.Http;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;

@Singleton
class VisualizationWithDegreesAnalyzer extends Analyzer {

    @Inject
    private Configuration config;

    @Inject
    private SimpleVisualizationAnalyzer simpleAnalyzer;

    @Override
    public AnalyzedElementsContainer analyze(final GraphForm frm) {
        final Map<String, Integer> countryCodeWithDegrees = calculateDegreeResult(frm);
        final AnalyzedElementsContainer result = simpleAnalyzer.analyze(frm);
        result.getNodes().stream().forEach( node -> {
            node.setSize( countryCodeWithDegrees.get(node.getId()) );
        } );

        return result;
    }

    private Map<String, Integer> calculateDegreeResult(final GraphForm frm) {
        final String xmlUrl = controllers.routes.XMLContentController.graphmlGraph().absoluteURL(Http.Context.current().request());
        final GraphStatContainer degreeResult = RGraphWrapper.get(xmlUrl, frm, config)
                .action( RGraphWrapper.Action.DEGREES )
                .calculate()
                .container();

        if(degreeResult.degrees().isPresent()) {
            return degreeResult.degrees().get();
        }
        else {
            throw new RuntimeException("Cannot visualize graph with degrees because cannot calculate degrees!");
        }
    }
}
