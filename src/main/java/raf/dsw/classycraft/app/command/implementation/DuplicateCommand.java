package raf.dsw.classycraft.app.command.implementation;

import raf.dsw.classycraft.app.command.AbstractCommand;
import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.painter.InterClassPainter;
import raf.dsw.classycraft.app.model.implementation.InterClass;

public class DuplicateCommand extends AbstractCommand {

    private DiagramView diagramView;
    private InterClassPainter icp;

    public DuplicateCommand(DiagramView diagramView, InterClassPainter icp) {
        this.diagramView = diagramView;
        this.icp = icp;
    }

    @Override
    public void doCommand() {
        diagramView.addPainter(icp);
        diagramView.getDiagram().addChild(icp.getDiagramElement());
        MainFrame.getInstance().getClassyTree().addDiagramChild(diagramView, diagramView.getD(), (InterClass) icp.getDiagramElement());

        if (icp.getDiagramElement().isSelected() && !diagramView.getSelected().contains(icp.getDiagramElement())) {
            diagramView.getSelected().add(icp.getDiagramElement());
        }
    }

    @Override
    public void undoCommand() {
        diagramView.removePainter(icp);
        diagramView.getDiagram().removeChild(icp.getDiagramElement());
        MainFrame.getInstance().getClassyTree().deleteDiagramChild(diagramView, diagramView.getD(), (InterClass) icp.getDiagramElement());

        if (icp.getDiagramElement().isSelected() && diagramView.getSelected().contains(icp.getDiagramElement())) {
            diagramView.getSelected().remove(icp.getDiagramElement());
        }
    }
}
