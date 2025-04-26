package com.bo.ml.tree.nariotree;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Node
 *
 * @author Marcos Quispe
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
public class Node {
    private String id; // solo para el print del docente
    private boolean viewed; // solo para el print del docente

    private int value;
    private List<Node> children;

    public Node(int value) {
        this.value = value;
        this.id = UUID.randomUUID().toString();

        this.value = value;
        children = new ArrayList<>(0);
    }

    public boolean isLeaf() {
        return children.isEmpty();
    }

    @Override
    public String toString() {
        String childrenStr = "";
        for (Node child : children) {
            childrenStr += child.getValue() + " ";
        }
        childrenStr = childrenStr.trim();
        return "Node{" +
                "value=" + value +
                ", children=[" + childrenStr + "]" +
                '}';
    }
}
