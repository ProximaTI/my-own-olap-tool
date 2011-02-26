package br.com.bi.controller.query;

import br.com.bi.model.BiFacade;
import br.com.bi.model.entity.metadata.Level;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

@ManagedBean
@SessionScoped
public class QueryEdit {
    
    private TreeNode levelsRoot;
    private TreeNode selectedLevel;

    /**
     * @return the levelsRoot
     */
    public TreeNode getLevelsRoot() {
        if (levelsRoot == null) {
            levelsRoot = new DefaultTreeNode("levelsRoot", null);            
            
            List<Level> levels = BiFacade.getInstance().findAllLevels();
            
            for (Level level : levels) {
                TreeNode node = new DefaultTreeNode(level.getName(), levelsRoot);
                levelsRoot.addChild(node);
            }
        }
        return levelsRoot;
    }

    /**
     * @param levelsRoot the levelsRoot to set
     */
    public void setLevelsRoot(TreeNode levelsRoot) {
        this.levelsRoot = levelsRoot;
    }

    /**
     * @return the selectedLevel
     */
    public TreeNode getSelectedLevel() {
        return selectedLevel;
    }

    /**
     * @param selectedLevel the selectedLevel to set
     */
    public void setSelectedLevel(TreeNode selectedLevel) {
        this.selectedLevel = selectedLevel;
    }
}
