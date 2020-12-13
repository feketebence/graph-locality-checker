package app;

import com.beust.jcommander.JCommander;
import graph.GraphBuilder;
import org.apache.commons.math3.util.CombinatoricsUtils;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.paukov.combinatorics3.Generator;
import org.paukov.combinatorics3.IGenerator;
import parser.Extractor;
import parser.FileReader;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        System.setProperty("org.graphstream.ui", "swing");

        // Parsing command line args
        Args jArgs = new Args();
        JCommander cmd = JCommander.newBuilder()
                .addObject(jArgs)
                .build();

        cmd.parse(args);

        // Reading graph nodes and edges from input file
        FileReader reader = new FileReader(jArgs.getInputFilePath());
        String input = reader.readFile();

        System.out.println("Input file: " + jArgs.getInputFilePath() + "\n");

        // extracting nodes
        List<String> nodeListStr = Extractor.getNodeList(input);

        // extracting edges
        List<String> edgeListStr = Extractor.getEdgeList(input);

        GraphBuilder builder = new GraphBuilder(nodeListStr, edgeListStr);
        Graph graph = builder.getGraph();

        graph.display();

        int posCount = 0;
        int negCount = 0;

        // filter nodes based on degree (degree >= 2)
        ArrayList<Node> nodes = new ArrayList<>();
        for (String nodeStr : nodeListStr) {
            Node selectedNode = graph.getNode(nodeStr);
            System.out.println("Node " + nodeStr + "'s degree: " +selectedNode.getDegree());

            if(selectedNode.getDegree() >= 2) {
                nodes.add(selectedNode);
            }
        }
        System.out.println();

        for (Node node : nodes) {
            // create a list with the neighbours of the current node
            ArrayList<Node> neighbours = new ArrayList<>();
            node.neighborNodes().forEach(e -> neighbours.add(e));

            // generate the combinations
            IGenerator<List<Node>> pairs = Generator.combination(neighbours)
                    .simple(2);

            System.out.print("Pairs of node " + node.getId() + "'s neighbours: ");

            // for every pair check if edge exists
            for(List<Node> pair : pairs) {
                if (pair.get(0).hasEdgeBetween(pair.get(1))) {
                    System.out.print("+{" + pair.get(0) + " " + pair.get(1) + "}, ");
                    posCount++;
                } else {
                    System.out.print("-{" + pair.get(0) + " " + pair.get(1) + "}, ");
                    negCount++;
                }
            }
            System.out.println();
        }
        System.out.println("\n\tnumber of positive cases = " + posCount + "\n\tnumber of negative cases = " + negCount);

        int n = nodeListStr.size();
        long possibleEdgeCount = CombinatoricsUtils.factorial(n) / (2 * CombinatoricsUtils.factorial(n - 2));
        System.out.println("\n\t number of nodes = " + n + "\n\t number of edges = " + edgeListStr.size() + "\n\t number of possible edges = " + possibleEdgeCount);

        double probabilityRand3thEdge = (posCount * 1.0 - 2)/(possibleEdgeCount - 2);
        double probabilityNonRand3thEdge = posCount * 1.0 / (posCount + negCount);

        System.out.println("\n\tP(random) = " + probabilityRand3thEdge);
        System.out.println("\t P(social) = " + probabilityNonRand3thEdge);

        if (probabilityNonRand3thEdge > probabilityRand3thEdge) {
            System.out.println("\nP(social) > P(random)");
            System.out.println(" => Graph has locality, it represents a social-network graph.");
        } else {
            System.out.println("\nP(social) < P(random)");
            System.out.println("\n => Graph does not exhibit locality.");
        }

    }

}
