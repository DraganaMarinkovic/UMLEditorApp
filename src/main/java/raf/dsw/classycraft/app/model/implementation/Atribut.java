package raf.dsw.classycraft.app.model.implementation;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class Atribut extends ClassContent{

    public Atribut(){
        subscribers = new ArrayList<>();
        type = "attribute";
    }
    public Atribut(String vidljivost, String tip, String naziv) {
        super(vidljivost, tip, naziv);
        type = "attribute";

    }

    public Object clone(){
        Atribut temp = new Atribut(String.copyValueOf(getVidljivost().toCharArray()), String.copyValueOf(getTip().toCharArray()), String.copyValueOf(getNaziv().toCharArray()));
        if (!subscribers.isEmpty())
            temp.addSubscriber(subscribers.get(0));
        return temp;
    }
}
