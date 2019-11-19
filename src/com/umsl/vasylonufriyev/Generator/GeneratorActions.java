package com.umsl.vasylonufriyev.Generator;

import com.umsl.vasylonufriyev.DataStructures.ProgramNode;
import com.umsl.vasylonufriyev.DataStructures.Token;
import com.umsl.vasylonufriyev.StaticSemantics.StaticStack;

import java.util.ArrayList;
import java.util.List;

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
    }

    void outputROFactorGT(ProgramNode node) {
    }

    void outputRO(ProgramNode node) {
    }

    void outputAssign(ProgramNode node) {
    }

    void outputLoop(ProgramNode node) {
    }

    void outputIf(ProgramNode node) {
    }

    void outputOut(ProgramNode node) {
        String temp1 = generateTempVariable();
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
    }

    void outputMStat(ProgramNode node) {
    }

    void outputStats(ProgramNode node) {
    }

    void outputR(ProgramNode node) {
        for (Token tk : node.tokenData) {
            if (tk != null && tk.getTokenType().equals("IDENTIFIER_TK")) {
                genOut.appendCommand("STACKR " + varStack.find(tk));
            } else if (tk != null && tk.getTokenType().equals("NUMBER_TK")) {
                genOut.appendCommand("LOAD " + tk.getTokenValue());
            }
        }
    }

    void outputM(ProgramNode node) {
    }

    void outputNFactor(ProgramNode node) {
    }

    void outputN(ProgramNode node) {
    }

    void outputA(ProgramNode node) {
        /*for (Token tk : node.tokenData) {
            if (tk != null && tk.getTokenType().equals("MINUS_TK")) {
                genOut.appendCommand("SUB Temporary");
            }
        }*/
    }

    void outputExprFactor(ProgramNode node) {
    }

    void outputExpr(ProgramNode node) {
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
