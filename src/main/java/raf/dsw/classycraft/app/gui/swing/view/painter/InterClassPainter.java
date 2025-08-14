package raf.dsw.classycraft.app.gui.swing.view.painter;

import org.w3c.dom.css.RGBColor;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.painter.ElementPainter;
import raf.dsw.classycraft.app.model.implementation.*;
import raf.dsw.classycraft.app.model.implementation.Enum;

import java.awt.*;
import java.awt.image.RGBImageFilter;

public class InterClassPainter extends ElementPainter {
    public InterClassPainter(DiagramElement diagramElement) {
        super(diagramElement);
    }

    @Override
    public void paint(Graphics2D g, DiagramElement element) {
        Color color;
        if (element.isSelected()){
            color = Color.BLUE;
        } else {
            color = Color.black;
        }

        g.setPaint(new Color(element.getColor()));
        g.setStroke(new BasicStroke(element.getStroke()));
        g.fillRect(((InterClass)element).getPozicija().x, ((InterClass) element).getPozicija().y, ((InterClass) element).getSize().width,20);
        g.setPaint(color);
        g.drawRect(((InterClass)element).getPozicija().x, ((InterClass) element).getPozicija().y, ((InterClass) element).getSize().width,20);
        g.setPaint(Color.black);
        g.drawString((element instanceof Klasa ? "C:  " : element instanceof Interfejs ? "I:  " : "E:  " ),((InterClass) element).getPozicija().x + 2, ((InterClass) element).getPozicija().y + 15);
        g.drawString( element.getName(), ((InterClass) element).getPozicija().x + ((InterClass) element).getSize().width/2 - (element.getName().length()/2)*6, ((InterClass) element).getPozicija().y + 15);
        if(element instanceof Klasa){
            int atribut = 0;
            int metoda = 0;

            for (ClassContent c : ((Klasa) element).getClassContents()){
                if (c instanceof Atribut)
                    atribut++;
                if (c instanceof Metod)
                    metoda++;
            }

            g.setPaint(new Color(element.getColor()));
            g.fillRect(((Klasa) element).getPozicija().x, ((Klasa) element).getPozicija().y + 20,((Klasa) element).getSize().width, 10 + atribut*20);
            g.setPaint(color);
            g.drawRect(((Klasa) element).getPozicija().x, ((Klasa) element).getPozicija().y + 20,((Klasa) element).getSize().width, 10 + atribut*20);
            g.setPaint(Color.black);
            int visinaA = ((Klasa) element).getPozicija().y + 40;
            for (ClassContent c : ((Klasa) element).getClassContents()){
                if(c instanceof Atribut){
                    g.drawString(c.getVidljivost() + " " + c.getNaziv() + " : " + c.getTip(),((Klasa) element).getPozicija().x + 5, visinaA);
                    visinaA+=20;
                }
            }
            g.setPaint(new Color(element.getColor()));
            g.fillRect(((Klasa) element).getPozicija().x, ((Klasa) element).getPozicija().y + 30 + atribut*20,((Klasa) element).getSize().width, 10 + metoda*20);
            g.setPaint(color);
            g.drawRect(((Klasa) element).getPozicija().x, ((Klasa) element).getPozicija().y + 30 + atribut*20,((Klasa) element).getSize().width, 10 + metoda*20);
            g.setPaint(Color.black);
            visinaA+=5;
            for (ClassContent c : ((Klasa) element).getClassContents()){
                if(c instanceof Metod){
                    g.drawString(c.getVidljivost() + " " + c.getNaziv() + " : " + c.getTip(),((Klasa) element).getPozicija().x + 5, visinaA);
                    visinaA+=20;
                }
            }
        } else if(element instanceof Interfejs){
            int metoda = ((Interfejs)element).getMetode().size();

            g.setPaint(new Color(element.getColor()));
            g.fillRect(((Interfejs) element).getPozicija().x, ((Interfejs) element).getPozicija().y + 20,((Interfejs) element).getSize().width, 10 + metoda*20);
            g.setPaint(color);
            g.drawRect(((Interfejs) element).getPozicija().x, ((Interfejs) element).getPozicija().y + 20,((Interfejs) element).getSize().width, 10 + metoda*20);
            g.setPaint(Color.black);
            int visinaA = ((Interfejs) element).getPozicija().y + 40;
            for (Metod c : ((Interfejs) element).getMetode()){
                g.drawString("+ " + c.getNaziv() + " : " + c.getTip(),((Interfejs) element).getPozicija().x + 5, visinaA);
                visinaA+=20;
            }


        }else if(element instanceof Enum){
            int e = ((Enum)element).getEnumElements().size();

            g.setPaint(new Color(element.getColor()));
            g.fillRect(((Enum) element).getPozicija().x, ((Enum) element).getPozicija().y + 20,((Enum) element).getSize().width, 10 + e*20);
            g.setPaint(color);
            g.drawRect(((Enum) element).getPozicija().x, ((Enum) element).getPozicija().y + 20,((Enum) element).getSize().width, 10 + e*20);
            g.setPaint(Color.black);
            int visinaA = ((Enum) element).getPozicija().y + 40;
            for (EnumElement c : ((Enum)element).getEnumElements()){
                g.drawString(c.getNaziv(),((Enum) element).getPozicija().x + 5, visinaA);
                visinaA+=20;
            }
        }
    }
}