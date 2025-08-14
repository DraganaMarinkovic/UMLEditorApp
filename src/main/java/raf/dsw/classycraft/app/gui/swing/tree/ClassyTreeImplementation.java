package raf.dsw.classycraft.app.gui.swing.tree;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.errorHandler.TipPoruke;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.tree.view.ClassyTreeView;
import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.painter.ConnectionPainter;
import raf.dsw.classycraft.app.gui.swing.view.painter.InterClassPainter;
import raf.dsw.classycraft.app.model.ClassyNode;
import raf.dsw.classycraft.app.model.ClassyNodeComposite;
import raf.dsw.classycraft.app.model.implementation.*;
import raf.dsw.classycraft.app.model.implementation.Enum;
import raf.dsw.classycraft.app.model.implementation.Package;
import raf.dsw.classycraft.app.observer.ISubscriber;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;

public class ClassyTreeImplementation implements ClassyTree {
    private ClassyTreeView treeView;
    private DefaultTreeModel treeModel;

    @Override
    public ClassyTreeView generateTree(ProjectExplorer projectExplorer) {
        ClassyTreeItem root = new ClassyTreeItem(projectExplorer);
        treeModel = new DefaultTreeModel(root);
        treeView = new ClassyTreeView(treeModel);
        return treeView;
    }

    @Override
    public void addChild(ClassyTreeItem parent) {

        if (parent==null || !(parent.getClassyNode() instanceof ClassyNodeComposite))
        {
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage("CHILD_CANNOT_BE_ADDED", TipPoruke.GRESKA);
            return;
        }

        ClassyNode child = createChild((ClassyNodeComposite) parent.getClassyNode());
        if (child instanceof Diagram) {
            ClassyTreeItem classyTreeItem = new ClassyTreeItem(child);
            ((DiagramView)((Diagram) child).getSubscribers().get(0)).setD(classyTreeItem);
            parent.add(classyTreeItem);
        }else {
            parent.add(new ClassyTreeItem(child));
        }
        ((ClassyNodeComposite) parent.getClassyNode()).addChild((ClassyNode) child);
        treeView.expandPath(treeView.getSelectionPath());
        SwingUtilities.updateComponentTreeUI(treeView);
    }

    public void addDiagramChild(DiagramView dv, ClassyTreeItem diagram, InterClass interClass) {
        if (diagram==null || !(diagram.getClassyNode() instanceof ClassyNodeComposite))
        {
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage("CHILD_CANNOT_BE_ADDED", TipPoruke.GRESKA);
            return;
        }

        ClassyTreeItem content = new ClassyTreeItem(interClass);
        dv.addCont(content);
        diagram.add(content);
        treeView.expandPath(treeView.getSelectionPath());
        SwingUtilities.updateComponentTreeUI(treeView);
    }

    @Override
    public ClassyTreeItem getSelectedNode() {
        return (ClassyTreeItem) treeView.getLastSelectedPathComponent();
    }

    @Override
    public void deleteChild(ClassyTreeItem child) {
        if (child.getParent() == null) {
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage("PROJECT_EXPLORER_CANNOT_BE_DELETED", TipPoruke.GRESKA);
            return;
        }
        if (child.getClassyNode() instanceof DiagramElement) {
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage("DIAGRAM_ELEMENTS_CANNOT_BE_DELETED", TipPoruke.UPOZORENJE);
            return;
        }

        ClassyNodeComposite temp = (ClassyNodeComposite) child.getClassyNode().getParent();
        ((ClassyNodeComposite) child.getClassyNode().getParent()).removeChild(child.getClassyNode());
        ((MutableTreeNode)child.getParent()).remove(child);
        treeView.expandPath(treeView.getSelectionPath());
        SwingUtilities.updateComponentTreeUI(treeView);
    }

    public void deleteDiagramChild(DiagramView dv, ClassyTreeItem diagram, InterClass interClass) {

        ClassyTreeItem del = null;
        for (ClassyTreeItem cc : dv.getContent()){
            if (cc.getClassyNode().equals(interClass)){
                del = cc;
                break;
            }
        }
        if (del != null) {
            diagram.remove(del);
            dv.getContent().remove(del);
        }
        treeView.expandPath(treeView.getSelectionPath());
        SwingUtilities.updateComponentTreeUI(treeView);
    }

    private ClassyNode createChild(ClassyNodeComposite parent) {
        NodeFactory factory;

        if (parent instanceof ProjectExplorer){
            factory = new ProjectFactory();
            return factory.create(parent);
        }

        if(parent instanceof Project){
            factory = new PackageFactory();
            Package p = (Package) factory.create(parent);
            p.addSubscriber(MainFrame.getInstance().getDesktop());
            return p;
        }
        if(parent instanceof raf.dsw.classycraft.app.model.implementation.Package){
            Object[] possibilities = {"Package", "Diagram"};
            String s = (String)JOptionPane.showInputDialog(null,
                    "Do you want to create a package or a diagram?",
                    "Please pick an option",1, null,possibilities,
                    "Diagram");

            if ((s != null) && (s.length() > 0)) {
                if(s.equals("Diagram")){
                    factory = new DiagramFactory();
                    return factory.create(parent);
                } else{
                    factory = new PackageFactory();
                    Package p = (Package) factory.create(parent);
                    p.addSubscriber(MainFrame.getInstance().getDesktop());
                    return p;
                }
            }
            return null;
        }
        return null;
    }

    public void addToTreeRec(ClassyTreeItem parent, ClassyNode node) {
        if (node instanceof Project) {
            ClassyTreeItem classyTreeItem = new ClassyTreeItem(node);
            parent.add(classyTreeItem);
            for (ClassyNode c : node.getChildren()) {
                c.setParent(node);
                addToTreeRec(classyTreeItem, c);
            }

        } else if (node instanceof Package) {
            ClassyTreeItem classyTreeItem = new ClassyTreeItem(node);
            parent.add(classyTreeItem);
            ((Package) node).addSubscriber(MainFrame.getInstance().getDesktop());

            for (ClassyNode c : node.getChildren()) {
                if (c instanceof Diagram) {
                    ((Diagram) c).setP((Package) node);
                }
                c.setParent(node);
                addToTreeRec(classyTreeItem, c);
            }
        } else if (node instanceof Diagram) {
            ClassyTreeItem dTree = new ClassyTreeItem(node);
            parent.add(dTree);
            DiagramView dv = new DiagramView((Diagram) node);
            dv.setD(dTree);
            ((Diagram) node).addSubscriber(dv);
            for (ClassyNode e : node.getChildren()){
                if (e instanceof InterClass) {
                    InterClassPainter icp = new InterClassPainter((DiagramElement) e);
                    dv.addPainter(icp);
                    addDiagramChild(dv, dTree, (InterClass) e);
                    if (e instanceof Klasa) {
                        for (ClassContent c : ((Klasa) e).getClassContents()) {
                            c.addSubscriber((ISubscriber) e);
                        }
                    } else if (e instanceof Interfejs) {
                        for (Metod c : ((Interfejs) e).getMetode()) {
                            c.addSubscriber((ISubscriber) e);
                        }
                    } else if (e instanceof Enum) {
                        for (EnumElement c : ((Enum) e).getEnumElements()) {
                            c.addSubscriber((ISubscriber) e);
                        }
                    }
                }
                e.setParent(node);
                if (e instanceof Connection) {

                    for (ClassyNode c : node.getChildren()) {
                        if (c instanceof InterClass && c.getName().equals(((Connection) e).getFrom().getName())){
                            ((Connection) e).setFrom((InterClass) c);
                        } else if (c instanceof InterClass && c.getName().equals(((Connection) e).getTo().getName())){
                            ((Connection) e).setTo((InterClass) c);
                        }
                    }
                    ConnectionPainter cp = new ConnectionPainter((DiagramElement) e);
                    dv.addPainter(cp);
                }
                if (e instanceof DiagramElement) {
                    ((DiagramElement) e).addSubscriber((ISubscriber) node);
                }
            }
        }
    }
}
