package raf.dsw.classycraft.app.model.implementation;

import raf.dsw.classycraft.app.model.ClassyNode;
import raf.dsw.classycraft.app.model.ClassyNodeComposite;

public class ProjectFactory extends NodeFactory{
    private static int id = 1;

    public ClassyNode create(ClassyNodeComposite parent){
        return new Project("Project" + id++,parent);
    }

}
