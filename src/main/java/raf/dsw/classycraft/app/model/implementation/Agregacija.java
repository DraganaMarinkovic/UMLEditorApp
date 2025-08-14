package raf.dsw.classycraft.app.model.implementation;

import com.fasterxml.jackson.annotation.JsonInclude;
import raf.dsw.classycraft.app.model.ClassyNode;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class Agregacija extends Connection{
    public Agregacija() {
        super("", null);
        type = "aggregation";
    }
    public Agregacija(String name, ClassyNode parent) {
        super(name, parent);
        type = "aggregation";
    }
}
