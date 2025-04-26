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
        for (int i = 0; i < node.getChildren().size(); i++) {
            Node child = node.getChildren().get(i);
            String contentChild = printRec(child, level + 1, dataLevels, edgesLevels, node, childrenMap, root);

            contentChildren.add(contentChild);
            //System.out.println("node: " + node.getValue() + " ." + contentChild + ".");
            if (i == 0) {
                childrenStr = contentChild;
            } else {
                boolean addSpace = !(contentChildren.get(i - 1).endsWith(" ") || contentChild.startsWith(" "));
                if (addSpace && !child.isLeaf()) {
                    //System.out.println("yyy: " + (accumulatedSiblings + childrenStr.length() + 1));
                    //dataLevels[level + 1].length();
                    addOneSpaceGoDown(dataLevels[level + 1].length() + childrenStr.length(), level + 2, dataLevels, edgesLevels, "(child): " + child.getValue());
                }
                childrenStr = childrenStr + (addSpace ? " " : "") + contentChild;
            }
        }

        String result = ""+node.getValue();
        try {
            //System.out.println("childrenStr:" + childrenStr);
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

            List<Integer[]> arrowIndexes = new ArrayList<>(node.getChildren().size());
            int countWord = 0;
            for (int i = 0; i < childrenStr.length(); i++) {
                if (childrenStr.charAt(i) != ' ') {
                    String word = "";
                    for (int j = i; j < childrenStr.length(); j++) {
                        if (childrenStr.charAt(j) != ' ') {
                            word += childrenStr.charAt(j);
                        } else
                            break;
                    }
                    countWord++;

                    boolean estaEnMedio = false;
                    if (node.getChildren().size() % 2 == 1 && countWord - 1 == node.getChildren().size() / 2) { // size impar
                        if (i + word.length() - 1 >= indexStartResult) {
                            estaEnMedio = true;
                        }
                    }
                    int arrowIndex = i;
                    char arrow = '|'; // 1
                    if (i < indexEndResult) {
                        arrowIndex = (indexEndResult + i) / 2;
                        if (node.getChildren().size() > 1 && !estaEnMedio)
                            arrow = '/'; // 0
                    } else if (i > indexStartResult) {
                        arrowIndex = (indexStartResult + i + word.length() - 1) / 2;
                        if (arrowIndex <= arrowIndexes.get(arrowIndexes.size() - 1)[1])
                            arrowIndex = arrowIndexes.get(arrowIndexes.size() - 1)[1] + 1;

                        if (node.getChildren().size() > 1 && !estaEnMedio)
                            arrow = '\\'; // 2
                    }

                    arrowIndexes.add(new Integer[]{i, arrowIndex, (arrow == '/' ? 0 : (arrow == '|' ? 1 : 2))});

                    i += word.length();
                }
            }

            if (node.getChildren().size() > 1) {
                for (int i = 0; i < node.getChildren().size(); i++) {
                    if (i == 0) { // first
                        if (arrowIndexes.get(i)[1] + 1 == arrowIndexes.get(i + 1)[1]) { // esta junto al siguiente
                            if (arrowIndexes.get(i)[1] > arrowIndexes.get(i)[0]) { // posicion valida para poder disminuir
                                arrowIndexes.get(i)[1] -= 1;
                            }
                        }
                    } else if (i == node.getChildren().size() - 1) { // last
                        if (arrowIndexes.get(i)[1] - 1 == arrowIndexes.get(i - 1)[1]) { // esta junto al anterior
                            if (arrowIndexes.get(i)[1] < arrowIndexes.get(i)[0]) { // posicion valida para poder aumentar
                                arrowIndexes.get(i)[1] += 1;
                            }
                        }
                    }
                }
            }
            for (int i = 0; i < arrowIndexes.size(); i++) {
                int arrowIndex = arrowIndexes.get(i)[1];
                char arrow = (arrowIndexes.get(i)[2] == 0 ? '/' : (arrowIndexes.get(i)[2] == 1 ? '|' : '\\'));
                edgesLevels[level + 1] = edgesLevels[level + 1].substring(0, acumulatedEdges + arrowIndex) + arrow + edgesLevels[level + 1].substring(acumulatedEdges + arrowIndex + 1);
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

    /**
     * Adiciona espacios hacia abajo para igualar longitudes de cada nivel, tanto en datos como en aristas
     * @param newContent
     * @param level
     * @param dataLevels
     * @param edgesLevels
     */
    private static void addSpacesGoDown(String newContent, int level, String[] dataLevels, String[] edgesLevels, String nodeValueRef) {
        //System.out.println("addSpacesGoDown nodeValueRef: " + nodeValueRef + "  newContent: " + newContent + "  level: " + level);
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
        //System.out.println("addOneSpaceGoDown nodeValueRef: " + nodeValueRef + "  level: " + level + "  index: " + index);
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
            //System.out.println("xxx: " + dataLevels[i].substring(dataLevels[i].length() - 1 + index));
            dataLevels[i] = dataLevels[i].substring(0, index) // adiciona espacio entre medio
                    + " " + dataLevels[i].substring(index);

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
