package raf.dsw.classycraft.app.controller;

import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import java.awt.event.ActionEvent;

public class DeletePaintAction extends AbstractClassyAction{

    public DeletePaintAction(){
        putValue(SMALL_ICON, loadIcon("/images/deletechild.png"));
        putValue(NAME, "Delete");
        putValue(SHORT_DESCRIPTION, "Delete");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().getDesktop().startBrisanje();
    }
}
