import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Tokenizer {

    private InputStream inputStream;
    private State curState;
    private int curSymbol;
    private List<Token> tokens;

    Tokenizer() {
        curState = new SpaceState(this);
        curSymbol = 0;
        tokens = new ArrayList<>();
    }

    void setState(State state) {
        curState = state;
    }

    int readCurSymbol() throws IOException {
        curSymbol = inputStream.read();
        return curSymbol;
    }

    int getCurSymbol() {
        return curSymbol;
    }

    void addToken(Token token) {
        tokens.add(token);
    }

    List<Token> tokenize(InputStream in) throws IOException {
        inputStream = in;
        while (!(curState instanceof EndState || curState instanceof ErrorState)) {
            curState.process();
        }
        if (curState instanceof ErrorState) {
            throw new IOException("Unexpected symbol: " + (char)curSymbol);
        }
        return tokens;
    }
}
