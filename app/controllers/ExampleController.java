package controllers;

import data.FileSystemDAO;
import models.GraphNode;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import views.html.example.example;

import java.util.Set;

public class ExampleController extends Controller {


    @Inject
    private FileSystemDAO fsDAO;

    public Result printBasicInfo() {
        return ok();
    }

    public Result printXml() {
        return ok();

    }

    public Result printHtml() {

        final String urlXml = routes.ExampleController.printXml().absoluteURL( request() ).toString();

        return ok( example.render( urlXml ) );

    }

    public Result testFileRead() {
        final Set<GraphNode> nodes = fsDAO.readNodes();
        //final Set<GraphEdge> edges = fsDAO.readEdges();

        final GraphNode sampleNode = nodes.stream().findAny().get();
        Logger.debug("Sample Node Id = " + sampleNode.getId());
        //final GraphEdge sampleEdge = edges.stream().findAny().get();
        //Logger.debug("Sample Edge Reported Id = " + sampleEdge.getReportedId() + " Partner Id = " + sampleEdge.getPartnerId());

        return ok();
    }

}
