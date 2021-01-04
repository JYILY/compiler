package compiler;

import compiler.basic.Token;
import compiler.exception.LexicalAnalysisException;
import compiler.exception.SemanticAnalysisException;
import compiler.symbol.VN;
import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * @author 苏富宝
 */
public class Main {

    public static void showTitle() {
        System.out.println();
        System.out.println("-----------------------------------------");
        System.out.println("               " +
                Utils.highlightNum("简易版编译器") +
                "                ");
        System.out.println("-----------------------------------------");
        System.out.println("   Author : " +
                Utils.highlightNum("苏富宝") +
                "   学号 : " +
                Utils.highlightNum("201827020119") +
                "   ");
        System.out.println("-----------------------------------------");
    }

    public static void showFunction() {
        String l = "***********";
        String help = l + Utils.toRed("输入标号执行相对应的功能") + l;
        System.out.println(help);
        System.out.println("[0] -->  退出程序");
        System.out.println("[1] -->  打开文件");
        System.out.println("[2] -->  词法分析");
        System.out.println("[3] -->  语法分析");
        System.out.println("[4] -->  语义分析");
        System.out.println("[5] -->  刷新文件");

    }

    public static void warn(String s) {
        System.out.println(Utils.toRed(s));
    }

    public static void tip(String s) {
        System.out.println(Utils.highlightKeyWord(s));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        showTitle();
        String codePath = "";
        boolean hasFile = false;
        char[] code = null;
        while (true) {
            showFunction();
            int i = scanner.nextInt();
            switch (i) {
                case 0:
                    return;
                case 1:
                    codePath = openFileUI();
                    hasFile = true;
                    code = openFile(codePath);
                    tip("成功打开文件");
                    break;
                case 2:
                    if (!hasFile) {
                        warn("你还没打开文件！");
                    } else {
                        lexicalAnalysis(code);
                    }
                    break;
                case 3:
                    if (!hasFile) {
                        warn("你还没打开文件！");
                    } else {
                        semanticAnalysis(code);
                    }
                    break;
                case 4:
                    if (!hasFile) {
                        warn("你还没打开文件！");
                    } else {
                        grammarAnalysis(code);
                    }
                    break;
                case 5:
                    if (hasFile) {
                        code = openFile(codePath);
                    }
                    break;

            }
        }
    }

    public static String openFileUI() {
        while (true) {
            System.out.print(Utils.highlightKeyWord("请输入文件路径") + ":");
            Scanner scanner = new Scanner(System.in);
            String path = scanner.nextLine();
            File file = new File(path);
            if (file.exists()) {
                return path;
            }
            warn("文件不存在!");
        }


    }

    public static char[] openFile(String path) {
        char[] code = Utils.getCode(path);
        showCode(code);
        return Utils.preprocess(code);
    }

    public static void showCode(char[] code) {
        int lineCount = 1;
        System.out.println("-----------------------");
        System.out.print("[1]  ");
        for (char ch : code) {
            System.out.print(ch);
            if (ch == '\n') {
                lineCount = lineCount + 1;
                System.out.print("[" + lineCount + "]  ");
            }
        }
        System.out.println();
        System.out.println("-----------------------");
    }

    public static void lexicalAnalysis(char[] code) {
        try {
            Lexical lexical = new Lexical(code);
            List<Token> result = lexical.getResult();
            System.out.println("--------------------------");
            System.out.println("       词法分析结果         ");
            System.out.println("--------------------------");
            for (Token token : result) {
                System.out.println(token);
            }
            tip("词法分析成功!");
        } catch (LexicalAnalysisException e) {
            warn("词法分析错误！" + e.errorMessage);
            warn("行:" + e.row + " 列:" + e.col);
        }
    }

    public static void semanticAnalysis(char[] code) {
        try {
            Lexical lexical = new Lexical(code);
            List<Token> result = lexical.getResult();
            tip("词法分析成功!");
            Semantic semantic = new Semantic();
            VN program = semantic.getProgram(result);
            Utils.showVN(program);
            tip("语法分析成功!");
        } catch (LexicalAnalysisException e) {
            warn("词法分析错误！" + e.errorMessage);
            warn("行:" + e.row + " 列:" + e.col);
        } catch (SemanticAnalysisException e) {
            warn(e.getMessage());
        }
    }

    public static void grammarAnalysis(char[] code) {
        try {
            Lexical lexical = new Lexical(code);
            List<Token> result = lexical.getResult();
            tip("词法分析成功!");
            Semantic semantic = new Semantic();
            VN program = semantic.getProgram(result);
            tip("语法分析成功!");
            Grammar grammar = new Grammar();
            tip("语义分析成功!");
            List<String> translation = grammar.translate(program);
            for (String s : translation) {
                System.out.println(s);
            }
        } catch (LexicalAnalysisException e) {
            warn("词法分析错误！" + e.errorMessage);
            warn("行:" + e.row + " 列:" + e.col);
        } catch (SemanticAnalysisException e) {
            warn(e.getMessage());
        }
    }

}
