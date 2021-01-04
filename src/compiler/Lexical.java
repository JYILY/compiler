package compiler;

import compiler.basic.Syn;
import compiler.basic.Token;
import compiler.exception.LexicalAnalysisException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 苏富宝
 * <p>
 * 根据字母表，可以得知
 * 一共有九个状态，
 * 一共有6种输入类型 EOF(0)  letter(1)   num(2)    symbol(3)    ">,<"(4)   "="(5)  "分隔符"(6)  "!"(7)
 */
public class Lexical {
    private int gnRow = 0;
    private int gnCol = 0;
    private int gnLocate = 0;
    private char[] code;
    private StringBuilder sb;
    private List<Token> result = new ArrayList<>();
    private boolean isError = false;
    private String errorString = "";

    public Lexical(char[] code) {
        this.code = code;
    }

    private void start() {
        while (true) {
            sb = new StringBuilder();
            char ch = code[gnLocate];
            sb.append(ch);
            gnLocate++;
            gnCol++;
            if (ch == '\r') {
                gnCol--;
            } else if (ch == '\t') {
                gnCol += 3;
            } else if (ch == '\n') {
                gnCol = 0;
                gnRow++;
            } else if (ch == '\0') {
                result.add(new Token(Syn.END));
                break;
            } else if (ch == '!') {
                ch = code[gnLocate];
                if (ch == '=') {
                    Token token = new Token(Syn.NE);
                    result.add(token);
                    gnLocate++;
                    gnCol++;
                } else {
                    error("符号'!'使用错误,只能用作!=");
                    return;
                }
            } else if (Utils.isCmp(ch)) {
                char nextCh = code[gnLocate];
                if (nextCh == '=') {
                    sb.append(nextCh);
                    gnLocate++;
                    gnCol++;
                    if (ch == '>') result.add(new Token(Syn.ME));
                    if (ch == '<') result.add(new Token(Syn.LE));
                    if (ch == '=') result.add(new Token(Syn.EQ));
                } else {
                    if ((Utils.isLegalSymbol(nextCh) && ch != '(') || Utils.isCmp(nextCh) || nextCh == '!') {
                        cannotNext(ch, nextCh);
                        return;
                    } else {
                        if (ch == '>') result.add(new Token(Syn.LG));
                        if (ch == '<') result.add(new Token(Syn.LT));
                        if (ch == '=') result.add(new Token(Syn.ASSIGN));
                    }
                }
            } else if (Utils.isLetter(ch)) {
                while (true) {
                    ch = code[gnLocate];
                    if (Utils.isLetter(ch) || Utils.isDigit(ch)) {
                        sb.append(ch);
                        gnLocate++;
                        gnCol++;
                    } else {
                        String id = sb.toString();
                        Token token = Utils.getToken(id);
                        if (token.syn == -1) {
                            token = new Token(10, id);
                        }
                        result.add(token);
                        break;
                    }
                }
            } else if (Utils.isDigit(ch)) {
                while (true) {
                    ch = code[gnLocate];
                    if (Utils.isDigit(ch)) {
                        sb.append(ch);
                        gnLocate++;
                        gnCol++;
                    } else if (Utils.isLetter(ch)) {
                        cannotNext(code[gnLocate - 1], ch);
                        return;
                    } else {
                        String num = sb.toString();
                        result.add(new Token(20, num));
                        break;
                    }
                }
            } else if (Utils.isLegalSymbol(ch)) {
                if (Utils.isLegalSymbol(code[gnLocate]) && !Utils.canNext(ch, code[gnLocate])) {
                    cannotNext(ch, code[gnLocate]);
                    return;
                }
                String doubleCmp = sb.toString();
                Token token = Utils.getToken(doubleCmp);
                result.add(token);
            } else if (ch != ' ') {
                error("未知符号 " + ch);
                return;
            }
        }

    }

    private void cannotNext(char l, char r) {
        error("'" + r + "'" + " can follow " + "'" + l + "'");
    }

    private void error(String s) {
        errorString = s;
        isError = true;
    }

    public List<Token> getResult() throws LexicalAnalysisException {
        result = new ArrayList<>();
        gnRow = 0;
        gnCol = 0;
        gnLocate = 0;
        start();
        if (isError) {
            throw new LexicalAnalysisException(gnRow, gnCol, errorString);
        }
        return result;
    }
}
