package com.bo.ml.tree.treebinary;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Node
 * Canal Youtube: MathLogic - Haciendo Fácil Lo Difícil
 * Curso de Árboles: https://www.youtube.com/playlist?list=PLJeMuvKPxpu2UGbpuFrcEAPCYhZzdCpBk
 *
 * @author Marcos Quispe
 * @since 1.0
 */
@Getter
@Setter
public class Node {
    private String id; // solo para el print del docente
    private boolean viewed; // solo para el print del docente

    private int value;
    private Node left;
    private Node right;

    public Node(int value) {
        this.value = value;
        this.id = UUID.randomUUID().toString();
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }

    public boolean hasTwoSon() {
        return left != null && right != null;
    }

    public List<Node> getChildren() {
        List<Node> children = new ArrayList<>(2);
        if (left != null)
            children.add(left);
        if (right != null)
            children.add(right);

        return children;
    }
}
