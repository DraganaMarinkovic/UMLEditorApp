package raf.dsw.classycraft.app.serializer.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import raf.dsw.classycraft.app.model.implementation.Diagram;
import raf.dsw.classycraft.app.model.implementation.Package;
import raf.dsw.classycraft.app.model.implementation.Project;
import raf.dsw.classycraft.app.model.implementation.ProjectExplorer;
import raf.dsw.classycraft.app.serializer.Serializer;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JacksonSerializer implements Serializer {

    @Override
    public Project loadProject(File file) {
        try (FileReader fileReader = new FileReader(file)) {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(fileReader, Project.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void saveProject(Project node) {
        try (FileWriter writer = new FileWriter(node.getPath())) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(writer, node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Diagram loadDiagram(File file) {
        try (FileReader fileReader = new FileReader(file)) {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(fileReader, Diagram.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void saveDiagram(Diagram node) {
        try (FileWriter writer = new FileWriter("src/main/resources/sabloni/" + node.getName())) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(writer, node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
