package raf.dsw.classycraft.app.model.implementation;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class Metod extends ClassContent{

    public Metod(){
        subscribers = new ArrayList<>();
    }
    public Metod(String vidljivost, String tip, String naziv) {
        super(vidljivost, tip, naziv);
        type = "method";
    }

    public Object clone(){
        Metod temp = new Metod(String.copyValueOf(getVidljivost().toCharArray()), String.copyValueOf(getTip().toCharArray()), String.copyValueOf(getNaziv().toCharArray()));
        if (!subscribers.isEmpty())
            temp.addSubscriber(subscribers.get(0));
        return temp;
    }
}
