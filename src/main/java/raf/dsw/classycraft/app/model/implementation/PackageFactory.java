package raf.dsw.classycraft.app.model.implementation;

import raf.dsw.classycraft.app.model.ClassyNode;
import raf.dsw.classycraft.app.model.ClassyNodeComposite;

public class PackageFactory extends NodeFactory{
    private static int id = 1;
    public ClassyNode create(ClassyNodeComposite parent){
        return new Package("Package" + id++, parent);
    }
}
