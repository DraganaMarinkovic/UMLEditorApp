package raf.dsw.classycraft.app.errorHandler;

import raf.dsw.classycraft.app.observer.IPublisher;
import raf.dsw.classycraft.app.observer.ISubscriber;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MessageGenerator implements IPublisher {
    private List<ISubscriber> subs;
    public MessageGenerator(){
        subs = new ArrayList<>();
    }
    public void generateMessage(String tekst,TipPoruke tipPoruke){
        Message message = new Message(tipPoruke,tekst,LocalDateTime.now());
        notifySubscribers(message);
    }

    @Override
    public void addSubscriber(ISubscriber sub) {
        subs.add(sub);
    }

    @Override
    public void removeSubscriber(ISubscriber sub) {
        subs.remove(sub);
    }

    @Override
    public void notifySubscribers(Object notification) {
        for(ISubscriber s:subs){
            s.update(notification);
        }
    }
}
