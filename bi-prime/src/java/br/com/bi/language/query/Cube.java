/* Generated By:JJTree: Do not edit this line. Cube.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package br.com.bi.language.query;

public
class Cube extends SimpleNode {
  public Cube(int id) {
    super(id);
  }

  public Cube(QueryParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public void jjtAccept(QueryParserVisitor visitor, StringBuilder data) {
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=f8db625544badaa0c54b64578b883ff2 (do not edit this line) */
