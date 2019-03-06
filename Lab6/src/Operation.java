enum OperationType {
    ADD, SUB, MUL, DIV
}

public class Operation implements Token {

    OperationType opType;

    Operation(OperationType oT) {
        opType = oT;
    }

    public boolean greaterOrEqualPriority(Operation other) {
        if (opType == OperationType.ADD || opType == OperationType.SUB) {
            if (other.opType == OperationType.MUL || other.opType == OperationType.DIV) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }
}