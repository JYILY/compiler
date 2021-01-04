package compiler;

import compiler.basic.Syn;
import compiler.symbol.Symbol;
import compiler.symbol.VN;
import compiler.symbol.VT;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 苏富宝
 */
public class Grammar {
    private int count = 0;
    private int address = 0;
    private List<String> variables = new ArrayList<>();
    List<String> translation = new ArrayList<>();

    private String getName() {
        return Utils.toRed("D" + count++);
    }

    private String getAddress(int address) {
        String s = "L" + String.format("%04d", address);
        return Utils.toBlue(s);
    }

    private void add(String oneAns) {
        String s = getAddress(address++) + ": " + oneAns + ";";
        translation.add(s);
    }

    private void addIf(int index, int a, String bool, int address) {
        String s = Utils.highlightKeyWord("if") + "(" + bool + ") " + Utils.highlightKeyWord("goto") + " " + getAddress(address);

        addAgain(s, index, a);
    }

    private void addAgain(String oneAns, int index, int address) {
        String p = getAddress(address) + ": ";
        String s = p + oneAns + ";";
        translation.set(index, s);
    }

    List<String> translate(VN vn) {
        VN k = (VN) vn.symbolList.get(4);
        translation.add("");
        translation.add("");
        translateBlock(k);
        add("");
        StringBuilder sb1 = new StringBuilder();
        sb1.append(Utils.highlightKeyWord("int"));
        sb1.append(" ");
        for (int i = 0; i < count; i++) {
            String s = Utils.toRed("D" + i);
            sb1.append(s);
            if (i != count - 1) {
                sb1.append(" , ");
            }
        }
        sb1.append(" = ");
        sb1.append(Utils.highlightNum("" + 0));
        sb1.append(";");
        translation.set(0, sb1.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append(Utils.highlightKeyWord("int"));
        sb2.append(" ");
        int len = variables.size();
        for (int i = 0; i < len; i++) {
            sb2.append(Utils.highlightString(variables.get(i)));
            if (i != len - 1) {
                sb2.append(" , ");
            }
        }
        sb2.append(" = ");
        sb2.append(Utils.highlightNum("" + 0));
        sb2.append(";");
        translation.set(1, sb2.toString());
        return translation;
    }

    private void translateBlock(VN vn) {
        VN c = (VN) vn.symbolList.get(1);
        for (Symbol symbol : c.symbolList) {
            VN statement = (VN) symbol;
            switch (statement.name) {
                case "赋值语句":
                    translateDefineStatement(statement);
                    break;
                case "条件语句":
                    translateConditionStatement(statement);
                    break;
                case "循环语句":
                    translateWhileStatement(statement);
                    break;
            }
        }
    }

    private void translateDefineStatement(VN vn) {
        List<Symbol> symbols = vn.symbolList;
        VT vt = (VT) symbols.get(0);
        if (vt.syn == Syn.INT.syn) {
            variables.add(((VT) symbols.get(1)).value);
            String e = translateExpression((VN) symbols.get(3));
            String value = Utils.highlightString(((VT) symbols.get(1)).value);
            add(value + " = " + e);
        } else {
            String e = translateExpression((VN) symbols.get(2));
            String value = Utils.highlightString(((VT) symbols.get(0)).value);
            add(value + " = " + e);
        }
    }

    private String translateExpression(VN vn) {
        List<Symbol> symbols = vn.symbolList;
        String i = translateItem((VN) symbols.get(0));
        if (symbols.size() == 1) {
            return i;
        } else {
            String name = null;
            for (int k = 2; k < symbols.size(); k += 2) {
                String j = translateItem((VN) symbols.get(k));
                name = getName();
                String ans = name + " = " + i + " " + ((VT) symbols.get(k - 1)).value + " " + j;
                i = name;
                add(ans);
            }
            return name;
        }
    }

    private String translateItem(VN vn) {
        List<Symbol> symbols = vn.symbolList;
        String f = translateFactor((VN) symbols.get(0));
        if (symbols.size() == 1) {
            return f;
        } else {
            String name = null;
            for (int k = 2; k < symbols.size(); k += 2) {
                String f2 = translateFactor((VN) symbols.get(k));
                name = getName();
                String ans = name + " = " + f + " " + ((VT) symbols.get(k - 1)).value + " " + f2;
                f = name;
                add(ans);
            }
            return name;
        }
    }

    private String translateFactor(VN vn) {
        List<Symbol> symbols = vn.symbolList;
        VT vt = (VT) symbols.get(0);
        if (vt.syn == Syn.L_PAREN.syn) {
            return translateExpression((VN) symbols.get(1));
        } else {
            if (vt.syn == Syn.NUM.syn) {
                return Utils.highlightNum(vt.value);
            } else {
                return Utils.highlightString(vt.value);
            }
        }
    }

    private void translateConditionStatement(VN vn) {
        List<Symbol> symbols = vn.symbolList;
        String bool = translateBooleanStatement((VN) symbols.get(2));
        int add = address;
        add("");
        int tmp = translation.size() - 1;
        translateBlock((VN) symbols.get(4));
        if (symbols.size() == 6) {
            int add2 = address;
            add("");
            int tmp2 = translation.size() - 1;
            addIf(tmp, add, bool, address);
            translateBranchStatement((VN) symbols.get(5));
            addAgain(Utils.highlightKeyWord("goto") + " " + getAddress(address), tmp2, add2);
        }else{
            addIf(tmp, add, bool, address);
        }
    }

    private String translateBooleanStatement(VN vn) {
        VN e1 = (VN) vn.symbolList.get(0);
        VT cmp = Utils.getOppositeCmp((VT) vn.symbolList.get(1));
        VN e2 = (VN) vn.symbolList.get(2);
        String avg1 = translateExpression(e1);
        String avg2 = translateExpression(e2);
        return avg1 + " " + cmp.value + " " + avg2;
    }

    private void translateBranchStatement(VN vn) {
        List<Symbol> symbols = vn.symbolList;
        VN v = (VN) symbols.get(1);
        if (v.name.equals("条件语句")) {
            translateConditionStatement(v);
        } else {
            translateBlock(v);
        }
    }

    private void translateWhileStatement(VN vn) {
        List<Symbol> symbols = vn.symbolList;
        String bool = translateBooleanStatement((VN) symbols.get(2));
        int tmp = address;
        add("");
        int index = translation.size() - 1;
        translateBlock((VN) symbols.get(4));
        add(Utils.highlightKeyWord("goto") + " " + getAddress(tmp));
        addIf(index, tmp, bool, address);
    }
}
