/* Generated By:JJTree: Do not edit this line. ASTCompare.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package br.com.proximati.biprime.server.olapql.language.query;

public
class ASTCompare extends SimpleNode {
  public ASTCompare(int id) {
    super(id);
  }

  public ASTCompare(QueryParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public void jjtAccept(QueryParserVisitor visitor, Object data) throws Exception {
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=4f87c895f860de0e82e6dc2609481e6b (do not edit this line) */
