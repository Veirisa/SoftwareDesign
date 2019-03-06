public interface Token {
    void accept(TokenVisitor visitor);
}
