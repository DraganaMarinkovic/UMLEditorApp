package raf.dsw.classycraft.app.state.impl;
import raf.dsw.classycraft.app.command.implementation.AddInterClassCommand;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.errorHandler.TipPoruke;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.PackageView;
import raf.dsw.classycraft.app.gui.swing.view.painter.InterClassPainter;
import raf.dsw.classycraft.app.model.ClassyNode;
import raf.dsw.classycraft.app.model.implementation.*;
import raf.dsw.classycraft.app.model.implementation.Enum;
import raf.dsw.classycraft.app.state.State;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.lang.Package;

public class DodavanjeInterClass implements State{
    public static int id = 1;
    @Override
    public void misKliknut(MouseEvent e, DiagramView dv) {
        Point p = new Point();
        try {
            AffineTransform af = dv.getAffineTransform().createInverse();
            af.transform(e.getPoint(), p);
        } catch (NoninvertibleTransformException ex) {
            throw new RuntimeException(ex);
        }

        for (ClassyNode c : dv.getDiagram().getChildren()) {
            if (c instanceof InterClass){
                if (p.getX() > ((InterClass) c).getPozicija().x && p.getX() < ((InterClass) c).getPozicija().x + ((InterClass) c).getSize().width &&
                        p.getY() > ((InterClass) c).getPozicija().y && p.getY() < ((InterClass) c).getPozicija().y + ((InterClass) c).getSize().height
                        ||
                        p.getX() + 150 > ((InterClass) c).getPozicija().x &&
                                p.getX() + 150 < ((InterClass) c).getPozicija().x + ((InterClass) c).getSize().width &&
                                p.getY() > ((InterClass) c).getPozicija().y && p.getY() < ((InterClass) c).getPozicija().y + ((InterClass) c).getSize().height

                        || p.getX() > ((InterClass) c).getPozicija().x &&
                        p.getX() < ((InterClass) c).getPozicija().x + ((InterClass) c).getSize().width &&
                        p.getY() + 40 > ((InterClass) c).getPozicija().y && p.getY() + 40 < ((InterClass) c).getPozicija().y + ((InterClass) c).getSize().height

                        || p.getX() + 150 > ((InterClass) c).getPozicija().x &&
                        p.getX() + 150 < ((InterClass) c).getPozicija().x + ((InterClass) c).getSize().width &&
                        p.getY() + 40 > ((InterClass) c).getPozicija().y && p.getY() + 40 < ((InterClass) c).getPozicija().y + ((InterClass) c).getSize().height){

                    ApplicationFramework.getInstance().getMessageGenerator().generateMessage("INTERCLASS_OBJECTS_CANNOT_INTERSECT", TipPoruke.GRESKA);
                    return;

                }
            }

        }

        InterClass interClass = DiagramElementFactory.createInterClassElement();
        if (interClass == null) return;
        interClass.setPozicija(p);
        interClass.setParent(dv.getDiagram());


        InterClassPainter icp = new InterClassPainter(interClass);

        dv.getCommandManager().addCommand(new AddInterClassCommand(dv, icp));
    }

    @Override
    public void misPovucen(MouseEvent e, DiagramView dv) {

    }

    @Override
    public void misOtpusten(MouseEvent e, DiagramView dv) {

    }
}
