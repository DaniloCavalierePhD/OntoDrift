/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.BorderFactory;
import static javax.swing.GroupLayout.Alignment.CENTER;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;
/**
 *
 * @author giuseppe
 */
public class VennDiagrams extends JPanel {

    
        String firstOntologyName;
        String secondOntologyName;
        int firstOntologySize;
        int secondOntologySize;
        int conceptsMatched;
        String similarityValue;
        VersionsDriftResults results;
        
        
    VennDiagrams(MyOntologyManager firstOntology, MyOntologyManager secondOntology, int conceptsMatched, String similarityValue, VersionsDriftResults results) {
        this.firstOntologyName=firstOntology.getName();
        this.secondOntologyName=secondOntology.getName();
        this.firstOntologySize=firstOntology.getAllConcepts().size();
        this.secondOntologySize=secondOntology.getAllConcepts().size();
        this.conceptsMatched=conceptsMatched;
        this.similarityValue=similarityValue;
        this.results=results;
        
    }
   
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;      
        int sizeOntologyA=0;
        int sizeOntologyB=0;
        int sizeConceptsMatched=0;
        if (this.firstOntologySize>=this.secondOntologySize){
            sizeOntologyA=500;
            sizeOntologyB=(int)this.secondOntologySize*500/this.firstOntologySize;
            sizeConceptsMatched=(int)this.conceptsMatched*500/this.firstOntologySize;
        }
        else{
            sizeOntologyB=500;
            sizeOntologyA=(int)this.firstOntologySize*500/this.secondOntologySize;
            sizeConceptsMatched=(int)this.conceptsMatched*500/this.secondOntologySize;
        }
        g2.setPaint(Color.WHITE);     
        g2.setStroke(new BasicStroke(2.0f));
        Ellipse2D firstSet=new Ellipse2D.Double(600, 20, sizeOntologyA, 250);
        Area area1=new Area(firstSet);
        g2.fill(area1);
        g2.draw(area1);
        Rectangle firstSetLegenda=new Rectangle(50,35,20,20);
        g2.fill(firstSetLegenda);       
        g2.setPaint(Color.DARK_GRAY);
        Ellipse2D secondSet=new Ellipse2D.Double(600+sizeOntologyA-sizeConceptsMatched, 20, sizeOntologyB,250);
        Area area2=new Area(secondSet);
        g2.fill(area2);
        g2.draw(area2);
        Rectangle secondSetLegenda=new Rectangle(50,85,20,20);
        g2.fill(secondSetLegenda);     
        g2.setColor(Color.ORANGE);
        area1.intersect(area2);
        g2.fill(area1);
        g2.draw(area1);
        Rectangle intersectionLegenda=new Rectangle(50,135,20,20);
        g2.fill(intersectionLegenda);      
        g2.setPaint(Color.BLACK);
        DecimalFormat df = new DecimalFormat("0.0000");
        g2.drawString("Set of Concepts from DBpedia "+this.firstOntologyName.substring(this.firstOntologyName.lastIndexOf("_")+1, this.firstOntologyName.lastIndexOf(".")) + " ("+this.firstOntologySize+" Concepts)",80,50);
        g2.drawString("Set of Concepts from DBpedia "+this.secondOntologyName.substring(this.secondOntologyName.lastIndexOf("_")+1, this.secondOntologyName.lastIndexOf(".")) + " ("+this.secondOntologySize+" Concepts)",80,100);
        g2.drawString("Set of Concepts with the same URI" + " ("+this.conceptsMatched+" Concepts)",80,150);
        g2.drawString("Overall similarity among versions (osim) without f value" + " = "+df.format(Double.parseDouble(this.similarityValue)),50,200);
        g2.drawString("Overall similarity among versions (osim) with f value" + " = "+df.format(Double.parseDouble(this.similarityValue)*this.conceptsMatched/(this.firstOntologySize+this.secondOntologySize)),50,250);
    }

    public static void createVenn(MyOntologyManager firstOntology, MyOntologyManager secondOntology, int conceptsMatched, String simValue, final VersionsDriftResults results) {
        final JFrame frame = new JFrame("DBpedia Versions Venn Diagrams");
        frame.setLayout(new GridLayout(2,1,20,20));
        JPanel panel = new JPanel(new FlowLayout());
        JLabel imageLabel=new JLabel();        
        ImageIcon image = new ImageIcon(new ImageIcon("./DBpediaLogoFull.png").getImage().getScaledInstance(125, 79, Image.SCALE_DEFAULT));
        imageLabel.setIcon(image);        
        panel.add(imageLabel);        
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JButton homeButton=new JButton("Home");
        JButton moreInfoButton=new JButton("More details");
        panel.add(homeButton);
        panel.add(moreInfoButton);
        VennDiagrams vennDiagrams = new VennDiagrams(firstOntology,secondOntology,conceptsMatched,simValue,results);
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "DBpedia Versions drift Results", TitledBorder.CENTER,TitledBorder.TOP));       
        frame.add(vennDiagrams);
        frame.add(panel);
        frame.pack();
        frame.setSize(new Dimension(1200, 600));       
        frame.setVisible(true);
        
        moreInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                results.createResults();
            }        
        });
        
        
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        new StartJFrame().setVisible(true);
                    }
                });          
            }
        });
    }
}
    
    

    

