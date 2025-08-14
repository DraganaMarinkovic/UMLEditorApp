package raf.dsw.classycraft.app.model.implementation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import raf.dsw.classycraft.app.model.ClassyNode;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class Enum extends InterClass{
    private List<EnumElement> enumElements;

    public Enum() {
        super("", null);
        enumElements = new ArrayList<>();
        type = "enum";
    }

    public Enum(String name, ClassyNode parent) {
        super(name, parent);
        enumElements = new ArrayList<>();
        type = "enum";
    }

    public void addElement(EnumElement e){
        enumElements.add(e);
        e.addSubscriber(this);
        if (e.getNaziv().length() > maxWidth) {
            maxWidth = e.getNaziv().length();
            if (size.width < 8 * maxWidth + 15) {
                size.width = 8 * maxWidth + 15;
            }
        }
        setSize(new Dimension(getSize().width, getSize().height + 20));
        //setSize poziva notify zato ne pozivamo i ovde
    }
    public void deleteElement(EnumElement e){
        enumElements.remove(e);
        e.removeSubscriber(this);
        if (e.getNaziv().length() == maxWidth){
            maxWidth = 0;
            for (EnumElement cc: enumElements ){
                if (cc.getNaziv().length() > maxWidth){
                    maxWidth = cc.getNaziv().length();
                }
            }
            if (size.width > 150 && size.width > maxWidth*8 + 15 && maxWidth*8 + 15 >= 150){
                size.width = maxWidth*8 + 15;
            } else if (size.width > maxWidth*8 + 15 && maxWidth*8 +15 < 150) {
                size.width = 150;
            }
        }
        setSize(new Dimension(getSize().width, getSize().height - 20));
        //setSize poziva notify zato ne pozivamo i ovde
    }

    public void changeElement(){
        maxWidth = 0;
        for (EnumElement cc: enumElements){
            if (cc.getTip().length() + cc.getNaziv().length() > maxWidth){
                maxWidth = cc.getTip().length() + cc.getNaziv().length();
            }
        }
        if (size.width > 150 && size.width > maxWidth*8 + 15 && maxWidth*8 + 15 >=150){
            size.width = maxWidth*8 + 15;
        } else if (size.width > maxWidth*8 + 15 && maxWidth*8 +15 < 150) {
            size.width = 150;
        } else if (size.width < 8 * maxWidth + 15) {
            size.width = 8 * maxWidth + 15;
        }
    }
    public void  update(Object notification){
        changeElement();
        calculatePoints();
        notifySubscribers(notification);
    }

    @JsonProperty("enumElements")
    public List<EnumElement> getEnumElements() {
        return enumElements;
    }

    @JsonProperty("enumElements")
    public void setEnumElements(List<EnumElement> enumElements) {
        this.enumElements = enumElements;
        notifySubscribers(null);
    }
}
