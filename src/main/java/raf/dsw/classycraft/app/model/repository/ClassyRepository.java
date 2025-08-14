package raf.dsw.classycraft.app.model.repository;

import raf.dsw.classycraft.app.model.ClassyNode;
import raf.dsw.classycraft.app.model.ClassyNodeComposite;
import raf.dsw.classycraft.app.model.implementation.ProjectExplorer;

public interface ClassyRepository{

    ProjectExplorer getProjectExplorer();

    void addChild(ClassyNodeComposite parent, ClassyNode child);

    String findAuthor(ClassyNode node);
}
