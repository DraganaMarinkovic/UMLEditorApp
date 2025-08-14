package raf.dsw.classycraft.app.model.implementation;

import raf.dsw.classycraft.app.model.ClassyNode;
import raf.dsw.classycraft.app.model.ClassyNodeComposite;

public class ProjectExplorerFactory extends NodeFactory{
    private static boolean exist = false;
    public ClassyNode create(ClassyNodeComposite parent){
        if(!exist){
            exist = true;
            return new ProjectExplorer("Project explorer");
        }
        return null;
    }
}
