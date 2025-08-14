package raf.dsw.classycraft.app.model;
import com.fasterxml.jackson.annotation.*;
import raf.dsw.classycraft.app.model.implementation.*;
import raf.dsw.classycraft.app.model.implementation.Enum;
import raf.dsw.classycraft.app.model.implementation.Package;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "TYPE")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Project.class, name = "project"),
        @JsonSubTypes.Type(value = Package.class, name = "package"),
        @JsonSubTypes.Type(value = Diagram.class, name = "diagram"),
        @JsonSubTypes.Type(value = Klasa.class, name = "class"),
        @JsonSubTypes.Type(value = Interfejs.class, name = "interface"),
        @JsonSubTypes.Type(value = Enum.class, name = "Enum"),
        @JsonSubTypes.Type(value = Agregacija.class, name = "aggregation"),
        @JsonSubTypes.Type(value = Generalizacija.class, name = "generalization"),
        @JsonSubTypes.Type(value = Kompozicija.class, name = "composition"),
        @JsonSubTypes.Type(value = Zavisnost.class, name = "dependency"),
        @JsonSubTypes.Type(value = Selekcija.class, name = "selection")
})

public abstract class ClassyNode {

    protected String name;


    protected transient ClassyNode parent;

    protected List<ClassyNode> children;

    protected String type;


    public ClassyNode(String name, ClassyNode parent) {
        this.name = name;
        this.parent = parent;
        children = new ArrayList<>();
    }

    @JsonIgnore
    public ClassyNode getParent() {
        return parent;
    }

    @JsonIgnore
    public void setParent(ClassyNode parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public List<ClassyNode> getChildren() {
        return children;
    }

    @JsonProperty("NAME")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("TYPE")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("CHILDREN")
    public void setChildren(List<ClassyNode> children) {
        this.children = children;
    }
}
