package raf.dsw.classycraft.app.model.implementation;

import raf.dsw.classycraft.app.model.ClassyNode;
import raf.dsw.classycraft.app.model.ClassyNodeComposite;

public abstract class NodeFactory {
    public abstract ClassyNode create(ClassyNodeComposite parent);
}
