package raf.dsw.classycraft.app.model.implementation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import raf.dsw.classycraft.app.model.ClassyNode;
import raf.dsw.classycraft.app.observer.IPublisher;
import raf.dsw.classycraft.app.observer.ISubscriber;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)

public abstract class DiagramElement extends ClassyNode implements IPublisher {
    private int color;
    private int stroke;
    private transient List<ISubscriber> subscribers;
    private boolean isSelected;

    public DiagramElement(String name, ClassyNode parent) {
        super(name, parent);
        subscribers = new ArrayList<>();
        isSelected = false;
        type = "diagramElement";
    }

    public void addSubscriber(ISubscriber sub){
        subscribers.add(sub);
    }

    public void removeSubscriber(ISubscriber sub){
        subscribers.remove(sub);
    }

    public void notifySubscribers(Object notification){
        for (ISubscriber s : subscribers){
            s.update(notification);
        }
    }


    public void setName(String name){
        this.name = name;
        notifySubscribers(null);
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        notifySubscribers(null);
    }

    public int getStroke() {
        return stroke;
    }

    public void setStroke(int stroke) {
        this.stroke = stroke;
        notifySubscribers(null);
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
        notifySubscribers(null);
    }

    @JsonIgnore
    public List<ISubscriber> getSubscribers() {
        return subscribers;
    }
}
