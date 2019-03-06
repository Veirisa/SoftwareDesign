public class ErrorState implements State {

    private Tokenizer tokenizer;

    ErrorState(Tokenizer t) {
        tokenizer = t;
    }

    @Override
    public void process() {}
}