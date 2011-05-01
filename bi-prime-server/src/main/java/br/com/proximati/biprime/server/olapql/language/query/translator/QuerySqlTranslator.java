/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.proximati.biprime.server.olapql.language.query.translator;

import br.com.proximati.biprime.server.olapql.language.measure.translator.MeasureSqlTranslator;
import br.com.proximati.biprime.server.olapql.language.query.Axis;
import br.com.proximati.biprime.server.olapql.language.query.AndCondition;
import br.com.proximati.biprime.server.olapql.language.query.Cube;
import br.com.proximati.biprime.server.olapql.language.query.OrCondition;
import br.com.proximati.biprime.server.olapql.language.query.Filter;
import br.com.proximati.biprime.server.olapql.language.query.FilterExpression;
import br.com.proximati.biprime.server.olapql.language.query.LevelOrMeasureOrFilter;
import br.com.proximati.biprime.server.olapql.language.query.Negation;
import br.com.proximati.biprime.server.olapql.language.query.Node;
import br.com.proximati.biprime.server.olapql.language.query.ParseException;
import br.com.proximati.biprime.server.olapql.language.query.Property;
import br.com.proximati.biprime.server.olapql.language.query.PropertyNode;
import br.com.proximati.biprime.server.olapql.language.query.Select;
import br.com.proximati.biprime.server.olapql.language.query.SimpleNode;
import br.com.proximati.biprime.server.olapql.language.query.StringLiteral;
import br.com.proximati.biprime.server.olapql.language.utils.TranslationUtils;
import br.com.proximati.biprime.metadata.Application;
import br.com.proximati.biprime.metadata.entity.CubeLevel;
import br.com.proximati.biprime.metadata.entity.Level;
import br.com.proximati.biprime.metadata.entity.Metadata;
import br.com.proximati.biprime.server.olapql.language.query.AdditiveExpression;
import br.com.proximati.biprime.server.olapql.language.query.Compare;
import br.com.proximati.biprime.server.olapql.language.query.DateLiteral;
import br.com.proximati.biprime.server.olapql.language.query.EndsWithExpression;
import br.com.proximati.biprime.server.olapql.language.query.InExpression;
import br.com.proximati.biprime.server.olapql.language.query.Instruction;
import br.com.proximati.biprime.server.olapql.language.query.LikeExpression;
import br.com.proximati.biprime.server.olapql.language.query.MultiplicativeExpression;
import br.com.proximati.biprime.server.olapql.language.query.NumberLiteral;
import br.com.proximati.biprime.server.olapql.language.query.QueryParser;
import br.com.proximati.biprime.server.olapql.language.query.StartsWithExpression;
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

    private br.com.proximati.biprime.metadata.entity.Cube cube;
    private QueryMetadataExtractor extractor = new QueryMetadataExtractor();
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
    private BidiMap axisNodeCoordinateMap = new DualHashBidiMap();

    @Override
    public void visit(Select node, StringBuilder data) {
        cube = Application.getCubeDao().findByName(TranslationUtils.extractName(((SimpleNode) node.jjtGetChild(2)).jjtGetValue().toString()));

        extractor.visit(node, data);

        data.append("select ");

        visitChildren(node, data);

        if (!axisNodeMetadataMap.isEmpty()) {

            StringBuilder groupBy = new StringBuilder();

            for (Node n : axisNodeList) {
                Metadata metadata = getAxisNodeMetadataMap().get(n);

                if (metadata instanceof br.com.proximati.biprime.metadata.entity.Level
                        || metadata instanceof br.com.proximati.biprime.metadata.entity.Property
                        || metadata instanceof br.com.proximati.biprime.metadata.entity.Filter) {
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
    public void visit(Cube node, StringBuilder data) {
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
    public void visit(FilterExpression node, StringBuilder data) {
        data.append(" and ");
        visitChildren(node, data);
    }

    @Override
    public void visit(OrCondition node, StringBuilder data) {
        visitOperation(node, "or", data);
    }

    @Override
    public void visit(AndCondition node, StringBuilder data) {
        visitOperation(node, "and", data);
    }

    @Override
    public void visit(Negation node, StringBuilder data) {
        if (data.charAt(data.length() - 1) != ' ') {
            data.append(" not");
        } else {
            data.append("not ");
        }

        visit(node.jjtGetChild(0), data);
    }

    @Override
    public void visit(br.com.proximati.biprime.server.olapql.language.query.Level node, StringBuilder data) {
        br.com.proximati.biprime.metadata.entity.Level level =
                extractor.getAllReferencedMetadata().getLevel(node.jjtGetValue().toString());

        data.append(translateLevel(level));
    }

    @Override
    public void visit(Filter node, StringBuilder data) {
        br.com.proximati.biprime.metadata.entity.Filter filter =
                Application.getFilterDao().findByName(TranslationUtils.extractName(node.jjtGetValue().toString()));

        data.append(translateFilterExpression(filter.getExpression()));
    }

    @Override
    public void visit(PropertyNode node, StringBuilder data) {
        nodeCoordinates.push(childIndex(node));

        br.com.proximati.biprime.metadata.entity.Property property =
                extractor.getAllReferencedMetadata().getProperty(node.jjtGetValue().toString());

        registrateAxisNode(node, property);

        data.append(translateProperty(property));
        data.append(" as ").append(getAxisNodeCoordinateMap().get(node));
        data.append(", ");

        super.visit(node, data);
        nodeCoordinates.pop();
    }

    @Override
    public void visit(Property node, StringBuilder data) {
        br.com.proximati.biprime.metadata.entity.Property property =
                extractor.getAllReferencedMetadata().getProperty(node.jjtGetValue().toString());

        data.append(translateProperty(property));
    }

    @Override
    public void visit(Compare node, StringBuilder data) {
        data.append(" ").append(node.jjtGetValue()).append(" ");
    }

    @Override
    public void visit(DateLiteral node, StringBuilder data) {
        data.append(node.jjtGetValue());
    }

    @Override
    public void visit(StringLiteral node, StringBuilder data) {
        String string = node.jjtGetValue().toString();

        data.append(TranslationUtils.encloseString(TranslationUtils.discloseString(string)));
    }

    @Override
    public void visit(AdditiveExpression node, StringBuilder data) {
        visitOperation(node, data);
    }

    @Override
    public void visit(MultiplicativeExpression node, StringBuilder data) {
        visitOperation(node, data);
    }

    @Override
    public void visit(NumberLiteral node, StringBuilder data) {
        data.append(node.jjtGetValue());
    }

    @Override
    public void visit(Axis node, StringBuilder data) {
        if (node.jjtGetValue().toString().equals("ROWS")) {
            nodeCoordinates.push(0);
        } else {
            nodeCoordinates.push(1);
        }

        super.visit(node, data);

        nodeCoordinates.pop();
    }

    @Override
    public void visit(LevelOrMeasureOrFilter node, StringBuilder data) {
        nodeCoordinates.push(childIndex(node));

        StringBuilder sb = new StringBuilder();

        br.com.proximati.biprime.metadata.entity.Metadata metadata =
                extractor.getAllReferencedMetadata().getMeasure(node.jjtGetValue().toString());

        if (metadata != null) {
            MeasureSqlTranslator translator = new MeasureSqlTranslator(cube);

            sb.append(translator.translate(node.jjtGetValue().toString()));
        } else {
            metadata = extractor.getAllReferencedMetadata().getLevel(node.jjtGetValue().toString());

            if (metadata != null) {
                data.append(TranslationUtils.columnExpression(((br.com.proximati.biprime.metadata.entity.Level) metadata).getTableName(), ((Level) metadata).getCodeProperty().getColumnName()));
            } else {
                metadata = extractor.getAllReferencedMetadata().getFilter(node.jjtGetValue().toString());

                sb.append("case when ").append(translateFilterExpression(((br.com.proximati.biprime.metadata.entity.Filter) metadata).getExpression())).append(" then 1 else 0 end");
            }
        }

        registrateAxisNode(node, metadata);

        data.append(sb).append(" as ").append(getAxisNodeCoordinateMap().get(node)).append(", ");

        super.visit(node, data);
        nodeCoordinates.pop();
    }

    @Override
    public void visit(InExpression node, StringBuilder data) {
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
    public void visit(LikeExpression node, StringBuilder data) {
        String operand = ((SimpleNode) node.jjtGetChild(0)).jjtGetValue().toString();

        StringBuilder sb = new StringBuilder();
        sb.append(TranslationUtils.likeWildcard());
        sb.append(TranslationUtils.discloseString(operand));
        sb.append(TranslationUtils.likeWildcard());

        data.append(" like ");
        data.append(TranslationUtils.encloseString(sb.toString()));
    }

    @Override
    public void visit(StartsWithExpression node, StringBuilder data) {
        String operand = ((SimpleNode) node.jjtGetChild(0)).jjtGetValue().toString();

        StringBuilder sb = new StringBuilder();
        sb.append(TranslationUtils.discloseString(operand));
        sb.append(TranslationUtils.likeWildcard());

        data.append(" like ");
        data.append(TranslationUtils.encloseString(sb.toString()));
    }

    @Override
    public void visit(EndsWithExpression node, StringBuilder data) {
        String operand = ((SimpleNode) node.jjtGetChild(0)).jjtGetValue().toString();

        StringBuilder sb = new StringBuilder();
        sb.append(TranslationUtils.likeWildcard());
        sb.append(TranslationUtils.discloseString(operand));

        data.append(" like ");
        data.append(TranslationUtils.encloseString(sb.toString()));
    }

    private void visitOperation(SimpleNode node, StringBuilder data) {
        visitOperation(node, node.jjtGetValue().toString(), data);
    }

    private void visitOperation(SimpleNode node, String op, StringBuilder data) {
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
            } else if (entry.getValue() instanceof br.com.proximati.biprime.metadata.entity.Property) {
                levels.add(((br.com.proximati.biprime.metadata.entity.Property) entry.getValue()).getLevel());
            }
        }

        return levels;
    }

    /**
     * Produz a cláusula WHERE da consulta, baseado nos joins resultantes das referências
     * para os níveis da consulta.
     * @return
     */
    private String whereExpression(StringBuilder data) {
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

    private String translateLevel(br.com.proximati.biprime.metadata.entity.Level level) {
        StringBuilder sb = new StringBuilder();

        sb.append(TranslationUtils.columnExpression(level.getTableName(), level.getCodeProperty().getColumnName()));

        return sb.toString();
    }

    private String translateProperty(br.com.proximati.biprime.metadata.entity.Property property) {
        StringBuilder sb = new StringBuilder();

        sb.append(TranslationUtils.columnExpression(property.getLevel().getTableName(), property.getColumnName()));

        return sb.toString();
    }

    private void registrateAxisNode(SimpleNode node, Metadata metadata) {
        getAxisNodeMetadataMap().put(node, metadata);
        axisNodeList.add(node);
        getAxisNodeCoordinateMap().put(node, calculateCoordinates(node));
    }

    private String calculateCoordinates(SimpleNode node) {
        StringBuilder coordinate = new StringBuilder();

        for (int i = 0; i < nodeCoordinates.size(); i++) {
            if (i == 0) {
                if (nodeCoordinates.get(i) == 0) {
                    coordinate.append("r_");
                } else {
                    coordinate.append("c_");
                }
            } else {
                coordinate.append(nodeCoordinates.get(i)).append("_");
            }
        }

        coordinate.deleteCharAt(coordinate.length() - 1);

        return coordinate.toString();
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
    public BidiMap getAxisNodeCoordinateMap() {
        return axisNodeCoordinateMap;
    }

    /**
     * Translates an olapql instruction into a sql instruction.
     * @param instruction
     * @return
     * @throws ParseException 
     */
    public String translateOlapQlInstruction(String instruction) throws ParseException {
        QueryParser parser = new QueryParser(IOUtils.toInputStream(instruction));
        Instruction node = (Instruction) parser.instruction();

        StringBuilder sb = new StringBuilder();
        QuerySqlTranslator translator = new QuerySqlTranslator();
        translator.visit(node, sb);

        return sb.toString();
    }

    public String translateFilterExpression(String filterExpression) {
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
}