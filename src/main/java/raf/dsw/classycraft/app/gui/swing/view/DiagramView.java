package raf.dsw.classycraft.app.gui.swing.view;

import raf.dsw.classycraft.app.command.CommandManager;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.view.painter.ElementPainter;
import raf.dsw.classycraft.app.model.implementation.Diagram;
import raf.dsw.classycraft.app.model.implementation.DiagramElement;
import raf.dsw.classycraft.app.observer.ISubscriber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class DiagramView extends JPanel implements ISubscriber {
    private AffineTransform affineTransform;
    private List<ElementPainter> painters;
    private List<DiagramElement> selected;
    private Diagram diagram;
    private ClassyTreeItem d;
    private List<ClassyTreeItem> content;
    private CommandManager commandManager;

    public DiagramView(Diagram diagram) {
        commandManager = new CommandManager();
        this.diagram = diagram;
        this.painters = new ArrayList<>();
        this.selected = new ArrayList<>();
        content = new ArrayList<>();
        affineTransform = new AffineTransform();
        MyMouseAdapter m = new MyMouseAdapter(this);
        addMouseListener(m);
        addMouseMotionListener(m);
    }

    @Override
    public void update(Object notification) {
        repaint();
    }

    public void paintComponent(Graphics g){

        Graphics g2 = g.create();
        AffineTransform temp = (AffineTransform) ((Graphics2D)g2).getTransform().clone();
        temp.concatenate(affineTransform);

        super.paintComponent(g2);
        ((Graphics2D) g2).setTransform(temp);

        for (ElementPainter p : painters){
            p.paint((Graphics2D) g2, p.getDiagramElement());
        }

        g2.dispose();
    }

    public void addPainter(ElementPainter ep){
        painters.add(ep);
    }
    public void removePainter(ElementPainter ep){
        painters.remove(ep);
        repaint();
    }

    public Diagram getDiagram() {
        return diagram;
    }

    public List<ElementPainter> getPainters() {
        return painters;
    }

    public List<DiagramElement> getSelected() {
        return selected;
    }

    public void setSelected(List<DiagramElement> selected) {
        this.selected = selected;
    }

    public void setPainters(List<ElementPainter> painters) {
        this.painters = painters;
    }

    public AffineTransform getAffineTransform() {
        return affineTransform;
    }

    public void setAffineTransform(AffineTransform affineTransform) {
        this.affineTransform = affineTransform;
        repaint();
    }

    public void translateAff(int dX, int dY) {
        affineTransform.translate(dX, dY);
        repaint();
    }


    public ClassyTreeItem getD() {
        return d;
    }

    public void setD(ClassyTreeItem d) {
        this.d = d;
    }

    public List<ClassyTreeItem> getContent() {
        return content;
    }

    public void addCont(ClassyTreeItem interClass) {
        content.add(interClass);
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }
}
