package raf.dsw.classycraft.app.gui.swing.view;

import raf.dsw.classycraft.app.controller.*;

import javax.swing.*;

public class MyToolBar extends JToolBar {
    public MyToolBar(){
        super(HORIZONTAL);
        setFloatable(false);

        ExitAction ea = MainFrame.getInstance().getActionManager().getExitAction();
        NewProjectAction newProjectAction = MainFrame.getInstance().getActionManager().getNewProjectAction();
        NewPackageAction newPackageAction = MainFrame.getInstance().getActionManager().getNewPackageAction();
        DeleteNodeAction deleteNodeAction = MainFrame.getInstance().getActionManager().getDeleteNodeAction();
        ChangeAuthorAction caa = MainFrame.getInstance().getActionManager().getChangeAuthorAction();

        add(ea);
        add(newProjectAction);
        add(newPackageAction);
        add(deleteNodeAction);
        add(caa);

        add(MainFrame.getInstance().getActionManager().getRedoAction());
        add(MainFrame.getInstance().getActionManager().getUndoAction());
        add(MainFrame.getInstance().getActionManager().getSaveAction());
        add(MainFrame.getInstance().getActionManager().getLoadAction());
    }
}
