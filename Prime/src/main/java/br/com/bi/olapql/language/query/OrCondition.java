/* Generated By:JJTree: Do not edit this line. OrCondition.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package br.com.bi.olapql.language.query;

public
class OrCondition extends SimpleNode {
  public OrCondition(int id) {
    super(id);
  }

  public OrCondition(QueryParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public void jjtAccept(QueryParserVisitor visitor, StringBuilder data) {
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=a6bbc98113bb4971b143cbb8f350fa0e (do not edit this line) */