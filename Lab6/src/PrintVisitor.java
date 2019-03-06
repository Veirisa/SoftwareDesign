public class PrintVisitor implements TokenVisitor {

    private StringBuffer expr;

    PrintVisitor() {
        expr = new StringBuffer();
    }

    String getPrintResult() {
        int exprLength = expr.length();
        return exprLength == 0 ? expr.toString() : expr.deleteCharAt(expr.length() - 1).toString();
    }

    @Override
    public void visit(NumberToken token) {
        expr.append(token.number);
        expr.append(' ');
    }

    @Override
    public void visit(Brace token) {
        expr.append(token.braceType == BraceType.OPEN ? '(' : ')');
        expr.append(' ');
    }

    @Override
    public void visit(Operation token) {
        switch (token.opType) {
            case ADD:
                expr.append('+');
                break;
            case SUB:
                expr.append('-');
                break;
            case MUL:
                expr.append('*');
                break;
            case DIV:
                expr.append('/');
                break;
        }
        expr.append(' ');
    }
}
