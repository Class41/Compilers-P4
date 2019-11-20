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

    private String generateTempVariable() {
        return TempVariableGenerator.generateTempVar();
    }

    private String generateUniqueAnchor() {
        return TempVariableGenerator.generateUniqueAnchor();
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
        int Exprpos = -1;

        for (int i = 0; i < node.children.length; i++) { //Check children L -> R
            if (node.children[i] != null && node.children[i].getNodeLabel().equals("<Expr>")) {
                Exprpos = i;
            }
        }

        for (int i = 0; i < node.tokenData.length; i++) {
            if (node.tokenData[i] != null && node.tokenData[i].getTokenType().equals("IDENTIFIER_TK")) {
                treePreorderGeneratorTraversal(node.children[Exprpos]);
                genOut.appendCommand("STACKW " + varStack.find(node.tokenData[i]));
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
        int Expr1pos = -1, ROpos = -1, Expr2pos = -1, Statpos = -1;

        for (int i = 0; i < node.children.length; i++) { //Check children L -> R
            if (node.children[i] != null && node.children[i].getNodeLabel().equals("<Expr>")) {
                Expr1pos = i;
                for (int j = i + 1; j < node.children.length; j++) { //Check children L -> R
                    if (node.children[j] != null && node.children[j].getNodeLabel().equals("<RO>")) {
                        ROpos = j;
                        for (int k = j + 1; k < node.children.length; k++) { //Check children L -> R
                            if (node.children[k] != null && node.children[k].getNodeLabel().equals("<Expr>")) {
                                Expr2pos = k;
                                for (int l = k + 1; l < node.children.length; l++) { //Check children L -> R
                                    if (node.children[l] != null && node.children[l].getNodeLabel().equals("<Stat>")) {
                                        Statpos = l;
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        break;
                    }
                }
                break;
            }
        }

        String temp1 = generateTempVariable();
        String temp2 = generateTempVariable();

        if (Expr1pos > -1)
            treePreorderGeneratorTraversal(node.children[Expr1pos]);
        genOut.appendCommand("STORE " + temp1);

        if (Expr2pos > -1)
            treePreorderGeneratorTraversal(node.children[Expr2pos]);
        genOut.appendCommand("STORE " + temp2);

        StringBuilder operator = new StringBuilder();

        if (ROpos > -1) {
            for (Token t : node.children[ROpos].tokenData) {
                if (t != null) {
                    operator.append(t.getTokenValue());
                }
            }

            for (ProgramNode pn : node.children[ROpos].children) {
                if (pn != null) {
                    for (Token t : pn.tokenData) {
                        if (t != null) {
                            operator.append(t.getTokenValue());
                        }
                    }
                }
            }
        }

        if (operator.toString().equals("<>")) {
            String anchor1 = generateUniqueAnchor();
            genOut.appendCommand("LOAD " + temp1);
            genOut.appendCommand("SUB " + temp2);
            genOut.appendCommand("BRZERO " + anchor1);
            if (Statpos > -1)
                treePreorderGeneratorTraversal(node.children[Statpos]);
            genOut.appendCommand(anchor1 + ": NOOP");
        } else if (operator.toString().equals("=")) {
            String anchor1 = generateUniqueAnchor();
            genOut.appendCommand("LOAD " + temp1);
            genOut.appendCommand("SUB " + temp2);
            genOut.appendCommand("BRPOS " + anchor1);
            genOut.appendCommand("BRNEG " + anchor1);
            if (Statpos > -1)
                treePreorderGeneratorTraversal(node.children[Statpos]);
            genOut.appendCommand(anchor1 + ": NOOP");
        } else if (operator.toString().equals("<")) {
            String anchor1 = generateUniqueAnchor();
            genOut.appendCommand("LOAD " + temp1);
            genOut.appendCommand("SUB " + temp2);
            genOut.appendCommand("BRZPOS " + anchor1);
            if (Statpos > -1)
                treePreorderGeneratorTraversal(node.children[Statpos]);
            genOut.appendCommand(anchor1 + ": NOOP");
        } else if (operator.toString().equals(">")) {
            String anchor1 = generateUniqueAnchor();
            genOut.appendCommand("LOAD " + temp1);
            genOut.appendCommand("SUB " + temp2);
            genOut.appendCommand("BRZNEG " + anchor1);
            if (Statpos > -1)
                treePreorderGeneratorTraversal(node.children[Statpos]);
            genOut.appendCommand(anchor1 + ": NOOP");
        } else if (operator.toString().equals(">>")) {
            String anchor1 = generateUniqueAnchor();
            genOut.appendCommand("LOAD " + temp1);
            genOut.appendCommand("SUB " + temp2);
            genOut.appendCommand("BRNEG " + anchor1);
            if (Statpos > -1)
                treePreorderGeneratorTraversal(node.children[Statpos]);
            genOut.appendCommand(anchor1 + ": NOOP");
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

        if (Npos > -1) treePreorderGeneratorTraversal(node.children[Npos]);
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

    /* NO CODE GENERATION REQUIRED. HANDLED IN EXPR*/
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
    private static int currentAnchor = 0;
    private static List<String> tempVars = new ArrayList<String>();

    static String generateTempVar() {
        String currentTemp = "Tempvar" + currentIter++;
        tempVars.add(currentTemp);
        return currentTemp;
    }

    static String generateUniqueAnchor() {
        return "Anchor" + currentAnchor++;
    }

    static String getFormattedTempVars() {
        StringBuilder total = new StringBuilder();
        for (String s : tempVars) total.append(s).append(" 0\n");

        return total.toString();
    }


}
