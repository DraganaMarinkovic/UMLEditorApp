package raf.dsw.classycraft.app.state.impl;

import raf.dsw.classycraft.app.command.implementation.DeleteCommand;
import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.painter.ElementPainter;
import raf.dsw.classycraft.app.model.ClassyNode;
import raf.dsw.classycraft.app.model.implementation.Connection;
import raf.dsw.classycraft.app.model.implementation.DiagramElement;
import raf.dsw.classycraft.app.model.implementation.InterClass;
import raf.dsw.classycraft.app.state.State;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.util.ArrayList;
import java.util.List;

public class Brisanje implements State{
    @Override
    public void misKliknut(MouseEvent e, DiagramView dv) {
        Point p = new Point();
        try {
            AffineTransform af = dv.getAffineTransform().createInverse();
            af.transform(e.getPoint(), p);
        } catch (NoninvertibleTransformException ex) {
            throw new RuntimeException(ex);
        }

        boolean select = false;
        ClassyNode del = null;

        List<ElementPainter> toDelete = new ArrayList<>();

        for (ClassyNode c : dv.getDiagram().getChildren()){
            if (c instanceof DiagramElement){
                if(c instanceof Connection && ((Connection) c).getHitBox() != null && ((Connection) c).getHitBox().contains(p) || c instanceof InterClass && (p.getX() > ((InterClass) c).getPozicija().x &&
                        p.getX() < ((InterClass) c).getPozicija().x + ((InterClass) c).getSize().width &&
                        p.getY() > ((InterClass) c).getPozicija().y && p.getY() < ((InterClass) c).getPozicija().y + ((InterClass) c).getSize().height)){
                    if (((DiagramElement) c).isSelected()){
                        for (ElementPainter ep : dv.getPainters()){
                            if (ep.getDiagramElement().isSelected()){
                                toDelete.add(ep);
                            }
                        }
                        select = true;
                    } else {
                        for (ElementPainter ep : dv.getPainters()){
                            if (ep.getDiagramElement().equals(c)){
                                toDelete.add(ep);
                                break;
                            }
                        }
                        del = c;
                    }
                }
            }
        }
        if (del != null){
            if (del instanceof InterClass){
                for (ElementPainter ep : dv.getPainters()){
                    if (ep.getDiagramElement() instanceof Connection && !toDelete.contains(ep) && (((Connection) ep.getDiagramElement()).getFrom().equals(del) ||
                            ((Connection) ep.getDiagramElement()).getTo().equals(del))){
                        toDelete.add(ep);
                    }
                }
            }
        } else if (select){
            for (DiagramElement d : dv.getSelected()){
                if (d instanceof InterClass){
                    for (ElementPainter ep : dv.getPainters()){
                        if (ep.getDiagramElement() instanceof Connection && !toDelete.contains(ep) && (((Connection) ep.getDiagramElement()).getFrom().equals(d) ||
                                ((Connection) ep.getDiagramElement()).getTo().equals(d))){
                            toDelete.add(ep);
                        }
                    }
                }
            }
            dv.setSelected(new ArrayList<>());
        }

        if (!toDelete.isEmpty())
            dv.getCommandManager().addCommand(new DeleteCommand(dv, toDelete));
    }


    @Override
    public void misPovucen(MouseEvent e, DiagramView dv) {

    }

    @Override
    public void misOtpusten(MouseEvent e, DiagramView dv) {

    }
}
