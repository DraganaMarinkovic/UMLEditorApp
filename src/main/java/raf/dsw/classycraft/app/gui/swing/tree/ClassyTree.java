package raf.dsw.classycraft.app.gui.swing.tree;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.tree.view.ClassyTreeView;
import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.model.ClassyNode;
import raf.dsw.classycraft.app.model.implementation.Diagram;
import raf.dsw.classycraft.app.model.implementation.InterClass;
import raf.dsw.classycraft.app.model.implementation.ProjectExplorer;

public interface ClassyTree {

    ClassyTreeView generateTree(ProjectExplorer projectExplorer);
    void addChild(ClassyTreeItem parent);
    ClassyTreeItem getSelectedNode();
    void deleteChild(ClassyTreeItem child);

    void addDiagramChild(DiagramView dv, ClassyTreeItem diagram, InterClass interClass);
    void deleteDiagramChild(DiagramView dv, ClassyTreeItem diagram, InterClass interClass);

    void addToTreeRec(ClassyTreeItem parent, ClassyNode node);

}