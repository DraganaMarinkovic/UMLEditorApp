package raf.dsw.classycraft.app.model.implementation;

import com.fasterxml.jackson.annotation.JsonInclude;
import raf.dsw.classycraft.app.model.ClassyNode;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class Kompozicija extends Connection{

    public Kompozicija() {
        super("", null);
        type = "composition";
    }

    public Kompozicija(String name, ClassyNode parent) {
        super(name, parent);
        type = "composition";
    }
}
