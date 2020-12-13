package graph;

import org.graphstream.graph.Element;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.List;

public class GraphBuilder {
    private final Graph graph;
    private final List<String> nodeList;
    private final List<String> edgeList;
    private final String styleSheet;

    public GraphBuilder(List<String> nodeList, List<String> edgeList) {
        this.graph = new SingleGraph("graph");
        graph.setAutoCreate(true);
        graph.setStrict(false);

        this.nodeList = nodeList;
        this.edgeList = edgeList;

        this.styleSheet = "graph {\n" +
                "\tpadding: 30px;\n}\n" +
                "node {\n" +
                "\tfill-color: rgb(105, 230, 255);\n" +
                "\tsize: 35px;\n" +
                "\tstroke-mode: plain;\n" +
                "\tstroke-color: #000;\n" +
                "\tstroke-width: 2px;\n" +
                "\ttext-size: 20px;\n" +
                "}\n" +
                "node:clicked {" +
                "   fill-color: red;" +
                "}" +

                "edge {\n" +
                "    fill-color: rgb(0, 0, 0);\n" +
                "    stroke-mode: plain;\n" +
                "    stroke-width: 3px; \n" +
                "}";
    }

    private void build() {
        for (String nodeStr : nodeList) {
            Element addedNode = graph.addNode(nodeStr);
            addedNode.setAttribute("ui.label", nodeStr);
        }

        for (String edgeStr : edgeList) {
            String from = edgeStr.split("-")[0];
            String to = edgeStr.split("-")[1];

            graph.addEdge(edgeStr, from, to);
        }


        this.graph.setAttribute("ui.stylesheet", styleSheet);
    }

    public Graph getGraph() {
        build();
        return this.graph;
    }
}
