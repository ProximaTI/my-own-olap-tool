/* Generated By:JJTree: Do not edit this line. ASTCondition.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package br.com.proximati.biprime.server.olapql.language.query;

public
class ASTCondition extends SimpleNode {
  public ASTCondition(int id) {
    super(id);
  }

  public ASTCondition(QueryParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public void jjtAccept(QueryParserVisitor visitor, Object data) throws Exception {
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=2748bc506b64f06ff8e28e5cd2ee13ca (do not edit this line) */
