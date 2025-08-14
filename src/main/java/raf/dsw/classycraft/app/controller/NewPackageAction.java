package raf.dsw.classycraft.app.controller;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.errorHandler.TipPoruke;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.model.implementation.Package;
import raf.dsw.classycraft.app.model.implementation.Project;

import java.awt.event.ActionEvent;

public class NewPackageAction extends AbstractClassyAction{
    public NewPackageAction(){
        putValue(SMALL_ICON, loadIcon("/images/packageplus.png"));
        putValue(NAME, "New Package");
        putValue(SHORT_DESCRIPTION, "New Package");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        ClassyTreeItem selected = MainFrame.getInstance().getClassyTree().getSelectedNode();
        if(selected.getClassyNode() instanceof Project || selected.getClassyNode() instanceof Package){
            MainFrame.getInstance().getClassyTree().addChild(selected);
        } else{
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage("CANNOT_MAKE_PACKAGE", TipPoruke.GRESKA);
        }
    }
}
