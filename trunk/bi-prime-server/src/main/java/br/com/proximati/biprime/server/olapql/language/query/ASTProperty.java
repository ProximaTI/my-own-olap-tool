/* Generated By:JJTree: Do not edit this line. ASTProperty.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package br.com.proximati.biprime.server.olapql.language.query;

public
class ASTProperty extends SimpleNode {
  public ASTProperty(int id) {
    super(id);
  }

  public ASTProperty(QueryParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public void jjtAccept(QueryParserVisitor visitor, Object data) throws Exception {
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=e3b1c7d3b669f5a6178b22c546f8ede1 (do not edit this line) */
