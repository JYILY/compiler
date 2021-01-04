package compiler.symbol;

import compiler.basic.Token;

public class VT implements Symbol {

    public String value;
    public int syn;

    public VT(Token token) {
        this.syn = token.syn;
        this.value = token.value;
    }

}
