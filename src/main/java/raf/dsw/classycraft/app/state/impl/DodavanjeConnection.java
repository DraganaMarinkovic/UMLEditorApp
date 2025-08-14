package raf.dsw.classycraft.app.state.impl;

import raf.dsw.classycraft.app.command.implementation.AddConnectionCommand;
import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.painter.ConnectionPainter;
import raf.dsw.classycraft.app.model.ClassyNode;
import raf.dsw.classycraft.app.model.implementation.*;
import raf.dsw.classycraft.app.state.State;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;

public class DodavanjeConnection implements State {
    private ConnectionPainter connectionPainter;

    @Override
    public void misKliknut(MouseEvent e, DiagramView dv) {
        Point p = new Point();
        try {
            AffineTransform af = dv.getAffineTransform().createInverse();
            af.transform(e.getPoint(), p);
        } catch (NoninvertibleTransformException ex) {
            throw new RuntimeException(ex);
        }

        if(connectionPainter == null){
            for (ClassyNode c : dv.getDiagram().getChildren()) {
                if (c instanceof InterClass) {
                    if (p.getX() > ((InterClass) c).getPozicija().x &&
                            p.getX() < ((InterClass) c).getPozicija().x + ((InterClass) c).getSize().width &&
                            p.getY() > ((InterClass) c).getPozicija().y && p.getY() < ((InterClass) c).getPozicija().y + ((InterClass) c).getSize().height) {

                        Connection connection = DiagramElementFactory.createConnectionElement();
                        if (connection == null)
                            return;
                        connection.setFrom((InterClass) c);
                        connection.setParent(dv.getDiagram());
                        ConnectionPainter cp = new ConnectionPainter(connection);
                        connectionPainter = cp;
                        dv.addPainter(cp);
                        connection.addSubscriber(dv.getDiagram());
                    }
                }
            }
//            if (connectionPainter != null){
//                dv.getDiagram().addChild(connectionPainter.getDiagramElement());
//            }
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

        if (connectionPainter != null) {
            ((Connection) connectionPainter.getDiagramElement()).setTmpPoint(p);
        }
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

        if(connectionPainter != null){
            boolean add = false;
            for (ClassyNode c : dv.getDiagram().getChildren()) {
                if (c instanceof InterClass) {
                    if (p.getX() > ((InterClass) c).getPozicija().x &&
                            p.getX() < ((InterClass) c).getPozicija().x + ((InterClass) c).getSize().width &&
                            p.getY() > ((InterClass) c).getPozicija().y && p.getY() < ((InterClass) c).getPozicija().y + ((InterClass) c).getSize().height) {

                        ((Connection)connectionPainter.getDiagramElement()).setTo((InterClass) c);
                        ((Connection)connectionPainter.getDiagramElement()).setTmpPoint(null);
                        add = true;
                    }
                }
            }
            if (add) {
                dv.getCommandManager().addCommand(new AddConnectionCommand(dv, connectionPainter));
            }else {
                dv.removePainter(connectionPainter);
                connectionPainter.getDiagramElement().removeSubscriber(dv.getDiagram());
            }

            connectionPainter = null;
        }
    }
}