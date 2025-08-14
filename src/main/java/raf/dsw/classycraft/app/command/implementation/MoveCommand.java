package raf.dsw.classycraft.app.command.implementation;

import raf.dsw.classycraft.app.command.AbstractCommand;
import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.painter.ElementPainter;
import raf.dsw.classycraft.app.model.implementation.Connection;
import raf.dsw.classycraft.app.model.implementation.DiagramElement;
import raf.dsw.classycraft.app.model.implementation.InterClass;

import java.awt.*;
import java.util.List;

public class MoveCommand extends AbstractCommand {

    private DiagramView diagramView;
    private List<DiagramElement> toBeMoved;
    private List<Point> start;
    private List<Point> end;
    private InterClass arrowOldDestination;
    private InterClass arrowNewDestination;

    public MoveCommand(DiagramView diagramView, List<Point> start, List<Point> end, List<DiagramElement> toBeMoved) {
        this.diagramView = diagramView;
        this.start = start;
        this.end = end;
        this.toBeMoved = toBeMoved;
    }

    @Override
    public void doCommand() {
        if(arrowNewDestination == null) {
            for (int i = 0; i < toBeMoved.size(); i++) {
                if (toBeMoved.get(i) instanceof InterClass) {
                    ((InterClass) toBeMoved.get(i)).setPozicija(end.get(i));
                }
            }
        } else {
            if (toBeMoved.get(0) instanceof Connection)
                ((Connection) toBeMoved.get(0)).setTo(arrowNewDestination);
        }
    }

    @Override
    public void undoCommand() {
        if(arrowNewDestination == null) {
            for (int i = 0; i < toBeMoved.size(); i++) {
                if (toBeMoved.get(i) instanceof InterClass) {
                    ((InterClass) toBeMoved.get(i)).setPozicija(start.get(i));
                }
            }
        } else {
            if (toBeMoved.get(0) instanceof Connection)
                ((Connection) toBeMoved.get(0)).setTo(arrowOldDestination);
        }
    }

    public InterClass getArrowOldDestination() {
        return arrowOldDestination;
    }

    public void setArrowOldDestination(InterClass arrowOldDestination) {
        this.arrowOldDestination = arrowOldDestination;
    }

    public InterClass getArrowNewDestination() {
        return arrowNewDestination;
    }

    public void setArrowNewDestination(InterClass arrowNewDestination) {
        this.arrowNewDestination = arrowNewDestination;
    }
}
