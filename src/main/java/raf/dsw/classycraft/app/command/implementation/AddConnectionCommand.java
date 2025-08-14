package raf.dsw.classycraft.app.command.implementation;

import raf.dsw.classycraft.app.command.AbstractCommand;
import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.painter.ConnectionPainter;
import raf.dsw.classycraft.app.gui.swing.view.painter.InterClassPainter;

public class AddConnectionCommand extends AbstractCommand {

    private DiagramView diagramView;
    private ConnectionPainter cp;

    public AddConnectionCommand(DiagramView diagramView, ConnectionPainter cp) {
        this.diagramView = diagramView;
        this.cp = cp;
    }

    @Override
    public void doCommand() {
        if (!diagramView.getPainters().contains(cp))
            diagramView.addPainter(cp);
        diagramView.getDiagram().addChild(cp.getDiagramElement());

        if (cp.getDiagramElement().isSelected() && !diagramView.getSelected().contains(cp.getDiagramElement())) {
            diagramView.getSelected().add(cp.getDiagramElement());
        }
    }

    @Override
    public void undoCommand() {
        diagramView.getDiagram().removeChild(cp.getDiagramElement());
        diagramView.removePainter(cp);

        if (cp.getDiagramElement().isSelected() && diagramView.getSelected().contains(cp.getDiagramElement())) {
            diagramView.getSelected().remove(cp.getDiagramElement());
        }
    }
}
