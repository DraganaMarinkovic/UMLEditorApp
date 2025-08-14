package raf.dsw.classycraft.app.model.repository;

import raf.dsw.classycraft.app.model.ClassyNode;
import raf.dsw.classycraft.app.model.ClassyNodeComposite;
import raf.dsw.classycraft.app.model.implementation.Project;
import raf.dsw.classycraft.app.model.implementation.ProjectExplorer;

public class ClassyRepositoryImplementation implements ClassyRepository{

    private ProjectExplorer projectExplorer;

    public ClassyRepositoryImplementation() {
        projectExplorer = new ProjectExplorer("My Project Explorer");
    }

    @Override
    public ProjectExplorer getProjectExplorer() {
        return projectExplorer;
    }

    @Override
    public void addChild(ClassyNodeComposite parent, ClassyNode child) {
        parent.addChild((ClassyNodeComposite) child);
    }

    public String findAuthor(ClassyNode node){
        if (node instanceof Project)
            return node.getName() + "   Author: " +((Project) node).getAutor();
        return findAuthor(node.getParent());
    }
}
