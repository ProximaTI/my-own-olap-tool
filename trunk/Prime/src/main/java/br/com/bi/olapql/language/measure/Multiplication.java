/* Generated By:JJTree: Do not edit this line. Multiplication.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package br.com.bi.olapql.language.measure;

public
class Multiplication extends SimpleNode {
  public Multiplication(int id) {
    super(id);
  }

  public Multiplication(MeasureParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public void jjtAccept(MeasureParserVisitor visitor, StringBuilder data) {
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=1794812ed13fc5a445e962ed27e92caf (do not edit this line) */