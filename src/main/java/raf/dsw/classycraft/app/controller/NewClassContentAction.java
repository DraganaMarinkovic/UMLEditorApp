package raf.dsw.classycraft.app.controller;

import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import java.awt.event.ActionEvent;

public class NewClassContentAction extends AbstractClassyAction{
    public NewClassContentAction(){
        putValue(SMALL_ICON, loadIcon("/images/content.png"));
        putValue(NAME, "New Content");
        putValue(SHORT_DESCRIPTION, "New Content");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().getDesktop().startDodavanjeContent();
    }
}
