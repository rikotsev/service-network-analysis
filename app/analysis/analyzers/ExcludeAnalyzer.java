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
import java.util.stream.Collectors;

@Singleton
class ExcludeAnalyzer extends Analyzer {

    @Inject
    private SQLDatabaseDAO dbDAO;

    @Override
    public AnalyzedElementsContainer analyze(GraphForm frm) {

        final DatabaseFilter filter = dbFilterBuilder(frm).finalValueBiggerThan(0.00).build();
        final Set<String> nodeCodesAsPartners = dbDAO.readPartners(filter);
        final Set<String> nodeCodesAsReporters = dbDAO.readReporters(filter);
        final Set<GraphNode> allNodes = dbDAO.readNodes();
        final Set<GraphNode> filteredNodes = allNodes.stream().filter(
                node -> nodeCodesAsPartners.contains(node.getId()) || nodeCodesAsReporters.contains(node.getId())
        ).collect(Collectors.toSet());
        final Set<DirectedGraphEdge> edges = dbDAO.readDirectedEdges(filter);


        return AnalyzedElementsContainer.wrapAround(filteredNodes, edges);
    }
}
