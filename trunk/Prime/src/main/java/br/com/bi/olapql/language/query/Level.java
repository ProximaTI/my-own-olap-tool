/* Generated By:JJTree: Do not edit this line. Level.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package br.com.bi.olapql.language.query;

public
class Level extends SimpleNode {
  public Level(int id) {
    super(id);
  }

  public Level(QueryParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public void jjtAccept(QueryParserVisitor visitor, StringBuilder data) {
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=3267d0af197f7b8aaee3d6fbe86085d9 (do not edit this line) */
