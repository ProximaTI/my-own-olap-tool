/* Generated By:JJTree: Do not edit this line. ASTEndsWithExpression.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package br.com.proximati.biprime.server.olapql.language.query;

public
class ASTEndsWithExpression extends SimpleNode {
  public ASTEndsWithExpression(int id) {
    super(id);
  }

  public ASTEndsWithExpression(QueryParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public void jjtAccept(QueryParserVisitor visitor, StringBuilder data) throws Exception {
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=f2837a12c662b182567a48e68d5bf1f5 (do not edit this line) */