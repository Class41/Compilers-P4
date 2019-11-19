package com.umsl.vasylonufriyev.Generator;

import com.umsl.vasylonufriyev.DataStructures.ProgramNode;
import com.umsl.vasylonufriyev.DataStructures.Token;
import com.umsl.vasylonufriyev.StaticSemantics.StaticStack;

class GeneratorActions {

    private final GeneratorOutput genOut;
    private final StaticStack varStack;

    GeneratorActions(boolean usingFile, StaticStack stack) {
        this.varStack = stack;
        this.genOut = new GeneratorOutput(usingFile);
    }

    void generateFile() {
        genOut.finalizeAndWrite();
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
        genOut.appendCommand("WRITE Temporary");
    }

    void outputIn(ProgramNode node) {
        genOut.appendCommand("READ Temporary");
        genOut.appendCommand("LOAD Temporary");
        for (Token tk : node.tokenData) {
            if (tk != null && tk.getTokenType().equals("IDENTIFIER_TK")) {
                genOut.appendCommand("STACKW " + varStack.find(tk));
            }
        }
    }

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
                genOut.appendCommand("STORE Temporary");
            } else if (tk != null && tk.getTokenType().equals("NUMBER_TK")) {
                genOut.appendCommand("LOAD " + tk.getTokenValue());
                genOut.appendCommand("STORE Temporary");
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

    void outputBlock(ProgramNode node) {
    }

    void outputProgram(ProgramNode node) {
    }

    void outputPush() {
        genOut.appendCommand("PUSH");
    }
}
