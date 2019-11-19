package com.umsl.vasylonufriyev.Generator;

import com.umsl.vasylonufriyev.DataStructures.ProgramNode;
import com.umsl.vasylonufriyev.DataStructures.Token;

import java.util.ArrayList;
import java.util.List;

import static com.umsl.vasylonufriyev.Generator.GeneratorTraversal.treePreorderGeneratorTraversal;

class GeneratorActions {

    private final GeneratorOutput genOut;
    private final GeneratorStack varStack;

    GeneratorActions(boolean usingFile, GeneratorStack stack) {
        this.varStack = stack;
        this.genOut = new GeneratorOutput(usingFile);
    }

    void generateFile() {
        genOut.finalizeAndWrite();
    }

    String generateTempVariable() {
        return TempVariableGenerator.generateTempVar();
    }

    void outputPop() {
        genOut.appendCommand("POP");
    }

    void outputROFactorLT(ProgramNode node) {
        for (int i = 0; i < node.children.length; i++) { //Check children L -> R
            if (node.children[i] != null) {
                treePreorderGeneratorTraversal(node.children[i]);
            }
        }
    }

    void outputROFactorGT(ProgramNode node) {
        for (int i = 0; i < node.children.length; i++) { //Check children L -> R
            if (node.children[i] != null) {
                treePreorderGeneratorTraversal(node.children[i]);
            }
        }
    }

    void outputRO(ProgramNode node) {
        for (int i = 0; i < node.children.length; i++) { //Check children L -> R
            if (node.children[i] != null) {
                treePreorderGeneratorTraversal(node.children[i]);
            }
        }
    }

    void outputAssign(ProgramNode node) {
        for (int i = 0; i < node.children.length; i++) { //Check children L -> R
            if (node.children[i] != null) {
                treePreorderGeneratorTraversal(node.children[i]);
            }
        }
    }

    void outputLoop(ProgramNode node) {
        for (int i = 0; i < node.children.length; i++) { //Check children L -> R
            if (node.children[i] != null) {
                treePreorderGeneratorTraversal(node.children[i]);
            }
        }
    }

    void outputIf(ProgramNode node) {
        for (int i = 0; i < node.children.length; i++) { //Check children L -> R
            if (node.children[i] != null) {
                treePreorderGeneratorTraversal(node.children[i]);
            }
        }
    }

    void outputOut(ProgramNode node) {
        String temp1 = generateTempVariable();
        for (int i = 0; i < node.children.length; i++) { //Check children L -> R
            if (node.children[i] != null) {
                treePreorderGeneratorTraversal(node.children[i]);
            }
        }
        genOut.appendCommand("STORE " + temp1);
        genOut.appendCommand("WRITE " + temp1);
    }

    void outputIn(ProgramNode node) {
        String temp1 = generateTempVariable();
        genOut.appendCommand("READ " + temp1);
        genOut.appendCommand("LOAD " + temp1);
        for (Token tk : node.tokenData) {
            if (tk != null && tk.getTokenType().equals("IDENTIFIER_TK")) {
                genOut.appendCommand("STACKW " + varStack.find(tk));
            }
        }
    }

    /* NO CODE GENERATION REQUIRED */
    void outputStat(ProgramNode node) {
        for (int i = 0; i < node.children.length; i++) { //Check children L -> R
            if (node.children[i] != null) {
                treePreorderGeneratorTraversal(node.children[i]);
            }
        }
    }

    void outputMStat(ProgramNode node) {
        for (int i = 0; i < node.children.length; i++) { //Check children L -> R
            if (node.children[i] != null) {
                treePreorderGeneratorTraversal(node.children[i]);
            }
        }
    }

    void outputStats(ProgramNode node) {
        for (int i = 0; i < node.children.length; i++) { //Check children L -> R
            if (node.children[i] != null) {
                treePreorderGeneratorTraversal(node.children[i]);
            }
        }
    }

    void outputR(ProgramNode node) {
        for (Token tk : node.tokenData) {
            if (tk != null && tk.getTokenType().equals("IDENTIFIER_TK")) {
                genOut.appendCommand("STACKR " + varStack.find(tk));
            } else if (tk != null && tk.getTokenType().equals("NUMBER_TK")) {
                genOut.appendCommand("LOAD " + tk.getTokenValue());
            }
        }

        for (int i = 0; i < node.children.length; i++) { //Check children L -> R
            if (node.children[i] != null) {
                treePreorderGeneratorTraversal(node.children[i]);
            }
        }
    }

    void outputM(ProgramNode node) {
        for (int i = 0; i < node.children.length; i++) { //Check children L -> R
            if (node.children[i] != null) {
                treePreorderGeneratorTraversal(node.children[i]);
            }
        }
    }

    void outputNFactor(ProgramNode node) {
        for (int i = 0; i < node.children.length; i++) { //Check children L -> R
            if (node.children[i] != null) {
                treePreorderGeneratorTraversal(node.children[i]);
            }
        }
    }

    void outputN(ProgramNode node) {
        for (int i = 0; i < node.children.length; i++) { //Check children L -> R
            if (node.children[i] != null) {
                treePreorderGeneratorTraversal(node.children[i]);
            }
        }
    }

    void outputA(ProgramNode node) {
        for (int i = 0; i < node.children.length; i++) { //Check children L -> R
            if (node.children[i] != null) {
                treePreorderGeneratorTraversal(node.children[i]);
            }
        }
    }

    void outputExprFactor(ProgramNode node) {
        for (int i = 0; i < node.children.length; i++) { //Check children L -> R
            if (node.children[i] != null) {
                treePreorderGeneratorTraversal(node.children[i]);
            }
        }
    }

    void outputExpr(ProgramNode node) {
        for (int i = 0; i < node.children.length; i++) { //Check children L -> R
            if (node.children[i] != null) {
                treePreorderGeneratorTraversal(node.children[i]);
            }
        }
    }

    void outputVars(ProgramNode node) {
        for (Token tk : node.tokenData) { //For each token
            if (tk != null && tk.getTokenType().equals("NUMBER_TK")) { //Get the identifier token
                outputPush();
                genOut.appendCommand("LOAD " + tk.getTokenValue());
                genOut.appendCommand("STACKW 0");
            }
        }
    }

    /* NO CODE GENERATION REQUIRED */
    void outputBlock(ProgramNode node) {
    }

    void outputProgram(ProgramNode node) {
        for (int i = 0; i < node.children.length; i++) { //Check children L -> R
            if (node.children[i] != null) {
                treePreorderGeneratorTraversal(node.children[i]);
            }
        }
    }

    void outputPush() {
        genOut.appendCommand("PUSH");
    }
}

class TempVariableGenerator {
    private static int currentIter = 0;
    private static List<String> tempVars = new ArrayList<String>();

    static String generateTempVar() {
        String currentTemp = "Tempvar" + currentIter++;
        tempVars.add(currentTemp);
        return currentTemp;
    }

    static String getFormattedTempVars() {
        StringBuilder total = new StringBuilder();
        for (String s : tempVars) total.append(s).append(" 0\n");

        return total.toString();
    }
}
