package raf.dsw.classycraft.app.model.implementation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import raf.dsw.classycraft.app.model.ClassyNode;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)


public class Interfejs extends InterClass{
    private List<Metod> metode;

    public Interfejs () {
        super("", null);
        metode = new ArrayList<>();
        type = "interface";
    }
    public Interfejs(String name, ClassyNode parent) {
        super(name, parent);
        metode = new ArrayList<>();
        type = "interface";
    }

    public void addMetod(Metod m){
        m.setVidljivost("+");
        metode.add(m);
        m.addSubscriber(this);
        if (m.getNaziv().length() + m.getTip().length() > maxWidth) {
            maxWidth = m.getNaziv().length() + m.getTip().length();
            if (size.width < 8 * maxWidth + 40) {
                size.width = 8 * maxWidth + 40;
            }
        }
        setSize(new Dimension(getSize().width, getSize().height + 20));
        //setSize poziva notify zato ne pozivamo i ovde
    }

    public void deleteMetod(Metod m){
        metode.remove(m);
        m.removeSubscriber(this);
        if (m.getNaziv().length() + m.getTip().length() == maxWidth){
            maxWidth = 0;
            for (Metod cc: metode){
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

    public void changeMetod(){
        maxWidth = 0;
        for (Metod cc: metode){
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
        changeMetod();
        calculatePoints();
        notifySubscribers(notification);
    }

    @JsonProperty("METODE")
    public List<Metod> getMetode() {
        return metode;
    }

    @JsonProperty("METODE")
    public void setMetode(List<Metod> metode) {
        this.metode = metode;
        notifySubscribers(null);
    }
}
