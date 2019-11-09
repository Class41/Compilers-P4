package com.umsl.vasylonufriyev.StaticSemantics;

import com.umsl.vasylonufriyev.DataStructures.ProgramNode;
import com.umsl.vasylonufriyev.DataStructures.Token;

import java.util.Stack;

public class StaticCheck {
    private ProgramNode parseResult;
    private StaticStack stack;
    private Stack<BlockContainer> blockStack = new Stack<BlockContainer>();

    public StaticCheck(ProgramNode parseResult) {
        this.parseResult = parseResult;
        this.stack = new StaticStack();
    }

    public void beginCheck() throws Exception {
        treePreorderStaticCheck(parseResult, 0);
    }

    private void treePreorderStaticCheck(ProgramNode node, int depth) throws Exception {
        if (node.getNodeLabel().equals("<Block>"))
            blockStack.push(new BlockContainer(node));

        checkMe(node, depth);
        for (int i = 0; i < node.children.length; i++) {
            if (node.children[i] != null) {
                treePreorderStaticCheck(node.children[i], depth + 1);
            }
        }

        if (node.getNodeLabel().equals("<Block>")) {
            for (int i = 0; i < blockStack.peek().getVarCount(); i++) {
                stack.pop();
            }
            blockStack.pop();
        }
    }

    private void checkMe(ProgramNode node, int depth) throws Exception {
        for (Token tk : node.tokenData) {
            if (node.getNodeLabel().equals("<Vars>")) {
                if (tk != null && tk.getTokenType().equals("IDENTIFIER_TK")) {
                    if (blockStack.peek() != null) {
                        if (blockStack.peek().getVarCount() > 0) {
                            int stackpos = stack.find(tk);
                            if (stackpos > -1 && stackpos < blockStack.peek().getVarCount()) {
                                throw new Exception(
                                        "STATICSEM:L"
                                                + tk.getTokenLine()
                                                + ": "
                                                + tk.getTokenValue() +
                                                " is already defined in this scope");
                            }
                        }
                        stack.push(tk);
                        blockStack.peek().setVarCount(blockStack.peek().getVarCount() + 1);
                    }
                }
            } else {
                if (tk != null && tk.getTokenType().equals("IDENTIFIER_TK")) {
                    if (stack.find(tk) == -1)
                        throw new Exception(
                                "STATICSEM:L"
                                        + tk.getTokenLine()
                                        + ": "
                                        + tk.getTokenValue() +
                                        " is not defined in this scope");
                }
            }
        }
    }
}