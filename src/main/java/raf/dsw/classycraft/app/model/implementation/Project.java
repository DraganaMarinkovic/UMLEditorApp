package raf.dsw.classycraft.app.model.implementation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.model.ClassyNode;
import raf.dsw.classycraft.app.model.ClassyNodeComposite;

import javax.swing.*;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class Project extends ClassyNodeComposite {

    private String autor;
    private String path;

    private boolean changed;

    public Project(){
        super("", null);
        autor = "";
        type = "project";
        changed = false;
    }
    public Project(String name, ClassyNode parent) {
        super(name, parent);
        autor = "";
        type = "project";
        changed = true;
    }

    @Override
    public void removeChild(ClassyNode child) {
        int cnt = -1;
        for (ClassyNode cn : children) {
            if (cn.equals(child)){
                break;
            }
            cnt++;
        }
        super.removeChild(child);

        if (children.isEmpty()){
            MainFrame.getInstance().getDesktop().getjTabbedPane().removeAll();
        } else if(children.get(cnt >=0 ? cnt : 0) instanceof Package)
            ((Package) children.get(cnt >=0 ? cnt : 0)).switchToThisPackage();
    }

    public void changeAutor(String autor){
        this.autor = autor;
        MainFrame.getInstance().getDesktop().getLabel().setText(ApplicationFramework.getInstance().getClassyRepository().findAuthor(this));
    }

    public String getAutor() {
        return autor;
    }

    @Override
    public void setName(String name) {
        super.setName(name);
        MainFrame.getInstance().getDesktop().getLabel().setText(ApplicationFramework.getInstance().getClassyRepository().findAuthor(this));
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @JsonProperty("CHANGED")
    public boolean getChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }
}

