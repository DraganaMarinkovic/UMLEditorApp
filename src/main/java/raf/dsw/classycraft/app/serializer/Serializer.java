package raf.dsw.classycraft.app.serializer;

import raf.dsw.classycraft.app.model.implementation.Diagram;
import raf.dsw.classycraft.app.model.implementation.Package;
import raf.dsw.classycraft.app.model.implementation.Project;
import raf.dsw.classycraft.app.model.implementation.ProjectExplorer;

import java.io.File;

public interface Serializer {
    Project loadProject(File file);
    void saveProject(Project node);

    Diagram loadDiagram(File file);

    void saveDiagram(Diagram node);
}
