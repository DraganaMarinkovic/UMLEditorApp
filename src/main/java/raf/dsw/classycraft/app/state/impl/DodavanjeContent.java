package raf.dsw.classycraft.app.state.impl;

import raf.dsw.classycraft.app.command.implementation.EditCommand;
import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.model.ClassyNode;
import raf.dsw.classycraft.app.model.implementation.*;
import raf.dsw.classycraft.app.model.implementation.Enum;
import raf.dsw.classycraft.app.observer.ISubscriber;
import raf.dsw.classycraft.app.state.State;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.util.ArrayList;
import java.util.List;

public class DodavanjeContent implements State{
    @Override
    public void misKliknut(MouseEvent e, DiagramView dv) {
        Point p = new Point();
        try {
            AffineTransform af = dv.getAffineTransform().createInverse();
            af.transform(e.getPoint(), p);
        } catch (NoninvertibleTransformException ex) {
            throw new RuntimeException(ex);
        }

        for(ClassyNode c : dv.getDiagram().getChildren()){
            if(c instanceof InterClass && p.getX() > ((InterClass) c).getPozicija().x &&
                    p.getX() < ((InterClass) c).getPozicija().x + ((InterClass) c).getSize().width &&
                    p.getY() > ((InterClass) c).getPozicija().y && p.getY() < ((InterClass) c).getPozicija().y + ((InterClass) c).getSize().height) {

                List<ClassContent> pre = new ArrayList<>();

                if(c instanceof Klasa){
                    for (ClassContent cc : ((Klasa) c).getClassContents()){
                        pre.add((ClassContent) cc.clone());
                    }
                } else if (c instanceof Interfejs) {
                    for (Metod m : ((Interfejs) c).getMetode()){
                        pre.add((ClassContent) m.clone());
                    }
                } else if (c instanceof Enum) {
                    for (EnumElement ee : ((Enum) c).getEnumElements()){
                        pre.add((ClassContent) ee.clone());
                    }
                }
                String nazivPre = c.getName();

                Dimension preDim = (Dimension) ((InterClass) c).getSize().clone();


                JScrollPane scroll = new JScrollPane();
                scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                JPanel jsp = new JPanel();
                JPanel jp1 = new JPanel();
                jp1.setLayout(new BoxLayout(jp1, BoxLayout.X_AXIS));
                JLabel enter = new JLabel("Press enter to save changes in text fields.");
                JLabel ime = new JLabel("Enter name: ");
                JTextField imet = new JTextField(c.getName());
                jsp.setLayout(new BoxLayout(jsp, BoxLayout.Y_AXIS));
                jsp.add(enter);
                jp1.add(ime);
                jp1.add(imet);
                imet.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        c.setName(imet.getText());
                    }
                });
                jsp.add(jp1);
                if(c instanceof Klasa){
                    JLabel at = new JLabel("Attributes: ");
                    jsp.add(at);
                    JPanel atributi = new JPanel();
                    atributi.setLayout(new BoxLayout(atributi, BoxLayout.Y_AXIS));
                    jsp.add(atributi);
                    JTextField temp;
                    JPanel jp2 = new JPanel();
                    jp2.setLayout(new BoxLayout(jp2,BoxLayout.X_AXIS));
                    JButton jbd = new JButton("Delete");
                    String[] v = {"+", "-", "#", " "};
                    JComboBox<String> temp1 = new JComboBox<String>(v);


                    for (ClassContent cc : ((Klasa) c).getClassContents()){
                        if (cc instanceof Atribut){
                            if(cc.getVidljivost().equals("+")){
                                temp1.setSelectedIndex(0);
                            }
                            else if(cc.getVidljivost().equals("-")){
                                temp1.setSelectedIndex(1);
                            }
                            else if(cc.getVidljivost().equals("#")){
                                temp1.setSelectedIndex(2);
                            }
                            else if(cc.getVidljivost().equals(" ")){
                                temp1.setSelectedIndex(3);
                            }
                            JComboBox<String> finalTemp = temp1;
                            temp1.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    cc.setVidljivost((String) finalTemp.getSelectedItem());
                                }
                            });
                            jp2.add(temp1);
                            temp1 = new JComboBox<String>(v);
                            temp = new JTextField(cc.getTip());
                            JTextField finalTemp1 = temp;
                            temp.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    cc.setTip(finalTemp1.getText());
                                }
                            });
                            jp2.add(temp);
                            temp = new JTextField(cc.getNaziv());
                            JTextField finalTemp2 = temp;
                            temp.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    cc.setNaziv(finalTemp2.getText());
                                }
                            });
                            jp2.add(temp);
                            atributi.add(jp2);
                            jp2.add(jbd);
                            JPanel finalJp1 = jp2;
                            jbd.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    ((Klasa) c).deleteClassContent(cc);
                                    atributi.remove(finalJp1);
                                    jsp.revalidate();
                                }
                            });
                            jbd = new JButton("Delete");
                            jp2 = new JPanel();
                            jp2.setLayout(new BoxLayout(jp2, BoxLayout.X_AXIS));

                        }
                    }
                    jsp.add(new JLabel("Add new attribute: "));
                    JComboBox<String> novaVidljivost = new JComboBox<String>(v);
                    JTextField noviTip = new JTextField("Type");
                    JTextField novNaziv = new JTextField("Name");
                    JButton jb = new JButton("Save");
                    jp2.add(novaVidljivost);
                    jp2.add(noviTip);
                    jp2.add(novNaziv);
                    jp2.add(jb);
                    jsp.add(jp2);

                    jb.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Atribut tempa = new Atribut((String) novaVidljivost.getSelectedItem(), noviTip.getText(), novNaziv.getText());
                            ((Klasa) c).addClassContent(tempa);
                            tempa.addSubscriber((ISubscriber) c);
                            JPanel jp3 = new JPanel();
                            jp3.setLayout(new BoxLayout(jp3, BoxLayout.X_AXIS));
                            JComboBox<String> j3 = new JComboBox<String>(v);
                            if(novaVidljivost.getSelectedItem().equals("+")){
                                j3.setSelectedIndex(0);
                            }
                            else if(novaVidljivost.getSelectedItem().equals("-")){
                                j3.setSelectedIndex(1);
                            }
                            else if(novaVidljivost.getSelectedItem().equals("#")){
                                j3.setSelectedIndex(2);
                            }
                            else if(novaVidljivost.getSelectedItem().equals(" ")){
                                j3.setSelectedIndex(3);
                            }
                            jp3.add(j3);
                            j3.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    tempa.setVidljivost((String) j3.getSelectedItem());

                                }
                            });
                            JTextField t3 = new JTextField(noviTip.getText());
                            JTextField finalT = t3;
                            t3.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    tempa.setTip(finalT.getText());
                                }
                            });
                            jp3.add(t3);
                            t3 = new JTextField(novNaziv.getText());
                            JTextField finalT1 = t3;
                            t3.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    tempa.setNaziv(finalT1.getText());
                                }
                            });
                            jp3.add(t3);
                            JButton del = new JButton("Delete");
                            jp3.add(del);
                            del.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    ((Klasa) c).deleteClassContent(tempa);
                                    atributi.remove(jp3);
                                    jsp.revalidate();
                                }
                            });
                            atributi.add(jp3);
                            atributi.revalidate();
                            jsp.revalidate();
                        }
                    });
                    JLabel mt = new JLabel("Methods: ");
                    jsp.add(mt);
                    JPanel metode = new JPanel();
                    metode.setLayout(new BoxLayout(metode, BoxLayout.Y_AXIS));
                    jsp.add(metode);
                    jp2 = new JPanel();
                    jp2.setLayout(new BoxLayout(jp2, BoxLayout.X_AXIS));
                    for (ClassContent cc : ((Klasa) c).getClassContents()){
                        if (cc instanceof Metod){
                            if(cc.getVidljivost().equals("+")){
                                temp1.setSelectedIndex(0);
                            }
                            else if(cc.getVidljivost().equals("-")){
                                temp1.setSelectedIndex(1);
                            }
                            else if(cc.getVidljivost().equals("#")){
                                temp1.setSelectedIndex(2);
                            }
                            else if(cc.getVidljivost().equals(" ")){
                                temp1.setSelectedIndex(3);
                            }
                            JComboBox<String> finalTemp3 = temp1;
                            temp1.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    cc.setVidljivost((String) finalTemp3.getSelectedItem());
                                }
                            });
                            jp2.add(temp1);
                            temp1 = new JComboBox<String>(v);
                            temp = new JTextField(cc.getTip());
                            jp2.add(temp);
                            JTextField finalTemp4 = temp;
                            temp.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    cc.setTip(finalTemp4.getText());
                                }
                            });
                            temp = new JTextField(cc.getNaziv());
                            JTextField finalTemp5 = temp;
                            temp.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    cc.setNaziv(finalTemp5.getText());
                                }
                            });
                            jp2.add(temp);
                            jp2.add(jbd);
                            JPanel finalJp = jp2;
                            jbd.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    ((Klasa) c).deleteClassContent(cc);
                                    metode.remove(finalJp);
                                    jsp.revalidate();
                                }
                            });
                            metode.add(jp2);
                            jbd = new JButton("Delete");
                            jp2 = new JPanel();
                            jp2.setLayout(new BoxLayout(jp2, BoxLayout.X_AXIS));

                        }
                    }
                    jsp.add(new JLabel("Add new method: "));
                    JComboBox<String> novaVidljivostM = new JComboBox<String>(v);
                    JTextField noviTipM = new JTextField("Type");
                    JTextField novNazivM = new JTextField("Name");
                    JButton jbM = new JButton("Save");
                    jp2.add(novaVidljivostM);
                    jp2.add(noviTipM);
                    jp2.add(novNazivM);
                    jp2.add(jbM);
                    jsp.add(jp2);
                    jbM.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Metod metod = new Metod((String) novaVidljivostM.getSelectedItem(), noviTipM.getText(), novNazivM.getText());
                            ((Klasa) c).addClassContent(metod);
                            metod.addSubscriber((ISubscriber) c);
                            JPanel jp3 = new JPanel();
                            jp3.setLayout(new BoxLayout(jp3, BoxLayout.X_AXIS));
                            JComboBox<String> j3 = new JComboBox<String>(v);
                            if(novaVidljivostM.getSelectedItem().equals("+")){
                                j3.setSelectedIndex(0);
                            }
                            else if(novaVidljivostM.getSelectedItem().equals("-")){
                                j3.setSelectedIndex(1);
                            }
                            else if(novaVidljivostM.getSelectedItem().equals("#")){
                                j3.setSelectedIndex(2);
                            }
                            else if(novaVidljivostM.getSelectedItem().equals(" ")){
                                j3.setSelectedIndex(3);
                            }
                            jp3.add(j3);
                            j3.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    metod.setVidljivost((String) j3.getSelectedItem());
                                }
                            });
                            JTextField t3 = new JTextField(noviTipM.getText());
                            JTextField finalT = t3;
                            t3.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    metod.setTip(finalT.getText());
                                }
                            });
                            jp3.add(t3);
                            t3 = new JTextField(novNazivM.getText());
                            JTextField finalT1 = t3;
                            t3.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    metod.setNaziv(finalT1.getText());
                                }
                            });
                            jp3.add(t3);
                            JButton del = new JButton("Delete");
                            jp3.add(del);
                            del.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {

                                    ((Klasa) c).deleteClassContent(metod);
                                    metode.remove(jp3);
                                    jsp.revalidate();
                                }
                            });
                            metode.add(jp3);
                            metode.revalidate();
                            jsp.revalidate();

                        }
                    });

                    scroll.setViewportView(jsp);

                    JOptionPane.showConfirmDialog(null, scroll, "Edit" , JOptionPane.DEFAULT_OPTION);


                } else if (c instanceof Enum) {
                    JTextField temp;
                    JPanel jp2 = new JPanel();
                    jp2.setLayout(new BoxLayout(jp2,BoxLayout.X_AXIS));
                    JButton jbd = new JButton("Delete");
                    JLabel mt = new JLabel("Elements: ");
                    jsp.add(mt);
                    JPanel elementi = new JPanel();
                    elementi.setLayout(new BoxLayout(elementi, BoxLayout.Y_AXIS));
                    jsp.add(elementi);
                    jp2 = new JPanel();
                    jp2.setLayout(new BoxLayout(jp2, BoxLayout.X_AXIS));
                    for (EnumElement cc: ((Enum) c).getEnumElements()){
                        if (cc instanceof EnumElement){
                            temp = new JTextField(cc.getNaziv());
                            JTextField finalTemp5 = temp;
                            temp.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    cc.setNaziv(finalTemp5.getText());
                                }
                            });
                            jp2.add(temp);
                            jp2.add(jbd);
                            JPanel finalJp = jp2;
                            jbd.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    ((Enum) c).deleteElement(cc);
                                    elementi.remove(finalJp);
                                    jsp.revalidate();
                                }
                            });
                            elementi.add(jp2);
                            jbd = new JButton("Delete");
                            jp2 = new JPanel();
                            jp2.setLayout(new BoxLayout(jp2, BoxLayout.X_AXIS));

                        }
                    }
                    jsp.add(new JLabel("Add new element: "));
                    JTextField novNazivM = new JTextField("Name");
                    JButton jbM = new JButton("Save");
                    jp2.add(novNazivM);
                    jp2.add(jbM);
                    jsp.add(jp2);
                    jbM.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            EnumElement element = new EnumElement(novNazivM.getText());
                            ((Enum) c).addElement(element);
                            element.addSubscriber((ISubscriber) c);
                            JPanel jp3 = new JPanel();
                            jp3.setLayout(new BoxLayout(jp3, BoxLayout.X_AXIS));
                            JTextField t3;
                            t3 = new JTextField(novNazivM.getText());
                            JTextField finalT1 = t3;
                            t3.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    element.setNaziv(finalT1.getText());
                                }
                            });
                            jp3.add(t3);
                            JButton del = new JButton("Delete");
                            jp3.add(del);
                            del.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    ((Enum) c).deleteElement(element);
                                    elementi.remove(jp3);
                                    jsp.revalidate();
                                }
                            });
                            elementi.add(jp3);
                            elementi.revalidate();
                            jsp.revalidate();

                        }
                    });

                    scroll.setViewportView(jsp);

                    JOptionPane.showConfirmDialog(null, scroll, "Edit" , JOptionPane.DEFAULT_OPTION);



                } else if (c instanceof Interfejs) {
                    JTextField temp;
                    JPanel jp2 = new JPanel();
                    jp2.setLayout(new BoxLayout(jp2,BoxLayout.X_AXIS));
                    JButton jbd = new JButton("Delete");
                    JLabel mt = new JLabel("Methods: ");
                    jsp.add(mt);
                    JPanel metode = new JPanel();
                    metode.setLayout(new BoxLayout(metode, BoxLayout.Y_AXIS));
                    jsp.add(metode);
                    jp2 = new JPanel();
                    jp2.setLayout(new BoxLayout(jp2, BoxLayout.X_AXIS));
                    for (Metod cc: ((Interfejs) c).getMetode()){
                        if (cc instanceof Metod){
                            temp = new JTextField(cc.getTip());
                            jp2.add(temp);
                            JTextField finalTemp4 = temp;
                            temp.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    cc.setTip(finalTemp4.getText());
                                }
                            });
                            temp = new JTextField(cc.getNaziv());
                            JTextField finalTemp5 = temp;
                            temp.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    cc.setNaziv(finalTemp5.getText());
                                }
                            });
                            jp2.add(temp);
                            jp2.add(jbd);
                            JPanel finalJp = jp2;
                            jbd.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    ((Interfejs) c).deleteMetod(cc);
                                    metode.remove(finalJp);
                                    jsp.revalidate();
                                }
                            });
                            metode.add(jp2);
                            jbd = new JButton("Delete");
                            jp2 = new JPanel();
                            jp2.setLayout(new BoxLayout(jp2, BoxLayout.X_AXIS));

                        }
                    }
                    jsp.add(new JLabel("Add new method: "));
                    JTextField noviTipM = new JTextField("Type");
                    JTextField novNazivM = new JTextField("Name");
                    JButton jbM = new JButton("Save");
                    jp2.add(noviTipM);
                    jp2.add(novNazivM);
                    jp2.add(jbM);
                    jsp.add(jp2);
                    jbM.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Metod metod = new Metod(" " ,noviTipM.getText(), novNazivM.getText());
                            ((Interfejs) c).addMetod(metod);
                            metod.addSubscriber((ISubscriber) c);
                            JPanel jp3 = new JPanel();
                            jp3.setLayout(new BoxLayout(jp3, BoxLayout.X_AXIS));
                            JTextField t3 = new JTextField(noviTipM.getText());
                            JTextField finalT = t3;
                            t3.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    metod.setTip(finalT.getText());
                                }
                            });
                            jp3.add(t3);
                            t3 = new JTextField(novNazivM.getText());
                            JTextField finalT1 = t3;
                            t3.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    metod.setNaziv(finalT1.getText());
                                }
                            });
                            jp3.add(t3);
                            JButton del = new JButton("Delete");
                            jp3.add(del);
                            del.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    ((Interfejs) c).deleteMetod(metod);
                                    metode.remove(jp3);
                                    jsp.revalidate();
                                }
                            });
                            metode.add(jp3);
                            metode.revalidate();
                            jsp.revalidate();

                        }
                    });

                    scroll.setViewportView(jsp);

                    JOptionPane.showConfirmDialog(null, scroll, "Edit" , JOptionPane.DEFAULT_OPTION);
                }

                List<ClassContent> posle = new ArrayList<>();

                if(c instanceof Klasa){
                    for (ClassContent cc : ((Klasa) c).getClassContents()){
                        posle.add((ClassContent) cc.clone());
                    }
                } else if (c instanceof Interfejs) {
                    for (Metod m : ((Interfejs) c).getMetode()){
                        posle.add((ClassContent) m.clone());
                    }
                } else if (c instanceof Enum) {
                    for (EnumElement ee : ((Enum) c).getEnumElements()){
                        posle.add((ClassContent) ee.clone());
                    }
                }
                Dimension posleDim = (Dimension) ((InterClass) c).getSize().clone();

                if (pre.size() == posle.size() && pre.size() == 0)
                    return;

                for (int i = 0; i < pre.size(); i++) {
                    if (pre.size() != posle.size())
                        break;
                    if (!pre.get(i).equals(posle.get(i)))
                        break;
                    if (i == pre.size() - 1)
                        return;
                }
                dv.getCommandManager().addCommand(new EditCommand((InterClass) c, pre, posle, preDim, posleDim));
            } else if (c instanceof Connection) {


                if (((Connection) c).getHitBox() == null)
                    ((Connection) c).calculateHitBox();

                if (((Connection) c).getHitBox().contains(p) && (c instanceof Agregacija || c instanceof Kompozicija)) {
                    JPanel jsp = new JPanel();
                    JPanel j1 = new JPanel();
                    JPanel j2 = new JPanel();
                    JLabel l1 = new JLabel("Enter name: ");
                    JLabel l2 = new JLabel("Select the multiplicity");
                    JTextField jtf = new JTextField(c.getName());
                    String options[] = {"Single", "List"};
                    JComboBox<String> jcb = new JComboBox<>(options);
                    if (((Connection) c).getKardinalitet() != null) {
                        if (((Connection) c).getKardinalitet().equals("List")) {
                            jcb.setSelectedIndex(1);
                        } else {
                            jcb.setSelectedIndex(0);
                        }
                    }
                    jsp.setLayout(new BoxLayout(jsp, BoxLayout.Y_AXIS));
                    j1.setLayout(new BoxLayout(j1, BoxLayout.X_AXIS));
                    j2.setLayout(new BoxLayout(j2, BoxLayout.X_AXIS));

                    j1.add(l1);
                    j1.add(jtf);
                    jsp.add(j1);
                    j2.add(l2);
                    j2.add(jcb);
                    jsp.add(j2);

                    List<String> connectionDataPre = new ArrayList<>();
                    List<String> connectionDataPosle = new ArrayList<>();

                    connectionDataPre.add(String.copyValueOf(c.getName().toCharArray()));
                    connectionDataPre.add(String.copyValueOf(((Connection) c).getKardinalitet() == null ? "Single".toCharArray() : ((Connection) c).getKardinalitet().toCharArray()));

                    jcb.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            ((Connection) c).setKardinalitet((String) jcb.getSelectedItem());
                        }
                    });

                    jtf.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            c.setName(jtf.getText());
                        }
                    });

                    JOptionPane.showConfirmDialog(null, jsp, "Edit", JOptionPane.DEFAULT_OPTION);

                    connectionDataPosle.add(String.copyValueOf(c.getName().toCharArray()));
                    connectionDataPosle.add(String.copyValueOf(((Connection) c).getKardinalitet().toCharArray()));

                    String nazivPosle = c.getName();


                    if (connectionDataPre.get(0).equals(connectionDataPosle.get(0)) && connectionDataPre.get(1).equals(connectionDataPosle.get(1))) {
                        break;
                    }

                    EditCommand ec = new EditCommand(null, null, null, null, null);
                    ec.setConnection((Connection) c);
                    ec.setConnectionDataPre(connectionDataPre);
                    ec.setConnectionDataPosle(connectionDataPosle);

                    dv.getCommandManager().addCommand(ec);
                    break;
                }
            }
        }
    }

    @Override
    public void misPovucen(MouseEvent e, DiagramView dv) {

    }

    @Override
    public void misOtpusten(MouseEvent e, DiagramView dv) {

    }
}
