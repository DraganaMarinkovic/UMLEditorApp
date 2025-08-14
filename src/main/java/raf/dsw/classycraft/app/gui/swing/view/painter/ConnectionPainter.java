package raf.dsw.classycraft.app.gui.swing.view.painter;

import raf.dsw.classycraft.app.gui.swing.view.painter.ElementPainter;
import raf.dsw.classycraft.app.model.implementation.*;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
public class ConnectionPainter extends ElementPainter {
    public ConnectionPainter(DiagramElement diagramElement) {
        super(diagramElement);
    }

    public void paint(Graphics2D g, DiagramElement diagramElement){
        if (diagramElement instanceof Connection && ((Connection) diagramElement).getFrom() != null){
            Color color;
            if (diagramElement.isSelected()){
                color = Color.blue;
            } else {
                color = Color.black;
            }

            if (diagramElement instanceof Agregacija){
                if(((Connection) diagramElement).getTo() == null && ((Connection) diagramElement).getTmpPoint() != null){
                    Point p = ((Connection)diagramElement).najkraciPut(((Connection) diagramElement).getFrom(), ((Connection) diagramElement).getTmpPoint());
                    g.setPaint(color);
                    g.drawLine(p.x, p.y,((Connection) diagramElement).getTmpPoint().x, ((Connection) diagramElement).getTmpPoint().y);
                    g.setPaint(Color.black);
                    Path2D.Double path = new Path2D.Double();
                    path.moveTo(((Connection) diagramElement).getTmpPoint().x, ((Connection) diagramElement).getTmpPoint().y);
                    double x = ((Connection) diagramElement).getTmpPoint().x - 10;
                    double y = ((Connection) diagramElement).getTmpPoint().y - 5;
                    path.lineTo(x,y);
                    x -= 10;
                    y += 5;
                    path.lineTo(x,y);
                    x += 10;
                    y += 5;
                    path.lineTo(x,y);
                    x += 10;
                    y -= 5;
                    path.lineTo(x,y);
                    AffineTransform at = new AffineTransform();
                    double ang = Math.atan2(((Connection) diagramElement).getTmpPoint().y - p.y, ((Connection) diagramElement).getTmpPoint().x - p.x);
                    at.rotate(ang, ((Connection) diagramElement).getTmpPoint().x, ((Connection) diagramElement).getTmpPoint().y);
                    path.transform(at);
                    g.setPaint(Color.white);
                    g.fill(path);
                    g.setPaint(color);
                    g.draw(path);
                    g.setPaint(Color.black);

                } else if (((Connection) diagramElement).getTo() != null){
                    Pair<Point,Point> p = ((Connection)diagramElement).najkraciPut(((Connection) diagramElement).getFrom(), ((Connection) diagramElement).getTo());
                    g.setPaint(color);
                    g.drawLine(p.getKey().x, p.getKey().y, p.getValue().x, p.getValue().y);
                    g.setPaint(Color.black);
                    Path2D.Double path = new Path2D.Double();
                    path.moveTo(p.getValue().x, p.getValue().y);
                    double x = p.getValue().x - 10;
                    double y = p.getValue().y - 5;
                    path.lineTo(x,y);
                    x -= 10;
                    y += 5;
                    path.lineTo(x,y);
                    x += 10;
                    y += 5;
                    path.lineTo(x,y);
                    x += 10;
                    y -= 5;
                    path.lineTo(x,y);
                    AffineTransform at = new AffineTransform();
                    double ang = Math.atan2(p.getValue().y - p.getKey().y, p.getValue().x - p.getKey().x);
                    at.rotate(ang, p.getValue().x, p.getValue().y);
                    path.transform(at);
                    g.setPaint(Color.white);
                    g.fill(path);
                    g.setPaint(color);
                    g.draw(path);
                    g.setPaint(Color.black);
                }
            } else if (diagramElement instanceof Zavisnost){
                if(((Connection) diagramElement).getTo() == null && ((Connection) diagramElement).getTmpPoint() != null){
                    Point p = ((Connection)diagramElement).najkraciPut(((Connection) diagramElement).getFrom(), ((Connection) diagramElement).getTmpPoint());
                    g.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL, 0, new float[]{5},0));
                    g.setPaint(color);
                    g.drawLine(p.x, p.y,((Connection) diagramElement).getTmpPoint().x, ((Connection) diagramElement).getTmpPoint().y);
                    g.setPaint(Color.black);
                    g.setStroke(new BasicStroke(1));
                    Path2D.Double path = new Path2D.Double();
                    path.moveTo(((Connection) diagramElement).getTmpPoint().x, ((Connection) diagramElement).getTmpPoint().y);
                    double x = ((Connection) diagramElement).getTmpPoint().x - 10;
                    double y = ((Connection) diagramElement).getTmpPoint().y - 5;
                    path.lineTo(x,y);
                    x = ((Connection) diagramElement).getTmpPoint().x - 10;
                    y = ((Connection) diagramElement).getTmpPoint().y + 5;
                    path.moveTo(((Connection) diagramElement).getTmpPoint().x, ((Connection) diagramElement).getTmpPoint().y);
                    path.lineTo(x,y);
                    AffineTransform at = new AffineTransform();
                    double ang = Math.atan2(((Connection) diagramElement).getTmpPoint().y - p.y, ((Connection) diagramElement).getTmpPoint().x - p.x);
                    at.rotate(ang, ((Connection) diagramElement).getTmpPoint().x, ((Connection) diagramElement).getTmpPoint().y);
                    path.transform(at);
                    g.setPaint(color);
                    g.draw(path);
                    g.setPaint(Color.black);
                } else if (((Connection) diagramElement).getTo() != null){
                    //najkraci put nalazi dve najblize tacke izmedju klasa
                    Pair<Point,Point> p = ((Connection)diagramElement).najkraciPut(((Connection) diagramElement).getFrom(), ((Connection) diagramElement).getTo());
                    //crtamo isprekidanu liniju
                    g.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL, 0, new float[]{5},0));
                    g.setPaint(color);
                    g.drawLine(p.getKey().x, p.getKey().y,p.getValue().x, p.getValue().y);
                    g.setPaint(Color.black);
                    g.setStroke(new BasicStroke(1));
                    //pravimo path koji ce nam prestavljati vrh konekcije u ovom slucaju strelica
                    Path2D.Double path = new Path2D.Double();
                    path.moveTo(p.getValue().x, p.getValue().y);
                    double x = p.getValue().x - 10;
                    double y = p.getValue().y - 5;
                    path.lineTo(x,y);
                    x = p.getValue().x - 10;
                    y = p.getValue().y + 5;
                    path.moveTo(p.getValue().x, p.getValue().y);
                    path.lineTo(x,y);
                    //ovime rotiramo ovaj shape da bude okrenut u pravcu strelice
                    AffineTransform at = new AffineTransform();
                    double ang = Math.atan2(p.getValue().y - p.getKey().y, p.getValue().x - p.getKey().x);
                    at.rotate(ang, p.getValue().x, p.getValue().y);
                    path.transform(at);
                    //crtamo vrh strelice
                    g.setPaint(color);
                    g.draw(path);
                    g.setPaint(Color.black);
                }
            } else if (diagramElement instanceof Generalizacija){
                if(((Connection) diagramElement).getTo() == null && ((Connection) diagramElement).getTmpPoint() != null){
                    Point p = ((Connection)diagramElement).najkraciPut(((Connection) diagramElement).getFrom(), ((Connection) diagramElement).getTmpPoint());
                    g.setPaint(color);
                    g.drawLine(p.x, p.y,((Connection) diagramElement).getTmpPoint().x, ((Connection) diagramElement).getTmpPoint().y);
                    g.setPaint(Color.black);
                    Path2D.Double path = new Path2D.Double();
                    path.moveTo(((Connection) diagramElement).getTmpPoint().x, ((Connection) diagramElement).getTmpPoint().y);
                    double x = ((Connection) diagramElement).getTmpPoint().x - 10;
                    double y = ((Connection) diagramElement).getTmpPoint().y - 7;
                    path.lineTo(x,y);
                    y += 14;
                    path.lineTo(x,y);
                    x += 10;
                    y -= 7;
                    path.lineTo(x,y);
                    AffineTransform at = new AffineTransform();
                    double ang = Math.atan2(((Connection) diagramElement).getTmpPoint().y - p.y, ((Connection) diagramElement).getTmpPoint().x - p.x);
                    at.rotate(ang, ((Connection) diagramElement).getTmpPoint().x, ((Connection) diagramElement).getTmpPoint().y);
                    path.transform(at);
                    g.setPaint(Color.white);
                    g.fill(path);
                    g.setPaint(color);
                    g.draw(path);
                    g.setPaint(Color.black);

                } else if (((Connection) diagramElement).getTo() != null){
                    Pair<Point,Point> p = ((Connection)diagramElement).najkraciPut(((Connection) diagramElement).getFrom(), ((Connection) diagramElement).getTo());
                    g.setPaint(color);
                    g.drawLine(p.getKey().x, p.getKey().y, p.getValue().x, p.getValue().y);
                    g.setPaint(Color.black);
                    Path2D.Double path = new Path2D.Double();
                    path.moveTo(p.getValue().x, p.getValue().y);
                    double x = p.getValue().x - 10;
                    double y = p.getValue().y - 7;
                    path.lineTo(x,y);
                    y += 14;
                    path.lineTo(x,y);
                    x += 10;
                    y -= 7;
                    path.lineTo(x,y);
                    AffineTransform at = new AffineTransform();
                    double ang = Math.atan2(p.getValue().y - p.getKey().y, p.getValue().x - p.getKey().x);
                    at.rotate(ang, p.getValue().x, p.getValue().y);
                    path.transform(at);
                    g.setPaint(Color.white);
                    g.fill(path);
                    g.setPaint(color);
                    g.draw(path);
                    g.setPaint(Color.black);
                }
            } else if(diagramElement instanceof Kompozicija) {
                if(((Connection) diagramElement).getTo() == null && ((Connection) diagramElement).getTmpPoint() != null){
                    Point p = ((Connection)diagramElement).najkraciPut(((Connection) diagramElement).getFrom(), ((Connection) diagramElement).getTmpPoint());
                    g.setPaint(color);
                    g.drawLine(p.x, p.y,((Connection) diagramElement).getTmpPoint().x, ((Connection) diagramElement).getTmpPoint().y);
                    g.setPaint(Color.black);
                    Path2D.Double path = new Path2D.Double();
                    path.moveTo(((Connection) diagramElement).getTmpPoint().x, ((Connection) diagramElement).getTmpPoint().y);
                    double x = ((Connection) diagramElement).getTmpPoint().x - 10;
                    double y = ((Connection) diagramElement).getTmpPoint().y - 5;
                    path.lineTo(x,y);
                    x -= 10;
                    y += 5;
                    path.lineTo(x,y);
                    x += 10;
                    y += 5;
                    path.lineTo(x,y);
                    x += 10;
                    y -= 5;
                    path.lineTo(x,y);
                    AffineTransform at = new AffineTransform();
                    double ang = Math.atan2(((Connection) diagramElement).getTmpPoint().y - p.y, ((Connection) diagramElement).getTmpPoint().x - p.x);
                    at.rotate(ang, ((Connection) diagramElement).getTmpPoint().x, ((Connection) diagramElement).getTmpPoint().y);
                    path.transform(at);
                    g.fill(path);
                    g.setPaint(color);
                    g.draw(path);
                    g.setPaint(Color.black);
                } else if (((Connection) diagramElement).getTo() != null){
                    Pair<Point,Point> p = ((Connection)diagramElement).najkraciPut(((Connection) diagramElement).getFrom(), ((Connection) diagramElement).getTo());
                    g.setPaint(color);
                    g.drawLine(p.getKey().x, p.getKey().y, p.getValue().x, p.getValue().y);
                    g.setPaint(Color.black);
                    Path2D.Double path = new Path2D.Double();
                    path.moveTo(p.getValue().x, p.getValue().y);
                    double x = p.getValue().x - 10;
                    double y = p.getValue().y - 5;
                    path.lineTo(x,y);
                    x -= 10;
                    y += 5;
                    path.lineTo(x,y);
                    x += 10;
                    y += 5;
                    path.lineTo(x,y);
                    x += 10;
                    y -= 5;
                    path.lineTo(x,y);
                    AffineTransform at = new AffineTransform();
                    double ang = Math.atan2(p.getValue().y - p.getKey().y, p.getValue().x - p.getKey().x);
                    at.rotate(ang, p.getValue().x, p.getValue().y);
                    path.transform(at);
                    g.fill(path);
                    g.setPaint(color);
                    g.draw(path);
                    g.setPaint(Color.black);
                }
            }
            ((Connection) diagramElement).calculateHitBox();
        }
    }
}