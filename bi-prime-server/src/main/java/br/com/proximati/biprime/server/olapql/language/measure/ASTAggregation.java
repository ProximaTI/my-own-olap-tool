/* Generated By:JJTree: Do not edit this line. ASTAggregation.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package br.com.proximati.biprime.server.olapql.language.measure;

public
class ASTAggregation extends SimpleNode {
  public ASTAggregation(int id) {
    super(id);
  }

  public ASTAggregation(MeasureParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public void jjtAccept(MeasureParserVisitor visitor, StringBuilder data) throws Exception {
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=8c1febb7c69cf40059b6085ad86fbc46 (do not edit this line) */
