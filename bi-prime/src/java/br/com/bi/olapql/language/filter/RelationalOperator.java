/* Generated By:JJTree: Do not edit this line. RelationalOperator.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package br.com.bi.olapql.language.filter;

public
class RelationalOperator extends SimpleNode {
  public RelationalOperator(int id) {
    super(id);
  }

  public RelationalOperator(FilterParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public void jjtAccept(FilterParserVisitor visitor, StringBuilder data) {
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=2bb0d7c30284cae53b9a16c96005c26e (do not edit this line) */
