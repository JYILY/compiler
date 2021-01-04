package compiler;

import com.sun.org.apache.xpath.internal.operations.Bool;
import compiler.basic.Syn;
import compiler.basic.Token;
import compiler.symbol.Symbol;
import compiler.symbol.VN;
import compiler.symbol.VT;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * @author 苏富宝
 */
public class Utils {
    private static Map<Integer, Integer> isLast = new HashMap<>();

    private static final char[] legalSymbol = {'(', ')', '[', ']', '{', '}', ',', ';', '+', '-', '*', '/'};

    // 预处理代码,删除#include语句，删除 注释语句
    public static char[] preprocess(char[] code) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < code.length; i++) {
            if (code[i] == '#' || (code[i] == '/' && i < code.length - 1 && code[i + 1] == '/')) {
                while (i < code.length && code[i] != '\n') {
                    i++;
                }
                i--;
            } else {
                sb.append(code[i]);
            }
        }
        return sb.toString().toCharArray();
    }

    public static char[] getCode(String path) {
        String code = "";
        try {
            File file = new File(path);
            FileReader fr = new FileReader(file);
            StringBuilder sb = new StringBuilder();
            char[] buf = new char[1024];
            int len;
            while ((len = fr.read(buf)) != -1) {
                sb.append(buf, 0, len);
            }
            sb.append('\0');
            code = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return code.toCharArray();
    }

    public static boolean isLetter(char ch) {
        return Character.isLetter(ch);
    }

    public static boolean isDigit(char ch) {
        return Character.isDigit(ch);
    }

    public static boolean isLegalSymbol(char ch) {
        for (char s : legalSymbol) {
            if (ch == s) {
                return true;
            }
        }
        return false;
    }

    public static boolean isCmp(char ch) {
        return ch == '>' || ch == '<' || ch == '=';
    }

    public static Token getToken(String value) {
        for (Syn syn : Syn.values()) {
            if (syn.value.equals(value)) {
                return new Token(syn.syn, value);
            }
        }
        return new Token(-1, "ERROR");
    }

    public static boolean canNext(char l, char r) {
        if ((l == '[' && r == ']') || (l == '(' && r == ')') || (l == '{' && r == '}')) return true;
        if (l == ')' && (r == '{' || r == '+' || r == '-' || r == '*' || r == '/' || r == ')' || r == ';')) return true;
        return r == '(' && (l == '+' || l == '-' || l == '*' || l == '/' || l == '(');
    }

    private static void forward(int fs) {
        while (fs >= 0) {
            System.out.print(" ");
            fs--;
        }
    }

    public static void showVN(VN vn) {
        showVN(vn, 0);
    }

    private static void showVN(VN vn, int d) {
        List<Symbol> symbols = vn.symbolList;
        System.out.println("\033[1;31;1m" + vn.name + "\033[0m");
        for (int i = 0; i < symbols.size(); i++) {
            for (int j = 0; j < d; j++) {
                forward(2);
                if (isLast.get(j) == 0) {
                    System.out.print("│");
                }
            }
            forward(2);
            if (i == symbols.size() - 1) {
                System.out.print("└─");
            } else {
                System.out.print("├─");
            }
            if (symbols.get(i) instanceof VT) {
                StringBuilder sb = new StringBuilder();
                while (symbols.get(i) instanceof VT) {
                    sb.append(" ");
                    sb.append(((VT) symbols.get(i++)).value);
                    if (i == symbols.size()) {
                        break;
                    }
                }
                i--;
                System.out.println("\033[1;33;1m" + sb + "\033[0m");
            } else {
                if (i == symbols.size() - 1) {
                    isLast.put(d, 1);
                } else {
                    isLast.put(d, 0);
                }
                showVN((VN) symbols.get(i), d + 1);
            }
        }
    }

    public static String toBlue(String s) {
        return "\033[0;35;1m" + s + "\033[0m";
    }

    public static String toRed(String s) {
        return "\033[1;31;1m" + s + "\033[0m";
    }

    public static String highlightNum(String s){
        return "\033[1;33;1m" + s + "\033[0m";
    }

    public static String highlightString(String s){
        return "\033[1;34;1m" + s + "\033[0m";
    }

    public static String highlightKeyWord(String s){
        return "\033[1;32;1m" + s + "\033[0m";
    }


    public static VT getOppositeCmp(VT c) {
        if (c.value.equals(">")) return new VT(new Token(Syn.LE));
        if (c.value.equals("<")) return new VT(new Token(Syn.ME));
        if (c.value.equals("==")) return new VT(new Token(Syn.NE));
        if (c.value.equals("!=")) return new VT(new Token(Syn.EQ));
        if (c.value.equals("<=")) return new VT(new Token(Syn.LG));
        if (c.value.equals(">=")) return new VT(new Token(Syn.LT));
        return new VT(new Token(Syn.ERROR));
    }
}
