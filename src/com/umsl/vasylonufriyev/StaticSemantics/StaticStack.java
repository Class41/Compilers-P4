package com.umsl.vasylonufriyev.StaticSemantics;

import com.umsl.vasylonufriyev.DataStructures.Token;

import java.util.Stack;

public class StaticStack {
    private Stack<Token> varStack = new Stack<Token>();

    public void push(Token tk) throws Exception {
        if(varStack.size() == 100)
            throw new Exception("StaticStack: Overflow! Too many variables stored on stack!");
        varStack.push(tk);
    }

    public Token pop() {
        return varStack.pop();
    }

    public int find(Token tk) {
        int stackPos = 0;
        for ( Token stackItem : varStack ) {
            if(stackItem.getTokenValue().equals(tk.getTokenValue()))
            {
                return stackPos;
            }
            stackPos++;
        }
        return -1;
    }
}
