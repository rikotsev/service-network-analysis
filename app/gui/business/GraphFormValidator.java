package gui.business;

import gui.html.GraphForm;
import models.PredefinedValuesContainer;
import org.apache.commons.lang.StringUtils;
import play.Logger;

import javax.inject.Singleton;
import java.util.Arrays;
import java.util.stream.IntStream;

@Singleton
class GraphFormValidator {

    public boolean validateForm(final GraphForm graphForm) {

        try {
            PredefinedValuesContainer.ItemCode.valueOf(graphForm.getItemCode());
            final boolean flowTypeCheck = checkFlowType(graphForm);
            final boolean yearsCheck = checkYears(graphForm);
            GraphForm.ActionType.valueOf(graphForm.getActionType());

            return (flowTypeCheck && yearsCheck);
        } catch (final IllegalArgumentException e) {
            Logger.warn("Failed in the validation with", e);
            return false;
        }

    }

    private boolean checkFlowType(final GraphForm graphForm) throws IllegalArgumentException {
        if (graphForm.getFlowType() != null && !graphForm.getFlowType().equals(StringUtils.EMPTY)) {
            PredefinedValuesContainer.FlowType.valueOf(graphForm.getFlowType());
            return true;
        } else if (graphForm.getFlowType() != null && graphForm.getFlowType().equals(StringUtils.EMPTY)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkYears(final GraphForm graphForm) {
        return IntStream.of(PredefinedValuesContainer.years).anyMatch( i -> i == graphForm.getYear());
    }

}
