package raf.dsw.classycraft.app.controller;

import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import java.awt.event.ActionEvent;

public class DuplicateAction extends AbstractClassyAction{
    public DuplicateAction(){
        putValue(SMALL_ICON, loadIcon("/images/copy.png"));
        putValue(NAME, "Duplicate Interclass");
        putValue(SHORT_DESCRIPTION, "Duplicate Interclass");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().getDesktop().startDuplicate();
    }
}
