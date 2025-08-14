package raf.dsw.classycraft.app.model.implementation;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class EnumElement extends ClassContent{

    public EnumElement(){
        subscribers = new ArrayList<>();
    }
    public EnumElement(String naziv) {
        super("", "", naziv);
        type = "enumElement";
    }

    public Object clone(){
        EnumElement temp = new EnumElement(String.copyValueOf(getNaziv().toCharArray()));
        if (!subscribers.isEmpty())
            temp.addSubscriber(subscribers.get(0));
        return temp;
    }
}