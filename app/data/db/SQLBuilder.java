package data.db;

import javax.inject.Singleton;

@Singleton
class SQLBuilder {

    private static String SELECT_STATEMENT = " SELECT id, reported_id, partner_id, flow_type, item_code, year, reported_value, final_value, final_value_methodology, balanced_value FROM %s ";

    private static String SELECT_DIRECTED_STATEMENT = "SELECT id, src, target, item_code, year, balanced_value FROM %s";

    private static String SELECT_AVERAGE = "SELECT AVG(final_value) AS final_value_average FROM %s";

    private static String SELECT_PARTNER_ID = " SELECT partner_id FROM %s ";

    private static String GROUP_BY_PARTNER_ID = " GROUP BY partner_id ";

    private static String SELECT_REPORTED_ID = " SELECT reported_id FROM %s ";

    private static String GROUP_BY_REPORTED_ID = " GROUP BY reported_id ";

    private static String ALL_SUMS = "SELECT sum(final_value) AS final_value_sum, year, item_code FROM %s GROUP BY item_code, year;";

    private static String AND = " AND ";

    private static String WHERE_ITEM_CODE = " item_code = ? ";

    private static String WHERE_YEAR = " year = ? ";

    private static String WHERE_FINAL_VALUE_BIGGER = " final_value > ? ";

    private static String WHERE_BALANCED_VALUE_BIGGER = " balanced_value > ? ";

    private String buildWhere(final DatabaseFilter filter) {
        String where = " WHERE 1=1 ";

        if (filter.itemCode().isPresent()) {
            where = where.concat(AND).concat(WHERE_ITEM_CODE);
        }

        if (filter.year().isPresent()) {
            where = where.concat(AND).concat(WHERE_YEAR);
        }

        if (filter.finalValueBiggerThan().isPresent()) {
            where = where.concat(AND).concat(WHERE_FINAL_VALUE_BIGGER);
        }

        return where;
    }

    private String buildWhereDirected(final DatabaseFilter filter) {
        String where = " WHERE balanced_value IS NOT NULL "; //taking edges with balanced_value = null doesn't make sense!

        if (filter.itemCode().isPresent()) {
            where = where.concat(AND).concat(WHERE_ITEM_CODE);
        }

        if (filter.year().isPresent()) {
            where = where.concat(AND).concat(WHERE_YEAR);
        }

        if (filter.finalValueBiggerThan().isPresent()) {
            where = where.concat(AND).concat(WHERE_BALANCED_VALUE_BIGGER);
        }

        return where;
    }

    public String create(final DatabaseFilter filter) {
        return SELECT_STATEMENT.concat(buildWhere(filter));
    }

    public String createDirected(final DatabaseFilter filter) {
        return SELECT_DIRECTED_STATEMENT.concat(buildWhereDirected(filter));
    }

    public String getAvgStatement(final DatabaseFilter filter) {
        return SELECT_AVERAGE.concat(buildWhere(filter));
    }

    public String getAllSums() {
        return ALL_SUMS;
    }

    public String getPartners(final DatabaseFilter filter) {
        return SELECT_PARTNER_ID.concat(buildWhere(filter)).concat(GROUP_BY_PARTNER_ID);
    }

    public String getReporters(final DatabaseFilter filter) {
        return SELECT_REPORTED_ID.concat(buildWhere(filter)).concat(GROUP_BY_REPORTED_ID);
    }

}
