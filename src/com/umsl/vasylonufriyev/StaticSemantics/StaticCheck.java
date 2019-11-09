package com.umsl.vasylonufriyev.StaticSemantics;

import com.umsl.vasylonufriyev.DataStructures.ProgramNode;
import com.umsl.vasylonufriyev.DataStructures.Token;

import java.util.Stack;

public class StaticCheck {
    private ProgramNode parseResult;
    private StaticStack stack;

    public StaticCheck(ProgramNode parseResult) {
        this.parseResult = parseResult;
        this.stack = new StaticStack();
    }

    public void beginCheck() throws Exception {
        treePreorderStaticCheck(parseResult, 0);
    }

    private static void treePreorderStaticCheck(ProgramNode node, int depth) {
        checkMe(node, depth);
        for (int i = 0; i < node.children.length; i++) {
            if (node.children[i] != null) {
                treePreorderStaticCheck(node.children[i], depth + 1);
            }
        }
    }

    private static void checkMe(ProgramNode node, int depth) {
        StringBuilder sb = new StringBuilder();
        if (depth > 0) {
            for (int i = 0; i < depth; i++)
                sb.append("| ");
        }
        sb.append(node.getNodeLabel());
        sb.append(" (");

        for (Token t : node.tokenData) {
            if (t != null)
                sb.append(t.toString());
        }
        sb.append(")");

        System.out.println(sb.toString());
    }
}
