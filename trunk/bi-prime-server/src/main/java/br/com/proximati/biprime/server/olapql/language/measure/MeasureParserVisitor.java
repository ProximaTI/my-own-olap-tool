/* Generated By:JavaCC: Do not edit this line. MeasureParserVisitor.java Version 5.0 */
package br.com.proximati.biprime.server.olapql.language.measure;

public interface MeasureParserVisitor
{
  public void visit(SimpleNode node, StringBuilder data) throws Exception;
  public void visit(ASTMeasureExpression node, StringBuilder data) throws Exception;
  public void visit(ASTAddition node, StringBuilder data) throws Exception;
  public void visit(ASTMultiplication node, StringBuilder data) throws Exception;
  public void visit(ASTMeasure node, StringBuilder data) throws Exception;
  public void visit(ASTAggregation node, StringBuilder data) throws Exception;
  public void visit(ASTColumn node, StringBuilder data) throws Exception;
  public void visit(ASTNumber node, StringBuilder data) throws Exception;
}
/* JavaCC - OriginalChecksum=d3562fd3ed356a624c8394d31c61fb03 (do not edit this line) */