package raf.dsw.classycraft.app.model.implementation;

import javax.swing.*;
import java.awt.*;

public class DiagramElementFactory {

    private static int id = 1;

    public static InterClass createInterClassElement() {
        String s = (String) JOptionPane.showInputDialog(null,"Choose InterClass type: ",
                "Please choose", 0,null, new String[]{"Class", "Interface", "Enum"}, "Class");

        if (s == null)
            return null;

        InterClass interClass = null;

        if(s.equals("Class")){
            interClass = new Klasa("Class " + id++,null);
            interClass.setColor(Color.PINK.getRGB());
            interClass.setSize(new Dimension(150, 40));
        } else if(s.equals("Interface")){
            interClass = new Interfejs("Interface " + id++, null);
            interClass.setColor(Color.CYAN.getRGB());
            interClass.setSize(new Dimension(150, 30));
        } else if (s.equals("Enum")){
            interClass = new Enum("Enum " + id++, null);
            interClass.setColor(Color.lightGray.getRGB());
            interClass.setSize(new Dimension(150, 30));
        }

        interClass.setStroke(1);
        interClass.calculatePoints();

        return interClass;
    }

    public static Connection createConnectionElement() {
        String s = (String) JOptionPane.showInputDialog(null, "Choose Connection type: ",
                "Please choose", 0, null, new String[]{"Dependency", "Generalization", "Composition", "Aggregation"}, "Dependency");
        if (s == null)
            return null;
        Connection connection = null;

        if (s.equals("Dependency")) {
            connection = new Zavisnost("", null);
        } else if (s.equals("Generalization")) {
            connection = new Generalizacija("", null);
        } else if (s.equals("Composition")) {
            connection = new Kompozicija("", null);
        } else if (s.equals("Aggregation")) {
            connection = new Agregacija("", null);
        }

        return connection;
    }
}
