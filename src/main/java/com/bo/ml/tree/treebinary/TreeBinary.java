package com.bo.ml.tree.treebinary;

import com.bo.ml.tree.treebinary.print.TBPrint;
import com.bo.ml.tree.treebinary.print.TBPrintUtil;
import lombok.Getter;

/**
 * TreeBinary
 *
 * @author Marcos Quispe
 * @since 1.0
 */
public class TreeBinary implements TBPrint {

    private Node root;

    @Getter
    private int size;

    public TreeBinary() {
        root = null;
        size = 0;
    }

    @Override
    public Node getRoot() {
        return root;
    }

    public void addNodesToTest() {
        root = new Node(10);
        Node node20 = new Node(20);
        Node node30 = new Node(30);
        root.setLeft(node20);
        root.setRight(node30);
        node30.setLeft(new Node(50));
        node30.setRight(new Node(60));
        node30.getRight().setRight(new Node(65));
    }

    public static void main(String[] args) {
        TreeBinary tb = new TreeBinary();
        tb.addNodesToTest();
        TBPrintUtil.print(tb);
    }
}
