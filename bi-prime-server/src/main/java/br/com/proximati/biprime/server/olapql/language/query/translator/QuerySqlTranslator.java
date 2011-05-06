/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.proximati.biprime.server.olapql.language.query.translator;

import br.com.proximati.biprime.server.olapql.language.measure.translator.MeasureSqlTranslator;
import br.com.proximati.biprime.server.olapql.language.query.Node;
import br.com.proximati.biprime.server.olapql.language.query.ParseException;
import br.com.proximati.biprime.server.olapql.language.query.SimpleNode;
import br.com.proximati.biprime.server.olapql.language.utils.TranslationUtils;
import br.com.proximati.biprime.metadata.Application;
import br.com.proximati.biprime.metadata.entity.Cube;
import br.com.proximati.biprime.metadata.entity.CubeLevel;
import br.com.proximati.biprime.metadata.entity.Filter;
import br.com.proximati.biprime.metadata.entity.Level;
import br.com.proximati.biprime.metadata.entity.Metadata;
import br.com.proximati.biprime.metadata.entity.Property;
import br.com.proximati.biprime.server.olapql.language.query.ASTAdditiveExpression;
import br.com.proximati.biprime.server.olapql.language.query.ASTAndCondition;
import br.com.proximati.biprime.server.olapql.language.query.ASTAxis;
import br.com.proximati.biprime.server.olapql.language.query.ASTCompare;
import br.com.proximati.biprime.server.olapql.language.query.ASTCube;
import br.com.proximati.biprime.server.olapql.language.query.ASTDateLiteral;
import br.com.proximati.biprime.server.olapql.language.query.ASTEndsWithExpression;
import br.com.proximati.biprime.server.olapql.language.query.ASTFilter;
import br.com.proximati.biprime.server.olapql.language.query.ASTFilterExpression;
import br.com.proximati.biprime.server.olapql.language.query.ASTInExpression;
import br.com.proximati.biprime.server.olapql.language.query.ASTLevel;
import br.com.proximati.biprime.server.olapql.language.query.ASTLevelOrMeasureOrFilter;
import br.com.proximati.biprime.server.olapql.language.query.ASTLikeExpression;
import br.com.proximati.biprime.server.olapql.language.query.ASTMultiplicativeExpression;
import br.com.proximati.biprime.server.olapql.language.query.ASTNegation;
import br.com.proximati.biprime.server.olapql.language.query.ASTNumberLiteral;
import br.com.proximati.biprime.server.olapql.language.query.ASTOrCondition;
import br.com.proximati.biprime.server.olapql.language.query.ASTProperty;
import br.com.proximati.biprime.server.olapql.language.query.ASTPropertyNode;
import br.com.proximati.biprime.server.olapql.language.query.ASTSelect;
import br.com.proximati.biprime.server.olapql.language.query.ASTStartsWithExpression;
import br.com.proximati.biprime.server.olapql.language.query.ASTStringLiteral;
import br.com.proximati.biprime.server.olapql.language.query.QueryParser;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;
import org.apache.commons.collections15.BidiMap;
import org.apache.commons.collections15.bidimap.DualHashBidiMap;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author luiz
 */
public class QuerySqlTranslator extends AbstractQueryVisitor {

    private Cube cube;
    private QueryMetadataExtractor extractor = new QueryMetadataExtractor();
    private ASTSelect select;

    public ASTSelect getSelect() {
        return select;
    }
    // =================================================
    // variables used in coordinates calculating process
    // =================================================
    /**
     * Stack used to registrate node position during its visitation.
     */
    private Stack<Integer> nodeCoordinates = new Stack<Integer>();
    /**
     * List used to put the nodes in the same order they appear in the query axis.
     * It's useful on translation of "group by" expression, due to its assortment.
     */
    private List<Node> axisNodeList = new ArrayList<Node>();
    private Map<Node, Metadata> axisNodeMetadataMap = new HashMap<Node, Metadata>();
    private BidiMap<Node, String> axisNodeCoordinateMap = new DualHashBidiMap<Node, String>();

    @Override
    public void visit(ASTSelect node, StringBuilder data) throws Exception {
        cube = Application.getCubeDao().findByName(TranslationUtils.extractName(((SimpleNode) node.jjtGetChild(2)).jjtGetValue().toString()));

        extractor.visit(node, data);

        data.append("select ");

        visitChildren(node, data);

        if (!axisNodeMetadataMap.isEmpty()) {

            StringBuilder groupBy = new StringBuilder();

            for (Node n : getAxisNodeList()) {
                Metadata metadata = getAxisNodeMetadataMap().get(n);

                if (metadata instanceof Level
                        || metadata instanceof Property
                        || metadata instanceof Filter) {
                    groupBy.append(getAxisNodeCoordinateMap().get(n)).append(", ");
                }
            }

            if (groupBy.length() > 0) {
                groupBy.delete(groupBy.length() - 2, groupBy.length());

                data.append(" group by ").append(groupBy);
            }
        }
    }

    @Override
    public void visit(ASTCube node, StringBuilder data) throws Exception {
        // delete last comma generated by axis translation
        data.delete(data.length() - 2, data.length());

        data.append(" from ");

        Set<String> tables = new HashSet<String>();

        tables.add(TranslationUtils.tableExpression(cube.getSchemaName(), cube.getTableName()));

        for (Level level : levelsPresent()) {
            for (Level lowerLevel : level.getLowerLevels()) {
                tables.add(TranslationUtils.tableExpression(lowerLevel.getSchemaName(), lowerLevel.getTableName()));
            }
        }

        for (String table : tables) {
            data.append(table).append(", ");
        }

        if (!tables.isEmpty()) {
            data.delete(data.length() - 2, data.length());
        }

        data.append(" where ");

        whereExpression(data);
    }

    @Override
    public void visit(ASTFilterExpression node, StringBuilder data) throws Exception {
        data.append(" and ");
        visitChildren(node, data);
    }

    @Override
    public void visit(ASTOrCondition node, StringBuilder data) throws Exception {
        visitOperation(node, "or", data);
    }

    @Override
    public void visit(ASTAndCondition node, StringBuilder data) throws Exception {
        visitOperation(node, "and", data);
    }

    @Override
    public void visit(ASTNegation node, StringBuilder data) throws Exception {
        if (data.charAt(data.length() - 1) != ' ') {
            data.append(" not");
        } else {
            data.append("not ");
        }

        visit(node.jjtGetChild(0), data);
    }

    @Override
    public void visit(ASTLevel node, StringBuilder data) throws Exception {
        Level level = extractor.getAllReferencedMetadata().getLevel(node.jjtGetValue().toString());
        data.append(translateLevel(level));
    }

    @Override
    public void visit(ASTFilter node, StringBuilder data) throws Exception {
        Filter filter = Application.getFilterDao().findByName(TranslationUtils.extractName(node.jjtGetValue().toString()));
        data.append(translateFilterExpression(filter.getExpression()));
    }

    @Override
    public void visit(ASTPropertyNode node, StringBuilder data) throws Exception {
        getNodeCoordinates().push(childIndex(node));

        Property property = extractor.getAllReferencedMetadata().getProperty(node.jjtGetValue().toString());

        registrateAxisNode(node, property);

        data.append(translateProperty(property));
        data.append(" as ").append(getAxisNodeCoordinateMap().get(node));
        data.append(", ");

        super.visit(node, data);
        getNodeCoordinates().pop();
    }

    @Override
    public void visit(ASTProperty node, StringBuilder data) throws Exception {
        Property property = extractor.getAllReferencedMetadata().getProperty(node.jjtGetValue().toString());
        data.append(translateProperty(property));
    }

    @Override
    public void visit(ASTCompare node, StringBuilder data) throws Exception {
        data.append(" ").append(node.jjtGetValue()).append(" ");
    }

    @Override
    public void visit(ASTDateLiteral node, StringBuilder data) throws Exception {
        data.append(node.jjtGetValue());
    }

    @Override
    public void visit(ASTStringLiteral node, StringBuilder data) throws Exception {
        String string = node.jjtGetValue().toString();
        data.append(TranslationUtils.encloseString(TranslationUtils.discloseString(string)));
    }

    @Override
    public void visit(ASTAdditiveExpression node, StringBuilder data) throws Exception {
        visitOperation(node, data);
    }

    @Override
    public void visit(ASTMultiplicativeExpression node, StringBuilder data) throws Exception {
        visitOperation(node, data);
    }

    @Override
    public void visit(ASTNumberLiteral node, StringBuilder data) throws Exception {
        data.append(node.jjtGetValue());
    }

    @Override
    public void visit(ASTAxis node, StringBuilder data) throws Exception {
        if (node.jjtGetValue().toString().equals("ROWS")) {
            getNodeCoordinates().push(0);
        } else {
            getNodeCoordinates().push(1);
        }

        super.visit(node, data);

        getNodeCoordinates().pop();
    }

    @Override
    public void visit(ASTLevelOrMeasureOrFilter node, StringBuilder data) throws Exception {
        getNodeCoordinates().push(childIndex(node));

        StringBuilder sb = new StringBuilder();

        Metadata metadata =
                extractor.getAllReferencedMetadata().getMeasure(node.jjtGetValue().toString());

        if (metadata != null) {
            MeasureSqlTranslator translator = new MeasureSqlTranslator(cube);

            sb.append(translator.translate(node.jjtGetValue().toString()));
        } else {
            metadata = extractor.getAllReferencedMetadata().getLevel(node.jjtGetValue().toString());

            if (metadata != null) {
                data.append(TranslationUtils.columnExpression(((Level) metadata).getTableName(), ((Level) metadata).getCodeProperty().getColumnName()));
            } else {
                metadata = extractor.getAllReferencedMetadata().getFilter(node.jjtGetValue().toString());

                sb.append("case when ").append(translateFilterExpression(((Filter) metadata).getExpression())).append(" then 1 else 0 end");
            }
        }

        registrateAxisNode(node, metadata);

        data.append(sb).append(" as ").append(getAxisNodeCoordinateMap().get(node)).append(", ");

        super.visit(node, data);
        getNodeCoordinates().pop();
    }

    @Override
    public void visit(ASTInExpression node, StringBuilder data) throws Exception {
        data.append(" in (");

        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            visit(node.jjtGetChild(i), data);
            data.append(", ");
        }

        // delete last comma generated by axis translation
        data.delete(data.length() - 2, data.length());
        data.append(")");
    }

    @Override
    public void visit(ASTLikeExpression node, StringBuilder data) throws Exception {
        String operand = ((SimpleNode) node.jjtGetChild(0)).jjtGetValue().toString();

        StringBuilder sb = new StringBuilder();
        sb.append(TranslationUtils.likeWildcard());
        sb.append(TranslationUtils.discloseString(operand));
        sb.append(TranslationUtils.likeWildcard());

        data.append(" like ");
        data.append(TranslationUtils.encloseString(sb.toString()));
    }

    @Override
    public void visit(ASTStartsWithExpression node, StringBuilder data) throws Exception {
        String operand = ((SimpleNode) node.jjtGetChild(0)).jjtGetValue().toString();

        StringBuilder sb = new StringBuilder();
        sb.append(TranslationUtils.discloseString(operand));
        sb.append(TranslationUtils.likeWildcard());

        data.append(" like ");
        data.append(TranslationUtils.encloseString(sb.toString()));
    }

    @Override
    public void visit(ASTEndsWithExpression node, StringBuilder data) throws Exception {
        String operand = ((SimpleNode) node.jjtGetChild(0)).jjtGetValue().toString();

        StringBuilder sb = new StringBuilder();
        sb.append(TranslationUtils.likeWildcard());
        sb.append(TranslationUtils.discloseString(operand));

        data.append(" like ");
        data.append(TranslationUtils.encloseString(sb.toString()));
    }

    private void visitOperation(SimpleNode node, StringBuilder data) throws Exception {
        visitOperation(node, node.jjtGetValue().toString(), data);
    }

    private void visitOperation(SimpleNode node, String op, StringBuilder data) throws Exception {
        data.append("(");

        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            visit(node.jjtGetChild(i), data);
            if (i < node.jjtGetNumChildren() - 1) {
                data.append(" ").append(op).append(" ");
            }
        }

        data.append(")");
    }

    // ==========================
    // ====== Utilitários =======
    // ==========================
    /**
     * Retorna os níveis presentes na consulta, seja explicitamente em um nó, ou indiretamente
     * através de um nó filtro, ou filtro de métrica, ou ainda filtro na consulta.
     * @param query
     * @return
     */
    private List<Level> levelsPresent() {
        List<Level> levels = new ArrayList<Level>();

        for (Entry<String, Metadata> entry :
                extractor.getAllReferencedMetadata().getInternalMap().entrySet()) {
            if (entry.getValue() instanceof Level) {
                levels.add((Level) entry.getValue());
            } else if (entry.getValue() instanceof Property) {
                levels.add(((Property) entry.getValue()).getLevel());
            }
        }

        return levels;
    }

    /**
     * Produz a cláusula WHERE da consulta, baseado nos joins resultantes das referências
     * para os níveis da consulta.
     * @return
     */
    private String whereExpression(StringBuilder data) throws Exception {
        List<String> joins = new ArrayList<String>();

        List<Level> levels = levelsPresent();

        // do nível mais alto vai descendo e adicionando os joins até chegar o nível
        // mais baixo e assim juntá-lo ao cubo
        for (Level level : levels) {
            List<Level> lowerLevels = level.getLowerLevels();

            for (int i = 0; i < lowerLevels.size(); i++) {
                // de duas em duas colunas, coloca o join na lista
                if (i > 0 & (i + 1) % 2 == 0) {
                    String upperColumn = TranslationUtils.columnExpression(lowerLevels.get(i - 1).getTableName(), lowerLevels.get(i - 1).getCodeProperty().getColumnName());

                    String thisColumn = TranslationUtils.columnExpression(lowerLevels.get(i).getTableName(), lowerLevels.get(i).getUpperLevelJoinColumn());

                    joins.add(thisColumn + " = " + upperColumn);
                }
            }

            // o nível mais baixo (cujo índice é o maior) é o nível que faz junção com o cubo,
            // e indiretamente liga todos os níveis superiores também.
            Collections.sort(lowerLevels, new Comparator<Level>() {

                @Override
                public int compare(Level level1, Level level2) {
                    return Integer.valueOf(level1.getIndice()).compareTo(Integer.valueOf(level2.getIndice()));
                }
            });

            Level lowestLevel = lowerLevels.get(lowerLevels.size() - 1);

            for (CubeLevel cubeLevel : cube.getCubeLevelList()) {
                if (cubeLevel.getLevel().getId() == lowestLevel.getId()) {
                    joins.add(TranslationUtils.columnExpression(cube.getTableName(), cubeLevel.getJoinColumn()) + " = " + TranslationUtils.columnExpression(lowestLevel.getTableName(), lowestLevel.getCodeProperty().getColumnName()));
                }
            }
        }

        if (!joins.isEmpty()) {
            for (int i = 0; i < joins.size(); i++) {
                data.append(joins.get(i));

                if (i < joins.size() - 1) {
                    data.append(" and ");
                }
            }
        }

        return data.toString();
    }

    private String translateLevel(Level level) {
        StringBuilder sb = new StringBuilder();

        sb.append(TranslationUtils.columnExpression(level.getTableName(), level.getCodeProperty().getColumnName()));

        return sb.toString();
    }

    private String translateProperty(Property property) {
        StringBuilder sb = new StringBuilder();

        sb.append(TranslationUtils.columnExpression(property.getLevel().getTableName(), property.getColumnName()));

        return sb.toString();
    }

    private void registrateAxisNode(SimpleNode node, Metadata metadata) {
        getAxisNodeMetadataMap().put(node, metadata);
        getAxisNodeList().add(node);
        getAxisNodeCoordinateMap().put(node, calculateCoordinates(node));
    }

    private String calculateCoordinates(SimpleNode node) {
        StringBuilder coordinate = new StringBuilder();

        for (int i = 0; i < getNodeCoordinates().size(); i++) {
            if (i == 0) {
                if (getNodeCoordinates().get(i) == 0) {
                    coordinate.append("r_");
                } else {
                    coordinate.append("c_");
                }
            } else {
                coordinate.append(getNodeCoordinates().get(i)).append("_");
            }
        }

        coordinate.deleteCharAt(coordinate.length() - 1);

        return coordinate.toString();
    }

    /**
     * Translates an olapql instruction into a sql instruction.
     * @param instruction
     * @return
     * @throws ParseException 
     */
    public String translateOlapQlInstruction(String instruction) throws Exception {
        QueryParser parser = new QueryParser(IOUtils.toInputStream(instruction));
        this.select = (ASTSelect) parser.select();
        StringBuilder sb = new StringBuilder();
        visit(this.select, sb);
        return sb.toString();
    }

    public String translateFilterExpression(String filterExpression) throws Exception {
        QueryParser parser = new QueryParser(IOUtils.toInputStream(filterExpression));

        try {
            StringBuilder sb = new StringBuilder();
            visit(parser.detachedFilterExpression(), sb);
            return sb.toString();
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * @return the axisNodeList
     */
    public List<Node> getAxisNodeList() {
        return axisNodeList;
    }

    /**
     * @return the nodeCoordinates
     */
    public Stack<Integer> getNodeCoordinates() {
        return nodeCoordinates;
    }

    /**
     * @return the axisNodeMetadataMap
     */
    public Map<Node, Metadata> getAxisNodeMetadataMap() {
        return axisNodeMetadataMap;
    }

    /**
     * @return the axisNodeCoordinateMap
     */
    public BidiMap<Node, String> getAxisNodeCoordinateMap() {
        return axisNodeCoordinateMap;
    }
}
