package raf.dsw.classycraft.app.controller;

import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import java.awt.event.ActionEvent;

public class NewConnectionAction extends AbstractClassyAction{
    public NewConnectionAction(){
        putValue(SMALL_ICON, loadIcon("/images/arrow.png"));
        putValue(NAME, "New Connection");
        putValue(SHORT_DESCRIPTION, "New Connection");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().getDesktop().startDodavanjeConnection();
    }
}
