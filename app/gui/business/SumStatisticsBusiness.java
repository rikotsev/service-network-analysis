package gui.business;

import data.db.DatabaseFilter;
import data.db.SQLDatabaseDAO;
import models.AggregateResult;
import models.PredefinedValuesContainer;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Singleton
class SumStatisticsBusiness {

    private static String KEY_TEMPLATE = "%s_%d";

    @Inject
    private SQLDatabaseDAO dbDAO;

    public Map<String, Double> getData() {

        final Set<AggregateResult> results = dbDAO.readAllSums();
        final Map<String, Double> data = new ConcurrentHashMap<>();

        results.parallelStream().forEach(result -> {
            final String key = String.format(KEY_TEMPLATE, result.getItemCode(), result.getYear());

            data.put(key, result.getValue());
        });


        return data;

    }

}
