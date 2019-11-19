/*
Author: Vasyl Onufriyev
Date: 11.8.2019
Class: CS4280
Instructor: Professor Janikow
Description: Handles static semantic scoping of variables
*/

package com.umsl.vasylonufriyev.Generator;

import com.umsl.vasylonufriyev.DataStructures.ProgramNode;
import com.umsl.vasylonufriyev.DataStructures.Token;

import java.util.Stack;

public class GeneratorTraversal {

    private final GeneratorCore generator;
    private ProgramNode parseResult;
    private GeneratorStack stack;
    private Stack<GeneratorBlockContainer> blockStack = new Stack<GeneratorBlockContainer>();

    public GeneratorTraversal(ProgramNode parseResult, GeneratorCore generator) {
        this.parseResult = parseResult;
        this.stack = new GeneratorStack();
        this.generator = generator;
        generator.setStaticStackReference(stack);
    }

    public void beginGenerate() throws Exception {
        treePreorderStaticCheck(parseResult);
        generator.generateFile();
    }

    private void treePreorderStaticCheck(ProgramNode node) throws Exception {
        if (node.getNodeLabel().equals("<Block>")) //If we are on a block node, push a block scope onto the stack
            blockStack.push(new GeneratorBlockContainer());

        if (node.getNodeLabel().equals("<Block>") || node.getNodeLabel().equals("<Vars>")) {
            checkMe(node); //Check current node
            for (int i = 0; i < node.children.length; i++) { //Check children L -> R
                if (node.children[i] != null) {
                    treePreorderStaticCheck(node.children[i]);
                }
            }
        } else {
            generator.generateCodeForNode(node);
        }

        if (node.getNodeLabel().equals("<Block>")) { //Delete this block, we've visited all children and are about to go up
            for (int i = 0; i < blockStack.peek().getVarCount(); i++) {
                stack.pop();
                generator.generatePop();
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
                    }
                    stack.push(tk); //push onto stack anyways, block or no block. this supports Global variables
                }
            }
        }
    }
}