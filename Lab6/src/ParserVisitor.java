import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ParserVisitor implements TokenVisitor {

    private Stack<Token> stack;
    private List<Token> revPolNot;

    ParserVisitor() {
        stack = new Stack<>();
        revPolNot = new ArrayList<>();
    }

    List<Token> getParserResult() {
        while (!stack.empty()) {
            revPolNot.add(stack.pop());
        }
        return revPolNot;
    }

    @Override
    public void visit(NumberToken token) {
        revPolNot.add(token);
    }

    @Override
    public void visit(Brace token) {
        if (token.braceType == BraceType.OPEN) {
            stack.push(token);
        } else {
            while (!(stack.peek() instanceof Brace && ((Brace) stack.peek()).braceType == BraceType.OPEN)) {
                revPolNot.add(stack.pop());
            }
            stack.pop();
        }
    }

    @Override
    public void visit(Operation token) {
        while (!stack.empty() && stack.peek() instanceof Operation && token.greaterOrEqualPriority((Operation)stack.peek())) {
            revPolNot.add(stack.pop());
        }
        stack.push(token);
    }
}
