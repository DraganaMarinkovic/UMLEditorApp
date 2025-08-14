package raf.dsw.classycraft.app.core;

import raf.dsw.classycraft.app.errorHandler.ConsoleLogger;
import raf.dsw.classycraft.app.errorHandler.FileLogger;
import raf.dsw.classycraft.app.errorHandler.LoggerFactory;
import raf.dsw.classycraft.app.errorHandler.MessageGenerator;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.model.repository.ClassyRepository;
import raf.dsw.classycraft.app.model.repository.ClassyRepositoryImplementation;
import raf.dsw.classycraft.app.serializer.Serializer;
import raf.dsw.classycraft.app.serializer.implementation.JacksonSerializer;

public class ApplicationFramework {

    private static ApplicationFramework instance;

    private FileLogger fileLogger;
    private ConsoleLogger consoleLogger;
    private MessageGenerator messageGenerator;
    private ClassyRepository classyRepository;
    private Serializer serializer;

    private ApplicationFramework(){
        fileLogger = LoggerFactory.getFileLogger();
        consoleLogger = LoggerFactory.getConsoleLogger();
        messageGenerator = new MessageGenerator();
        classyRepository = new ClassyRepositoryImplementation();
        messageGenerator.addSubscriber(fileLogger);
        messageGenerator.addSubscriber(consoleLogger);
        serializer = new JacksonSerializer();
    }

    public MessageGenerator getMessageGenerator() {
        return messageGenerator;
    }

    public void initialize(){
        MainFrame.getInstance().setVisible(true);
    }

    public static ApplicationFramework getInstance(){
        if(instance==null){
            instance = new ApplicationFramework();
        }
        return instance;
    }

    public ClassyRepository getClassyRepository() {
        return classyRepository;
    }

    public Serializer getSerializer() {
        return serializer;
    }
}
