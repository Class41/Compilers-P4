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

    /* NO CODE GENERATION REQUIRED */
    void outputMStat(ProgramNode node) {
        for (int i = 0; i < node.children.length; i++) { //Check children L -> R
            if (node.children[i] != null) {
                treePreorderGeneratorTraversal(node.children[i]);
            }
        }
    }

    /* NO CODE GENERATION REQUIRED */
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
        int Mpos = -1, Rpos = -1;

        for (int i = 0; i < node.children.length; i++) {
            if (node.children[i] != null && node.children[i].getNodeLabel().equals("<M>")) {
                Mpos = i;
            } else if (node.children[i] != null && node.children[i].getNodeLabel().equals("<R>")) {
                Rpos = i;
            }
        }

        if (Mpos > -1) {
            treePreorderGeneratorTraversal(node.children[Mpos]);
            genOut.appendCommand("MULT -1");
        } else if (Rpos > -1) {
            treePreorderGeneratorTraversal(node.children[Rpos]);
        }
    }

    void outputNFactor(ProgramNode node) {
        int Npos = -1;

        for (int i = 0; i < node.children.length; i++) { //Check children L -> R
            if (node.children[i] != null && node.children[i].getNodeLabel().equals("<N>")) {
                Npos = i;
            }
        }

        if (Npos > -1) {
            treePreorderGeneratorTraversal(node.children[Npos]);
        }

    }

    void outputN(ProgramNode node) {
        int Mpos = -1, NFactorpos = -1;
        String operator = null;

        for (int i = 0; i < node.children.length; i++) { //Check children L -> R
            if (node.children[i] != null && node.children[i].getNodeLabel().equals("<M>")) {
                Mpos = i;
            } else if (node.children[i] != null && node.children[i].getNodeLabel().equals("<NFactor>")) {
                NFactorpos = i;
            }
        }
        if (NFactorpos > -1) {
            treePreorderGeneratorTraversal(node.children[NFactorpos]);
            for (int i = 0; i < node.children[NFactorpos].children.length; i++) { //Check children L -> R
                if (node.children[NFactorpos].children[i] != null && node.children[NFactorpos].children[i].getNodeLabel().equals("<N>")) {
                    for (Token t : node.children[NFactorpos].tokenData) {
                        if (t != null && (t.getTokenType().equals("MULT_TK") || t.getTokenType().equals("DIVIDE_TK"))) {
                            operator = t.getTokenType();
                        }
                    }
                }
            }


        }
        String temp1 = generateTempVariable();
        genOut.appendCommand("STORE " + temp1);

        if (Mpos > -1)
            treePreorderGeneratorTraversal(node.children[Mpos]);


        if (operator != null) {
            if (operator.equals("MULT_TK")) {
                genOut.appendCommand("MULT " + temp1);
            } else {
                genOut.appendCommand("DIV " + temp1);
            }
        }
    }

    void outputA(ProgramNode node) {
        int Apos = -1, Npos = -1;

        for (int i = 0; i < node.children.length; i++) { //Check children L -> R
            if (node.children[i] != null && node.children[i].getNodeLabel().equals("<A>")) {
                Apos = i;
            } else if (node.children[i] != null && node.children[i].getNodeLabel().equals("<N>")) {
                Npos = i;
            }
        }

        if (Apos > -1) {
            treePreorderGeneratorTraversal(node.children[Apos]);
            String temp1 = generateTempVariable();
            genOut.appendCommand("STORE " + temp1);
            treePreorderGeneratorTraversal(node.children[Npos]);
            genOut.appendCommand("SUB " + temp1);
        } else {
            treePreorderGeneratorTraversal(node.children[Npos]);
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
        int factor = -1;
        for (int i = 0; i < node.children.length; i++) { //Check children L -> R
            if (node.children[i] != null && node.children[i].getNodeLabel().equals("<ExprFactor>")) {
                factor = i;
            }
        }

        for (int i = 0; i < node.children.length; i++) { //Check children L -> R
            if (node.children[i] != null && node.children[i].getNodeLabel().equals("<A>")) {
                if (factor > -1) {
                    treePreorderGeneratorTraversal(node.children[factor]);
                    String temp1 = generateTempVariable();
                    genOut.appendCommand("STORE " + temp1);
                    treePreorderGeneratorTraversal(node.children[i]);
                    genOut.appendCommand("ADD " + temp1);
                } else {
                    treePreorderGeneratorTraversal(node.children[i]);
                }
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

    /* NO CODE GENERATION REQUIRED */
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
