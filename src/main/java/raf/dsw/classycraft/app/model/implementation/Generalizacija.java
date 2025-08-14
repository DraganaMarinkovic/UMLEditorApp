package raf.dsw.classycraft.app.model.implementation;

import com.fasterxml.jackson.annotation.JsonInclude;
import raf.dsw.classycraft.app.model.ClassyNode;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class Generalizacija extends Connection{

    public Generalizacija() {
        super("", null);
        type = "generalization";
    }
    public Generalizacija(String name, ClassyNode parent) {
        super(name, parent);
        type = "generalization";
    }
}
