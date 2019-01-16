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
class AllAnalyzer extends Analyzer {

    @Inject
    private SQLDatabaseDAO dbDAO;

    @Override
    public AnalyzedElementsContainer analyze(GraphForm frm) {
        final Set<GraphNode> nodes = dbDAO.readNodes();
        final DatabaseFilter filter = dbFilterBuilder(frm).finalValueBiggerThan(0.00).build();
        final Set<DirectedGraphEdge> edges = dbDAO.readDirectedEdges(filter);

        return AnalyzedElementsContainer.wrapAround(nodes, edges);
    }
}
