package raf.dsw.classycraft.app.model.implementation;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import raf.dsw.classycraft.app.model.ClassyNode;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

@JsonInclude(JsonInclude.Include.NON_NULL)

public abstract class Connection extends DiagramElement{
    private InterClass from;
    private InterClass to;
    private Point tmpPoint;
    private transient Path2D.Double hitBox;
    private Point toP;
    private Point fromP;

    private String kardinalitet;


    public Connection(String name, ClassyNode parent) {
        super(name, parent);
        type = "connection";
        kardinalitet = "Single";
    }
    public Point najkraciPut(InterClass from, Point tmpP){
        double min = Integer.MAX_VALUE;
        Point minP = null;

        for (Point p : from.getPointList()){
            if (Math.sqrt(Math.pow(Math.abs(tmpP.x - p.x),2) + Math.pow(Math.abs(tmpP.y - p.y),2)) < min){
                min = Math.sqrt(Math.pow(Math.abs(tmpP.x - p.x),2) + Math.pow(Math.abs(tmpP.y - p.y),2));
                minP = p;
            }
        }
        fromP = minP;
        return minP;
    }

    public Pair<Point, Point>  najkraciPut(InterClass from, InterClass to){
        double min = Integer.MAX_VALUE;
        Pair<Point, Point> minP = null;
        for (Point p1 : from.getPointList()){
            for (Point p2 : to.getPointList()){
                if(Math.sqrt(Math.pow(Math.abs(p1.x - p2.x),2) + Math.pow(Math.abs(p1.y - p2.y),2)) < min){
                    min = Math.sqrt(Math.pow(Math.abs(p1.x - p2.x),2) + Math.pow(Math.abs(p1.y - p2.y),2));
                    minP = new Pair<>(p1,p2);
                }
            }
        }
        toP = minP.getValue();
        fromP = minP.getKey();
        return minP;
    }

    public void calculateHitBox(){

        if (fromP != null && toP != null){
            hitBox = new Path2D.Double();
            double x = fromP.x;
            double y = fromP.y - 4;
            hitBox.moveTo(x,y);
            y += 8;
            hitBox.lineTo(x,y);
            x +=  Math.sqrt(Math.pow(Math.abs(fromP.x - toP.x),2) + Math.pow(Math.abs(fromP.y - toP.y),2));
            hitBox.lineTo(x,y);
            y -= 8;
            hitBox.lineTo(x,y);
            x -= Math.sqrt(Math.pow(Math.abs(fromP.x - toP.x),2) + Math.pow(Math.abs(fromP.y - toP.y),2));
            AffineTransform at = new AffineTransform();
            double ang = Math.atan2(toP.y - fromP.y, toP.x - fromP.x);
            at.rotate(ang, fromP.x, fromP.y);
            hitBox.transform(at);

        }

    }

    @JsonProperty("from")
    public InterClass getFrom() {
        return from;
    }

    @JsonProperty("from")
    public void setFrom(InterClass from) {
        this.from = from;
        notifySubscribers(null);
    }

    @JsonProperty("to")
    public InterClass getTo() {
        return to;
    }

    @JsonProperty("to")
    public void setTo(InterClass to) {
        this.to = to;
        notifySubscribers(null);
    }

    @JsonProperty("tmpPoint")
    public Point getTmpPoint() {
        return tmpPoint;
    }

    @JsonProperty("tmpPoint")
    public void setTmpPoint(Point tmpPoint) {
        this.tmpPoint = tmpPoint;
        notifySubscribers(null);
    }

    @JsonIgnore
    public Path2D.Double getHitBox() {
        return hitBox;
    }

    @JsonProperty("toP")
    public Point getToP() {
        return toP;
    }

    @JsonProperty("fromP")
    public Point getFromP() {
        return fromP;
    }

    @JsonProperty("kardinalitet")
    public String getKardinalitet() {
        return kardinalitet;
    }

    @JsonProperty("kardinalitet")
    public void setKardinalitet(String kardinalitet) {
        this.kardinalitet = kardinalitet;
    }
}
