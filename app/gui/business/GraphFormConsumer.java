package gui.business;

import data.db.DatabaseFilter;
import data.db.SQLDatabaseDAO;
import gui.html.GraphForm;
import models.PredefinedValuesContainer;
import models.TypifiedGraphEdge;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Set;

@Singleton
class GraphFormConsumer {

    @Inject
    private SQLDatabaseDAO dbDAO;

    /**
     * Form should be valid
     *
     * @param frm
     * @return
     */
    public synchronized Set<TypifiedGraphEdge> consume(final GraphForm frm) {
        final PredefinedValuesContainer.ItemCode itemCode = PredefinedValuesContainer.ItemCode.valueOf(frm.getItemCode());
        final int year = frm.getYear();
        final DatabaseFilter.Builder filterBuilder = DatabaseFilter.builder(itemCode, year);

        if (frm.getFlowType() != null && !frm.getFlowType().equals(StringUtils.EMPTY)) {
            filterBuilder.flowType(PredefinedValuesContainer.FlowType.valueOf(frm.getFlowType()));
        }

        //TODO implement a smart way to determine it for every year and item code
        filterBuilder.finalValueBiggerThan(600.00);

        final DatabaseFilter filter = filterBuilder.build();
        return dbDAO.readEdges(filter);
    }

}
