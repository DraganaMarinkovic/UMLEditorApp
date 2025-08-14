package raf.dsw.classycraft.app.state.impl;

import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.painter.ElementPainter;
import raf.dsw.classycraft.app.gui.swing.view.painter.SelectionPainter;
import raf.dsw.classycraft.app.model.ClassyNode;
import raf.dsw.classycraft.app.model.implementation.Connection;
import raf.dsw.classycraft.app.model.implementation.DiagramElement;
import raf.dsw.classycraft.app.model.implementation.InterClass;
import raf.dsw.classycraft.app.model.implementation.Selekcija;
import raf.dsw.classycraft.app.state.State;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.util.ArrayList;

public class Select implements State{
    private SelectionPainter selectionPainter;
    @Override
    public void misKliknut(MouseEvent e, DiagramView dv) {
        Point p = new Point();
        try {
            AffineTransform af = dv.getAffineTransform().createInverse();
            af.transform(e.getPoint(), p);
        } catch (NoninvertibleTransformException ex) {
            throw new RuntimeException(ex);
        }

        for (ElementPainter elementPainter : dv.getPainters()) {
            if (elementPainter instanceof SelectionPainter) {
                selectionPainter = (SelectionPainter) elementPainter;
                break;
            }
        }
        dv.removePainter(selectionPainter);
        selectionPainter = null;

        for (ElementPainter d : dv.getPainters()) {
            d.getDiagramElement().setSelected(false);
        }
        dv.setSelected(new ArrayList<>());
        Selekcija s = new Selekcija(" ", dv.getDiagram());
        s.setPocetak(p);
        s.setKraj(p);
        SelectionPainter sp = new SelectionPainter(s);
        selectionPainter = sp;
        dv.addPainter(sp);
        s.addSubscriber(dv.getDiagram());

        for (ClassyNode c : dv.getDiagram().getChildren()) {
            if (c instanceof InterClass) {
                if (p.getX() > ((InterClass) c).getPozicija().x &&
                        p.getX() < ((InterClass) c).getPozicija().x + ((InterClass) c).getSize().width &&
                        p.getY() > ((InterClass) c).getPozicija().y && p.getY() < ((InterClass) c).getPozicija().y + ((InterClass) c).getSize().height) {
                    ((InterClass) c).setSelected(true);
                    dv.getSelected().add((DiagramElement) c);
                }
            } else if (c instanceof Connection){
                if (((Connection) c).getHitBox() != null && ((Connection) c).getHitBox().contains(p)){
                    ((Connection) c).setSelected(true);
                    dv.getSelected().add((DiagramElement) c);
                }
            }
        }
    }

    @Override
    public void misPovucen(MouseEvent e, DiagramView dv) {
        Point p = new Point();
        try {
            AffineTransform af = dv.getAffineTransform().createInverse();
            af.transform(e.getPoint(), p);
        } catch (NoninvertibleTransformException ex) {
            throw new RuntimeException(ex);
        }


        if (selectionPainter.getDiagramElement() instanceof Selekcija)
            if (p.getY() < ((Selekcija) selectionPainter.getDiagramElement()).getPocetak().y && p.getX() > ((Selekcija) selectionPainter.getDiagramElement()).getPocetak().x) {
                ((Selekcija) selectionPainter.getDiagramElement()).setTempPocetak(new Point(((Selekcija) selectionPainter.getDiagramElement()).getPocetak().x, p.y));
                ((Selekcija) selectionPainter.getDiagramElement()).setTempKraj(new Point(p.x, ((Selekcija) selectionPainter.getDiagramElement()).getPocetak().y));

            } else if (p.getX() < ((Selekcija) selectionPainter.getDiagramElement()).getPocetak().x && p.getY() > ((Selekcija) selectionPainter.getDiagramElement()).getPocetak().y) {
                ((Selekcija) selectionPainter.getDiagramElement()).setTempPocetak(new Point((int) p.getX(),((Selekcija) selectionPainter.getDiagramElement()).getPocetak().y));
                ((Selekcija) selectionPainter.getDiagramElement()).setTempKraj(new Point(((Selekcija) selectionPainter.getDiagramElement()).getPocetak().x, (int) p.getY()));
            } else if (p.getX() < ((Selekcija) selectionPainter.getDiagramElement()).getPocetak().x && p.getY() < ((Selekcija) selectionPainter.getDiagramElement()).getPocetak().y){
                ((Selekcija) selectionPainter.getDiagramElement()).setTempPocetak(p);
                ((Selekcija) selectionPainter.getDiagramElement()).setTempKraj(((Selekcija) selectionPainter.getDiagramElement()).getPocetak());
            } else {
                ((Selekcija) selectionPainter.getDiagramElement()).setKraj(p);
                ((Selekcija) selectionPainter.getDiagramElement()).setTempPocetak(null);
                ((Selekcija) selectionPainter.getDiagramElement()).setTempKraj(null);
            }
    }

    @Override
    public void misOtpusten(MouseEvent e, DiagramView dv) {

        dv.getPainters().remove(selectionPainter);
        Point p = null;
        Point k = null;
        if (selectionPainter.getDiagramElement() instanceof Selekcija && (((Selekcija) selectionPainter.getDiagramElement()).getTempKraj() != null)) {
            p = ((Selekcija)selectionPainter.getDiagramElement()).getTempPocetak();
            k = ((Selekcija)selectionPainter.getDiagramElement()).getTempKraj();
        } else {
            p = ((Selekcija) selectionPainter.getDiagramElement()).getPocetak();
            k = ((Selekcija) selectionPainter.getDiagramElement()).getKraj();
        }
        for (ClassyNode c : dv.getDiagram().getChildren()){
            if (c instanceof InterClass){
                if (((InterClass) c).getPozicija().x > p.x && ((InterClass) c).getPozicija().x < k.x &&
                        ((InterClass) c).getPozicija().y > p.y && ((InterClass) c).getPozicija().y < k.y
                        ||
                        ((InterClass) c).getPozicija().x + ((InterClass) c).getSize().width > p.x &&
                                ((InterClass) c).getPozicija().x + ((InterClass) c).getSize().width < k.x &&
                                ((InterClass) c).getPozicija().y > p.y  && ((InterClass) c).getPozicija().y < k.y

                        || ((InterClass) c).getPozicija().x + ((InterClass) c).getSize().width > p.x &&
                        ((InterClass) c).getPozicija().x + ((InterClass) c).getSize().width < k.x &&
                        ((InterClass) c).getPozicija().y + ((InterClass) c).getSize().height > p.y &&
                        ((InterClass) c).getPozicija().y + ((InterClass) c).getSize().height < k.y

                        || ((InterClass) c).getPozicija().x > p.x &&
                        ((InterClass) c).getPozicija().x < k.x &&
                        ((InterClass) c).getPozicija().y + ((InterClass) c).getSize().height > p.y &&
                        ((InterClass) c).getPozicija().y + ((InterClass) c).getSize().height < k.y
                        //2 4 6 7
                        || ((InterClass) c).getPointList().get(2).x > p.x && ((InterClass) c).getPointList().get(2).x < k.x &&
                        ((InterClass) c).getPointList().get(2).y> p.y && ((InterClass) c).getPointList().get(2).y < k.y
                        ||
                        ((InterClass) c).getPointList().get(4).x > p.x &&
                                ((InterClass) c).getPointList().get(4).x < k.x &&
                                ((InterClass) c).getPointList().get(4).y > p.y  && ((InterClass) c).getPointList().get(4).y < k.y

                        || ((InterClass) c).getPointList().get(6).x > p.x &&
                        ((InterClass) c).getPointList().get(6).x < k.x &&
                        ((InterClass) c).getPointList().get(6).y  > p.y &&
                        ((InterClass) c).getPointList().get(6).y < k.y

                        || ((InterClass) c).getPointList().get(7).x  > p.x &&
                        ((InterClass) c).getPointList().get(7).x < k.x &&
                        ((InterClass) c).getPointList().get(7).y > p.y &&
                        ((InterClass) c).getPointList().get(7).y < k.y
                ) {
                    ((InterClass) c).setSelected(true);
                    if (!dv.getSelected().contains(c))
                        dv.getSelected().add((DiagramElement) c);

                }
            }
        }
        for (ClassyNode c : dv.getDiagram().getChildren()){
            if (c instanceof Connection){
                if (!((Connection) c).isSelected() && (((Connection) c).getFrom().isSelected() || ((Connection) c).getTo() != null && ((Connection) c).getTo().isSelected())){
                    ((Connection) c).setSelected(true);
                    dv.getSelected().add((DiagramElement) c);
                }
            }
        }

        ((Selekcija) selectionPainter.getDiagramElement()).setKraj(null);
        ((Selekcija) selectionPainter.getDiagramElement()).setTempKraj(null);
        selectionPainter = null;
    }
}
