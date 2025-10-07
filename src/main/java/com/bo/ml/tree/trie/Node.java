package com.bo.ml.tree.trie;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
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

    private char value;
    private boolean checked;
    private Node[] children;

    private String id; // solo para el print del docente
    private boolean viewed; // solo para el print del docente

    public Node(char value) {
        this.value = value;
        children = new Node[26];
        this.id = UUID.randomUUID().toString(); // agregar esta linea
    }

    public boolean isLeaf() {
        return Arrays.stream(children).allMatch(x -> x == null);
    }

    public int getChildrenLenght() {
        return (int) Arrays.stream(children).filter(x -> x != null).count();
    }

    @Override
    public String toString() {
        String childrenStr = "";
        for (Node child : children) {
            if (child != null)
                childrenStr += child.getValue() + " ";
        }
        childrenStr = childrenStr.trim();
        return "Node{" +
                "value=" + value +
                ", children=[" + childrenStr + "]" +
                '}';
    }
}
