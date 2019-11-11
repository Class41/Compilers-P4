/*
Author: Vasyl Onufriyev
Date: 11.8.2019
Class: CS4280
Instructor: Professor Janikow
Description: Handles static semantic scoping of variables
*/

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
        treePreorderStaticCheck(parseResult);
    }

    private void treePreorderStaticCheck(ProgramNode node) throws Exception {
        if (node.getNodeLabel().equals("<Block>")) //If we are on a block node, push a block scope onto the stack
            blockStack.push(new BlockContainer());

        checkMe(node); //Check current node
        for (int i = 0; i < node.children.length; i++) { //Check children L -> R
            if (node.children[i] != null) {
                treePreorderStaticCheck(node.children[i]);
            }
        }

        if (node.getNodeLabel().equals("<Block>")) { //Delete this block, we've visited all children and are about to go up
            for (int i = 0; i < blockStack.peek().getVarCount(); i++) {
                stack.pop();
            }
            blockStack.pop();
        }
    }

    private void checkMe(ProgramNode node) throws Exception {
        for (Token tk : node.tokenData) { //For each token
            if (node.getNodeLabel().equals("<Vars>")) { //If it is a vars block
                if (tk != null && tk.getTokenType().equals("IDENTIFIER_TK")) { //Get the identifier token
                    if (blockStack.size() > 0 && blockStack.peek() != null) { //check is block scope is null or not
                        if (blockStack.peek().getVarCount() > 0) { //check if vars exist in this scope already
                            int stackpos = stack.find(tk); //get position of current tk in stack
                            if (stackpos > -1 && stackpos < blockStack.peek().getVarCount()) { //check if it is in current bound
                                throw new Exception(
                                        "STATICSEM:L"
                                                + tk.getTokenLine()
                                                + ": "
                                                + tk.getTokenValue() +
                                                " is already defined in this scope");
                            }
                        }
                        blockStack.peek().setVarCount(blockStack.peek().getVarCount() + 1); //increment +1 if block exist
                    } else {
                        int stackpos = stack.find(tk);
                        if (stackpos > -1) { //check if it is in current bound
                            throw new Exception(
                                    "STATICSEM:L"
                                            + tk.getTokenLine()
                                            + ": "
                                            + tk.getTokenValue() +
                                            " is already defined in global scope!");
                        }
                    }

                    stack.push(tk); //push onto stack anyways, block or no block. this supports Global variables
                }
            } else {
                if (tk != null && tk.getTokenType().equals("IDENTIFIER_TK")) { //We are using a IDtk in a statement
                    if (stack.find(tk) == -1) //if it doesn't exist, it wasn't defined anywhere in the scope
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
