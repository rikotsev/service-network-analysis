package analysis;

import com.github.rcaller.rstuff.*;
import gui.html.GraphForm;
import models.GraphStatContainer;
import play.Configuration;
import play.Logger;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class RGraphWrapper {

    public enum Action {
        DEGREES(
                "degreesList",
                "%s <- degree(%s)"
        ),
        IN_CLOSENESS_CENTRALITY(
                "inClosenessCentralityList",
                "%s <- closeness(graph = %s, mode=\"in\", weights = " + WEIGHT_VARIABLE + ", normalize=T)"
        ),
        OUT_CLOSENESS_CENTRALITY(
                "outClosenessCentralityList",
                "%s <- closeness(graph = %s, mode=\"out\", weights = " + WEIGHT_VARIABLE + ", normalize=T)"
        ),
        BETWEENNESS_CENTRALITY(
                "betweennessCentralityList",
                "%s <- betweenness(graph=%s, weights= " + WEIGHT_VARIABLE + ")"
        ),
        EDGE_DENSITY(
                "edgeDensity",
                "%s <- edge_density(%s)"
        ),
        TRANSITIVITY_GLOBAL(
                "transitivityGlobal",
                "%s <- transitivity(graph=%s, weights=" + WEIGHT_VARIABLE +")"
        ),
        TRANSITIVITY_AVERAGE(
                "transitivityAverage",
                "%s <- mean(transitivity(graph=%s, type=\"local\", weights=" + WEIGHT_VARIABLE + "))"),
        PAGE_RANK(
                "prl",
                "%s <- page_rank(graph=%s)"
        ),
        EIGENVECTOR_CENTRALITY(
                "eigenvectorCentrality",
                "%s <- eigen_centrality(graph=%s)"
        ),
        DIAMETER(
                "diameterValue",
                "%s <- diameter(graph=%s, weights=" + WEIGHT_VARIABLE +")"
        );

        Action(final String variableName, final String commandTemplate) {
            this.variableName = variableName;
            this.commandTemplate = commandTemplate;
        }

        final private String variableName;

        final private String commandTemplate;

        public String getVariableName() {
            return variableName;
        }

        public String getFilledCommandTemplate(final String graphVariableName) {
            return String.format(commandTemplate, variableName, graphVariableName);
        }
    }

    private static final String R_INSTALLATION_KEY = "r_language.root_dir";

    private static final String R_EXE_KEY = "r_language.exe";

    private static final String R_SCRIPT_KEY = "r_language.script";

    private static final String VERTICES_NAME_VARIABLE = "verticesNames";

    private static final String GRAPH_VARIABLE = "igraphGraphInstance";

    private static final String WEIGHT_VARIABLE = "properEdgeWeights";

    private String rInstallation;

    private String rExe;

    private String rScript;

    private List<Action> actions;

    private RCaller rCaller;

    private RCode rCode;

    private String tempFileName;

    private String xmlUrl;

    private GraphForm frm;

    private RGraphWrapper(final Configuration config) {

        this.rInstallation = config.getString(R_INSTALLATION_KEY);
        this.rExe = config.getString(R_EXE_KEY);
        this.rScript = config.getString(R_SCRIPT_KEY);

        final RCallerOptions options = RCallerOptions.create(rInstallation + rScript,
                rInstallation + rExe,
                FailurePolicy.RETRY_1,
                3000l,
                1000l,
                RProcessStartUpOptions.create());

        rCaller = RCaller.create(options);
        rCode = RCode.create();
        tempFileName = UUID.randomUUID().toString() + ".txt";
        actions = new ArrayList<>();
    }

    public static RGraphWrapper get(final String xmlUrl, final GraphForm frm, final Configuration config) {
        final RGraphWrapper wrapper = new RGraphWrapper(config);
        wrapper.xmlUrl = xmlUrl;
        wrapper.frm = frm;

        return wrapper;
    }

    public RGraphWrapper action(final Action action) {
        this.actions.add(action);
        return this;
    }


    private List<Integer> intValues(final Action action) {
        return IntStream.of(rCaller.getParser().getAsIntArray(action.getVariableName())).mapToObj(i -> Integer.valueOf(i)).collect(Collectors.toList());
    }

    private List<Double> dblValues(final Action action) {
        return DoubleStream.of(rCaller.getParser().getAsDoubleArray(action.getVariableName())).mapToObj(d -> Double.valueOf(d)).collect(Collectors.toList());
    }

    private <T> Map<String, T> buildMap(final List<T> elements) {
        final List<String> keys = Arrays.asList(rCaller.getParser().getAsStringArray(VERTICES_NAME_VARIABLE));
        final Map<String, T> result = new LinkedHashMap<>(); //We might sort it later ;)

        for (int i = 0; i < keys.size(); i++) {
            result.put(keys.get(i), elements.get(i));
        }

        return result;
    }

    public RGraphWrapper calculate() {

        rCode.clear();
        libraries();
        fileUtils();
        verticesList();

        actions.stream().forEach(action -> {
            rCode.addRCode(action.getFilledCommandTemplate(GRAPH_VARIABLE));
        });

        final String resultVariables = actions.stream().map(action -> {
            if(action.equals(Action.EIGENVECTOR_CENTRALITY) || action.equals(Action.PAGE_RANK)) {
                return String.format("%s=%s$vector", action.getVariableName(), action.getVariableName());
            }
            else {
                return String.format("%s=%s", action.getVariableName(), action.getVariableName());
            }
        }).collect(Collectors.joining(","));

        final String resultVariablesAndNames = String.format("%s=%s,%s", VERTICES_NAME_VARIABLE, VERTICES_NAME_VARIABLE, resultVariables);

        final String resultLine = String.format("result <- list(%s)", resultVariablesAndNames);

        rCode.addRCode(resultLine);

        rCaller.setRCode(rCode);
        rCaller.runAndReturnResult("result");

        //Logger.debug("Code = {}", rCode.getCode());

        return this;
    }

    public GraphStatContainer container() {
        final GraphStatContainer.Builder builder = GraphStatContainer.builder();

        actions.stream().forEach(action -> {
            switch (action) {
                case DEGREES:
                    builder.degrees(buildMap(intValues(action)));
                    break;
                case IN_CLOSENESS_CENTRALITY:
                    builder.inClosenessCentrality(buildMap(dblValues(action)));
                    break;
                case OUT_CLOSENESS_CENTRALITY:
                    builder.outClosenessCentrality(buildMap(dblValues(action)));
                    break;
                case BETWEENNESS_CENTRALITY:
                    builder.betweennessCentrality(buildMap(dblValues(action)));
                    break;
                case PAGE_RANK:
                    builder.pageRank(buildMap(dblValues(action)));
                    break;
                case EIGENVECTOR_CENTRALITY:
                    builder.eigenvectorCentrality(buildMap(dblValues(action)));
                    break;
                case EDGE_DENSITY:
                    builder.edgeDensity(dblValues(action).get(0));
                    break;
                case TRANSITIVITY_GLOBAL:
                    builder.transivityGlobal(dblValues(action).get(0));
                    break;
                case TRANSITIVITY_AVERAGE:
                    builder.transivityAverage(dblValues(action).get(0));
                    break;
                case DIAMETER:
                    builder.diameter(dblValues(action).get(0));
                    break;
                default:
                    throw new RuntimeException("Unhandled action = " + action.toString());
            }
        });

        return builder.build();
    }


    private void libraries() {
        rCode.addRCode("library(\"httr\")");
        rCode.addRCode("library(\"igraph\")");
    }

    private void fileUtils() {
        final String urlLine = String.format("url <- \"%s\"", xmlUrl);
        rCode.addRCode(urlLine);
        final String bodyLine = String.format("body <- list(itemCode=\"%s\", year=%d, flowType=\"%s\", actionType=\"%s\")",
                frm.getItemCode(),
                frm.getYear(),
                frm.getFlowType(),
                frm.getActionType());
        rCode.addRCode(bodyLine);
        rCode.addRCode("result <- POST(url, body = body, encode=\"form\")");
        rCode.addRCode("bin <- content(result, \"raw\")");
        final String writeFileLine = String.format("writeBin(bin, \"%s\")", tempFileName);
        rCode.addRCode(writeFileLine);
        final String readFileLine = String.format("%s <- read_graph(\"%s\", format=\"graphml\")", GRAPH_VARIABLE, tempFileName);
        rCode.addRCode(readFileLine);
        final String properWeightLine = String.format("%s <- 1/(E(%s)$weight/mean(E(%s)$weight))", WEIGHT_VARIABLE, GRAPH_VARIABLE, GRAPH_VARIABLE);
        rCode.addRCode(properWeightLine);
        final String removeFileLine = String.format("file.remove(\"%s\")", tempFileName);
        rCode.addRCode(removeFileLine);
    }

    private void verticesList() {
        final String codeLine = String.format("%s <- V(%s)$code", VERTICES_NAME_VARIABLE, GRAPH_VARIABLE);
        rCode.addRCode(codeLine);
    }

}
