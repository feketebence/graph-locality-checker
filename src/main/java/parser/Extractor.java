package parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Extractor {

    public static String extract(String input, String patternString) {
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(input);

        String result = "";

        if (matcher.find()) {
            result = matcher.group(0);
        }

        return result;
    }

    public static List<String> getNodeList(String input) {
        String nodesStr = extract(input, "^([A-Z]\\n)+");
        List<String> nodeList = new ArrayList<>();

        String[] split = nodesStr.split("\n");

        Collections.addAll(nodeList, split);

        return nodeList;
    }

    public static List<String> getEdgeList(String input) {
        String edgeStr = extract(input, "([A-Z]-[A-Z](\n|))+");
        List<String> edgeList = new ArrayList<>();

        String[] split = edgeStr.split("\n");

        Collections.addAll(edgeList, split);

        return edgeList;
    }
}
