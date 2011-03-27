/* Generated By:JavaCC: Do not edit this line. QueryParserVisitor.java Version 5.0 */
package br.com.bi.language.query;

public interface QueryParserVisitor
{
  public void visit(SimpleNode node, StringBuilder data);
  public void visit(Instruction node, StringBuilder data);
  public void visit(Select node, StringBuilder data);
  public void visit(Axis node, StringBuilder data);
  public void visit(Set node, StringBuilder data);
  public void visit(LevelOrMeasureOrFilter node, StringBuilder data);
  public void visit(Property node, StringBuilder data);
  public void visit(Crossjoin node, StringBuilder data);
  public void visit(Cube node, StringBuilder data);
  public void visit(FilterExpression node, StringBuilder data);
  public void visit(Disjunction node, StringBuilder data);
  public void visit(Conjunction node, StringBuilder data);
  public void visit(Negation node, StringBuilder data);
  public void visit(Comparison node, StringBuilder data);
  public void visit(LevelOrMeasure node, StringBuilder data);
  public void visit(Measure node, StringBuilder data);
  public void visit(Filter node, StringBuilder data);
  public void visit(RelationalOperator node, StringBuilder data);
  public void visit(ArithmeticExpression node, StringBuilder data);
  public void visit(Date node, StringBuilder data);
  public void visit(StringLiteral node, StringBuilder data);
  public void visit(Addition node, StringBuilder data);
  public void visit(Multiplication node, StringBuilder data);
  public void visit(Number node, StringBuilder data);
}
/* JavaCC - OriginalChecksum=201b4f512468b0ccbad7a859b4af76f3 (do not edit this line) */