package com.umsl.vasylonufriyev.Generator;

import com.umsl.vasylonufriyev.DataStructures.ProgramNode;
import com.umsl.vasylonufriyev.StaticSemantics.StaticStack;

public class GeneratorCore {
    private GeneratorActions genActions;
    private boolean usingFile;

    public GeneratorCore(boolean usingFile) {
        this.usingFile = usingFile;
    }

    public void generateCodeForNode(ProgramNode node, int ordermode) { //0 = preorder, 1 = inorder, 2 = postorder
        switch (node.getNodeLabel()) {
            case "<Program>":
                if (ordermode == 0)
                    genActions.outputProgram(node);
                break;
            case "<Block>":
                if (ordermode == 0)
                    genActions.outputBlock(node);
                break;
            case "<Vars>":
                if (ordermode == 0)
                    genActions.outputVars(node);
                break;
            case "<Expr>":
                if (ordermode == 0)
                    genActions.outputExpr(node);
                break;
            case "<ExprFactor>":
                if (ordermode == 0)
                    genActions.outputExprFactor(node);
                break;
            case "<A>":
                if (ordermode == 0)
                    genActions.outputA(node);
                break;
            case "<N>":
                if (ordermode == 0)
                    genActions.outputN(node);
                break;
            case "<NFactor>":
                if (ordermode == 0)
                    genActions.outputNFactor(node);
                break;
            case "<M>":
                if (ordermode == 0)
                    genActions.outputM(node);
                break;
            case "<R>":
                if (ordermode == 0)
                    genActions.outputR(node);
                break;
            case "<Stats>":
                if (ordermode == 0)
                    genActions.outputStats(node);
                break;
            case "<MStat>":
                if (ordermode == 0)
                    genActions.outputMStat(node);
                break;
            case "<Stat>":
                if (ordermode == 0)
                    genActions.outputStat(node);
                break;
            case "<In>":
                if (ordermode == 0)
                    genActions.outputIn(node);
                break;
            case "<Out>":
                if (ordermode == 2)
                    genActions.outputOut(node);
                break;
            case "<If>":
                if (ordermode == 0)
                    genActions.outputIf(node);
                break;
            case "<Loop>":
                if (ordermode == 0)
                    genActions.outputLoop(node);
                break;
            case "<Assign>":
                if (ordermode == 0)
                    genActions.outputAssign(node);
                break;
            case "<RO>":
                if (ordermode == 0)
                    genActions.outputRO(node);
                break;
            case "<ROFactorGT>":
                if (ordermode == 0)
                    genActions.outputROFactorGT(node);
                break;
            case "<ROFactorLT>":
                if (ordermode == 0)
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

    public void generatePush() {
        genActions.outputPush();
    }

    public void generateFile() {
        genActions.generateFile();
    }

    public void setStaticStackReference(StaticStack stack) {
        genActions = new GeneratorActions(usingFile, stack);
    }
}
