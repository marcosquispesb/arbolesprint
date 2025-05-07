package com.bo.ml.tree.nariotree;

import com.bo.ml.tree.nariotree.print.TNPrint;
import com.bo.ml.tree.nariotree.print.TNPrintUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * BinaryTree
 *
 * @author Marcos Quispe
 * @since 1.0
 */
public class NarioTree implements TNPrint {

    @Getter
    @Setter
    private Node root;

    @Getter
    private int size;

    public NarioTree() {
        root = null;
        size = 0;
    }

    /**
     * Constructor que recibira el valor para el root
     * @param value
     */
    public NarioTree(int value) {
        root = new Node(value);
        size = 1;
    }

    /**
     * Método que inserta nuevos nodos (children) como hijos
     * del nodo con valor valueParent
     * @param valueParent: valor de algun nodo que existe en el árbol
     * @param children: valores de los nuevos nodos que se crearán
     */
    public void putChildren(int valueParent, Integer ...children) {
        Node parent = getNode(valueParent, root);
        if (parent == null)
            return;
        for (Integer valueChild : children) {
            parent.getChildren().add(new Node(valueChild));
            size++;
        }
    }

    public Node getNode(int valueToSearch, Node node) {
        if (node == null)
            return null;
        if (node.getValue() == valueToSearch)
            return node;

        for (Node child : node.getChildren()) {
            Node aux = getNode(valueToSearch, child);
            if (aux != null)
                return aux;
        }
        return null;
    }

    public int funcionRec(Node node) {
        if (node == null)
            return 0;
        if (node.isLeaf())
            return 1;

        int result = 0;
        for (Node child : node.getChildren()) {
            int resultChild = funcionRec(child);
            result += resultChild;
        }
        return result + 1;
    }

    /**
     * Imprime los elementos del árbol
     * @param node: Nodo actual de quien se itera los hijos
     * haciendo las llamadas recursivas por cada hijo
     */
    public void print(Node node) {
        if (node == null) // caso base
            return;

        System.out.println(node);
        for (Node child : node.getChildren()) {
            print(child);
        }
    }

    public static void main(String[] args) {
        NarioTree t = new NarioTree(1);
        t.putChildren(1, 10, 20, 30);
        t.putChildren(10, 12);
        t.putChildren(30, 11, 18);

        // En el método main llamar al print, enviar el root
        //t.print(t.getRoot());

//        NarioTree t = new NarioTree();

//        t = new NarioTree(1); // arbol ok
//        t.putChildren(1, 10, 20, 30);
//        t.putChildren(10, 11, 14, 17);
//        t.putChildren(30, 33, 39);

//        t = new NarioTree(1); // arbol ok
//        t.putChildren(1, 10, 20, 30);
//        t.putChildren(10, 5, 12, 13);
//        t.putChildren(30, 11, 18);
//        t.putChildren(12, 3, 7);
//        t.putChildren(18, 4);

//        t = new NarioTree(40);
//        t.putChildren(40, 30, 10, 20); // arbol ok
//        t.putChildren(10, 12);
//        t.putChildren(30, 15, 11);
//        t.putChildren(11, 2);
//        t.putChildren(2, 3, 1, 5);

//        t = new NarioTree(1);
//        t.putChildren(1, 30, 20, 10, 40, 50, 60);
//        //t.putChildren(1, 70, 30, 20, 10, 40, 50, 60);
//        t.putChildren(10, 12);
//        t.putChildren(30, 15, 11);
//        t.putChildren(11, 2);
//        t.putChildren(2, 3, 31, 5);

        TNPrintUtil.print(t);
    }
}
