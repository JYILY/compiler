package compiler;

import compiler.basic.Syn;
import compiler.basic.Token;
import compiler.exception.SemanticAnalysisException;
import compiler.symbol.*;

import java.util.*;

/**
 * @author 苏富宝
 */
public class Semantic {

    ListIterator<Token> tokenIterator;
    Set<String> variablesSet = new HashSet<>();
    Token nowToken = null;

    private VT wantVT(Syn syn) throws SemanticAnalysisException {
        nowToken = tokenIterator.next();
        if (nowToken.syn == syn.syn) {
            return new VT(nowToken);
        } else {
            throw new SemanticAnalysisException(nowToken);
        }
    }

    private VT wantCmp() throws SemanticAnalysisException {
        nowToken = tokenIterator.next();
        if (nowToken.syn == Syn.LG.syn ||
                nowToken.syn == Syn.LE.syn ||
                nowToken.syn == Syn.LT.syn ||
                nowToken.syn == Syn.ME.syn ||
                nowToken.syn == Syn.EQ.syn) {
            return new VT(nowToken);
        } else {
            throw new SemanticAnalysisException(nowToken);
        }
    }

    public VN getProgram(List<Token> tokens) throws SemanticAnalysisException {
        tokenIterator = tokens.listIterator();
        return program();
    }

    private VN program() throws SemanticAnalysisException {
        VN p = new VN("程序");
        p.addSymbol(wantVT(Syn.INT));
        p.addSymbol(wantVT(Syn.MAIN));
        p.addSymbol(wantVT(Syn.L_PAREN));
        p.addSymbol(wantVT(Syn.R_PAREN));
        p.addSymbol(block());
        return p;
    }

    private VN block() throws SemanticAnalysisException {
        VN k = new VN("语句块");
        k.addSymbol(wantVT(Syn.L_BRACKET));
        k.addSymbol(statementString());
        k.addSymbol(wantVT(Syn.R_BRACKET));
        return k;
    }

    private VN statementString() throws SemanticAnalysisException {
        VN ss = new VN("语句串");
        VN s;
        while ((s = statement()) != null) {
            ss.addSymbol(s);
        }
        return ss;
    }

    private VN statement() throws SemanticAnalysisException {
        nowToken = tokenIterator.next();
        if (nowToken.syn == Syn.INT.syn) {
            VN fs = new VN("赋值语句");
            fs.addSymbol(new VT(nowToken));
            fs.addSymbol(wantVT(Syn.ID));
            if (variablesSet.contains(nowToken.value)) {
                throw new SemanticAnalysisException("重复定义变量 : " + nowToken.value);
            }
            variablesSet.add(nowToken.value);
            fs.addSymbol(wantVT(Syn.ASSIGN));
            fs.addSymbol(expression());
            fs.addSymbol(wantVT(Syn.SEMICOLON));
            return fs;
        } else if (nowToken.syn == Syn.ID.syn) {
            if (!variablesSet.contains(nowToken.value)) {
                throw new SemanticAnalysisException("未定义变量 : " + nowToken.value);
            }
            VN fs = new VN("赋值语句");
            fs.addSymbol(new VT(nowToken));
            fs.addSymbol(wantVT(Syn.ASSIGN));
            fs.addSymbol(expression());
            fs.addSymbol(wantVT(Syn.SEMICOLON));
            return fs;
        } else if (nowToken.syn == Syn.IF.syn) {
            VN fs = new VN("条件语句");
            fs.addSymbol(new VT(nowToken));
            fs.addSymbol(wantVT(Syn.L_PAREN));
            fs.addSymbol(boolStatement());
            fs.addSymbol(wantVT(Syn.R_PAREN));
            fs.addSymbol(block());
            nowToken = tokenIterator.next();
            if (nowToken.syn == Syn.ELSE.syn) {
                tokenIterator.previous();
                fs.addSymbol(elseS());
                return fs;
            }
            tokenIterator.previous();
            return fs;
        } else if (nowToken.syn == Syn.WHILE.syn) {
            VN fs = new VN("循环语句");
            fs.addSymbol(new VT(nowToken));
            fs.addSymbol(wantVT(Syn.L_PAREN));
            fs.addSymbol(boolStatement());
            fs.addSymbol(wantVT(Syn.R_PAREN));
            fs.addSymbol(block());
            return fs;
        }
        tokenIterator.previous();
        return null;
    }

    private VN elseS() throws SemanticAnalysisException {
        VN e = new VN("分支语句");
        e.addSymbol(wantVT(Syn.ELSE));
        nowToken = tokenIterator.next();
        if (nowToken.syn == Syn.IF.syn) {
            tokenIterator.previous();
            e.addSymbol(statement());
        } else {
            tokenIterator.previous();
            e.addSymbol(block());
        }
        return e;
    }

    private VN expression() throws SemanticAnalysisException {
        VN e = new VN("表达式");
        e.addSymbol(item());
        while (true) {
            nowToken = tokenIterator.next();
            if (nowToken.syn == Syn.PLUS.syn) {
                e.addSymbol(new VT(nowToken));
                e.addSymbol(item());
            } else if (nowToken.syn == Syn.MINUS.syn) {
                e.addSymbol(new VT(nowToken));
                e.addSymbol(item());
            } else {
                tokenIterator.previous();
                break;
            }
        }
        return e;
    }

    private VN boolStatement() throws SemanticAnalysisException {
        VN b = new VN("布尔语句");
        b.addSymbol(expression());
        b.addSymbol(wantCmp());
        b.addSymbol(expression());
        return b;
    }

    private VN item() throws SemanticAnalysisException {
        VN i = new VN("项");
        i.addSymbol(factor());
        while (true) {
            nowToken = tokenIterator.next();
            if (nowToken.syn == Syn.DIVIDE.syn) {
                i.addSymbol(new VT(nowToken));
                i.addSymbol(factor());
            } else if (nowToken.syn == Syn.TIMES.syn) {
                i.addSymbol(new VT(nowToken));
                i.addSymbol(factor());
            } else {
                tokenIterator.previous();
                break;
            }
        }
        return i;
    }

    private VN factor() throws SemanticAnalysisException {
        VN f = new VN("因子");
        nowToken = tokenIterator.next();
        if (nowToken.syn == Syn.L_PAREN.syn) {
            f.addSymbol(new VT(nowToken));
            f.addSymbol(expression());
            f.addSymbol(wantVT(Syn.R_PAREN));
        } else if (nowToken.syn == Syn.ID.syn) {
            if (!variablesSet.contains(nowToken.value)) {
                throw new SemanticAnalysisException("未声明的变量 : " + nowToken.value);
            }
            f.addSymbol(new VT(nowToken));
        } else {
            tokenIterator.previous();
            f.addSymbol(wantVT(Syn.NUM));
        }
        return f;
    }


}
