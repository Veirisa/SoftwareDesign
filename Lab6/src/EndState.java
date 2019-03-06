public class EndState implements State {

    private Tokenizer tokenizer;

    EndState(Tokenizer t) {
        tokenizer = t;
    }

    @Override
    public void process() {}
}