package compiler.exception;

import compiler.basic.Token;

public class SemanticAnalysisException extends Exception{
    public SemanticAnalysisException(Token token){
        super("语法分析错误,原因："+token.value);
    }
    public SemanticAnalysisException(String s){
        super(s);
    }
}
