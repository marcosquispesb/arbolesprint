package com.bo.ml.tree.treebinary.print;

import com.bo.ml.tree.treebinary.Node;

import java.util.*;

/**
 * TreeBinaryUtil
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
        if (node == null)
            return "";

        // VALIDATION LOOPS
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
        if (node.isLeaf())
            return "" + node.getValue();

        String contentLeft = printRec(node.getLeft(), level + 1, dataLevels, edgesLevels, node, childrenMap, root);
        String contentRight = printRec(node.getRight(), level + 1, dataLevels, edgesLevels, node, childrenMap, root);
        String childrenStr = contentLeft + (contentLeft.endsWith(" ") || contentRight.startsWith(" ") ? "" : " ") + contentRight;

        boolean childrenUniqueValueLeft = false;
        boolean childrenUniqueValueRight = false;
        if (node.getRight() == null && node.getLeft() != null) { // nodo tiene solo hijo izquierdo
            //System.out.println(node.getValue());
            childrenUniqueValueLeft = true;
        } else if (node.getLeft() == null && node.getRight() != null) { // nodo tiene solo hijo derecho
            //System.out.println(node.getValue());
            childrenUniqueValueRight = true;
        } else { // tiene dos hijos
            if (node.getLeft().isLeaf()) { // hijo izq es hoja
                addSpacesGoDown(contentLeft, level, dataLevels, edgesLevels, " (de "+node.getValue()+" hijoIzq hoja)");
                if (node.getRight().getLeft() != null) {
                    //System.out.println("aaa:" + node.getRight().getValue());
                    addSpacesGoDown(contentLeft, level, dataLevels, edgesLevels, " (de "+node.getValue()+" hijoIzq de Der existe)");

                    int index = childrenStr.indexOf(contentRight);
                    childrenStr = childrenStr.substring(0, index) + " " + childrenStr.substring(index);
                }
            }
            if (node.getRight().isLeaf()) { // hijo der es hoja
                if (node.getLeft().getRight() != null) {
                    //System.out.println("bbb:" + node.getRight().getValue());

                    int index = getIndex(childrenStr, contentRight); //childrenStr.indexOf(contentRight);
                    if (index > -1)
                        childrenStr = childrenStr.substring(0, index) + " " + childrenStr.substring(index);
                }
            }
        }

        String result = ""+node.getValue();
        try {
            //System.out.println("childrenStr:" + childrenStr);
            String addSpace = (!dataLevels[level + 1].endsWith(" ") && !childrenStr.startsWith(" ") ? " " : "");
            addSpace = dataLevels[level + 1].isEmpty() ? "" : addSpace;
            childrenStr = addSpace + childrenStr;
            dataLevels[level + 1] += childrenStr;
            childrenMap.put(node.getId(), childrenStr);

            result = spaces(childrenStr.length() / 2) + node.getValue();
            result = result + spaces(childrenStr.length() - result.length());

            if (childrenUniqueValueRight) {
                //System.out.println("childrenStr:" + childrenStr);

                //System.out.println("rr:" + result);
                if (result.startsWith(" ")) {
                    int index2 = result.indexOf(result.trim());
                    String spaces2 = result.substring(0, index2);
                    result = result.substring(index2) + spaces2;
                }
                //System.out.println("rr:" + result);

                // recorriendo hijos
                if (parent != null && parent.getRight() != null && Objects.equals(parent.getRight().getValue(), node.getValue())) {
                    //System.out.println("rh:" + result);
                    //System.out.println("childrenStr:" + childrenStr);
                    recorrerHijos(node, level, dataLevels, edgesLevels, childrenMap);
                }

            } else if (childrenUniqueValueLeft) {
                //System.out.println("childrenStr:" + childrenStr);
                int indexChild = childrenStr.indexOf(childrenStr.trim());
                int indexResult = result.indexOf(result.trim());
                if (indexResult <= indexChild) {
                    String spacesToAdd = spaces(indexChild - indexResult + 1);
                    result = result.substring(0, indexResult) + spacesToAdd + result.substring(indexResult);
                }

            } else if (node.hasTwoSon()) {
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
            }

            // ARISTAS
            int acumulated = edgesLevels[level + 1].length();
            edgesLevels[level + 1] += spaces(childrenStr.length());
            int indexResult = result.indexOf(result.trim());

            if (node.getLeft() != null) { // arista izq
                int indexInitial = childrenStr.indexOf(childrenStr.trim());
                int indexEdgeLeft = acumulated + indexInitial + ((indexResult + (result.trim().length() - 1) - indexInitial) / 2);
                edgesLevels[level + 1] = edgesLevels[level + 1].substring(0, indexEdgeLeft) + "/" + edgesLevels[level + 1].substring(indexEdgeLeft + 1);

                if (node.getRight() != null) { // arista der
                    int indexRight = indexInitial + childrenStr.trim().length();
                    int indexEdgeRight = acumulated + indexRight - ((indexRight - indexResult) / 2);
                    if ((indexRight - indexResult) % 2 == 1)
                        indexEdgeRight--;
                    edgesLevels[level + 1] = edgesLevels[level + 1].substring(0, indexEdgeRight) + "\\" + edgesLevels[level + 1].substring(indexEdgeRight + 1);
                }
            }

            if (node.getLeft() == null && node.getRight() != null) { // arista solo der
                int indexRight = childrenStr.indexOf(childrenStr.trim()) + ("" + node.getRight().getValue()).length() - 1;
                int indexEdgeRight = acumulated + indexRight - ((indexRight - indexResult) / 2);
                edgesLevels[level + 1] = edgesLevels[level + 1].substring(0, indexEdgeRight) + "\\" + edgesLevels[level + 1].substring(indexEdgeRight + 1);
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

    private static void recorrerHijos(Node node, int level, String[] dataLevels, String[] edgesLevels, Map<String, String> childrenMap) {
        if (node == null)
            return;
        if (node.isLeaf())
            return;

        String childrenStr = childrenMap.get(node.getId());
        int indexStr = dataLevels[level + 1].indexOf(childrenStr);
        dataLevels[level + 1] = dataLevels[level + 1].substring(0, indexStr) + " " + dataLevels[level + 1].substring(indexStr);
        edgesLevels[level + 1] = edgesLevels[level + 1].substring(0, indexStr) + " " + edgesLevels[level + 1].substring(indexStr);

        recorrerHijos(node.getLeft(), level + 1, dataLevels, edgesLevels, childrenMap);
        recorrerHijos(node.getRight(), level + 1, dataLevels, edgesLevels, childrenMap);
    }

    /**
     * Adiciona espacios hacia abajo para igualar longitudes de cada nivel, tanto en datos como en aristas
     * @param contentLeft
     * @param level
     * @param dataLevels
     * @param edgesLevels
     */
    private static void addSpacesGoDown(String contentLeft, int level, String[] dataLevels, String[] edgesLevels, String nodeValueRef) {
        //System.out.println("addSpacesGoDown nodeValueRef: " + nodeValueRef);
        int lengthLeft = contentLeft.trim().length();
        //System.out.println("gd:"+spaceLeft);
        for (int i = level + 2; i < dataLevels.length; i++) {
            if (dataLevels[i].length() < dataLevels[level + 1].length()) { // linea i menor que level
                //System.out.println("linea menor que sig "+ nodeValueRef);
                //System.out.println("yyy" + dataLevels[i]);
                int diff = dataLevels[level + 1].length() - dataLevels[i].length();
                dataLevels[i] += spaces(diff); // iguala linea actual con la siguiente
                dataLevels[i] += spaces(lengthLeft); // adiciona nueva longitud
                //System.out.println("yyy" + dataLevels[i]);

                diff = edgesLevels[level + 1].length() - edgesLevels[i].length();
                edgesLevels[i] += spaces(diff); // iguala linea actual con la siguiente
                edgesLevels[i] += spaces(lengthLeft); // adiciona nueva longitud

            } else if (dataLevels[i].length() > dataLevels[level + 1].length()) { // linea actual mayor que level
                //System.out.println("linea mayor que sig " + nodeValueRef);
                //System.out.println("xxx" + dataLevels[i]);
                int lengthLevel = dataLevels[level + 1].length();
                dataLevels[i] = dataLevels[i].substring(0, lengthLevel)
                        + spaces(lengthLeft)
                        + dataLevels[i].substring(lengthLevel);
                //System.out.println("xxx" + dataLevels[i]);

                edgesLevels[i] = edgesLevels[i].substring(0, edgesLevels[level + 1].length())
                        + spaces(lengthLeft)
                        + edgesLevels[i].substring(edgesLevels[level + 1].length());
            }
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

        int izq = dpt(node.getLeft());
        int der = dpt(node.getRight());
        return Math.max(izq, der) + 1;
    }
}
