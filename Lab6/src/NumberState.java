import java.io.IOException;

public class NumberState implements State {

    private Tokenizer tokenizer;
    private Integer number;

    NumberState(Tokenizer t) {
        tokenizer = t;
        number = 0;
    }

    @Override
    public void process() throws IOException {
        int symbol = tokenizer.getCurSymbol();
        while (symbol != -1 && Character.isDigit((char)symbol)) {
            number *= 10;
            number += symbol - '0';
            symbol = tokenizer.readCurSymbol();
        }
        tokenizer.addToken(new NumberToken(number));
        if (symbol == -1) {
            tokenizer.setState(new EndState(tokenizer));
            return;
        }
        if (Character.isSpaceChar((char)symbol)) {
            tokenizer.setState(new SpaceState(tokenizer));
            return;
        }
        tokenizer.setState(new SymbolState(tokenizer));
    }
}