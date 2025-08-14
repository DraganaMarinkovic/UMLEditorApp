package raf.dsw.classycraft.app.controller;

import raf.dsw.classycraft.app.state.impl.Brisanje;

public class ActionManager {
    private ExitAction exitAction;
    private NewProjectAction newProjectAction;
    private AboutUs aboutUs;
    private NewPackageAction newPackageAction;
    private DeleteNodeAction deleteNodeAction;
    private ChangeAuthorAction changeAuthorAction;
    private NewInterClassAction interClassAction;
    private NewConnectionAction connectionAction;
    private NewClassContentAction classContentAction;
    private DeletePaintAction deletePaintAction;
    private SelectAction selectAction;
    private MoveAction moveAction;
    private ZoomOutAction zoomOutAction;
    private ZoomInAction zoomInAction;
    private DuplicateAction duplicateAction;
    private DragAction dragAction;
    private RedoAction redoAction;
    private UndoAction undoAction;
    private SaveAction saveAction;
    private LoadAction loadAction;
    public ActionManager(){
        initialiseActions();
    }
    private void initialiseActions(){
        exitAction = new ExitAction();
        aboutUs = new AboutUs();
        newProjectAction = new NewProjectAction();
        newPackageAction = new NewPackageAction();
        deleteNodeAction = new DeleteNodeAction();
        changeAuthorAction = new ChangeAuthorAction();
        interClassAction = new NewInterClassAction();
        connectionAction = new NewConnectionAction();
        classContentAction = new NewClassContentAction();
        deletePaintAction = new DeletePaintAction();
        selectAction = new SelectAction();
        moveAction = new MoveAction();
        zoomInAction = new ZoomInAction();
        zoomOutAction = new ZoomOutAction();
        duplicateAction = new DuplicateAction();
        dragAction = new DragAction();
        redoAction = new RedoAction();
        undoAction = new UndoAction();
        saveAction = new SaveAction();
        loadAction = new LoadAction();
    }

    public ExitAction getExitAction() {
        return exitAction;
    }

    public NewProjectAction getNewProjectAction() {
        return newProjectAction;
    }

    public AboutUs getAboutUs() {
        return aboutUs;
    }

    public NewPackageAction getNewPackageAction() {
        return newPackageAction;
    }

    public DeleteNodeAction getDeleteNodeAction() {
        return deleteNodeAction;
    }

    public ChangeAuthorAction getChangeAuthorAction() {
        return changeAuthorAction;
    }

    public NewInterClassAction getInterClassAction() {
        return interClassAction;
    }

    public NewConnectionAction getConnectionAction() {
        return connectionAction;
    }

    public NewClassContentAction getClassContentAction() {
        return classContentAction;
    }

    public DeletePaintAction getDeletePaintAction() {
        return deletePaintAction;
    }

    public SelectAction getSelectAction() {
        return selectAction;
    }

    public MoveAction getMoveAction() {
        return moveAction;
    }

    public ZoomOutAction getZoomOutAction() {
        return zoomOutAction;
    }

    public ZoomInAction getZoomInAction() {
        return zoomInAction;
    }

    public DuplicateAction getDuplicateAction() {
        return duplicateAction;
    }
    public DragAction getDragAction(){
        return dragAction;
    }

    public RedoAction getRedoAction() {
        return redoAction;
    }

    public UndoAction getUndoAction() {
        return undoAction;
    }

    public SaveAction getSaveAction() {
        return saveAction;
    }

    public LoadAction getLoadAction() {
        return loadAction;
    }
}
