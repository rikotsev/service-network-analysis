package analysis.analyzers;

import data.db.DatabaseFilter;
import gui.html.GraphForm;
import models.AnalyzedElementsContainer;
import models.PredefinedValuesContainer;

abstract class Analyzer {

    public abstract AnalyzedElementsContainer analyze(final GraphForm frm);

    protected DatabaseFilter.Builder dbFilterBuilder(final GraphForm frm) {
        final DatabaseFilter.Builder builder = DatabaseFilter.builder(PredefinedValuesContainer.ItemCode.valueOf(frm.getItemCode()), frm.getYear());

        return builder;
    }

}
