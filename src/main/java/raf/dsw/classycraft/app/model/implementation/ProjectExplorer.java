package raf.dsw.classycraft.app.model.implementation;

import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.model.ClassyNode;
import raf.dsw.classycraft.app.model.ClassyNodeComposite;

public class ProjectExplorer extends ClassyNodeComposite {

    public ProjectExplorer(String name) {
        super(name,null);
        type = "projectExplorer";
    }

    @Override
    public void removeChild(ClassyNode child) {
        super.removeChild(child);
        MainFrame.getInstance().getDesktop().getLabel().setText("");
        MainFrame.getInstance().getDesktop().getjTabbedPane().removeAll();
    }
}
