/* Generated By:JJTree: Do not edit this line. Conjunction.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package br.com.bi.olapql.language.query;

public
class Conjunction extends SimpleNode {
  public Conjunction(int id) {
    super(id);
  }

  public Conjunction(QueryParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public void jjtAccept(QueryParserVisitor visitor, StringBuilder data) {
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=511440a5ac9e06bc4d2ed78c55003b96 (do not edit this line) */
