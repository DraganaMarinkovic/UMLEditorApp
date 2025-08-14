package raf.dsw.classycraft.app.gui.swing.view;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.model.ClassyNode;
import raf.dsw.classycraft.app.model.ClassyNodeComposite;
import raf.dsw.classycraft.app.model.implementation.Diagram;
import raf.dsw.classycraft.app.model.implementation.Package;
import raf.dsw.classycraft.app.model.implementation.Project;
import raf.dsw.classycraft.app.observer.ISubscriber;
import raf.dsw.classycraft.app.state.StateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class PackageView extends JPanel implements ISubscriber {
    private JTabbedPane jTabbedPane;
    private JLabel label;
    private StateManager stateManager;
    private JToolBar toolBar;
    private Package current;
    public PackageView() {
        stateManager = new StateManager();
        this.jTabbedPane = new JTabbedPane();
        this.label = new JLabel("");
        this.toolBar = new JToolBar(1);
        toolBar.add(MainFrame.getInstance().getActionManager().getInterClassAction());
        toolBar.add(MainFrame.getInstance().getActionManager().getConnectionAction());
        toolBar.add(MainFrame.getInstance().getActionManager().getClassContentAction());
        toolBar.add(MainFrame.getInstance().getActionManager().getSelectAction());
        toolBar.add(MainFrame.getInstance().getActionManager().getMoveAction());
        toolBar.add(MainFrame.getInstance().getActionManager().getDeletePaintAction());
        toolBar.add(MainFrame.getInstance().getActionManager().getZoomInAction());
        toolBar.add(MainFrame.getInstance().getActionManager().getZoomOutAction());
        toolBar.add(MainFrame.getInstance().getActionManager().getDuplicateAction());
        toolBar.add(MainFrame.getInstance().getActionManager().getDragAction());
        setLayout(new BorderLayout());
        add(label,BorderLayout.NORTH);
        add(toolBar, BorderLayout.EAST);
        add(jTabbedPane,BorderLayout.CENTER);
    }

    @Override
    public void update(Object notification) {
        if (notification.equals(current) && notification instanceof Package){
            jTabbedPane.removeAll();
            label.setText(ApplicationFramework.getInstance().getClassyRepository()
                    .findAuthor(MainFrame.getInstance().getClassyTree().getSelectedNode().getClassyNode()));
            for (ClassyNode cn : ((ClassyNodeComposite) notification).getChildren()) {
                if (cn instanceof Diagram) {
                    jTabbedPane.addTab(cn.getName(), (Component) ((Diagram) cn).getSubscribers().get(0));
                }
            }
        }
        revalidate();
    }

    public void misKliknut(MouseEvent e, DiagramView dv){
        stateManager.getCurrState().misKliknut(e,dv);
    }
    public void misOtpusten(MouseEvent e, DiagramView dv){
        stateManager.getCurrState().misOtpusten(e,dv);
    }
    public void misPovucen(MouseEvent e, DiagramView dv){
        stateManager.getCurrState().misPovucen(e,dv);
    }

    public void startDodavanjeInterClass(){
        stateManager.setDodavanjeInterClass();
    }
    public void startDodavanjeConnection(){
        stateManager.setDodavanjeConnection();
    }
    public void startDodavanjeContent(){
        stateManager.setDodavanjeContent();
    }
    public void startBrisanje(){
        stateManager.setBrisanje();
    }
    public void startSelect(){
        stateManager.setSelect();
    }
    public void startMove(){
        stateManager.setPomeranje();
    }
    public void startZoomIn(){ stateManager.setZoomIn(); }
    public void startZoomOut(){ stateManager.setZoomOut(); }
    public void startDuplicate() {stateManager.setDuplicate();}
    public void startDrag(){
        stateManager.setDrag();
    }


    public JToolBar getToolBar() {
        return toolBar;
    }

    public JTabbedPane getjTabbedPane() {
        return jTabbedPane;
    }
    public JLabel getLabel() {
        return label;
    }


    public Package getCurrent() {
        return current;
    }

    public void swapToThisPackage(Package p) {
        current = p;
        update(p);
    }

    public void disableUndoAction() {
        MainFrame.getInstance().getActionManager().getUndoAction().setEnabled(false);
    }

    public void disableRedoAction() {
        MainFrame.getInstance().getActionManager().getRedoAction().setEnabled(false);
    }

    public void enableRedoAction() {
        MainFrame.getInstance().getActionManager().getRedoAction().setEnabled(true);
    }

    public void enableUndoAction() {
        MainFrame.getInstance().getActionManager().getUndoAction().setEnabled(true);
    }
}
