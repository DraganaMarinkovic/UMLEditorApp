package raf.dsw.classycraft.app.controller;

import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class UndoAction extends AbstractClassyAction{

    public UndoAction(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
                KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        putValue(MNEMONIC_KEY, KeyEvent.VK_U);
        putValue(SMALL_ICON, loadIcon("/images/editundo.png"));
        putValue(NAME, "Undo");
        putValue(SHORT_DESCRIPTION, "Undo");
        setEnabled(false);
    }

    public void actionPerformed(ActionEvent e) {
        if (MainFrame.getInstance().getDesktop().getjTabbedPane().getSelectedComponent() instanceof DiagramView)
            ((DiagramView) MainFrame.getInstance().getDesktop().getjTabbedPane().getSelectedComponent()).getCommandManager().undoCommand();
    }

}
