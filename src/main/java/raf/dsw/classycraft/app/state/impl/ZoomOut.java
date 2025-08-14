package raf.dsw.classycraft.app.state.impl;

import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.state.State;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;

public class ZoomOut implements State {

    @Override
    public void misKliknut(MouseEvent e, DiagramView dv) {
        Point p = new Point();
        try {
            AffineTransform af = dv.getAffineTransform().createInverse();
            af.transform(e.getPoint(), p);
        } catch (NoninvertibleTransformException ex) {
            throw new RuntimeException(ex);
        }
        AffineTransform af = dv.getAffineTransform();
        if (af == null)
            af = new AffineTransform();
        af.scale(0.9,0.9);
        af.translate(0.1*p.x, 0.1*p.y);
        dv.setAffineTransform(af);
    }

    @Override
    public void misPovucen(MouseEvent e, DiagramView dv) {

    }

    @Override
    public void misOtpusten(MouseEvent e, DiagramView dv) {

    }
}
