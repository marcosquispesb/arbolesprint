package com.bo.ml.tree.trie;

import com.bo.ml.tree.print.nario.INode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
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
public class Node implements INode {

    private char value;
    private boolean endWord;
    private Node[] children;

    private String id; // solo para el print del docente
    private boolean viewed; // solo para el print del docente

    public Node(char value) {
        this.value = value;
        children = new Node[26];
        this.id = UUID.randomUUID().toString(); // agregar esta linea
    }

    public Node(char value, boolean endWord) {
        this.value = value;
        this.endWord = endWord;
        children = new Node[26];
        this.id = UUID.randomUUID().toString(); // agregar esta linea
    }

    @Override
    public String getValueToPrint() {
        return ""+value;
    }

    @Override
    public List<? extends INode> getChildrenToPrint() {
        return Arrays.stream(children).filter(x -> x != null).toList();
    }

    public boolean isLeaf() {
        return Arrays.stream(children).allMatch(x -> x == null);
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
