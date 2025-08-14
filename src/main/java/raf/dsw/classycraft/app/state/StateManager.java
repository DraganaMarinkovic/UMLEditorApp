package raf.dsw.classycraft.app.state;

import raf.dsw.classycraft.app.controller.DuplicateAction;
import raf.dsw.classycraft.app.state.impl.*;

public class StateManager {
    private State currState;
    private DodavanjeInterClass dodavanjeInterClass;
    private DodavanjeConnection dodavanjeConnection;
    private DodavanjeContent dodavanjeContent;
    private Brisanje brisanje;
    private Select select;
    private Pomeranje pomeranje;
    private ZoomIn zoomIn;
    private ZoomOut zoomOut;
    private Duplicate duplicate;
    private Drag drag;

    public StateManager() {
        initStates();
    }

    private void initStates(){
        dodavanjeInterClass = new DodavanjeInterClass();
        dodavanjeConnection = new DodavanjeConnection();
        dodavanjeContent = new DodavanjeContent();
        brisanje = new Brisanje();
        select = new Select();
        pomeranje = new Pomeranje();
        zoomIn = new ZoomIn();
        zoomOut = new ZoomOut();
        duplicate = new Duplicate();
        drag = new Drag();
        currState = dodavanjeInterClass;
    }
    public State getCurrState() {
        return currState;
    }
    public void setDodavanjeInterClass() {
        currState = dodavanjeInterClass;
    }

    public void setDodavanjeConnection() {
        currState = dodavanjeConnection;
    }

    public void setDodavanjeContent() {
        currState = dodavanjeContent;
    }

    public void setBrisanje() {
        currState = brisanje;
    }

    public void setSelect() {
        currState = select;
    }
    public void setPomeranje(){
        currState = pomeranje;
    }

    public void setZoomIn(){
        currState = zoomIn;
    }
    public void setZoomOut(){
        currState = zoomOut;
    }

    public void setDuplicate() {
        currState = duplicate;
    }
    public void setDrag(){
        currState = drag;
    }
}
