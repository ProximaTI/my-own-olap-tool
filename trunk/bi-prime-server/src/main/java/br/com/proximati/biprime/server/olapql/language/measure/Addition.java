/* Generated By:JJTree: Do not edit this line. Addition.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package br.com.proximati.biprime.server.olapql.language.measure;

public
class Addition extends SimpleNode {
  public Addition(int id) {
    super(id);
  }

  public Addition(MeasureParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public void jjtAccept(MeasureParserVisitor visitor, StringBuilder data) {
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=8b53e345d8975cb93ed156271d334a58 (do not edit this line) */