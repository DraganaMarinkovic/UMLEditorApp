package raf.dsw.classycraft.app.errorHandler;
import raf.dsw.classycraft.app.observer.ISubscriber;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileLogger implements Logger, ISubscriber {
    private Message poruka;

    @Override
    public void log() {
        File file = new File(getClass().getResource("/log/log.txt").getFile());
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write("[" + poruka.getTip() + "] [" + poruka.getVreme().toString() + "]\n" + poruka.getTekst() + "\n");
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Object notification) {
        if (notification instanceof Message){
            poruka = (Message) notification;
            log();
        }
    }
}
