/* Generated By:JJTree: Do not edit this line. Number.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package br.com.bi.language.filter;

public
class Number extends SimpleNode {
  public Number(int id) {
    super(id);
  }

  public Number(FilterParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public void jjtAccept(FilterParserVisitor visitor, StringBuilder data) {
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=1ea8234cc60aecce00dca343ad3b4975 (do not edit this line) */
