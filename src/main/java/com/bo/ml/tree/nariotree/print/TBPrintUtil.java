package com.bo.ml.tree.nariotree.print;

import com.bo.ml.tree.nariotree.Node;

import java.util.*;

/**
 * TreeBinaryUtil
 * Canal Youtube: MathLogic - Haciendo Fácil Lo Difícil
 * Curso de Árboles: https://www.youtube.com/playlist?list=PLJeMuvKPxpu2UGbpuFrcEAPCYhZzdCpBk
 *
 * @author Marcos Quispe
 * @since 1.0
 */
public class TBPrintUtil {

    public static void print(TBPrint tbPrint) {
        if (tbPrint.getRoot() == null) {
            System.out.println("(Arbol vacío)");
            return;
        }

        setViewedFalse(tbPrint.getRoot());
        int dpt = dpt(tbPrint.getRoot());
        setViewedFalse(tbPrint.getRoot());

        String[] edgesLevels = new String[dpt];
        String[] dataLevels = new String[dpt];
        Arrays.fill(edgesLevels, "");
        Arrays.fill(dataLevels, "");

        Map<String, String> childrenMap = new HashMap<>();
        String spacesChildren = printRec(tbPrint.getRoot(), 0, dataLevels, edgesLevels, null, childrenMap, tbPrint.getRoot());
        dataLevels[0] = spacesChildren;

        for (int i = 0; i < dataLevels.length; i++) {
            //System.out.println("level " + i + ": " + dataLevels[i]);
            if (i > 0)
                System.out.println(edgesLevels[i]);
            System.out.println(dataLevels[i]);
        }
    }

    private static String printRec(Node node, int level, String[] dataLevels, String[] edgesLevels, Node parent, Map<String, String> childrenMap, Node root) {
        if (node == null) // CASO BASE
            return "";

        // VALIDATION CICLO INFINITO
        if (node.isViewed()) {
            if (node.equals(root)) {
                System.err.println("WARN La raiz " + node.getValue() + " esta siendo apuntada por un descendiente");
                System.err.println("WARN Cayo en un bucle infinito, revise los punteros de: " + parent.getValue());
            } else {
                System.err.println("WARN El nodo " + node.getValue() + " tiene varios nodos que lo apuntan");
                System.err.println("WARN Podria caer en un bucle infinito, revise los punteros de: " + parent.getValue());
            }
            return "";
        }

        node.setViewed(true);

        if (node.isLeaf()) { // CASO BASE
            childrenMap.put(node.getId(), "" + node.getValue());
            if (level + 1 < dataLevels.length)
                addSpacesGoDown("" + node.getValue(), level + 1, dataLevels, edgesLevels, "");
            return "" + node.getValue();
        }

        List<String> contentChildren = new ArrayList<>(node.getChildren().size());
        String childrenStr = "";
        int accumulatedSiblings = dataLevels[level].length();
        for (int i = 0; i < node.getChildren().size(); i++) {
            Node child = node.getChildren().get(i);
            String contentChild = printRec(child, level + 1, dataLevels, edgesLevels, node, childrenMap, root);

            contentChildren.add(contentChild);
            System.out.println("node: " + node.getValue() + " ." + contentChild + ".");
            if (i == 0) {
                childrenStr = contentChild;
            } else {
                boolean addSpace = !(contentChildren.get(i - 1).endsWith(" ") || contentChild.startsWith(" "));
                if (addSpace && !child.isLeaf())
                    addOneSpaceGoDown(accumulatedSiblings + childrenStr.length() + 1, level + 2, dataLevels, edgesLevels, "(child): " + child.getValue());
                childrenStr = childrenStr + (addSpace ? " " : "") + contentChild;
            }
        }

        String result = ""+node.getValue();
        try {
            //System.out.println("childrenStr:" + childrenStr);
//            String addSpace = (!dataLevels[level + 1].endsWith(" ") && !childrenStr.startsWith(" ") ? " " : "");
//            addSpace = dataLevels[level + 1].isEmpty() ? "" : addSpace;
//            childrenStr = addSpace + childrenStr;
            dataLevels[level + 1] += childrenStr;
            childrenMap.put(node.getId(), childrenStr);

            result = spaces(childrenStr.length() / 2) + node.getValue();
            result = result + spaces(childrenStr.length() - result.length());

            // reajuste result solo si hijos son dos o mas elementos, para que dicho valor quede lo mejor centrado posible
            int index2 = childrenStr.indexOf(childrenStr.trim());
            String spacesInitial = childrenStr.substring(0, index2);

            int countSpacesInitial = 0;
            if (childrenStr.trim().length() % 2 == 0 && ("" + node.getValue()).length() % 2 == 0) { // ambos pares
                countSpacesInitial = (childrenStr.trim().length() - ("" + node.getValue()).length()) / 2;

            } else if (childrenStr.trim().length() % 2 == 1 && ("" + node.getValue()).length() % 2 == 1) { // ambos impares
                countSpacesInitial = (childrenStr.trim().length() - ("" + node.getValue()).length()) / 2;

            } else if (childrenStr.trim().length() % 2 == 1 && ("" + node.getValue()).length() % 2 == 0) { // hijos impar, padre par
                countSpacesInitial = (childrenStr.trim().length() + 1 - ("" + node.getValue()).length()) / 2;

            } else if (childrenStr.trim().length() % 2 == 0 && ("" + node.getValue()).length() % 2 == 1) { // hijos par, padre impar
                countSpacesInitial = (childrenStr.trim().length() + 1 - ("" + node.getValue()).length()) / 2;
            }
            result = spacesInitial + spaces(countSpacesInitial) + node.getValue();
            result = result + spaces(childrenStr.length() - result.length());

            int indexStartResult = result.indexOf(result.trim());
            int indexEndResult = indexStartResult + (""+node.getValue()).length() - 1;

            // ARISTAS
            int acumulatedEdges = edgesLevels[level + 1].length();
            edgesLevels[level + 1] += spaces(childrenStr.length());

            List<Integer> arrowIndexes = new ArrayList<>(node.getChildren().size());
            for (int i = 0; i < childrenStr.length(); i++) {
                if (childrenStr.charAt(i) != ' ') {
                    String word = "";
                    for (int j = i; j < childrenStr.length(); j++) {
                        if (childrenStr.charAt(j) != ' ') {
                            word += childrenStr.charAt(j);
                        } else
                            break;
                    }
                    //int arrowIndex = (i < indexStartResult ? (indexStartResult + i) / 2 : i);
                    int arrowIndex = i;
                    char arrow = '|';
                    if (i < indexEndResult) {
                        arrowIndex = (indexEndResult + i) / 2;
                        arrow = '/';
                    } else if (i > indexStartResult) {
                        arrowIndex = (indexStartResult + i + word.length() - 1) / 2;
                        if (arrowIndex <= arrowIndexes.get(arrowIndexes.size() - 1))
                            arrowIndex = arrowIndexes.get(arrowIndexes.size() - 1) + 1;
                        //System.out.println("arrowIndex \\: " + arrowIndex + "  indexStartResult: " + indexStartResult + "  i: " + i + "  word: " + word);
                        arrow = '\\';
                    }
                    //System.out.println("arrowIndex: " + arrowIndex);

                    edgesLevels[level + 1] = edgesLevels[level + 1].substring(0, acumulatedEdges + arrowIndex) + arrow + edgesLevels[level + 1].substring(acumulatedEdges + arrowIndex + 1);
                    arrowIndexes.add(arrowIndex);

//                    edgesLevels[level + 1] = edgesLevels[level + 1].substring(0, acumulatedEdges + i) + "|" + edgesLevels[level + 1].substring(acumulatedEdges + i + 1);

                    i += word.length();
                }
            }

            // igualando espacios finales hacia abajo
            for (int i = level + 2; i < dataLevels.length; i++) {
                if (dataLevels[level + 1].length() > dataLevels[i].length()) {
                    int dif = dataLevels[level + 1].length() - dataLevels[i].length();
                    dataLevels[i] += spaces(dif);
                }
                if (edgesLevels[level + 1].length() > edgesLevels[i].length()) {
                    int dif = edgesLevels[level + 1].length() - edgesLevels[i].length();
                    edgesLevels[i] += spaces(dif);
                }
            }

//        if (childrenUniqueValueLeft) {
//            System.out.println("result:" + result);
//            System.out.println();
//        }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

//    private static void recorrerHijos(Node node, int level, String[] dataLevels, String[] edgesLevels, Map<String, String> childrenMap) {
//        if (node == null)
//            return;
//        if (node.isLeaf())
//            return;
//
//        String childrenStr = childrenMap.get(node.getId());
//        int indexStr = dataLevels[level + 1].indexOf(childrenStr);
//        dataLevels[level + 1] = dataLevels[level + 1].substring(0, indexStr) + " " + dataLevels[level + 1].substring(indexStr);
//        edgesLevels[level + 1] = edgesLevels[level + 1].substring(0, indexStr) + " " + edgesLevels[level + 1].substring(indexStr);
//
//        recorrerHijos(node.getLeft(), level + 1, dataLevels, edgesLevels, childrenMap);
//        recorrerHijos(node.getRight(), level + 1, dataLevels, edgesLevels, childrenMap);
//    }

    /**
     * Adiciona espacios hacia abajo para igualar longitudes de cada nivel, tanto en datos como en aristas
     * @param newContent
     * @param level
     * @param dataLevels
     * @param edgesLevels
     */
    private static void addSpacesGoDown(String newContent, int level, String[] dataLevels, String[] edgesLevels, String nodeValueRef) {
        System.out.println("addSpacesGoDown nodeValueRef: " + nodeValueRef + "  newContent: " + newContent + "  level: " + level);
        int lengthToAdd = newContent.trim().length();
        //System.out.println("gd:"+spaceLeft);
        int maxLenght = 0;
        for (int i = level; i < dataLevels.length; i++) {
            maxLenght = Math.max(maxLenght, dataLevels[i].length());
        }
        int maxLenghtEdges = 0;
        for (int i = level; i < edgesLevels.length; i++) {
            maxLenghtEdges = Math.max(maxLenghtEdges, edgesLevels[i].length());
        }

        for (int i = level; i < dataLevels.length; i++) {
            int diff = maxLenght - dataLevels[i].length();
            if (diff > 0)
                dataLevels[i] += spaces(diff); // iguala linea actual con la siguiente
            dataLevels[i] += spaces(lengthToAdd); // adiciona nueva longitud

            diff = maxLenghtEdges - edgesLevels[i].length();
            if (diff > 0)
                edgesLevels[i] += spaces(diff); // iguala linea actual con la siguiente
            edgesLevels[i] += spaces(lengthToAdd); // adiciona nueva longitud
        }
    }

    private static void addOneSpaceGoDown(int index, int level, String[] dataLevels, String[] edgesLevels, String nodeValueRef) {
        System.out.println("addOneSpaceGoDown nodeValueRef: " + nodeValueRef + "  level: " + level + "  index: " + index);
        //System.out.println("gd:"+spaceLeft);
        int maxLenght = 0;
        for (int i = level; i < dataLevels.length; i++) {
            maxLenght = Math.max(maxLenght, dataLevels[i].length());
        }
        int maxLenghtEdges = 0;
        for (int i = level; i < edgesLevels.length; i++) {
            maxLenghtEdges = Math.max(maxLenghtEdges, edgesLevels[i].length());
        }

        for (int i = level; i < dataLevels.length; i++) {
            int diff = maxLenght - dataLevels[i].length();
            if (diff > 0)
                dataLevels[i] += spaces(diff); // iguala linea actual
            dataLevels[i] = dataLevels[i].substring(0, index) // adiciona espacio entre medio
                    + " " + dataLevels[i].substring(index);
            System.out.println("xxx: " + dataLevels[i].substring(index));

            diff = maxLenghtEdges - edgesLevels[i].length();
            if (diff > 0)
                edgesLevels[i] += spaces(diff); // iguala linea actual
            edgesLevels[i] = edgesLevels[i].substring(0, index) // adiciona espacio entre medio
                    + " " + edgesLevels[i].substring(index);
        }
    }

    private static String spaces(int length) {
        String result = "";
        for (int i = 0; i < length; i++) {
            result += " ";
        }
        return result;
    }

    public static Map<Integer, String> getIndexWordsMap(String value) {
        Map<Integer, String> resultMap = new LinkedHashMap<>();
        if (value == null || value.trim().isEmpty())
            return resultMap;

        boolean concatChars = false;
        Integer indexInitWord = null;
        String word = "";
        for (int i = 0; i < value.length(); i++) {
            if (concatChars) {
                if (value.charAt(i) == ' ') {
                    concatChars = false;
                    resultMap.put(indexInitWord, word);
                } else {
                    word += value.charAt(i);
                    if (i == value.length() - 1) {
                        resultMap.put(indexInitWord, word);
                    }
                }
            } else {
                if (value.charAt(i) != ' ') {
                    concatChars = true;
                    indexInitWord = i;
                    word = "" + value.charAt(i);
                    if (i == value.length() - 1) {
                        resultMap.put(indexInitWord, word);
                    }
                }
            }
        }

        return resultMap;
    }

    public static int getIndex(String line, String valueToSearch) {
        Map<Integer, String> indexWordsMap = getIndexWordsMap(line);
        //System.out.println(indexWordsMap);
        Integer index = -1;
        if (valueToSearch.trim().contains(" ")) {
            index = line.indexOf(valueToSearch);
        } else {
            for (Map.Entry<Integer, String> entry : indexWordsMap.entrySet()) {
                if (entry.getValue().equals(valueToSearch.trim())) {
                    index = entry.getKey();
                    break;
                }
            }
        }
        return index;
    }

    public static void setViewedFalse(Node root) {
        if (root == null)
            return;

        List<String> visited = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        Node node;
        do {
            node = queue.poll(); // sacar
            node.setViewed(false);
            visited.add(node.getId());

            List<Node> children = node.getChildren();
            for (Node child : children) {
                if (!visited.contains(child.getId()))
                    queue.add(child);
            }
        } while (!queue.isEmpty());
    }

    public static int dpt(Node node) {
        if (node == null)
            return 0;

        if (node.isViewed())
            return 0;

        if (node.isLeaf())
            return 1;

        node.setViewed(true);

        int maxDepth = 0;
        for (Node child : node.getChildren()) {
            maxDepth = Math.max(maxDepth, dpt(child));
        }

        return maxDepth + 1;
    }
}
