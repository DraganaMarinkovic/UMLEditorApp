package raf.dsw.classycraft.app.model.implementation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.model.ClassyNode;
import raf.dsw.classycraft.app.model.ClassyNodeComposite;
import raf.dsw.classycraft.app.observer.IPublisher;
import raf.dsw.classycraft.app.observer.ISubscriber;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class Diagram extends ClassyNodeComposite implements IPublisher, ISubscriber {

    private transient List<ISubscriber> subscribers;

    private transient Package p;

    public Diagram() {
        super("", null);
        type = "diagram";
        subscribers = new ArrayList<>();
    }

    public Diagram(String name, ClassyNode parent) {
        super(name, parent);
        type = "diagram";
        subscribers = new ArrayList<>();
        addSubscriber(new DiagramView(this));
    }

    @Override
    public void addSubscriber(ISubscriber sub) {
        subscribers.add(sub);
    }

    @Override
    public void removeSubscriber(ISubscriber sub) {
        subscribers.remove(sub);
    }

    @Override
    public void notifySubscribers(Object notification) {
        for (ISubscriber s : subscribers) {
            s.update(notification);
        }
    }

    public void addChild(ClassyNode cn) {
        if (cn instanceof DiagramElement) {
            children.add(cn);
            if (!((DiagramElement) cn).getSubscribers().contains(this))
                ((DiagramElement) cn).addSubscriber(this);
            notifySubscribers(null);
        }

    }

    public void removeChild(ClassyNode cn) {
        if (cn instanceof DiagramElement) {
            children.remove(cn);
            ((DiagramElement) cn).removeSubscriber(this);
            notifySubscribers(null);
        }
    }

    @Override
    public void setParent(ClassyNode parent) {
        super.setParent(parent);
        notifySubscribers(null);
    }

    @Override
    public void setName(String name) {
        super.setName(name);
        notifySubscribers(null);
        //
        if (p != null)
            p.notifySubscribers(p);
    }

    @JsonIgnore
    public List<ISubscriber> getSubscribers() {
        return subscribers;
    }

    @JsonIgnore
    public Package getPackage() {
        return p;
    }

    @JsonIgnore
    public void setP(Package p) {
        this.p = p;
    }

    public void setPackage(Package p) {
        this.p = p;
    }

    @Override
    public void update(Object notification) {
        notifySubscribers(null);
    }
}
