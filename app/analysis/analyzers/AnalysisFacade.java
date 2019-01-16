package analysis.analyzers;

import gui.html.GraphForm;
import models.AnalyzedElementsContainer;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AnalysisFacade {

    @Inject
    private ExcludeAnalyzer excludeAnalyzer;

    @Inject
    private AllAnalyzer allAnalyzer;

    @Inject
    private SimpleVisualizationAnalyzer simpleVisualizationAnalyzer;

    @Inject
    private VisualizationWithDegreesAnalyzer degreesAnalyzer;

    public AnalyzedElementsContainer all(final GraphForm frm) {
        return allAnalyzer.analyze(frm);
    }

    public AnalyzedElementsContainer simpleVisualization(final GraphForm frm) {
        return simpleVisualizationAnalyzer.analyze(frm);
    }

    /**
     * Excludes nodes that are not present in the set of edges
     * @param frm
     * @return
     */
    public AnalyzedElementsContainer excludeNodes(final GraphForm frm) {
        return excludeAnalyzer.analyze(frm);
    }

    /**
     * Adjusts the size parameter of nodes based on the degree
     * @param frm
     * @return
     */
    public AnalyzedElementsContainer degreeNodes(final GraphForm frm) {
        return degreesAnalyzer.analyze(frm);
    }

}
