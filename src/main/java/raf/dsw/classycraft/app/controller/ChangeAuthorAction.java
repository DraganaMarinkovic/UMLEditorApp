package raf.dsw.classycraft.app.controller;

import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.errorHandler.TipPoruke;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.model.implementation.Project;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ChangeAuthorAction extends AbstractClassyAction{


    public ChangeAuthorAction(){
        putValue(SMALL_ICON, loadIcon("/images/autor.png"));
        putValue(NAME, "Change author");
        putValue(SHORT_DESCRIPTION, "Change author");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        ClassyTreeItem selected = MainFrame.getInstance().getClassyTree().getSelectedNode();
        if (selected.getClassyNode() instanceof Project) {
            String n = ((Project) selected.getClassyNode()).getAutor();
            String s = (String) JOptionPane.showInputDialog(null,
                    "Please enter name: ", "Change author");
            ((Project)selected.getClassyNode()).changeAutor(s == null ? n : s);

        } else {
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage("THIS_IS_NOT_A_PROJECT", TipPoruke.OBAVESTENJE);
        }
    }
}
