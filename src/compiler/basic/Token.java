package compiler.basic;

/**
 * 二元组
 *
 * @author 苏富宝
 */
public class Token {
    public final int syn;
    public final String value;

    public Token(int syn, String value) {
        this.syn = syn;
        this.value = value;
    }

    public Token(Syn s) {
        this.syn = s.syn;
        this.value = s.value;
    }

    @Override
    public String toString() {
        return "< " + syn + "," + value + " >";
    }


}
