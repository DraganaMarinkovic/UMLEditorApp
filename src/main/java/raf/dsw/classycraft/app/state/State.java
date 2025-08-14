package raf.dsw.classycraft.app.state;

import raf.dsw.classycraft.app.gui.swing.view.DiagramView;

import java.awt.event.MouseEvent;

public interface State {
    void misKliknut(MouseEvent e, DiagramView dv);
    void misPovucen(MouseEvent e, DiagramView dv);
    void misOtpusten(MouseEvent e, DiagramView dv);
}
