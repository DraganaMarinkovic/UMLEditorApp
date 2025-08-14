package raf.dsw.classycraft.app.controller;

import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.model.implementation.Diagram;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class RedoAction extends AbstractClassyAction{

    public RedoAction(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
                KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
        putValue(MNEMONIC_KEY, KeyEvent.VK_R);
        putValue(SMALL_ICON, loadIcon("/images/editredo.png"));
        putValue(NAME, "Redo");
        putValue(SHORT_DESCRIPTION, "Redo");
        setEnabled(false);
    }

    public void actionPerformed(ActionEvent e) {
        if (MainFrame.getInstance().getDesktop().getjTabbedPane().getSelectedComponent() instanceof DiagramView)
            ((DiagramView) MainFrame.getInstance().getDesktop().getjTabbedPane().getSelectedComponent()).getCommandManager().doCommand();
    }


}
