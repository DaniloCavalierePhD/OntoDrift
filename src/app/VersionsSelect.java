package app;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import semanticdriftmetrics.Exceptions.OntologyCreationException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author giuseppe
 */
public class VersionsSelect {
    
    
    public  VersionsSelect(){}
    
    
    public void createComboBox(){
        final JComboBox comboBox1=new JComboBox();
        final JComboBox comboBox2=new JComboBox();
        comboBox1.addItem("DBpedia 3.7");
        comboBox1.addItem("DBpedia 3.8");
        comboBox1.addItem("DBpedia 3.9");
        comboBox1.addItem("DBpedia 2015-04");
        comboBox1.addItem("DBpedia 2015-10");
        comboBox1.addItem("DBpedia 2016-04");
        comboBox1.addItem("DBpedia 2016-10");
        comboBox2.addItem("DBpedia 3.7");
        comboBox2.addItem("DBpedia 3.8");
        comboBox2.addItem("DBpedia 3.9");
        comboBox2.addItem("DBpedia 2015-04");
        comboBox2.addItem("DBpedia 2015-10");
        comboBox2.addItem("DBpedia 2016-04");
        comboBox2.addItem("DBpedia 2016-10");
        
        final JFrame frame=new JFrame("Versions Semantic drift");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panelHeader = new JPanel(new FlowLayout());
        JLabel imageLabel=new JLabel();        
        ImageIcon image = new ImageIcon(new ImageIcon("./DBpediaLogoFull.png").getImage().getScaledInstance(125, 79, Image.SCALE_DEFAULT));
        imageLabel.setIcon(image);
        panelHeader.add(imageLabel);
        frame.add(panelHeader);
        JPanel panelComboBox = new JPanel(new FlowLayout());
        frame.setLayout(new GridLayout(3,1,15,15));
        panelComboBox.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Please Select two different DBpedia versions:", TitledBorder.CENTER,TitledBorder.TOP));      
        panelComboBox.add(comboBox1);
        panelComboBox.add(comboBox2);
        frame.add(panelComboBox);
        JPanel panelStart = new JPanel(new FlowLayout());
        JButton startButton=new JButton("  Start  ");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(comboBox1.getSelectedItem().toString().equals(comboBox2.getSelectedItem().toString())){
                    JOptionPane.showMessageDialog(null,"Please Select two different DBpedia versions");  
                    return;            
                }
                String pathToHome = null;
                File f = new File (pathToHome,"");
                File pdf = new File (f.getAbsolutePath());
                int end=pdf.toString().lastIndexOf("/");
                String pdf2=pdf.toString().substring(0, end);
                int end2=pdf2.toString().lastIndexOf("/");
                String path1= pdf2.toString()+"/DBpediaVersions/dbpedia_" + comboBox1.getSelectedItem().toString().substring(comboBox1.getSelectedItem().toString().lastIndexOf(" ")+1) +".owl";
                String path2= pdf2.toString()+"/DBpediaVersions/dbpedia_" + comboBox2.getSelectedItem().toString().substring(comboBox2.getSelectedItem().toString().lastIndexOf(" ")+1) +".owl";
                
                MatchInOntologies ontologiesDrift=new MatchInOntologies(path1,path2);
                try {
                    ontologiesDrift.searchAllConceptName();
                } catch (OntologyCreationException ex) {
                    Logger.getLogger(VersionsSelect.class.getName()).log(Level.SEVERE, null, ex);
                }
                frame.setVisible(false);
   }
});
        JButton homeButton=new JButton(" Home ");
        
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
            frame.setVisible(false);       
            java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StartJFrame().setVisible(true);
            }
            });
            }                       
        });
             
         
        panelStart.add(homeButton);
        panelStart.add(startButton);        
        frame.add(panelStart);
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);            
        }
    
}

