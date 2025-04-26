package com.bo.ml.tree.nariotree;

import com.bo.ml.tree.nariotree.print.TBPrint;
import com.bo.ml.tree.nariotree.print.TBPrintUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * BinaryTree
 *
 * @author Marcos Quispe
 * @since 1.0
 */
public class NarioTree implements TBPrint {

    @Getter
    @Setter
    private Node root;

    @Getter
    private int size;

    public NarioTree() {
        root = null;
        size = 0;
    }

    public NarioTree(int value) {
        root = new Node(value);
        size = 1;
    }

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

    public void print(Node node) {
        if (node == null)
            return;

        System.out.println(node);
        for (Node child : node.getChildren()) {
            print(child);
        }
    }


    public static void main(String[] args) {
        NarioTree t = new NarioTree();

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

        t = new NarioTree(40);
        t.putChildren(40, 30, 10, 20); // arbol ok
        t.putChildren(10, 12);
        t.putChildren(30, 15, 11);
        t.putChildren(11, 2);
        t.putChildren(2, 3, 1, 5);

        TBPrintUtil.print(t);
    }
}
