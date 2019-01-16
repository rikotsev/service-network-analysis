package analysis.analyzers;

import data.db.DatabaseFilter;
import data.db.SQLDatabaseDAO;
import gui.html.GraphForm;
import models.AnalyzedElementsContainer;
import models.DirectedGraphEdge;
import models.GraphNode;
import models.TypifiedGraphEdge;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Set;

@Singleton
class SimpleVisualizationAnalyzer extends Analyzer {

    @Inject
    private SQLDatabaseDAO db;

    @Override
    public AnalyzedElementsContainer analyze(final GraphForm frm) {

        final Set<GraphNode> nodes = db.readNodes();
        final DatabaseFilter dbFilterForAverage = dbFilterBuilder(frm).build();
        final double averageEdgeValue = db.readAverage(dbFilterForAverage);

        final DatabaseFilter.Builder mainDbFilterBuilder = dbFilterBuilder(frm);
        mainDbFilterBuilder.finalValueBiggerThan(2.00 * averageEdgeValue);
        final Set<DirectedGraphEdge> edges = db.readDirectedEdges(mainDbFilterBuilder.build());

        return AnalyzedElementsContainer.wrapAround(nodes, edges);
    }

}
