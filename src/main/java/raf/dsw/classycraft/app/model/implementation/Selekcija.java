package raf.dsw.classycraft.app.model.implementation;

import com.fasterxml.jackson.annotation.JsonInclude;
import raf.dsw.classycraft.app.model.ClassyNode;

import java.awt.*;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class Selekcija extends DiagramElement{
    private Point pocetak;
    private Point tempPocetak;
    private Point tempKraj;
    private Point kraj;
    public Selekcija(String name, ClassyNode parent) {
        super(name, parent);
        type = "selection";
    }

    public Point getPocetak() {
        return pocetak;
    }

    public void setPocetak(Point pocetak) {
        this.pocetak = pocetak;
        notifySubscribers(null);
    }

    public Point getKraj() {
        return kraj;
    }

    public void setKraj(Point kraj) {
        this.kraj = kraj;
        notifySubscribers(kraj);
    }

    public Point getTempPocetak() {
        return tempPocetak;
    }

    public void setTempPocetak(Point tempPocetak) {
        this.tempPocetak = tempPocetak;
    }

    public Point getTempKraj() {
        return tempKraj;
    }

    public void setTempKraj(Point tempKraj) {
        this.tempKraj = tempKraj;
        notifySubscribers(tempKraj);
    }
}
