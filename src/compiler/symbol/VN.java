package compiler.symbol;

import java.util.ArrayList;
import java.util.List;

public class VN implements Symbol {
    public List<Symbol> symbolList = new ArrayList<>();
    public String name;
    public VN(String name) {
        this.name = name;
    }
    public void addSymbol(Symbol symbol) {
        symbolList.add(symbol);
    }
}
