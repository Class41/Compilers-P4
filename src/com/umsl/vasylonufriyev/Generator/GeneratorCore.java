package com.umsl.vasylonufriyev.Generator;

import com.umsl.vasylonufriyev.DataStructures.ProgramNode;

public class GeneratorCore {
    private GeneratorActions genActions;

    public GeneratorCore(boolean usingFile) {
        genActions = new GeneratorActions(usingFile);
    }

    public void generateCodeForNode(ProgramNode node) {
        switch (node.getNodeLabel()) {
            case "<Program>":
                genActions.outputProgram(node);
                break;
            case "<Block>":
                genActions.outputBlock(node);
                break;
            case "<Vars>":
                genActions.outputVars(node);
                break;
            case "<Expr>":
                genActions.outputExpr(node);
                break;
            case "<ExprFactor>":
                genActions.outputExprFactor(node);
                break;
            case "<A>":
                genActions.outputA(node);
                break;
            case "<N>":
                genActions.outputN(node);
                break;
            case "<NFactor>":
                genActions.outputNFactor(node);
                break;
            case "<M>":
                genActions.outputM(node);
                break;
            case "<R>":
                genActions.outputR(node);
                break;
            case "<Stats>":
                genActions.outputStats(node);
                break;
            case "<MStat>":
                genActions.outputMStat(node);
                break;
            case "<Stat>":
                genActions.outputStat(node);
                break;
            case "<In>":
                genActions.outputIn(node);
                break;
            case "<Out>":
                genActions.outputOut(node);
                break;
            case "<If>":
                genActions.outputIf(node);
                break;
            case "<Loop>":
                genActions.outputLoop(node);
                break;
            case "<Assign>":
                genActions.outputAssign(node);
                break;
            case "<RO>":
                genActions.outputRO(node);
                break;
            case "<ROFactorGT>":
                genActions.outputROFactorGT(node);
                break;
            case "<ROFactorLT>":
                genActions.outputROFactorLT(node);
                break;
            default:
                System.out.println("Unrecognized token: " + node.getNodeLabel());
                System.exit(-255);
        }
    }

    public void generatePop() {
        genActions.outputPop();
    }
}
