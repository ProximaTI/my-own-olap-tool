/* Generated By:JJTree: Do not edit this line. FilterExpression.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package br.com.bi.language.filter;

public
class FilterExpression extends SimpleNode {
  public FilterExpression(int id) {
    super(id);
  }

  public FilterExpression(FilterParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public void jjtAccept(FilterParserVisitor visitor, StringBuilder data) {
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=a8e0c4eb81ac3a44fcf78e5a19ce8231 (do not edit this line) */
