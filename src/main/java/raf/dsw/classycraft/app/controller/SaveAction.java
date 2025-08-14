package raf.dsw.classycraft.app.controller;

import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.model.implementation.Diagram;
import raf.dsw.classycraft.app.model.implementation.Project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

public class SaveAction extends AbstractClassyAction {
    public SaveAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
                KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        putValue(NAME, "Save action");
        putValue(SHORT_DESCRIPTION, "Save action");
    }

    public void actionPerformed(ActionEvent arg0) {
        JFileChooser jfc = new JFileChooser();

        if (MainFrame.getInstance().getClassyTree().getSelectedNode().getClassyNode() instanceof Project) {

            Project project = (Project) MainFrame.getInstance().getClassyTree().getSelectedNode().getClassyNode();
            File projectFile = null;

            if (!project.getChanged()) {
                return;
            }

            if (project.getPath() == null || project.getPath().isEmpty()) {
                if (jfc.showSaveDialog(MainFrame.getInstance()) == JFileChooser.APPROVE_OPTION) {
                    projectFile = jfc.getSelectedFile();
                    project.setPath(projectFile.getPath());
                } else {
                    return;
                }

            }

            ApplicationFramework.getInstance().getSerializer()
                    .saveProject(project);

            project.setChanged(false);

        } else if (MainFrame.getInstance().getClassyTree().getSelectedNode().getClassyNode() instanceof Diagram) {

            Diagram diagram = (Diagram) MainFrame.getInstance().getClassyTree().getSelectedNode().getClassyNode();

            ApplicationFramework.getInstance().getSerializer()
                    .saveDiagram(diagram);

        }
    }
}
