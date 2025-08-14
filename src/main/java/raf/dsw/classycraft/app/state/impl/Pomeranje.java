package raf.dsw.classycraft.app.state.impl;

import raf.dsw.classycraft.app.command.implementation.MoveCommand;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.errorHandler.TipPoruke;
import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.painter.ElementPainter;
import raf.dsw.classycraft.app.gui.swing.view.painter.InterClassPainter;
import raf.dsw.classycraft.app.model.ClassyNode;
import raf.dsw.classycraft.app.model.implementation.Connection;
import raf.dsw.classycraft.app.model.implementation.DiagramElement;
import raf.dsw.classycraft.app.model.implementation.InterClass;
import raf.dsw.classycraft.app.state.State;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Pomeranje implements State {

    private Point pocetak;
    private Point temp;
    private boolean selected;
    private ElementPainter element;
    private InterClass to;
    private List<Point> po;

    @Override
    public void misKliknut(MouseEvent e, DiagramView dv) {
        Point p = new Point();
        try {
            AffineTransform af = dv.getAffineTransform().createInverse();
            af.transform(e.getPoint(), p);
        } catch (NoninvertibleTransformException ex) {
            throw new RuntimeException(ex);
        }

        selected = false;
        pocetak = null;
        element = null;
        temp = null;
        for (ClassyNode c: dv.getDiagram().getChildren()){
            if (c instanceof InterClass && p.getX() > ((InterClass) c).getPozicija().x &&
                    p.getX() < ((InterClass) c).getPozicija().x + ((InterClass) c).getSize().width &&
                    p.getY() > ((InterClass) c).getPozicija().y && p.getY() < ((InterClass) c).getPozicija().y + ((InterClass) c).getSize().height
                    || c instanceof Connection && ((Connection) c).getHitBox() != null && ((Connection) c).getHitBox().contains(p)) {
                pocetak = p;
                if (c instanceof InterClass) {
                    pocetak = ((InterClass) c).getPozicija();
                }
                temp = p;
                if (((DiagramElement) c).isSelected()){
                    selected = true;
                    po = new ArrayList<>();
                    for (DiagramElement de: dv.getSelected()){
                        if (de instanceof InterClass){
                            po.add(((InterClass) de).getPozicija());
                        }
                    }
                }else {
                    selected = false;
                    for (ElementPainter ep: dv.getPainters()){
                        if (ep.getDiagramElement().equals(c)){
                            element = ep;
                            break;
                        }
                    }
                    if (c instanceof Connection)
                        to = ((Connection) c).getTo();
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

        if (selected){
            for (DiagramElement d: dv.getSelected()){
                if (d instanceof InterClass){
                    ((InterClass) d).setPozicija(new Point((int) (((InterClass) d).getPozicija().x + (p.getX() - temp.x)),
                            (((InterClass) d).getPozicija().y + (p.y - temp.y))));
                }
            }
        }else {
            if (element != null && element.getDiagramElement() instanceof InterClass){
                ((InterClass) element.getDiagramElement()).setPozicija(new Point(((InterClass) element.getDiagramElement()).getPozicija().x + (p.x - temp.x),
                        ((InterClass) element.getDiagramElement()).getPozicija().y + (p.y - temp.y)));
            } else if (element != null && element.getDiagramElement() instanceof Connection) {
                ((Connection) element.getDiagramElement()).setTo(null);
                ((Connection) element.getDiagramElement()).setTmpPoint(p);
            }
        }
        temp = p;
    }

    @Override
    public void misOtpusten(MouseEvent e, DiagramView dv) {
        Point p = new Point();
        try {
            AffineTransform af = dv.getAffineTransform().createInverse();
            af.transform(e.getPoint(), p);
        } catch (NoninvertibleTransformException ex) {
            throw new RuntimeException(ex);
        }

        if (element != null && element.getDiagramElement() instanceof InterClass){
            for (ClassyNode c : dv.getDiagram().getChildren()) {
                if (c instanceof Connection)
                    continue;
                if (c instanceof InterClass){
                    if (element.getDiagramElement() instanceof InterClass && ((InterClass) element.getDiagramElement()).getPozicija().getX() > ((InterClass) c).getPozicija().x && ((InterClass) element.getDiagramElement()).getPozicija().getX() < ((InterClass) c).getPozicija().x + ((InterClass) c).getSize().width &&
                            ((InterClass) element.getDiagramElement()).getPozicija().getY() > ((InterClass) c).getPozicija().y && ((InterClass) element.getDiagramElement()).getPozicija().getY() < ((InterClass) c).getPozicija().y + ((InterClass) c).getSize().height
                            ||
                            ((InterClass) element.getDiagramElement()).getPozicija().getX() + ((InterClass) element.getDiagramElement()).getSize().width > ((InterClass) c).getPozicija().x &&
                                    ((InterClass) element.getDiagramElement()).getPozicija().getX() + ((InterClass) element.getDiagramElement()).getSize().width < ((InterClass) c).getPozicija().x + ((InterClass) c).getSize().width &&
                                    ((InterClass) element.getDiagramElement()).getPozicija().getY() > ((InterClass) c).getPozicija().y && ((InterClass) element.getDiagramElement()).getPozicija().getY() < ((InterClass) c).getPozicija().y + ((InterClass) c).getSize().height

                            || ((InterClass) element.getDiagramElement()).getPozicija().getX() > ((InterClass) c).getPozicija().x &&
                            ((InterClass) element.getDiagramElement()).getPozicija().getX() < ((InterClass) c).getPozicija().x + ((InterClass) c).getSize().width &&
                            ((InterClass) element.getDiagramElement()).getPozicija().getY() + ((InterClass) element.getDiagramElement()).getSize().height > ((InterClass) c).getPozicija().y && ((InterClass) element.getDiagramElement()).getPozicija().getY() + ((InterClass) element.getDiagramElement()).getSize().height < ((InterClass) c).getPozicija().y + ((InterClass) c).getSize().height

                            || ((InterClass) element.getDiagramElement()).getPozicija().getX() + ((InterClass) element.getDiagramElement()).getSize().width > ((InterClass) c).getPozicija().x &&
                            ((InterClass) element.getDiagramElement()).getPozicija().getX() + ((InterClass) element.getDiagramElement()).getSize().width < ((InterClass) c).getPozicija().x + ((InterClass) c).getSize().width &&
                            ((InterClass) element.getDiagramElement()).getPozicija().getY() + ((InterClass) element.getDiagramElement()).getSize().height > ((InterClass) c).getPozicija().y && ((InterClass) element.getDiagramElement()).getPozicija().getY() + ((InterClass) element.getDiagramElement()).getSize().height < ((InterClass) c).getPozicija().y + ((InterClass) c).getSize().height

                            ||

                            ((InterClass) element.getDiagramElement()).getPozicija().getX() < ((InterClass) c).getPozicija().x && ((InterClass) element.getDiagramElement()).getPozicija().getX() + ((InterClass) element.getDiagramElement()).getSize().width > ((InterClass) c).getPozicija().x &&
                                    ((InterClass) element.getDiagramElement()).getPozicija().getY() < ((InterClass) c).getPozicija().y && ((InterClass) element.getDiagramElement()).getPozicija().getY() + ((InterClass) element.getDiagramElement()).getSize().height > ((InterClass) c).getPozicija().y
                            ||
                            ((InterClass) element.getDiagramElement()).getPozicija().getX() < ((InterClass) c).getPozicija().x + ((InterClass) c).getSize().width &&
                                    ((InterClass) element.getDiagramElement()).getPozicija().getX() + ((InterClass) element.getDiagramElement()).getSize().width > ((InterClass) c).getPozicija().x + ((InterClass) c).getSize().width &&
                                    ((InterClass) element.getDiagramElement()).getPozicija().getY() < ((InterClass) c).getPozicija().y && ((InterClass) element.getDiagramElement()).getPozicija().getY() + ((InterClass) element.getDiagramElement()).getSize().height > ((InterClass) c).getPozicija().y

                            || ((InterClass) element.getDiagramElement()).getPozicija().getX() < ((InterClass) c).getPozicija().x &&
                            ((InterClass) element.getDiagramElement()).getPozicija().getX() + ((InterClass) element.getDiagramElement()).getSize().width > ((InterClass) c).getPozicija().x &&
                            ((InterClass) element.getDiagramElement()).getPozicija().getY() < ((InterClass) c).getPozicija().y + ((InterClass) c).getSize().height && ((InterClass) element.getDiagramElement()).getPozicija().getY() + ((InterClass) element.getDiagramElement()).getSize().height > ((InterClass) c).getPozicija().y + ((InterClass) c).getSize().height

                            || ((InterClass) element.getDiagramElement()).getPozicija().getX() < ((InterClass) c).getPozicija().x + ((InterClass) c).getSize().width &&
                            ((InterClass) element.getDiagramElement()).getPozicija().getX() + ((InterClass) element.getDiagramElement()).getSize().width > ((InterClass) c).getPozicija().x + ((InterClass) c).getSize().width &&
                            ((InterClass) element.getDiagramElement()).getPozicija().getY() < ((InterClass) c).getPozicija().y + ((InterClass) c).getSize().height && ((InterClass) element.getDiagramElement()).getPozicija().getY() + ((InterClass) element.getDiagramElement()).getSize().height > ((InterClass) c).getPozicija().y + ((InterClass) c).getSize().height){

                        ApplicationFramework.getInstance().getMessageGenerator().generateMessage("INTERCLASS_OBJECTS_CANNOT_INTERSECT", TipPoruke.GRESKA);
                        ((InterClass) element.getDiagramElement()).setPozicija(pocetak);
                        return;

                    }
                }
            }
            List<Point> start = new ArrayList<>();
            List<Point> end = new ArrayList<>();
            List<DiagramElement> toBeMoved = new ArrayList<>();
            start.add((Point) pocetak.clone());
            end.add((Point) ((InterClass) element.getDiagramElement()).getPozicija().clone());
            toBeMoved.add(element.getDiagramElement());

            dv.getCommandManager().addCommand(new MoveCommand(dv,start,end, toBeMoved));
        }
        else if (element != null && element.getDiagramElement() instanceof Connection) {
            for (ClassyNode c : dv.getDiagram().getChildren()) {
                if (c instanceof InterClass) {
                    if (p.getX() > ((InterClass) c).getPozicija().x &&
                            p.getX() < ((InterClass) c).getPozicija().x + ((InterClass) c).getSize().width &&
                            p.getY() > ((InterClass) c).getPozicija().y && p.getY() < ((InterClass) c).getPozicija().y + ((InterClass) c).getSize().height) {

                        ((Connection) element.getDiagramElement()).setTmpPoint(null);

                        List<DiagramElement> toBeMoved = new ArrayList<>();
                        toBeMoved.add(element.getDiagramElement());
                        MoveCommand mc = new MoveCommand(dv, null, null, toBeMoved);
                        mc.setArrowOldDestination(to);
                        mc.setArrowNewDestination((InterClass) c);

                        dv.getCommandManager().addCommand(mc);

                        element = null;
                        return;
                    } else {
                        ((Connection) element.getDiagramElement()).setTmpPoint(null);
                        ((Connection) element.getDiagramElement()).setTo(to);
                    }
                }
            }
        } else if (selected) {
            for (DiagramElement di: dv.getSelected()){
                element = new InterClassPainter(di);
                for (ClassyNode c : dv.getDiagram().getChildren()) {
                    if (c instanceof Connection)
                        continue;
                    if (c instanceof InterClass) {
                        //ZAGRADA OKO ILI BLOKA
                        if (element.getDiagramElement() instanceof InterClass && (((InterClass) element.getDiagramElement()).getPozicija().getX() > ((InterClass) c).getPozicija().x && ((InterClass) element.getDiagramElement()).getPozicija().getX() < ((InterClass) c).getPozicija().x + ((InterClass) c).getSize().width &&
                                ((InterClass) element.getDiagramElement()).getPozicija().getY() > ((InterClass) c).getPozicija().y && ((InterClass) element.getDiagramElement()).getPozicija().getY() < ((InterClass) c).getPozicija().y + ((InterClass) c).getSize().height
                                ||
                                ((InterClass) element.getDiagramElement()).getPozicija().getX() + ((InterClass) element.getDiagramElement()).getSize().width > ((InterClass) c).getPozicija().x &&
                                        ((InterClass) element.getDiagramElement()).getPozicija().getX() + ((InterClass) element.getDiagramElement()).getSize().width < ((InterClass) c).getPozicija().x + ((InterClass) c).getSize().width &&
                                        ((InterClass) element.getDiagramElement()).getPozicija().getY() > ((InterClass) c).getPozicija().y && ((InterClass) element.getDiagramElement()).getPozicija().getY() < ((InterClass) c).getPozicija().y + ((InterClass) c).getSize().height

                                || ((InterClass) element.getDiagramElement()).getPozicija().getX() > ((InterClass) c).getPozicija().x &&
                                ((InterClass) element.getDiagramElement()).getPozicija().getX() < ((InterClass) c).getPozicija().x + ((InterClass) c).getSize().width &&
                                ((InterClass) element.getDiagramElement()).getPozicija().getY() + ((InterClass) element.getDiagramElement()).getSize().height > ((InterClass) c).getPozicija().y && ((InterClass) element.getDiagramElement()).getPozicija().getY() + ((InterClass) element.getDiagramElement()).getSize().height < ((InterClass) c).getPozicija().y + ((InterClass) c).getSize().height

                                || ((InterClass) element.getDiagramElement()).getPozicija().getX() + ((InterClass) element.getDiagramElement()).getSize().width > ((InterClass) c).getPozicija().x &&
                                ((InterClass) element.getDiagramElement()).getPozicija().getX() + ((InterClass) element.getDiagramElement()).getSize().width < ((InterClass) c).getPozicija().x + ((InterClass) c).getSize().width &&
                                ((InterClass) element.getDiagramElement()).getPozicija().getY() + ((InterClass) element.getDiagramElement()).getSize().height > ((InterClass) c).getPozicija().y && ((InterClass) element.getDiagramElement()).getPozicija().getY() + ((InterClass) element.getDiagramElement()).getSize().height < ((InterClass) c).getPozicija().y + ((InterClass) c).getSize().height

                                ||

                                ((InterClass) element.getDiagramElement()).getPozicija().getX() < ((InterClass) c).getPozicija().x && ((InterClass) element.getDiagramElement()).getPozicija().getX() + ((InterClass) element.getDiagramElement()).getSize().width > ((InterClass) c).getPozicija().x &&
                                        ((InterClass) element.getDiagramElement()).getPozicija().getY() < ((InterClass) c).getPozicija().y && ((InterClass) element.getDiagramElement()).getPozicija().getY() + ((InterClass) element.getDiagramElement()).getSize().height > ((InterClass) c).getPozicija().y
                                ||
                                ((InterClass) element.getDiagramElement()).getPozicija().getX() < ((InterClass) c).getPozicija().x + ((InterClass) c).getSize().width &&
                                        ((InterClass) element.getDiagramElement()).getPozicija().getX() + ((InterClass) element.getDiagramElement()).getSize().width > ((InterClass) c).getPozicija().x + ((InterClass) c).getSize().width &&
                                        ((InterClass) element.getDiagramElement()).getPozicija().getY() < ((InterClass) c).getPozicija().y && ((InterClass) element.getDiagramElement()).getPozicija().getY() + ((InterClass) element.getDiagramElement()).getSize().height > ((InterClass) c).getPozicija().y

                                || ((InterClass) element.getDiagramElement()).getPozicija().getX() < ((InterClass) c).getPozicija().x &&
                                ((InterClass) element.getDiagramElement()).getPozicija().getX() + ((InterClass) element.getDiagramElement()).getSize().width > ((InterClass) c).getPozicija().x &&
                                ((InterClass) element.getDiagramElement()).getPozicija().getY() < ((InterClass) c).getPozicija().y + ((InterClass) c).getSize().height && ((InterClass) element.getDiagramElement()).getPozicija().getY() + ((InterClass) element.getDiagramElement()).getSize().height > ((InterClass) c).getPozicija().y + ((InterClass) c).getSize().height

                                || ((InterClass) element.getDiagramElement()).getPozicija().getX() < ((InterClass) c).getPozicija().x + ((InterClass) c).getSize().width &&
                                ((InterClass) element.getDiagramElement()).getPozicija().getX() + ((InterClass) element.getDiagramElement()).getSize().width > ((InterClass) c).getPozicija().x + ((InterClass) c).getSize().width &&
                                ((InterClass) element.getDiagramElement()).getPozicija().getY() < ((InterClass) c).getPozicija().y + ((InterClass) c).getSize().height && ((InterClass) element.getDiagramElement()).getPozicija().getY() + ((InterClass) element.getDiagramElement()).getSize().height > ((InterClass) c).getPozicija().y + ((InterClass) c).getSize().height)) {

                            ApplicationFramework.getInstance().getMessageGenerator().generateMessage("INTERCLASS_OBJECTS_CANNOT_INTERSECT", TipPoruke.GRESKA);
                            int i = 0;
                            for (DiagramElement d: dv.getSelected()){
                                if (d instanceof InterClass){
                                    ((InterClass) d).setPozicija(po.get(i++));
                                }
                            }
                            return;
                        }
                    }
                }
            }

            List<DiagramElement> toBeMoved = new ArrayList<>();
            for (DiagramElement d : dv.getSelected()){
                if (d instanceof InterClass) {
                    toBeMoved.add(d);
                }
            }
            List<Point> start = List.copyOf(po);
            List<Point> end = new ArrayList<>();
            for (DiagramElement d : dv.getSelected()) {
                if (d instanceof InterClass) {
                    end.add((Point) ((InterClass) d).getPozicija().clone());
                }
            }
            dv.getCommandManager().addCommand(new MoveCommand(dv, start, end, toBeMoved));
        }
    }
}
