package gui.business;

import gui.html.GraphForm;
import models.TypifiedGraphEdge;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;
import java.util.Set;

@Singleton
public class GuiBusinessFacade {

    @Inject
    private SumStatisticsBusiness sumStatisticsBusiness;

    @Inject
    private GraphFormValidator graphFormValidator;

    @Inject
    private GraphFormConsumer graphFormConsumer;

    public Map<String, Double> sumStatisticsData() {
        return sumStatisticsBusiness.getData();
    }

    public boolean validateForm(final GraphForm graphForm) {
        return graphFormValidator.validateForm(graphForm);
    }

    public Set<TypifiedGraphEdge> consume(final GraphForm graphForm) { return graphFormConsumer.consume(graphForm); }

}
