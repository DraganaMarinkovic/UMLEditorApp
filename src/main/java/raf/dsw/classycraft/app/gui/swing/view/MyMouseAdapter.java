package raf.dsw.classycraft.app.gui.swing.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

//zbog mouse dragged treba da promenimo impl
public class MyMouseAdapter extends MouseAdapter implements MouseMotionListener {
    private DiagramView dv;

    public MyMouseAdapter(DiagramView dv) {
        this.dv = dv;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        MainFrame.getInstance().getDesktop().misKliknut(e, dv);

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        MainFrame.getInstance().getDesktop().misOtpusten(e, dv);

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        MainFrame.getInstance().getDesktop().misPovucen(e, dv);

    }
}
