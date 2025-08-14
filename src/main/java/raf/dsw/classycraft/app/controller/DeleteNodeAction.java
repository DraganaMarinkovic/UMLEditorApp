package raf.dsw.classycraft.app.controller;

import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.errorHandler.TipPoruke;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.model.implementation.ProjectExplorer;
import java.awt.event.ActionEvent;

public class DeleteNodeAction extends AbstractClassyAction{
    public DeleteNodeAction(){
        putValue(SMALL_ICON, loadIcon("/images/deletechild.png"));
        putValue(NAME, "Delete node");
        putValue(SHORT_DESCRIPTION, "Delete node");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        ClassyTreeItem selected = MainFrame.getInstance().getClassyTree().getSelectedNode();
        if(selected != null && !(selected.getClassyNode() instanceof ProjectExplorer)){
            MainFrame.getInstance().getClassyTree().deleteChild(selected);
        } else{
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage("PROJECT_EXPLORER_CANNOT_BE_DELETED", TipPoruke.GRESKA);
        }
    }
}
