/*
Author: Vasyl Onufriyev
Date: 11.8.2019
Class: CS4280
Instructor: Professor Janikow
Description: Used for processing block statement scopes
*/

package com.umsl.vasylonufriyev.StaticSemantics;

import com.umsl.vasylonufriyev.DataStructures.ProgramNode;

public class BlockContainer {

    private ProgramNode blockTk;
    private int varCount;

    public BlockContainer(ProgramNode blockTk) {
        this.blockTk = blockTk;
        this.varCount = 0;
    }

    public ProgramNode getBlockTk() {
        return blockTk;
    }

    public int getVarCount() {
        return varCount;
    }

    public void setVarCount(int varCount) {
        this.varCount = varCount;
    }
}
