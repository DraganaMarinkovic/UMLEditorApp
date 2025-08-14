package raf.dsw.classycraft.app.state.impl;

import raf.dsw.classycraft.app.command.implementation.DuplicateCommand;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.errorHandler.TipPoruke;
import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.painter.InterClassPainter;
import raf.dsw.classycraft.app.model.ClassyNode;
import raf.dsw.classycraft.app.model.implementation.*;
import raf.dsw.classycraft.app.model.implementation.Enum;
import raf.dsw.classycraft.app.state.State;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;

public class Duplicate implements State {
    private InterClass duplicate;
    private boolean copied;
    @Override
    public void misKliknut(MouseEvent e, DiagramView dv) {
        Point p = new Point();
        try {
            AffineTransform af = dv.getAffineTransform().createInverse();
            af.transform(e.getPoint(), p);
        } catch (NoninvertibleTransformException ex) {
            throw new RuntimeException(ex);
        }

        if (copied) {
            for (ClassyNode c : dv.getDiagram().getChildren()) {
                if (c instanceof InterClass){
                    if (p.getX() > ((InterClass) c).getPozicija().x && p.getX() < ((InterClass) c).getPozicija().x + ((InterClass) c).getSize().width &&
                            p.getY() > ((InterClass) c).getPozicija().y && p.getY() < ((InterClass) c).getPozicija().y + ((InterClass) c).getSize().height
                            ||
                            p.getX() + duplicate.getSize().width > ((InterClass) c).getPozicija().x &&
                                    p.getX() + duplicate.getSize().width < ((InterClass) c).getPozicija().x + ((InterClass) c).getSize().width &&
                                    p.getY() > ((InterClass) c).getPozicija().y && p.getY() < ((InterClass) c).getPozicija().y + ((InterClass) c).getSize().height

                            || p.getX() > ((InterClass) c).getPozicija().x &&
                            p.getX() < ((InterClass) c).getPozicija().x + ((InterClass) c).getSize().width &&
                            p.getY() + duplicate.getSize().height > ((InterClass) c).getPozicija().y && p.getY() + duplicate.getSize().height < ((InterClass) c).getPozicija().y + ((InterClass) c).getSize().height

                            || p.getX() + duplicate.getSize().width > ((InterClass) c).getPozicija().x &&
                            p.getX() + duplicate.getSize().width < ((InterClass) c).getPozicija().x + ((InterClass) c).getSize().width &&
                            p.getY() + duplicate.getSize().height > ((InterClass) c).getPozicija().y && p.getY() + duplicate.getSize().height < ((InterClass) c).getPozicija().y + ((InterClass) c).getSize().height

                            ||

                            p.getX() < ((InterClass) c).getPozicija().x && p.getX() + duplicate.getSize().width > ((InterClass) c).getPozicija().x  &&
                            p.getY() < ((InterClass) c).getPozicija().y && p.getY() + duplicate.getSize().height > ((InterClass) c).getPozicija().y
                            ||
                            p.getX() < ((InterClass) c).getPozicija().x + ((InterClass) c).getSize().width &&
                                    p.getX() + duplicate.getSize().width > ((InterClass) c).getPozicija().x + ((InterClass) c).getSize().width &&
                                    p.getY() < ((InterClass) c).getPozicija().y && p.getY() + duplicate.getSize().height > ((InterClass) c).getPozicija().y

                            || p.getX() < ((InterClass) c).getPozicija().x &&
                            p.getX() + duplicate.getSize().width > ((InterClass) c).getPozicija().x &&
                            p.getY() < ((InterClass) c).getPozicija().y + ((InterClass) c).getSize().height && p.getY() + duplicate.getSize().height > ((InterClass) c).getPozicija().y + ((InterClass) c).getSize().height

                            || p.getX() < ((InterClass) c).getPozicija().x + ((InterClass) c).getSize().width &&
                            p.getX() + duplicate.getSize().width > ((InterClass) c).getPozicija().x + ((InterClass) c).getSize().width &&
                            p.getY() < ((InterClass) c).getPozicija().y + ((InterClass) c).getSize().height && p.getY() + duplicate.getSize().height > ((InterClass) c).getPozicija().y + ((InterClass) c).getSize().height){

                        ApplicationFramework.getInstance().getMessageGenerator().generateMessage("INTERCLASS_OBJECTS_CANNOT_INTERSECT", TipPoruke.GRESKA);
                        return;
                    }
                }
            }

            duplicate.setPozicija(p);
            InterClassPainter icp = new InterClassPainter(duplicate);

            dv.getCommandManager().addCommand(new DuplicateCommand(dv, icp));

            duplicate = null;
            copied = false;
        } else {
            for (ClassyNode c : dv.getDiagram().getChildren()) {
                if (c instanceof InterClass) {
                    if (p.getX() > ((InterClass) c).getPozicija().x &&
                            p.getX() < ((InterClass) c).getPozicija().x + ((InterClass) c).getSize().width &&
                            p.getY() > ((InterClass) c).getPozicija().y && p.getY() < ((InterClass) c).getPozicija().y + ((InterClass) c).getSize().height) {

                        if (c instanceof Klasa) {
                            duplicate = new Klasa(c.getName(), c.getParent());
                            duplicate.setSize(((InterClass) c).getSize());
                            for (ClassContent cc : ((Klasa) c).getClassContents()) {
                                ((Klasa) duplicate).addClassContent(cc);
                            }
                        } else if (c instanceof Interfejs) {
                            duplicate = new Interfejs(c.getName(), c.getParent());
                            duplicate.setSize(((InterClass) c).getSize());
                            for (Metod cc : ((Interfejs) c).getMetode()) {
                                ((Interfejs) duplicate).addMetod(cc);
                            }
                        }  else if (c instanceof Enum) {
                            return;
                        }

                        duplicate.setSize(((InterClass) c).getSize());
                        duplicate.setColor(((InterClass) c).getColor());
                        duplicate.setStroke(((InterClass) c).getStroke());

                        copied = true;
                    }
                }
            }
        }
    }

    @Override
    public void misPovucen(MouseEvent e, DiagramView dv) {

    }

    @Override
    public void misOtpusten(MouseEvent e, DiagramView dv) {

    }
}
