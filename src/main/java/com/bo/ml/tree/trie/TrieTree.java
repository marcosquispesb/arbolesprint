package com.bo.ml.tree.trie;

import com.bo.ml.tree.trie.print.TTPrint;
import com.bo.ml.tree.trie.print.TTPrintUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * BinaryTree
 *
 * @author Marcos Quispe
 * @since 1.0
 */
public class TrieTree implements TTPrint {

    @Getter
    @Setter
    private Node root;

    @Getter
    private int size;

    public TrieTree() {
        root = new Node('#');
        size = 1;
    }

    public Node getNode(char valueToSearch, Node node) {
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

    public static TrieTree createTrieDefault() {
        TrieTree t = new TrieTree();

        Node node = t.getRoot();
        node.getChildren()['D' - 'A'] = new Node('D');
        node.getChildren()['A' - 'A'] = new Node('A', true);

        node = node.getChildren()['D' - 'A'];
        node.getChildren()['E' - 'A'] = new Node('E', true);

        node = node.getChildren()['E' - 'A'];
        node.getChildren()['D' - 'A'] = new Node('D');
        node.getChildren()['B' - 'A'] = new Node('B');

        Node nodeDE = node;
        node = nodeDE.getChildren()['D' - 'A'];
        node.getChildren()['O' - 'A'] = new Node('O', true);

        node = nodeDE.getChildren()['B' - 'A'];
        node.getChildren()['E' - 'A'] = new Node('E');
        node.getChildren()['E' - 'A'].getChildren()['R' - 'A'] = new Node('R', true);
        return t;
    }

    public static void main(String[] args) {
        TrieTree t = createTrieDefault();
        TTPrintUtil.print(t);
        System.out.println();
        //System.out.println((int)'A');
//        System.out.println('D' - 'A');
    }
}
