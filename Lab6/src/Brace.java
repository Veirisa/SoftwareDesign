enum BraceType {
    OPEN, CLOSE;
}

public class Brace implements Token {

    BraceType braceType;

    Brace(BraceType bT) {
        braceType = bT;
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }
}
