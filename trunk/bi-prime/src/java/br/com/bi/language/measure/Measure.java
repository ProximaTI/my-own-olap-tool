/* Generated By:JJTree: Do not edit this line. Measure.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package br.com.bi.language.measure;

public
class Measure extends SimpleNode {
  public Measure(int id) {
    super(id);
  }

  public Measure(MeasureParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public void jjtAccept(MeasureParserVisitor visitor, StringBuilder data) {
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=2d7cbacb9eeae845f8395cfa8faacebd (do not edit this line) */
