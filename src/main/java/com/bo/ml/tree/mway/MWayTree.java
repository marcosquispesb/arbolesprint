package com.bo.ml.tree.mway;

import com.bo.ml.tree.nariotree.print.TNPrintUtil;
import lombok.Getter;

public class MWayTree {

    private int m;

    @Getter
    private Node root;

    private int size;

    public MWayTree(int m) {
        this.m = m;
        root = null;
        size = 0;
    }

    public MWayTree(Node root, int m) {
        this.root = root;
        this.m = m;
    }
    public static MWayTree createTreeMVDefault() {
        int m = 3;
        Node node1 = new Node(m);
        node1.setValue(0, 1);
        node1.setValue(1, 5);

        Node node2 = new Node(m);
        node2.setValue(0, 3);
        node2.setValue(1, 7);

        Node node3 = new Node(m);
        node3.setValue(0, 4);
        node3.setValue(1, 9);

        Node node4 = new Node(m);
        node4.setValue(0, 10);
        node4.setValue(1, 13);

        Node node5 = new Node(m);
        node5.setValue(0, 8);
        node5.setValue(1, 12);

        node1.setVia(0, node2);
        node1.setVia(1, node3);
        node1.setVia(2, node4);

        node4.setVia(0, node5);

        MWayTree MWayTree = new MWayTree(node1, m);
        return MWayTree;
    }

    public static void main(String[] args) {
        MWayTree t = createTreeMVDefault();
        TNPrintUtil.print(t.root);
    }
}
