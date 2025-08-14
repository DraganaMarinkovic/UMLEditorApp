package raf.dsw.classycraft.app.controller;

import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class NewInterClassAction extends AbstractClassyAction{
    public NewInterClassAction(){
        putValue(SMALL_ICON, loadIcon("/images/klasa.png"));
        putValue(NAME, "New InterClass");
        putValue(SHORT_DESCRIPTION, "New InterClass");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().getDesktop().startDodavanjeInterClass();
    }
}
