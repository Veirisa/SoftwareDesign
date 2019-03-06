public class NumberToken implements Token {

    Integer number;

    NumberToken(Integer n) {
        number = n;
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }
}
