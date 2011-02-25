/* Generated By:JavaCC: Do not edit this line. OlapQlParserVisitor.java Version 5.0 */
package br.com.bi.model.query.grammar;

public interface OlapQlParserVisitor
{
  public Object visit(SimpleNode node, Object data);
  public Object visit(Instruction node, Object data);
  public Object visit(Select node, Object data);
  public Object visit(Create node, Object data);
  public Object visit(Alter node, Object data);
  public Object visit(Delete node, Object data);
  public Object visit(Axis node, Object data);
  public Object visit(Crossjoin node, Object data);
  public Object visit(MetadataObject node, Object data);
  public Object visit(Cube node, Object data);
  public Object visit(Filter node, Object data);
  public Object visit(Disjunction node, Object data);
  public Object visit(Conjunction node, Object data);
  public Object visit(Negation node, Object data);
  public Object visit(Comparison node, Object data);
  public Object visit(RelationalOperator node, Object data);
  public Object visit(ArithmeticExpression node, Object data);
  public Object visit(Date node, Object data);
  public Object visit(StringLiteral node, Object data);
  public Object visit(Addition node, Object data);
  public Object visit(Multiplication node, Object data);
  public Object visit(Number node, Object data);
}
/* JavaCC - OriginalChecksum=331cb2085840ad855c5927501d676621 (do not edit this line) */
