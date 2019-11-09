/*
Author: Vasyl Onufriyev
Date: 11.8.2019
Class: CS4280
Instructor: Professor Janikow
Description: Used for processing block statement scopes
*/

package com.umsl.vasylonufriyev.StaticSemantics;

import com.umsl.vasylonufriyev.DataStructures.ProgramNode;

class BlockContainer {

    private int varCount;

    BlockContainer() {
        this.varCount = 0;
    }

    int getVarCount() {
        return varCount;
    }

    void setVarCount(int varCount) {
        this.varCount = varCount;
    }
}
