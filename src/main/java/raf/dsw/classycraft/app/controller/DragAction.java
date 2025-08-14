package raf.dsw.classycraft.app.controller;

import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import java.awt.event.ActionEvent;

public class DragAction extends AbstractClassyAction{
    public DragAction() {
        putValue(SMALL_ICON, loadIcon("/images/drag.png"));
        putValue(NAME, "Drag surface");
        putValue(SHORT_DESCRIPTION, "Drag surface");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().getDesktop().startDrag();
    }
}
