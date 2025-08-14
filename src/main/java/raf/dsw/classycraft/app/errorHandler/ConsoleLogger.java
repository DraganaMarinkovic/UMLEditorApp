package raf.dsw.classycraft.app.errorHandler;

import raf.dsw.classycraft.app.observer.ISubscriber;

public class ConsoleLogger implements Logger,ISubscriber {
    private Message poruka;
    @Override
    public void log() {
        System.out.println("[" + poruka.getTip() + "] [" + poruka.getVreme().toString() + "]\n" + poruka.getTekst() + "\n");
    }

    @Override
    public void update(Object notification) {
        if (notification instanceof Message){
            poruka = (Message) notification;
            log();
        }
    }
}
