package raf.dsw.classycraft.app.controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URL;


public class AboutUs extends AbstractClassyAction {
    public AboutUs() {
        putValue(NAME, "AboutUs");
        putValue(SHORT_DESCRIPTION, "AboutUs");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        createFrame();
    }


    public static void createFrame()
    {
        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                JFrame frame = new JFrame("AboutUs");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                JPanel panel = new JPanel();
                panel.setLayout(new BorderLayout());
                panel.setOpaque(true);
                JTextArea textArea = new JTextArea(4, 30);
                textArea.append("Dragana Marinković\t\tMaša Uzelac");
                textArea.setWrapStyleWord(true);
                textArea.setEditable(false);
                textArea.setFont(Font.getFont(Font.SANS_SERIF));
                panel.add(BorderLayout.NORTH, textArea);
                URL imageURL = getClass().getResource("/images/student.png");
                Icon icon = null;

                if(imageURL != null){
                    icon = new ImageIcon(imageURL);
                } else{
                    System.err.println("Resource not found: " + "/images/student.png");
                }

                JLabel imageStudent = new JLabel();

                Image newimg = ((ImageIcon)icon).getImage().getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);
                icon = new ImageIcon(newimg);

                imageStudent.setIcon(icon);
                panel.add(BorderLayout.WEST, imageStudent);

                JLabel image2 = new JLabel();
                image2.setIcon(icon);
                panel.add(BorderLayout.EAST, image2);
                JTextArea textArea2 = new JTextArea(5, 30);
                panel.add(BorderLayout.SOUTH, textArea2);

                frame.getContentPane().add(BorderLayout.CENTER, panel);
                frame.pack();
                frame.setLocationByPlatform(true);
                frame.setVisible(true);
                frame.setResizable(false);
            }
        });
    }
}