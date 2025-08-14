package raf.dsw.classycraft.app.command;

import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {
    private List<AbstractCommand> commands = new ArrayList<>();
    private int currentCommand = 0;

    public void addCommand(AbstractCommand command){
        while(currentCommand < commands.size())
            commands.remove(currentCommand);
        commands.add(command);
        doCommand();
    }

    public void doCommand(){
        if(currentCommand < commands.size()){
            commands.get(currentCommand++).doCommand();
            MainFrame.getInstance().getDesktop().enableUndoAction();
        }
        if(currentCommand==commands.size()){
            MainFrame.getInstance().getDesktop().disableRedoAction();
        }
    }


    public void undoCommand(){
        if(currentCommand > 0){
            MainFrame.getInstance().getDesktop().enableRedoAction();
            commands.get(--currentCommand).undoCommand();
        }
        if(currentCommand==0){
            MainFrame.getInstance().getDesktop().disableUndoAction();
        }
    }

}
