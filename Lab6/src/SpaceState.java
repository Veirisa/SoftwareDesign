import java.io.IOException;

public class SpaceState implements State {

    private Tokenizer tokenizer;

    SpaceState(Tokenizer t) {
        tokenizer = t;
    }

    @Override
    public void process() throws IOException {
        int symbol = tokenizer.readCurSymbol();
        while (symbol != -1 && Character.isSpaceChar((char)symbol)) {
            symbol = tokenizer.readCurSymbol();
        }
        if (symbol == -1) {
            tokenizer.setState(new EndState(tokenizer));
            return;
        }
        if (Character.isDigit((char)symbol)) {
            tokenizer.setState(new NumberState(tokenizer));
            return;
        }
        tokenizer.setState(new SymbolState(tokenizer));
    }
}
