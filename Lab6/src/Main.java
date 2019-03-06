import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize(new FileInputStream("input.txt"));

        /*
        PrintVisitor printVisitor = new PrintVisitor();
        for (Token t : tokens) {
            t.accept(printVisitor);
        }
        String expression = printVisitor.getPrintResult();
        System.out.println("Expression: " + expression);
        */

        ParserVisitor parserVisitor = new ParserVisitor();
        for (Token t : tokens) {
            t.accept(parserVisitor);
        }
        List<Token> revPolNot = parserVisitor.getParserResult();

        PrintVisitor printRPNVisitor = new PrintVisitor();
        for (Token t : revPolNot) {
            t.accept(printRPNVisitor);
        }
        String revPolNotExpression = printRPNVisitor.getPrintResult();
        System.out.println("RPN expression: " + revPolNotExpression);

        CalcVisitor calcVisitor = new CalcVisitor();
        for (Token t : revPolNot) {
            t.accept(calcVisitor);
        }
        Integer calculatedValue = calcVisitor.getCalcResult();
        System.out.println("Calculated value: " + calculatedValue.toString());
    }
}
