package raf.dsw.classycraft.app.command.implementation;

import raf.dsw.classycraft.app.command.AbstractCommand;
import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.painter.ElementPainter;
import raf.dsw.classycraft.app.gui.swing.view.painter.InterClassPainter;
import raf.dsw.classycraft.app.model.implementation.InterClass;

import java.util.List;

public class DeleteCommand extends AbstractCommand {

    private DiagramView diagramView;
    private List<ElementPainter> delete;

    public DeleteCommand(DiagramView diagramView, List<ElementPainter> delete) {
        this.diagramView = diagramView;
        this.delete = delete;
    }

    @Override
    public void doCommand() {
        for (ElementPainter ep : delete) {
            diagramView.removePainter(ep);
            diagramView.getDiagram().removeChild(ep.getDiagramElement());

            if (ep.getDiagramElement().isSelected() && diagramView.getSelected().contains(ep.getDiagramElement())) {
                diagramView.getSelected().remove(ep.getDiagramElement());
            }

            if (ep.getDiagramElement() instanceof InterClass) {
                MainFrame.getInstance().getClassyTree().deleteDiagramChild(diagramView, diagramView.getD(), (InterClass) ep.getDiagramElement());
            }
        }
    }

    @Override
    public void undoCommand() {
        for (ElementPainter ep : delete) {
            diagramView.addPainter(ep);
            diagramView.getDiagram().addChild(ep.getDiagramElement());

            if (ep.getDiagramElement().isSelected() && !diagramView.getSelected().contains(ep.getDiagramElement())) {
                diagramView.getSelected().add(ep.getDiagramElement());
            }

            if (ep.getDiagramElement() instanceof InterClass) {
                MainFrame.getInstance().getClassyTree().addDiagramChild(diagramView, diagramView.getD(), (InterClass) ep.getDiagramElement());
            }
        }
    }
}
