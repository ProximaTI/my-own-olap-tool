package br.com.bi.view.managedbeans;


import br.com.bi.model.entity.queries.Node;
import br.com.bi.model.metadata.MetadataFacade;
import br.com.bi.view.Util;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.myfaces.trinidad.event.RowDisclosureEvent;
import org.apache.myfaces.trinidad.model.ChildPropertyTreeModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.TreeModel;

import org.olap4j.OlapException;
import org.olap4j.metadata.Cube;
import org.olap4j.metadata.Dimension;
import org.olap4j.metadata.Hierarchy;
import org.olap4j.metadata.Level;
import org.olap4j.metadata.Measure;
import org.olap4j.metadata.Member;
import org.olap4j.metadata.MetadataElement;


/**
 * Backing bean para o controle dos metadados apresentados na página
 * de edição de consultas.
 *
 * @author Luiz Augusto Garcia da Silva
 */
public class CubeMetadataBean {
    private CubeMetadataListener listener;

    private Cube selectedCube;
    private List<SelectItem> cubes;
    private List<Node> measuresModel;
    private List<Node> levels;
    private TreeModel levelsTreeModel;
    private TreeModel measuresTreeModel;

    /**
     * Retorna o caminho da imagem relacionada ao tipo do nó.
     * @return
     */
    public String getNodeImage() {
        if (getActualNode().isLevel()) {
            return "/images/level.gif";
        } else if (getActualNode().isMeasure()) {
            return "/images/measure.gif";
        } else if (getActualNode().isMember()) {
            return "/images/member.gif";
        }
        return null;
    }

    /**
     * Retorna o nó que está ligado ao bean "tuple".
     * @return
     */
    public Node getActualNode() {
        return (Node)Util.getManagedBean("node");
    }

    /**
     * Retorna a lista de cubos do catálogo.
     * @return
     * @throws OlapException
     */
    public List<SelectItem> getCubes() throws OlapException {
        if (cubes == null) {
            cubes = new ArrayList<SelectItem>();

            for (Cube cube : MetadataFacade.getInstance().findAllCubes()) {
                cubes.add(new SelectItem(cube, cube.getName()));
            }
        }
        return cubes;
    }

    /**
     * Altera o cubo selecionado.
     * @param selectedCube
     */
    public void setSelectedCube(Cube selectedCube) {
        this.selectedCube = selectedCube;
    }

    /**
     * Retorna o cubo selecionado.
     * @return
     * @throws OlapException
     */
    public Cube getSelectedCube() throws OlapException {
        if (getCubes() != null && getCubes().size() == 1)
            selectedCube = (Cube)getCubes().get(0).getValue();
        return selectedCube;
    }

    /**
     * Retorna o modelo da árvore de dimensões com suas hierarquias e níveis.
     * @return
     * @throws OlapException
     */
    public TreeModel getLevelsTreeModel() throws OlapException {
        if (levelsTreeModel == null && getSelectedCube() != null) {
            levels = new ArrayList<Node>();

            for (Dimension dimension : getSelectedCube().getDimensions())
                if (!dimension.getDimensionType().equals(Dimension.Type.MEASURE))
                    for (Hierarchy hierarchy : dimension.getHierarchies())
                        for (Level level : hierarchy.getLevels())
                            if (!level.getName().equals("(All)")) {
                                MetadataElementNode node =
                                    new MetadataElementNode(level);
                                node.addChild(new Node("..."));
                                levels.add(node);
                            }


            levelsTreeModel = new ChildPropertyTreeModel(levels, "children");
        }
        return levelsTreeModel;
    }

    /**
     * Retorna o modelo da árvore de métricas
     * @return
     * @throws OlapException
     */
    public TreeModel getMeasuresTreeModel() throws OlapException {
        if (measuresTreeModel == null && getSelectedCube() != null) {
            measuresModel = new ArrayList<Node>();

            for (Measure measure : getSelectedCube().getMeasures())
                measuresModel.add(new MetadataElementNode(measure));

            measuresTreeModel =
                    new ChildPropertyTreeModel(measuresModel, "children");
        }
        return measuresTreeModel;
    }

    /**
     * Trata da expansão dos nós da árvore de níveis.
     * @param rowDisclosureEvent
     * @throws OlapException
     */
    public void levelExpanded(RowDisclosureEvent rowDisclosureEvent) throws OlapException {
        // Trata da expansão
        if (rowDisclosureEvent.getAddedSet().getSize() > 0) {
            RowKeySet expandedRowKeySet = rowDisclosureEvent.getAddedSet();
            Object expandedKey = expandedRowKeySet.iterator().next();
            levelsTreeModel.setRowKey(expandedKey);

            Node node = (Node)levelsTreeModel.getRowData();

            // Trata da expansão de um nível
            if (node.isLevel() && node.getChildren().size() == 1 &&
                node.getChildren().get(0).getName().equals("...")) {
                node.getChildren().clear();

                Level level = (Level)((MetadataElementNode)node).getElement();

                for (Member member : topMembers(level)) {
                    MetadataElementNode newNode =
                        new MetadataElementNode(member);

                    if (member.getChildMemberCount() > 0)
                        newNode.addChild(new Node("..."));

                    node.addChild(newNode);
                }
                //Trata da expansão de um membro
            } else if (node.isMember() && node.getChildren().size() == 1 &&
                       node.getChildren().get(0).getName().equals("...")) {
                node.getChildren().clear();

                Member parentMember =
                    (Member)((MetadataElementNode)node).getElement();

                for (Member member : parentMember.getChildMembers()) {
                    MetadataElementNode newNode =
                        new MetadataElementNode(member);

                    if (member.getChildMemberCount() > 0)
                        newNode.addChild(new Node("..."));

                    node.addChild(newNode);
                }

            }
        }

        // Trata da contração
        if (rowDisclosureEvent.getRemovedSet().getSize() > 0) {
            RowKeySet collapsedRowKeySet = rowDisclosureEvent.getRemovedSet();
            Object expandedKey = collapsedRowKeySet.iterator().next();
            levelsTreeModel.setRowKey(expandedKey);

            Node node = (Node)levelsTreeModel.getRowData();

            if (node.getChildren().size() > 0) {
                node.getChildren().clear();
                node.getChildren().add(new Node("..."));
            } else
                node.getChildren().clear();
        }
    }

    /**
     * Retorna os membros top-level de um membro.
     * @param level
     * @return
     * @throws OlapException
     */
    private List<Member> topMembers(Level level) throws OlapException {
        List<Member> members = new ArrayList<Member>();

        if (level.getMembers().size() > 0) {
            Member parent = level.getMembers().get(0);

            while (parent != null &&
                   parent.getMemberType() != parent.getMemberType().ALL) {
                parent = parent.getParentMember();
            }

            members = (List<Member>)parent.getChildMembers();
        }
        return members;
    }

    public void cubeSelected(ValueChangeEvent valueChangeEvent) {
        listener.selectedCubeChanged((Cube)valueChangeEvent.getNewValue());
        selectedCube = (Cube)valueChangeEvent.getNewValue();
        measuresModel = null;
        levels = null;
        levelsTreeModel = null;
        measuresTreeModel = null;
    }

    public CubeMetadataListener getListener() {
        return listener;
    }

    public void setListener(CubeMetadataListener listener) {
        this.listener = listener;
    }

    class MetadataElementNode extends Node {
        private transient MetadataElement element;

        public MetadataElementNode(MetadataElement element) {
            this.element = element;
        }

        public MetadataElement getElement() {
            return element;
        }

        public void setElement(MetadataElement element) {
            this.element = element;
        }

        @Override
        public String getName() {
            return element.getName();
        }

        @Override
        public int getType() {
            if (element instanceof Level)
                return Node.TYPE_LEVEL;
            else if (element instanceof Measure)
                return Node.TYPE_MEASURE;
            else
                return Node.TYPE_MEMBER;
        }

        @Override
        public boolean isLevel() {
            return (element instanceof Level);
        }

        @Override
        public boolean isMeasure() {
            return (element instanceof Measure);
        }

        @Override
        public boolean isMember() {
            return (element instanceof Member);
        }

        @Override
        public String getAssociateElement() {
            return element.getUniqueName();
        }
    }
}
