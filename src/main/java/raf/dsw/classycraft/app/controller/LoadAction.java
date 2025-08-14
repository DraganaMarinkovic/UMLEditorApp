package raf.dsw.classycraft.app.controller;

import com.sun.tools.javac.Main;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.model.implementation.Diagram;
import raf.dsw.classycraft.app.model.implementation.Package;
import raf.dsw.classycraft.app.model.implementation.Project;
import raf.dsw.classycraft.app.model.implementation.ProjectExplorer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

public class LoadAction extends AbstractClassyAction{
    public LoadAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
                KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        putValue(NAME, "Load action");
        putValue(SHORT_DESCRIPTION, "Load action");
    }

    public void actionPerformed(ActionEvent arg0) {
        if (MainFrame.getInstance().getClassyTree().getSelectedNode().getClassyNode() instanceof ProjectExplorer) {
            JFileChooser jfc = new JFileChooser();

            if (jfc.showOpenDialog(MainFrame.getInstance()) == JFileChooser.APPROVE_OPTION) {
                try {
                    File file = jfc.getSelectedFile();
                    Project p = ApplicationFramework.getInstance().getSerializer().loadProject(file);
                    if (p != null) {
                        p.setChanged(false);
                        MainFrame.getInstance().getClassyTree().addToTreeRec(MainFrame.getInstance().getClassyTree().getSelectedNode(), p);
                        ((ProjectExplorer) MainFrame.getInstance().getClassyTree().getSelectedNode().getClassyNode()).addChild(p);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (MainFrame.getInstance().getClassyTree().getSelectedNode().getClassyNode() instanceof Package) {
            File file = new File("src/main/resources/sabloni");
            String[] options = file.list();
            Object selectionObject = JOptionPane.showInputDialog(null, "Choose Template: ", "Template Selection", JOptionPane.PLAIN_MESSAGE, null, options, options.length == 0 ? "Empty" : options[0]);
            if (selectionObject != null) {
                File newFile = new File("src/main/resources/sabloni/" + selectionObject.toString());
                Diagram d = ApplicationFramework.getInstance().getSerializer().loadDiagram(newFile);
                DiagramView dv = new DiagramView(d);
                if (d != null) {
                    MainFrame.getInstance().getClassyTree().addToTreeRec(MainFrame.getInstance().getClassyTree().getSelectedNode(), d);
                    ((Package) MainFrame.getInstance().getClassyTree().getSelectedNode().getClassyNode()).addChild(d);
                }
            }
        }
    }
}
