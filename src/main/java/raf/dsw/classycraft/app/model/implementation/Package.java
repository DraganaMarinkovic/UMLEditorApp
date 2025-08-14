package raf.dsw.classycraft.app.model.implementation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.model.ClassyNode;
import raf.dsw.classycraft.app.model.ClassyNodeComposite;
import raf.dsw.classycraft.app.observer.IPublisher;
import raf.dsw.classycraft.app.observer.ISubscriber;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class Package extends ClassyNodeComposite implements IPublisher {
    private transient List<ISubscriber> subscribers; //subscriber

    public Package() {
        super("", null);
        subscribers = new ArrayList<>();
        type = "package";
    }


    public Package(String name, ClassyNode parent) {
        super(name, parent);
        subscribers = new ArrayList<>();
        type = "package";
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
        for (ISubscriber s : subscribers){
            s.update(notification);
        }
    }

    public void addChild(ClassyNode child){
        super.addChild(child);
        if (child instanceof Diagram){
            ((Diagram) child).setPackage(this);
        }
        notifySubscribers(this);
    }

    public void removeChild(ClassyNode child){
        super.removeChild(child);
        notifySubscribers(this);
    }

    @JsonIgnore
    public List<ISubscriber> getSubscribers() {
        return subscribers;
    }

    public void switchToThisPackage(){
        MainFrame.getInstance().getDesktop().swapToThisPackage(this);
    }
}
