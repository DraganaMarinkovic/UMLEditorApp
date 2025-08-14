package raf.dsw.classycraft.app.gui.swing.view;

import raf.dsw.classycraft.app.controller.*;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class MyMenyBar extends JMenuBar {

    public MyMenyBar(){
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        ExitAction ea = MainFrame.getInstance().getActionManager().getExitAction();
        AboutUs au = MainFrame.getInstance().getActionManager().getAboutUs();
        NewProjectAction npa = MainFrame.getInstance().getActionManager().getNewProjectAction();
        NewPackageAction npg = MainFrame.getInstance().getActionManager().getNewPackageAction();
        DeleteNodeAction dna = MainFrame.getInstance().getActionManager().getDeleteNodeAction();
        ChangeAuthorAction caa = MainFrame.getInstance().getActionManager().getChangeAuthorAction();
        fileMenu.add(npa);
        fileMenu.add(npg);
        fileMenu.add(dna);
        fileMenu.add(caa);
        editMenu.add(au);
        fileMenu.add(ea);
        add(fileMenu);
        add(editMenu);
    }

}
