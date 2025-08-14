package raf.dsw.classycraft.app.model.implementation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import raf.dsw.classycraft.app.model.ClassyNode;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class Klasa extends InterClass{
    private List<ClassContent> classContents;

    public Klasa() {
        super("", null);
        classContents = new ArrayList<>();
        type = "class";
    }

    public Klasa(String name, ClassyNode parent) {
        super(name, parent);
        classContents = new ArrayList<>();
        type = "class";
    }

    public void addClassContent(ClassContent c) {
        classContents.add(c);
        c.addSubscriber(this);
        if (c.getNaziv().length() + c.getTip().length() > maxWidth) {
            maxWidth = c.getNaziv().length() + c.getTip().length();
            if (size.width < 8 * maxWidth + 40) {
                size.width = 8 * maxWidth + 40;
            }
        }
        setSize(new Dimension(getSize().width, getSize().height + 20));
        //setSize poziva notify zato ne pozivamo i ovde
    }

    public void changeClassContent(){
        maxWidth = 0;
        for (ClassContent cc: classContents){
            if (cc.getTip().length() + cc.getNaziv().length() > maxWidth){
                maxWidth = cc.getTip().length() + cc.getNaziv().length();
            }
        }
        if (size.width > 150 && size.width > maxWidth*8 + 40 && maxWidth*8 + 40 >=150){
            size.width = maxWidth*8 + 40;
        } else if (size.width > maxWidth*8 + 40 && maxWidth*8 +40 < 150) {
            size.width = 150;
        } else if (size.width < 8 * maxWidth + 40) {
            size.width = 8 * maxWidth + 40;
        }
    }
    public void  update(Object notification){
        changeClassContent();
        calculatePoints();
        notifySubscribers(notification);
    }

    public void deleteClassContent(ClassContent c){
        classContents.remove(c);
        c.removeSubscriber(this);
        if (c.getNaziv().length() + c.getTip().length() == maxWidth){
            maxWidth = 0;
            for (ClassContent cc: classContents){
                if (cc.getTip().length() + cc.getNaziv().length() > maxWidth){
                    maxWidth = cc.getTip().length() + cc.getNaziv().length();
                }
            }
            if (size.width > 150 && size.width > maxWidth*8 + 40 && maxWidth*8 + 40 >= 150){
                size.width = maxWidth*8 + 40;
            } else if (size.width > maxWidth*8 + 40 && maxWidth*8 +40 < 150) {
                size.width = 150;
            }
        }

        setSize(new Dimension(getSize().width, getSize().height - 20));
        //setSize poziva notify zato ne pozivamo i ovde
    }
    public void newClassContent(List<Atribut> aa, List<Metod> mm){
        classContents = new ArrayList<>();
        for(Atribut a: aa){
            classContents.add(a);
        }
        for (Metod m: mm){
            classContents.add(m);
        }
        notifySubscribers(null);
    }

    @JsonProperty("classContents")
    public List<ClassContent> getClassContents() {
        return classContents;
    }

    @JsonProperty("classContents")
    public void setClassContents(List<ClassContent> classContents) {
        this.classContents = classContents;
        notifySubscribers(null);
    }
}
