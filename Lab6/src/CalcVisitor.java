import java.util.Stack;

public class CalcVisitor implements TokenVisitor {

    private Stack<Integer> stack;

    CalcVisitor() {
        stack = new Stack<>();
    }

    Integer getCalcResult() {
        return stack.peek();
    }

    @Override
    public void visit(NumberToken token) {
        stack.push(token.number);
    }

    @Override
    public void visit(Brace token) {}

    @Override
    public void visit(Operation token) {
        Integer right = stack.pop();
        Integer left = stack.pop();
        switch (token.opType) {
            case ADD:
                stack.push(left + right);
                break;
            case SUB:
                stack.push(left - right);
                break;
            case MUL:
                stack.push(left * right);
                break;
            case DIV:
                stack.push(left / right);
                break;
        }
    }
}
