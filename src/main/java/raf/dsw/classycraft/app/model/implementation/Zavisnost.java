package raf.dsw.classycraft.app.model.implementation;

import com.fasterxml.jackson.annotation.JsonInclude;
import raf.dsw.classycraft.app.model.ClassyNode;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class Zavisnost extends Connection{

    public Zavisnost(){
        super("", null);
        type = "dependency";
    }
    public Zavisnost(String name, ClassyNode parent) {
        super(name, parent);
        type = "dependency";
    }
}
