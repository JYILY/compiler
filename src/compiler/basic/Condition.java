package compiler.basic;

public class Condition {
    public String a, b, c;

    public Condition(String a, String b, String c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public String toString() {
        return "if(" + a + "<" + b + ") goto" + c;
    }
}
