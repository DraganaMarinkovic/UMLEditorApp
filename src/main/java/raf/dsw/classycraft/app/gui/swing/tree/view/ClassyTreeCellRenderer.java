package raf.dsw.classycraft.app.gui.swing.tree.view;


//import lombok.NoArgsConstructor;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.model.implementation.Diagram;
import raf.dsw.classycraft.app.model.implementation.Package;
import raf.dsw.classycraft.app.model.implementation.Project;
import raf.dsw.classycraft.app.model.implementation.ProjectExplorer;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.net.URL;

//@NoArgsConstructor
public class ClassyTreeCellRenderer extends DefaultTreeCellRenderer {

        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {

            super.getTreeCellRendererComponent(tree, value, sel,expanded, leaf, row, hasFocus);
            URL imageURL = null;

            if (((ClassyTreeItem)value).getClassyNode() instanceof ProjectExplorer) {
                imageURL = getClass().getResource("/images/explorer.png");
            }
            else if (((ClassyTreeItem)value).getClassyNode() instanceof Project) {
                imageURL = getClass().getResource("/images/project.png");
            } else if (((ClassyTreeItem)value).getClassyNode() instanceof Diagram){
                imageURL = getClass().getResource("/images/diagram.png");
            } else if (((ClassyTreeItem)value).getClassyNode() instanceof Package){
                imageURL = getClass().getResource("/images/package.png");
            }

            Icon icon = null;
            if (imageURL != null)
                icon = new ImageIcon(imageURL);
            setIcon(icon);

            return this;
        }
}


