package raf.dsw.classycraft.app.gui.swing.view.painter;

import raf.dsw.classycraft.app.model.implementation.DiagramElement;

import java.awt.*;

public abstract class ElementPainter {
    private DiagramElement diagramElement;

    public ElementPainter(DiagramElement diagramElement) {
        this.diagramElement = diagramElement;
    }

    public abstract void paint(Graphics2D g, DiagramElement element);

    public DiagramElement getDiagramElement() {
        return diagramElement;
    }

}
