/* Generated By:JJTree&JavaCC: Do not edit this line. MeasureParser.java */
package br.com.bi.olapql.language.measure;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class MeasureParser/*@bgen(jjtree)*/implements MeasureParserTreeConstants, MeasureParserConstants {/*@bgen(jjtree)*/
  protected JJTMeasureParserState jjtree = new JJTMeasureParserState();
    public static void main(String args[]) {
        InputStream in = new ByteArrayInputStream(
              ("soma(-1 / (100.8 + 2)) + [Total]").getBytes());

        MeasureParser parser = new MeasureParser(in);

        try {
            SimpleNode node = parser.measureExpression();
            node.dump(" ");
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }

  final public SimpleNode measureExpression() throws ParseException {
 /*@bgen(jjtree) MeasureExpression */
  MeasureExpression jjtn000 = new MeasureExpression(JJTMEASUREEXPRESSION);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      aggregationExpression();
      jj_consume_token(0);
      jjtree.closeNodeScope(jjtn000, true);
      jjtc000 = false;
        {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
      if (jjtc000) {
        jjtree.clearNodeScope(jjtn000);
        jjtc000 = false;
      } else {
        jjtree.popNode();
      }
      if (jjte000 instanceof RuntimeException) {
        {if (true) throw (RuntimeException)jjte000;}
      }
      if (jjte000 instanceof ParseException) {
        {if (true) throw (ParseException)jjte000;}
      }
      {if (true) throw (Error)jjte000;}
    } finally {
      if (jjtc000) {
        jjtree.closeNodeScope(jjtn000, true);
      }
    }
    throw new Error("Missing return statement in function");
  }

  final public void aggregationExpression() throws ParseException {
    aggregationAdditiveExpression();
  }

  final public void aggregationAdditiveExpression() throws ParseException {
 /*@bgen(jjtree) #Addition(> 1) */
    Addition jjtn000 = new Addition(JJTADDITION);
    boolean jjtc000 = true;
    jjtree.openNodeScope(jjtn000);String op = null;
    try {
      aggregationMultiplicativeExpression();
      label_1:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case PLUS:
        case MINUS:
          ;
          break;
        default:
          jj_la1[0] = jj_gen;
          break label_1;
        }
        op = additiveOperator();
        aggregationMultiplicativeExpression();
      }
      jjtree.closeNodeScope(jjtn000, jjtree.nodeArity() > 1);
      jjtc000 = false;
        jjtn000.value = op;
    } catch (Throwable jjte000) {
      if (jjtc000) {
        jjtree.clearNodeScope(jjtn000);
        jjtc000 = false;
      } else {
        jjtree.popNode();
      }
      if (jjte000 instanceof RuntimeException) {
        {if (true) throw (RuntimeException)jjte000;}
      }
      if (jjte000 instanceof ParseException) {
        {if (true) throw (ParseException)jjte000;}
      }
      {if (true) throw (Error)jjte000;}
    } finally {
      if (jjtc000) {
        jjtree.closeNodeScope(jjtn000, jjtree.nodeArity() > 1);
      }
    }
  }

  final public void aggregationMultiplicativeExpression() throws ParseException {
 /*@bgen(jjtree) #Multiplication(> 1) */
    Multiplication jjtn000 = new Multiplication(JJTMULTIPLICATION);
    boolean jjtc000 = true;
    jjtree.openNodeScope(jjtn000);String op = null;
    try {
      measureAtom();
      label_2:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case MULT:
        case DIV:
          ;
          break;
        default:
          jj_la1[1] = jj_gen;
          break label_2;
        }
        op = multiplicativeOperator();
        measureAtom();
      }
      jjtree.closeNodeScope(jjtn000, jjtree.nodeArity() > 1);
      jjtc000 = false;
        jjtn000.value = op;
    } catch (Throwable jjte000) {
      if (jjtc000) {
        jjtree.clearNodeScope(jjtn000);
        jjtc000 = false;
      } else {
        jjtree.popNode();
      }
      if (jjte000 instanceof RuntimeException) {
        {if (true) throw (RuntimeException)jjte000;}
      }
      if (jjte000 instanceof ParseException) {
        {if (true) throw (ParseException)jjte000;}
      }
      {if (true) throw (Error)jjte000;}
    } finally {
      if (jjtc000) {
        jjtree.closeNodeScope(jjtn000, jjtree.nodeArity() > 1);
      }
    }
  }

  final public void measureAtom() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 22:
      jj_consume_token(22);
      aggregationExpression();
      jj_consume_token(23);
      break;
    case AGGREGATOR:
      aggregation();
      break;
    case METADATA_OBJECT_NAME:
      measure();
      break;
    default:
      jj_la1[2] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  final public void measure() throws ParseException {
 /*@bgen(jjtree) Measure */
    Measure jjtn000 = new Measure(JJTMEASURE);
    boolean jjtc000 = true;
    jjtree.openNodeScope(jjtn000);Token t;
    try {
      t = jj_consume_token(METADATA_OBJECT_NAME);
      jjtree.closeNodeScope(jjtn000, true);
      jjtc000 = false;
        jjtn000.value = t.image;
    } finally {
      if (jjtc000) {
        jjtree.closeNodeScope(jjtn000, true);
      }
    }
  }

  final public void aggregation() throws ParseException {
 /*@bgen(jjtree) Aggregation */
    Aggregation jjtn000 = new Aggregation(JJTAGGREGATION);
    boolean jjtc000 = true;
    jjtree.openNodeScope(jjtn000);Token t;
    try {
      t = jj_consume_token(AGGREGATOR);
      jj_consume_token(22);
      arithmeticExpression();
      jj_consume_token(23);
      jjtree.closeNodeScope(jjtn000, true);
      jjtc000 = false;
        jjtn000.value = t.image;
    } catch (Throwable jjte000) {
      if (jjtc000) {
        jjtree.clearNodeScope(jjtn000);
        jjtc000 = false;
      } else {
        jjtree.popNode();
      }
      if (jjte000 instanceof RuntimeException) {
        {if (true) throw (RuntimeException)jjte000;}
      }
      if (jjte000 instanceof ParseException) {
        {if (true) throw (ParseException)jjte000;}
      }
      {if (true) throw (Error)jjte000;}
    } finally {
      if (jjtc000) {
        jjtree.closeNodeScope(jjtn000, true);
      }
    }
  }

  final public void arithmeticExpression() throws ParseException {
    additiveExpression();
  }

  final public void additiveExpression() throws ParseException {
 /*@bgen(jjtree) #Addition(> 1) */
    Addition jjtn000 = new Addition(JJTADDITION);
    boolean jjtc000 = true;
    jjtree.openNodeScope(jjtn000);String op = null;
    try {
      multiplicativeExpression();
      label_3:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case PLUS:
        case MINUS:
          ;
          break;
        default:
          jj_la1[3] = jj_gen;
          break label_3;
        }
        op = additiveOperator();
        multiplicativeExpression();
      }
      jjtree.closeNodeScope(jjtn000, jjtree.nodeArity() > 1);
      jjtc000 = false;
        jjtn000.value = op;
    } catch (Throwable jjte000) {
      if (jjtc000) {
        jjtree.clearNodeScope(jjtn000);
        jjtc000 = false;
      } else {
        jjtree.popNode();
      }
      if (jjte000 instanceof RuntimeException) {
        {if (true) throw (RuntimeException)jjte000;}
      }
      if (jjte000 instanceof ParseException) {
        {if (true) throw (ParseException)jjte000;}
      }
      {if (true) throw (Error)jjte000;}
    } finally {
      if (jjtc000) {
        jjtree.closeNodeScope(jjtn000, jjtree.nodeArity() > 1);
      }
    }
  }

  final public String additiveOperator() throws ParseException {
    Token t;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case PLUS:
      t = jj_consume_token(PLUS);
      break;
    case MINUS:
      t = jj_consume_token(MINUS);
      break;
    default:
      jj_la1[4] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
        {if (true) return t.image;}
    throw new Error("Missing return statement in function");
  }

  final public void multiplicativeExpression() throws ParseException {
 /*@bgen(jjtree) #Multiplication(> 1) */
    Multiplication jjtn000 = new Multiplication(JJTMULTIPLICATION);
    boolean jjtc000 = true;
    jjtree.openNodeScope(jjtn000);String op = null;
    try {
      arithmeticAtom();
      label_4:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case MULT:
        case DIV:
          ;
          break;
        default:
          jj_la1[5] = jj_gen;
          break label_4;
        }
        op = multiplicativeOperator();
        arithmeticAtom();
      }
      jjtree.closeNodeScope(jjtn000, jjtree.nodeArity() > 1);
      jjtc000 = false;
        jjtn000.value = op;
    } catch (Throwable jjte000) {
      if (jjtc000) {
        jjtree.clearNodeScope(jjtn000);
        jjtc000 = false;
      } else {
        jjtree.popNode();
      }
      if (jjte000 instanceof RuntimeException) {
        {if (true) throw (RuntimeException)jjte000;}
      }
      if (jjte000 instanceof ParseException) {
        {if (true) throw (ParseException)jjte000;}
      }
      {if (true) throw (Error)jjte000;}
    } finally {
      if (jjtc000) {
        jjtree.closeNodeScope(jjtn000, jjtree.nodeArity() > 1);
      }
    }
  }

  final public String multiplicativeOperator() throws ParseException {
    Token t;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case MULT:
      t = jj_consume_token(MULT);
      break;
    case DIV:
      t = jj_consume_token(DIV);
      break;
    default:
      jj_la1[6] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
        {if (true) return t.image;}
    throw new Error("Missing return statement in function");
  }

  final public void arithmeticAtom() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 22:
      jj_consume_token(22);
      arithmeticExpression();
      jj_consume_token(23);
      break;
    case IDENTIFIER:
      column();
      break;
    case MINUS:
    case FLOATING_POINT_LITERAL:
    case INTEGER_LITERAL:
      number();
      break;
    default:
      jj_la1[7] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  final public void column() throws ParseException {
 /*@bgen(jjtree) Column */
    Column jjtn000 = new Column(JJTCOLUMN);
    boolean jjtc000 = true;
    jjtree.openNodeScope(jjtn000);Token column;
    try {
      column = jj_consume_token(IDENTIFIER);
      jjtree.closeNodeScope(jjtn000, true);
      jjtc000 = false;
        jjtn000.value = column.image;
    } finally {
      if (jjtc000) {
        jjtree.closeNodeScope(jjtn000, true);
      }
    }
  }

  final public void number() throws ParseException {
 /*@bgen(jjtree) Number */
    Number jjtn000 = new Number(JJTNUMBER);
    boolean jjtc000 = true;
    jjtree.openNodeScope(jjtn000);Token minus = null;
    Token number;
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case MINUS:
        minus = jj_consume_token(MINUS);
        break;
      default:
        jj_la1[8] = jj_gen;
        ;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case INTEGER_LITERAL:
        number = jj_consume_token(INTEGER_LITERAL);
        break;
      case FLOATING_POINT_LITERAL:
        number = jj_consume_token(FLOATING_POINT_LITERAL);
        break;
      default:
        jj_la1[9] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      jjtree.closeNodeScope(jjtn000, true);
      jjtc000 = false;
        jjtn000.value = minus == null ? number.image : minus.image + number.image;
    } finally {
      if (jjtc000) {
        jjtree.closeNodeScope(jjtn000, true);
      }
    }
  }

  /** Generated Token Manager. */
  public MeasureParserTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[10];
  static private int[] jj_la1_0;
  static {
      jj_la1_init_0();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x600,0x1800,0x410080,0x600,0x600,0x1800,0x1800,0x4a0500,0x400,0xa0000,};
   }

  /** Constructor with InputStream. */
  public MeasureParser(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public MeasureParser(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new MeasureParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 10; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jjtree.reset();
    jj_gen = 0;
    for (int i = 0; i < 10; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public MeasureParser(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new MeasureParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 10; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jjtree.reset();
    jj_gen = 0;
    for (int i = 0; i < 10; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public MeasureParser(MeasureParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 10; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(MeasureParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jjtree.reset();
    jj_gen = 0;
    for (int i = 0; i < 10; i++) jj_la1[i] = -1;
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[24];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 10; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 24; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

}