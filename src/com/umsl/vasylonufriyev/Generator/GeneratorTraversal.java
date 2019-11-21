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

    private static GeneratorCore generator = null;
    private ProgramNode parseResult;
    private static GeneratorStack stack;
    private static Stack<GeneratorBlockContainer> blockStack = new Stack<GeneratorBlockContainer>();

    public GeneratorTraversal(ProgramNode parseResult, GeneratorCore generator) {
        this.parseResult = parseResult;
        stack = new GeneratorStack();
        GeneratorTraversal.generator = generator;
        generator.setStaticStackReference(stack);
    }

    public void beginGenerate() throws Exception {
        treePreorderGeneratorTraversal(parseResult);
        generator.generateFile();
    }

    static void treePreorderGeneratorTraversal(ProgramNode node) {
        if (node.getNodeLabel().equals("<Block>")) //If we are on a block node, push a block scope onto the stack
            blockStack.push(new GeneratorBlockContainer());

        generator.generateCodeForNode(node);
        if (node.getNodeLabel().equals("<Block>") || node.getNodeLabel().equals("<Vars>")) {
            checkMe(node); //Check current node
            for (int i = 0; i < node.children.length; i++) { //Check children L -> R
                if (node.children[i] != null) {
                    treePreorderGeneratorTraversal(node.children[i]);
                }
            }
        }


        if (node.getNodeLabel().equals("<Block>")) { //Delete this block, we've visited all children and are about to go up
            for (int i = 0; i < blockStack.peek().getVarCount(); i++) {
                stack.pop();
                generator.generatePop();
            }
            blockStack.pop();
        }
    }

    private static void checkMe(ProgramNode node) {
        for (Token tk : node.tokenData) { //For each token
            if (node.getNodeLabel().equals("<Vars>")) { //If it is a vars block
                if (tk != null && tk.getTokenType().equals("IDENTIFIER_TK")) { //Get the identifier token
                    if (blockStack.size() > 0 && blockStack.peek() != null) { //check is block scope is null or not
                        blockStack.peek().setVarCount(blockStack.peek().getVarCount() + 1); //increment +1 if block exist
                    }
                    stack.push(tk); //push onto stack anyways, block or no block. this supports Global variables
                }
            }
        }
    }
}
