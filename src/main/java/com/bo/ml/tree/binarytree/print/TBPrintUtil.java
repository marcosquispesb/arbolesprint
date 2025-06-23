package com.bo.ml.tree.binarytree.print;

import com.bo.ml.tree.binarytree.Node;

import java.util.*;

/**
 * TreeBinaryPrintUtil
 * Canal Youtube: MathLogic - Haciendo Fácil Lo Difícil
 * Curso de Árboles: https://www.youtube.com/playlist?list=PLJeMuvKPxpu2UGbpuFrcEAPCYhZzdCpBk
 *
 * @author Marcos Quispe
 * @version v1.0
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

        boolean hasOnlyChildLeft = (node.getLeft() != null && node.getRight() == null);
        boolean hasOnlyChildRight = (node.getLeft() == null && node.getRight() != null);
        boolean hasTwoSon = (node.getLeft() != null && node.getRight() != null);

        String contentLeft = printRec(node.getLeft(), level + 1, dataLevels, edgesLevels, node, childrenMap, root);
        String contentRight = printRec(node.getRight(), level + 1, dataLevels, edgesLevels, node, childrenMap, root);

        String childrenStr = contentLeft + contentRight;;
        if (hasTwoSon) {
            // REVISANDO SI ENTRE los hijos NO hay ESPACIO para adicionarle
            boolean addSpace = !contentLeft.endsWith(" ") && !contentRight.startsWith(" ");
            if (addSpace) {
                //System.out.println("xxx " + node.getValue());
                addOneSpaceGoDown(dataLevels[level + 1].length() + contentLeft.length(), level + 2, dataLevels, edgesLevels, contentLeft + contentRight);
            }
            childrenStr = contentLeft + (addSpace ? " " : "") + contentRight;
        } else if (hasOnlyChildLeft) {
            childrenStr = contentLeft + " ";
        } else if (hasOnlyChildRight) {
            if (!contentRight.startsWith(" ")) {
                addOneSpaceGoDown(dataLevels[level + 1].length(), level + 2, dataLevels, edgesLevels, contentLeft + contentRight);
                childrenStr = " " + contentRight;
            }
        }

        String result = "" + node.getValue();
        try {
            // REVISANDO SI ENTRE dataLevels[level + 1] y childrenStr NO hay ESPACIO para adicionarle
            if (!dataLevels[level + 1].isEmpty()) {
                boolean addSpace = !dataLevels[level + 1].endsWith(" ") && !childrenStr.startsWith(" ");
                if (addSpace) {
                    //System.out.println("yyy " + node.getValue());
                    addOneSpaceGoDown(dataLevels[level + 1].length(), level + 2, dataLevels, edgesLevels, "" + childrenStr);
                }
                childrenStr = (addSpace ? " " : "") + childrenStr;
            }

            dataLevels[level + 1] += childrenStr;
            childrenMap.put(node.getId(), childrenStr);
            //System.out.println("childrenStr:" + childrenStr);

            if (hasOnlyChildRight) {
                int indexStartChildren = childrenStr.indexOf(childrenStr.trim());
                int posNode = indexStartChildren - 1;
                result = spaces(posNode) + node.getValue();
                result = result + spaces(childrenStr.length() - result.length());

            } else if (hasOnlyChildLeft) {
                int indexStartChildren = childrenStr.indexOf(childrenStr.trim());
                int posNode = indexStartChildren + 1;
                result = spaces(posNode) + node.getValue();
                result = result + spaces(childrenStr.length() - result.length());

            } else if (hasTwoSon) {
                // reajuste result solo si hijos son dos o mas elementos, para que dicho valor quede lo mejor centrado posible
                int indexStartChildren = childrenStr.indexOf(childrenStr.trim());
                String spacesChildrenInitial = childrenStr.substring(0, indexStartChildren);

                int countSpacesCenterInitial = 0;
                if (childrenStr.trim().length() % 2 == 0 && ("" + node.getValue()).length() % 2 == 0) { // hijos par, padre par
                    countSpacesCenterInitial = (childrenStr.trim().length() - ("" + node.getValue()).length()) / 2;

                } else if (childrenStr.trim().length() % 2 == 1 && ("" + node.getValue()).length() % 2 == 1) { // hijos impar, padre impar
                    countSpacesCenterInitial = (childrenStr.trim().length() - ("" + node.getValue()).length()) / 2;

                } else if (childrenStr.trim().length() % 2 == 1 && ("" + node.getValue()).length() % 2 == 0) { // hijos impar, padre par
                    countSpacesCenterInitial = (childrenStr.trim().length() + 1 - ("" + node.getValue()).length()) / 2;

                } else if (childrenStr.trim().length() % 2 == 0 && ("" + node.getValue()).length() % 2 == 1) { // hijos par, padre impar
                    countSpacesCenterInitial = (childrenStr.trim().length() + 1 - ("" + node.getValue()).length()) / 2;
                }
                result = spacesChildrenInitial + spaces(countSpacesCenterInitial) + node.getValue();
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
                    if (indexEdgeRight <= indexEdgeLeft)
                        indexEdgeRight = indexEdgeLeft + 1;
                    edgesLevels[level + 1] = edgesLevels[level + 1].substring(0, indexEdgeRight) + "\\" + edgesLevels[level + 1].substring(indexEdgeRight + 1);
                }
            }

            if (hasOnlyChildRight) { // arista solo der
                int indexRight = childrenStr.indexOf(childrenStr.trim()) + ("" + node.getRight().getValue()).length() - 1;
                int indexEdgeRight = acumulated + indexRight - ((indexRight - indexResult) / 2);
                edgesLevels[level + 1] = edgesLevels[level + 1].substring(0, indexEdgeRight) + "\\" + edgesLevels[level + 1].substring(indexEdgeRight + 1);
            }

            // IGUALANDO ESPACIOS finales hacia abajo
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(String.format("result: \"%s\"     childrenStr: \"%s\"", result, childrenStr));
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
        //System.out.println("addOneSpaceGoDown nodeValueRef: \"" + nodeValueRef + "\"  level: " + level + "  index: " + index);
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
            //System.out.println(String.format("  i=%s dataLevels[i]: \"%s\"", i, dataLevels[i]));
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

        int izq = dpt(node.getLeft());
        int der = dpt(node.getRight());
        return Math.max(izq, der) + 1;
    }
}
