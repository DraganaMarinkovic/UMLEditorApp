package raf.dsw.classycraft.app.command.implementation;

import raf.dsw.classycraft.app.command.AbstractCommand;
import raf.dsw.classycraft.app.model.implementation.*;
import raf.dsw.classycraft.app.model.implementation.Enum;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EditCommand extends AbstractCommand {

    private String nazivPre;
    private String nazivPosle;
    private InterClass interClass;
    private Dimension preDim;
    private Dimension posleDim;
    private List<ClassContent> pre;
    private List<ClassContent> posle;

    private List<String> connectionDataPre;
    private List<String> connectionDataPosle;
    private Connection connection;

    public EditCommand(InterClass interClass, List<ClassContent> pre, List<ClassContent> posle, Dimension preDim, Dimension posleDim) {
        this.interClass = interClass;
        this.pre = pre;
        this.posle = posle;
        this.preDim = preDim;
        this.posleDim = posleDim;
    }

    @Override
    public void doCommand() {
        if (interClass != null) {
            interClass.setName(nazivPosle);
            if (interClass instanceof Klasa) {
                List<ClassContent> temp = new ArrayList<>();
                for (ClassContent classContent : posle) {
                    temp.add((ClassContent) classContent.clone());
                }
                ((Klasa) interClass).setClassContents(temp);
            } else if (interClass instanceof Interfejs) {
                List<Metod> temp = new ArrayList<>();
                for (ClassContent classContent : posle) {
                    if (classContent instanceof Metod) {
                        temp.add((Metod) classContent.clone());
                    }
                }
                ((Interfejs) interClass).setMetode(temp);
            } else if (interClass instanceof raf.dsw.classycraft.app.model.implementation.Enum) {
                List<EnumElement> temp = new ArrayList<>();
                for (ClassContent classContent : posle) {
                    if (classContent instanceof EnumElement) {
                        temp.add((EnumElement) classContent.clone());
                    }
                }
                ((Enum) interClass).setEnumElements(temp);
            }
            interClass.setSize((Dimension) posleDim.clone());
        } else {
            connection.setName(connectionDataPosle.get(0));
            connection.setKardinalitet(connectionDataPosle.get(1));
        }
    }

    @Override
    public void undoCommand() {

        if (interClass != null) {
            interClass.setName(nazivPre);
            if (interClass instanceof Klasa) {
                List<ClassContent> temp = new ArrayList<>();
                for (ClassContent classContent : pre) {
                    temp.add((ClassContent) classContent.clone());
                }
                ((Klasa) interClass).setClassContents(temp);
            } else if (interClass instanceof Interfejs) {
                List<Metod> temp = new ArrayList<>();
                for (ClassContent classContent : pre) {
                    if (classContent instanceof Metod) {
                        temp.add((Metod) classContent.clone());
                    }
                }
                ((Interfejs) interClass).setMetode(temp);
            } else if (interClass instanceof raf.dsw.classycraft.app.model.implementation.Enum) {
                List<EnumElement> temp = new ArrayList<>();
                for (ClassContent classContent : pre) {
                    if (classContent instanceof EnumElement) {
                        temp.add((EnumElement) classContent.clone());
                    }
                }
                ((Enum) interClass).setEnumElements(temp);
            }
            interClass.setSize((Dimension) preDim.clone());
        } else {
            connection.setName(connectionDataPre.get(0));
            connection.setKardinalitet(connectionDataPre.get(1));
        }
    }

    public void setConnectionDataPre(List<String> connectionDataPre) {
        this.connectionDataPre = connectionDataPre;
    }

    public void setConnectionDataPosle(List<String> connectionDataPosle) {
        this.connectionDataPosle = connectionDataPosle;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public String getNazivPre() {
        return nazivPre;
    }

    public void setNazivPre(String nazivPre) {
        this.nazivPre = nazivPre;
    }

    public String getNazivPosle() {
        return nazivPosle;
    }

    public void setNazivPosle(String nazivPosle) {
        this.nazivPosle = nazivPosle;
    }
}
