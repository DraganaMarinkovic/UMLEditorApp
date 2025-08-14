package raf.dsw.classycraft.app.command.implementation;

import raf.dsw.classycraft.app.command.AbstractCommand;
import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.painter.ElementPainter;
import raf.dsw.classycraft.app.gui.swing.view.painter.InterClassPainter;
import raf.dsw.classycraft.app.model.implementation.Connection;
import raf.dsw.classycraft.app.model.implementation.InterClass;

import java.util.ArrayList;
import java.util.List;

public class AddInterClassCommand extends AbstractCommand {

    private DiagramView diagramView;
    private InterClassPainter icp;

    private List<ElementPainter> elementPainters= new ArrayList<>();

    public AddInterClassCommand(DiagramView diagramView, InterClassPainter icp) {
        this.diagramView = diagramView;
        this.icp = icp;
    }

    @Override
    public void doCommand() {
        MainFrame.getInstance().getClassyTree().addDiagramChild(diagramView, diagramView.getD(), (InterClass) icp.getDiagramElement());
        diagramView.addPainter(icp);
        diagramView.getDiagram().addChild(icp.getDiagramElement());

        if (icp.getDiagramElement().isSelected() && !diagramView.getSelected().contains(icp.getDiagramElement())) {
            diagramView.getSelected().add(icp.getDiagramElement());
        }

        for (ElementPainter ep : elementPainters) {
            diagramView.addPainter(ep);
            diagramView.getDiagram().addChild(ep.getDiagramElement());
        }
        elementPainters = new ArrayList<>();
    }

    @Override
    public void undoCommand() {
        MainFrame.getInstance().getClassyTree().deleteDiagramChild(diagramView, diagramView.getD(), (InterClass) icp.getDiagramElement());
        diagramView.removePainter(icp);
        diagramView.getDiagram().removeChild(icp.getDiagramElement());

        if (icp.getDiagramElement().isSelected() && diagramView.getSelected().contains(icp.getDiagramElement())) {
            diagramView.getSelected().remove(icp.getDiagramElement());
        }

        List<ElementPainter> temp= new ArrayList<>();
        for (ElementPainter ep : diagramView.getPainters()){
            if (!(ep.getDiagramElement() instanceof Connection && (((Connection) ep.getDiagramElement()).getFrom().equals(icp.getDiagramElement()) ||
                    ((Connection) ep.getDiagramElement()).getTo().equals(icp.getDiagramElement())))){
                temp.add(ep);
            } else {
                diagramView.getDiagram().removeChild(ep.getDiagramElement());
                elementPainters.add(ep);
            }
        }
        diagramView.setPainters(temp);
    }
}
