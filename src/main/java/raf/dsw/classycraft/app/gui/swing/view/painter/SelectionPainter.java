package raf.dsw.classycraft.app.gui.swing.view.painter;

import raf.dsw.classycraft.app.model.implementation.DiagramElement;
import raf.dsw.classycraft.app.model.implementation.Selekcija;

import java.awt.*;

public class SelectionPainter extends  ElementPainter{


    public SelectionPainter(DiagramElement diagramElement) {
        super(diagramElement);
    }

    @Override
    public void paint(Graphics2D g, DiagramElement element) {
        if (element instanceof Selekcija){
            g.setStroke(new BasicStroke(2));
            g.setPaint(Color.blue);
            if (((Selekcija) element).getTempKraj() != null) {
                g.drawRect(((Selekcija) element).getTempPocetak().x, ((Selekcija) element).getTempPocetak().y,
                        ((Selekcija) element).getTempKraj().x - ((Selekcija) element).getTempPocetak().x,
                        ((Selekcija) element).getTempKraj().y - ((Selekcija) element).getTempPocetak().y);

            } else if(((Selekcija) element).getKraj() != null) {
                g.drawRect(((Selekcija) element).getPocetak().x, ((Selekcija) element).getPocetak().y,
                        ((Selekcija) element).getKraj().x - ((Selekcija) element).getPocetak().x,
                        ((Selekcija) element).getKraj().y - ((Selekcija) element).getPocetak().y);
            }
        }

    }
}
