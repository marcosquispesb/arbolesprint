package com.bo.ml.tree.binarytree;

import com.bo.ml.tree.binarytree.print.TBPrint;
import com.bo.ml.tree.binarytree.print.TBPrintUtil;
import lombok.Getter;

/**
 * TreeBinary
 * Canal Youtube: MathLogic - Haciendo Fácil Lo Difícil
 * Curso de Árboles: https://www.youtube.com/playlist?list=PLJeMuvKPxpu2UGbpuFrcEAPCYhZzdCpBk
 *
 * @author Marcos Quispe
 * @since 1.0
 */
public class BinaryTree implements TBPrint {

    @Getter
    private Node root;

    @Getter
    private int size;

    public BinaryTree(int valueRoot) {
        root = new Node(valueRoot);
        size = 1;
    }

    public void putChildren(int valueParent, Integer valueLeft, Integer valueRight) {
        Node parent = getNode(valueParent, root);
        if (parent != null) {
            if (valueLeft != null) {
                parent.setLeft(new Node(valueLeft));
                size++;
            }
            if (valueRight != null) {
                parent.setRight(new Node(valueRight));
                size++;
            }
        }
    }

    public Node getNode(int valueToSearch, Node node) {
        if (node == null)
            return null;
        if (node.getValue() == valueToSearch)
            return node;

        Node izq = getNode(valueToSearch, node.getLeft());
        if (izq != null)
            return izq;

        return getNode(valueToSearch, node.getRight());
    }

    public int funcionRec(Node node) {
        if (node == null)
            return 0;
        if (node.isLeaf())
            return 1;

        int izq = funcionRec(node.getLeft());
        int der = funcionRec(node.getRight());
        return izq + der + 1;
    }

    public void print(Node node) {
        if (node == null)
            return;

        System.out.println("node: " + node);
        print(node.getLeft());
        print(node.getRight());
    }

    public static void main(String[] args) {
        BinaryTree t;

        t = new BinaryTree(10);
        t.putChildren(10, 20, 30);
        t.putChildren(30, 50, 60);
        t.putChildren(60, null, 65);
        TBPrintUtil.print(t);

        // En el método main llamar al print, enviar el root
        //t.print(t.getRoot());

//        t = new BinaryTree(10);
//        t.putChildren(10, 20, 30);
//        t.putChildren(20, null, 15);
//        t.putChildren(30, 25, null);
//        t.putChildren(25, null, 28);
//        TBPrintUtil.print(t);

//        t = new BinaryTree(10);
//        t.putChildren(10, 20, 30);
//        t.putChildren(20, 40, 50);
//        TBPrintUtil.print(t);

//        t = new BinaryTree(10);
//        t.putChildren(10, 20, 30);
//        t.putChildren(20, 40, 50);
//        t.putChildren(30, 60, 70);
//        t.putChildren(60, 80, 90);
//        TBPrintUtil.print(t);

//        t = new BinaryTree(2);
//        t.putChildren(2, 200, 5);
//        t.putChildren(200, 1, 6);
//        t.putChildren(5, 400, null); // no imprime bien con 200
//        TBPrintUtil.print(t);

//        t = new BinaryTree(38);
//        t.putChildren(38, 14, null);
//        t.putChildren(14, 11, 29);
//        t.putChildren(29, 17, 34);
//        TBPrintUtil.print(t);

//        t = new BinaryTree(20);
//        t.putChildren(20, null, 21);
//        t.putChildren(21, null, 22);
//        TBPrintUtil.print(t);

//        t = new BinaryTree(20);
//        t.putChildren(20, 21, null);
//        t.putChildren(21, 22, null);
//        TBPrintUtil.print(t);

//        t = new BinaryTree(20);
//        t.putChildren(20, null, 21);
//        t.putChildren(21, 19, 22);
//        t.putChildren(19, 18, null);
//        t.putChildren(18, 17, null);
//        TBPrintUtil.print(t);

//        t = new BinaryTree(20);
//        t.putChildren(20, 21, null);
//        t.putChildren(21, 19, 22);
//        t.putChildren(19, 18, null);
//        t.putChildren(18, null, 17);
//        t.putChildren(17, null, 14);
//        t.putChildren(14, null, 13);
//        t.putChildren(13, null, 12);
//        TBPrintUtil.print(t);

//        t = new BinaryTree(20);
//        t.putChildren(20, 30, 21);
//        t.putChildren(21, 19, 22);
//        t.putChildren(19, 18, null);
//        t.putChildren(18, 17, null);
//        TBPrintUtil.print(t);
    }
}
