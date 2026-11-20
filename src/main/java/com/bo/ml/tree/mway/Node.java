package com.bo.ml.tree.mway;

import com.bo.ml.tree.print.nario.INode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@ToString
public class Node implements INode {

    @Getter
    private Integer[] values;

    private Node[] children;

    @Getter
    private String id; // solo para el print del docente
    @Getter @Setter
    private boolean viewed; // solo para el print del docente

    public Node(int m) {
        children = new Node[m];
        values = new Integer[m - 1];
        this.id = UUID.randomUUID().toString(); // agregar esta linea
    }

    public Node(Integer firstValue, int m) {
        children = new Node[m];
        values = new Integer[m - 1];
        values[0] = firstValue;
        this.id = UUID.randomUUID().toString(); // agregar esta linea
    }

    public int ways() {
        return children.length;
    }

    public Integer getValue(int pos) {
        return values[pos];
    }

    public void setValue(int pos, Integer value) {
        values[pos] = value;
    }

    public Node getVia(int pos) {
        return children[pos];
    }

    public void setVia(int pos, Node node) {
        children[pos] = node;
    }

    public boolean isFull() {
        for (Integer value : values) {
            if (value == null)
                return false;
        }
        return true;
    }

    public String getValueToPrint() {
        String result = children[0] != null ? "‖" : "|";
        for (int i = 0; i < values.length; i++) {
            result += (values[i] != null ? values[i] : "_")
                    + (children[i + 1] != null ? "‖" : "|");
        }
        return result;
    }

    @Override
    public List<? extends INode> getChildrenToPrint() {
        return Arrays.stream(children).filter(x -> x != null).toList();
    }

    public boolean isLeaf() {
        return Arrays.stream(children).allMatch(x -> x == null);
    }

    public static void main(String[] args) {
        int m = 3;
        Node node1 = new Node(m);
        node1.setValue(0, 40);
        node1.setValue(1, 80);

        Node node2 = new Node(m);
        node2.setValue(0, 10);

        node1.setVia(0, node2);
    }
}
