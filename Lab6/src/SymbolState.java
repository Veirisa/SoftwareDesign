public class SymbolState implements State {

    private Tokenizer tokenizer;

    SymbolState(Tokenizer t) {
        tokenizer = t;
    }

    @Override
    public void process() {
        switch ((char)tokenizer.getCurSymbol()) {
            case '+':
                tokenizer.addToken(new Operation(OperationType.ADD));
                break;
            case '-':
                tokenizer.addToken(new Operation(OperationType.SUB));
                break;
            case '*':
                tokenizer.addToken(new Operation(OperationType.MUL));
                break;
            case '/':
                tokenizer.addToken(new Operation(OperationType.DIV));
                break;
            case '(':
                tokenizer.addToken(new Brace(BraceType.OPEN));
                break;
            case ')':
                tokenizer.addToken(new Brace(BraceType.CLOSE));
                break;
            default:
                tokenizer.setState(new ErrorState(tokenizer));
                return;
        }
        tokenizer.setState(new SpaceState(tokenizer));
    }
}