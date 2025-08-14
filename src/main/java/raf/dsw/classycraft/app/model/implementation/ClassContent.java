package raf.dsw.classycraft.app.model.implementation;

import com.fasterxml.jackson.annotation.*;
import raf.dsw.classycraft.app.observer.IPublisher;
import raf.dsw.classycraft.app.observer.ISubscriber;

import java.util.ArrayList;
import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "TYPE")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Atribut.class, name = "attribute"),
        @JsonSubTypes.Type(value = Metod.class, name = "method"),
        @JsonSubTypes.Type(value = EnumElement.class, name = "enumElement"),
})
public abstract class ClassContent implements IPublisher {
    private String vidljivost;
    private String tip;
    private String naziv;

    protected transient List<ISubscriber> subscribers;
    protected String type;

    public ClassContent(){
        subscribers = new ArrayList<>();
    }
    public ClassContent(String vidljivost, String tip, String naziv) {
        this.vidljivost = vidljivost;
        this.tip = tip;
        this.naziv = naziv;
        subscribers = new ArrayList<>();
    }

    @Override
    public void addSubscriber(ISubscriber sub) {
        subscribers.add(sub);
    }

    @Override
    public void removeSubscriber(ISubscriber sub) {
        subscribers.remove(sub);
    }

    @Override
    public void notifySubscribers(Object notification) {
        for (ISubscriber s : subscribers){
            s.update(notification);
        }
    }

    @JsonProperty("VIDLJIVOST")
    public void setVidljivost(String vidljivost) {
        this.vidljivost = vidljivost;
        notifySubscribers(null);
    }

    @JsonProperty("TIP")
    public void setTip(String tip) {
        this.tip = tip;
        notifySubscribers(null);
    }

    @JsonProperty("NAZIV")
    public void setNaziv(String naziv) {
        this.naziv = naziv;
        notifySubscribers(null);
    }

    @JsonProperty("VIDLJIVOST")

    public String getVidljivost() {
        return vidljivost;
    }

    @JsonProperty("TIP")
    public String getTip() {
        return tip;
    }

    @JsonProperty("NAZIV")
    public String getNaziv() {
        return naziv;
    }

    public Object clone(){
        return this;
    }

    public boolean equals(Object o) {
        return o instanceof ClassContent && vidljivost.equals(((ClassContent) o).vidljivost) && tip.equals(((ClassContent) o).tip) && naziv.equals(((ClassContent) o).naziv);
    }

    @JsonProperty("TYPE")
    public String getType() {
        return type;
    }

    @JsonProperty("TYPE")
    public void setType(String type) {
        this.type = type;
    }

    @JsonIgnore
    public List<ISubscriber> getSubscribers() {
        return subscribers;
    }
}
