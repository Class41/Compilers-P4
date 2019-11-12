/*
Author: Vasyl Onufriyev
Date: 11.8.2019
Class: CS4280
Instructor: Professor Janikow
Description: Wrapper on standard stack to deal with scoping
*/

package com.umsl.vasylonufriyev.StaticSemantics;

import com.umsl.vasylonufriyev.DataStructures.Token;

import java.util.Stack;

class StaticStack {
    private Stack<Token> varStack = new Stack<Token>();

    void push(Token tk) throws Exception {
        if(varStack.size() == 100)
            throw new Exception("StaticStack: Overflow! Too many variables stored on stack!");
        varStack.push(tk);
    }

    void pop() {
        varStack.pop();
    }

    int find(Token tk) {
        int stackPos = varStack.size() - 1;
        for (; stackPos >= 0; stackPos-- ) {
            if(varStack.get(stackPos).getTokenValue().equals(tk.getTokenValue()))
            {
                return varStack.size() - 1 - stackPos;
            }
        }
        return -1;
    }
}
