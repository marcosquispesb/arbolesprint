package com.bo.ml.tree.print.nario;

import java.util.List;

/**
 * TreeNarioPrint
 * Canal Youtube: MathLogic - Haciendo Fácil Lo Difícil
 * Curso de Árboles: https://www.youtube.com/playlist?list=PLJeMuvKPxpu2UGbpuFrcEAPCYhZzdCpBk
 *
 * @author Marcos Quispe
 * @since 1.0
 */
public interface INode {

    String getId();

    void setViewed(boolean viewed);
    boolean isViewed();

    String getValueToPrint();

    List<? extends INode> getChildrenToPrint();

    boolean isLeaf();
}
