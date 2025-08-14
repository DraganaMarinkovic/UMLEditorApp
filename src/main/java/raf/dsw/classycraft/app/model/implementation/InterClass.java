package raf.dsw.classycraft.app.model.implementation;

import com.fasterxml.jackson.annotation.JsonInclude;
import raf.dsw.classycraft.app.model.ClassyNode;
import raf.dsw.classycraft.app.observer.ISubscriber;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)

public abstract class InterClass extends DiagramElement implements ISubscriber {
    protected String vidljivost;
    protected Point pozicija;
    protected Dimension size;
    protected List<Point> pointList;

    protected int maxWidth;

    public InterClass(String name, ClassyNode parent) {
        super(name, parent);
        maxWidth = name.length();
        type = "interClass";
    }

    public void calculatePoints(){
        if(pozicija != null && size != null) {
            pointList = new ArrayList<>();
            pointList.add(pozicija);
            pointList.add(new Point(pozicija.x + size.width, pozicija.y));
            pointList.add(new Point(pozicija.x + size.width / 2, pozicija.y));
            pointList.add(new Point(pozicija.x, pozicija.y + size.height));
            pointList.add(new Point(pozicija.x, pozicija.y + size.height / 2));
            pointList.add(new Point(pozicija.x + size.width, pozicija.y + size.height));
            pointList.add(new Point(pozicija.x + size.width / 2, pozicija.y + size.height));
            pointList.add(new Point(pozicija.x + size.width, pozicija.y + size.height / 2));
        }
    }

    public String getVidljivost() {
        return vidljivost;
    }

    public void setVidljivost(String vidljivost) {
        this.vidljivost = vidljivost;
    }

    public Point getPozicija() {
        return pozicija;
    }

    public void setPozicija(Point pozicija) {
        this.pozicija = pozicija;
        calculatePoints();
        notifySubscribers(null);
    }

    public Dimension getSize() {
        return size;
    }

    public void setSize(Dimension size) {
        this.size = size;
        calculatePoints();
        notifySubscribers(null);
    }

    public List<Point> getPointList() {
        return pointList;
    }
}
