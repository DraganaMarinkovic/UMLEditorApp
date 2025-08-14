package raf.dsw.classycraft.app.state.impl;

import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.state.State;

import java.awt.event.MouseEvent;

public class Drag implements State {
    private int dX;
    private int dY;
    private boolean dragged;
    @Override
    public void misKliknut(MouseEvent e, DiagramView dv) {
        if (dv.contains(e.getPoint())) {
            dX = e.getX();
            dY = e.getY();
            dragged = true;
        }
    }

    @Override
    public void misPovucen(MouseEvent e, DiagramView dv) {
        if (dragged) {
            dv.translateAff(e.getX() - dX, e.getY() - dY);
            dX = e.getX();
            dY = e.getY();
        }
    }

    @Override
    public void misOtpusten(MouseEvent e, DiagramView dv) {
        if(dragged)
            dragged = false;
    }
}
