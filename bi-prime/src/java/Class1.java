import java.io.StringReader;

public class Class1 implements FilterParserConstants {
    
    private FilterParser parser;

    private Token lookahead;

    public static void main(String[] args) {
        Class1 class1 = new Class1();
        class1.parser =
                new FilterParser(new StringReader("([a] > 10) ou ([yyyyyyyyb] < 1 e ([b] = 1))"));

        class1.expression();
    }

    private void expression() {
        F(); E_();
    }

    private void F() {
        switch (lookahead().kind) {
        case LPAR:
            match(LPAR); expression(); match(RPAR);
            break;
        case NOT:
            match(NOT); F();
            break;
        case IDENTIFIER:
            match(IDENTIFIER); match(RELATIONAL_OPERATOR); match(DECIMAL_LITERAL);
            break;
        default:
            throw new Error("Esperado um nó ou identificador");
        }
    }

    private void E_() {
      switch (lookahead().kind) {
      case BINARY_LOGIC_OPERATOR:
          match(BINARY_LOGIC_OPERATOR); F(); E_();
          break;
      case EOF:
          match(EOF);
          break;
      default:
      }
    }

    private Token lookahead() {
        if (lookahead == null) {
            lookahead = parser.getNextToken();
        }
        return lookahead;
    }

    private void match(int i) {
        if (lookahead().kind == i)
            lookahead = parser.getNextToken();
        else
            throw new Error("Erro de sintaxe");
    }
}
