package raf.dsw.classycraft.app.model;

import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.model.implementation.Package;
import raf.dsw.classycraft.app.model.implementation.Project;
import raf.dsw.classycraft.app.model.implementation.ProjectExplorer;

public abstract class ClassyNodeComposite extends ClassyNode {
    public ClassyNodeComposite(String name, ClassyNode parent) {
        super(name, parent);
    }

    public void addChild(ClassyNode child){
        children.add(child);
    }
    public void removeChild(ClassyNode child){
        children.remove(child);
    }
}
