/* Generated By:JJTree: Do not edit this line. Property.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package br.com.bi.olapql.language.filter;

public
class Property extends SimpleNode {
  public Property(int id) {
    super(id);
  }

  public Property(FilterParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public void jjtAccept(FilterParserVisitor visitor, StringBuilder data) {
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=17547eadf79906021267549a667debc8 (do not edit this line) */