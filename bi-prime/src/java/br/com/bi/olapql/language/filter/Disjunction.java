/* Generated By:JJTree: Do not edit this line. Disjunction.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package br.com.bi.olapql.language.filter;

public
class Disjunction extends SimpleNode {
  public Disjunction(int id) {
    super(id);
  }

  public Disjunction(FilterParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public void jjtAccept(FilterParserVisitor visitor, StringBuilder data) {
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=831b98c9e78c1bd300f763477a5c65d9 (do not edit this line) */
