/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author giuseppe
 */
public class VersionsDriftResults {

        ArrayList<MyConcept> conceptFromOntologyAWithoutNameMatch;
        ArrayList<MyConcept> conceptFromOntologyBWithoutNameMatch; 
        ArrayList<Node> resultsNode; 
        MyOntologyManager firstOntology;
        MyOntologyManager secondOntology;
        
    
    
    

    VersionsDriftResults(ArrayList<MyConcept> conceptFromOntologyAWithoutNameMatch, ArrayList<MyConcept> conceptFromOntologyBWithoutNameMatch, ArrayList<Node> resultsNode, MyOntologyManager firstOntology, MyOntologyManager secondOntology) {
        this.conceptFromOntologyAWithoutNameMatch=conceptFromOntologyAWithoutNameMatch;
        this.conceptFromOntologyBWithoutNameMatch=conceptFromOntologyBWithoutNameMatch;
        this.resultsNode=resultsNode;
        this.firstOntology=firstOntology;
        this.secondOntology=secondOntology;               
    }
    
    public void createResults(){
        double wholeSimilarityValue=0.0;
        final JFrame frame = new JFrame("Versions Drift Results");
        //frame.setLayout(new GridLayout(2,1,15,15));        
        final JTextArea textArea=new JTextArea();
        textArea.setText("****************************\n\n");
        textArea.append("Name of First Ontology: "+firstOntology.getName().substring(firstOntology.getName().lastIndexOf("/")+1));
        textArea.append("\nNumber of Concepts: "+firstOntology.getAllConcepts().size());
        textArea.append("\n\n****************************\n\n");
        textArea.append("Name of Second Ontology: "+secondOntology.getName().substring(secondOntology.getName().lastIndexOf("/")+1));
        textArea.append("\nNumber of Concepts: "+secondOntology.getAllConcepts().size());
        textArea.append("\n\n****************************\n\n");
        textArea.append("Number of Concepts with same name in these ontologies: "+this.resultsNode.size());
        textArea.append("\nAverage Value of Similarity: ");
        for (Node node:this.resultsNode){
            wholeSimilarityValue=wholeSimilarityValue+node.wholeSimilarityValue;
        }
        textArea.append(Double.toString(wholeSimilarityValue/this.resultsNode.size()));
        textArea.append("\n\n****************************\n\n");
        textArea.append("Similarity final value between ontologies: "+ Double.toString(this.resultsNode.size()*(wholeSimilarityValue/this.resultsNode.size())/(this.firstOntology.getAllConcepts().size()+this.secondOntology.getAllConcepts().size())));
        textArea.append("\n\n****************************\n\n");
        textArea.append("Number of Concepts of first Ontology with no match in second Ontology: "+conceptFromOntologyAWithoutNameMatch.size());
        textArea.append("\n\n****************************\n\n");
        textArea.append("Number of Concepts of second Ontology with no match in first Ontology: "+conceptFromOntologyBWithoutNameMatch.size());
        textArea.append("\n\n****************************\n\n");
        textArea.append("Concepts with same name and their similarity values:");
        int i=1;
        for (Node node:this.resultsNode){
            textArea.append("\n\n- Concept number "+i+"\n\n");
            textArea.append("Concept: "+node.comparedConcept.getName().substring(node.comparedConcept.getName().lastIndexOf("/")+1));     
            textArea.append("\nURI of Concept: "+node.comparedConcept.getName());     
            if (node.labelsSimilarityValue!=-1)
                textArea.append("\nLabels Similarity: "+node.labelsSimilarityValue);
            else
                textArea.append("\nLabel Similarity: "+"-");
            if (node.intSimilarityValue!=-1)
                textArea.append("\nIntensional Similarity: "+node.intSimilarityValue);
            else
                textArea.append("\nIntensional Similarity: "+"-");
            if (node.subClassesSimilarityValue!=-1)
                textArea.append("\nSubclasses Similarity: "+node.subClassesSimilarityValue);
            else
                textArea.append("\nSubclasses Similarity: "+"-");
            if (node.superClassesSimilarityValue!=-1)
                textArea.append("\nSuperclasses Similarity: "+node.superClassesSimilarityValue);
            else
                textArea.append("\nSuperclasses Similarity: "+"-");
            if (node.commentsSimilarityValue!=-1)
                textArea.append("\nComment Similarity: "+node.commentsSimilarityValue);
            else
                textArea.append("\nComment Similarity: "+"-");
            if (node.eqClassesSimilarityValue!=-1)
                textArea.append("\nEQClasses Similarity: "+node.eqClassesSimilarityValue);
            else
                textArea.append("\nEQClasses Similarity: "+"-");
            if (node.extSimilarityValue!=-1)
                textArea.append("\nExtensional Similarity: "+node.extSimilarityValue);
            else
                textArea.append("\nExtensional Similarity: "+"-");
            textArea.append("\nAll-Aspects Similarity: "+node.wholeSimilarityValue);  
            i++;        
        }
        i=1;
        textArea.append("\n\n****************************\n\n");
        textArea.append("Concepts of first Ontology with no match in second Ontology: ");       
        for (MyConcept concept:this.conceptFromOntologyAWithoutNameMatch){
            textArea.append("\n\n- Concept number "+i+"\n\n");
            textArea.append("URI of Concept: "+concept.getName()); 
            i++;
        }
        
        i=1;
        textArea.append("\n\n****************************\n\n");
        textArea.append("Concepts of second Ontology with no match in first Ontology: ");       
        for (MyConcept concept:this.conceptFromOntologyBWithoutNameMatch){
            textArea.append("\n\n- Concept number "+i+"\n\n");
            textArea.append("URI of Concept: "+concept.getName()); 
            i++;
        }
        
        frame.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        JButton saveButton=new JButton("Save Results"); 
        c.gridx = 2;
        c.gridy = 0;
        frame.add(saveButton, c);
        
        JLabel imageLabel=new JLabel();        
        ImageIcon image = new ImageIcon(new ImageIcon("./DBpediaLogoFull.png").getImage().getScaledInstance(125, 79, Image.SCALE_DEFAULT));
        imageLabel.setIcon(image); 
        c.gridx = 0;
        c.gridy = 0;
        frame.add(imageLabel, c);
        JButton homeButton=new JButton("Home"); 
        c.gridx = 1;
        c.gridy = 0;
        frame.add(homeButton, c);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JScrollPane panelText = new JScrollPane(textArea);       
        c.ipady = 650;     
        c.weightx = 100.0;
        c.weighty = 300;
        c.gridwidth = 3;
        c.gridheight=2;
        c.gridx = 0;
        c.gridy = 1;
        c.anchor=GridBagConstraints.CENTER;
        frame.add(panelText, c);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
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
        
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter("Text File", "txt");
                final JFileChooser saveAsFileChooser = new JFileChooser();
                saveAsFileChooser.setApproveButtonText("Save");
                saveAsFileChooser.setFileFilter(extensionFilter);
                int actionDialog = saveAsFileChooser.showOpenDialog(frame);
                if (actionDialog != JFileChooser.APPROVE_OPTION) {
                   return;
                }
                File file = saveAsFileChooser.getSelectedFile();
                if (!file.getName().endsWith(".txt")) {
                   file = new File(file.getAbsolutePath() + ".txt");
                }
                BufferedWriter outFile = null;
                try {
                   outFile = new BufferedWriter(new FileWriter(file));
                   textArea.write(outFile);
                } catch (IOException ex) {
                   ex.printStackTrace();
                } finally {
                   if (outFile != null) {
                      try {
                         outFile.close();
                      } catch (IOException ex) {}
                   }
                }
            }
        });
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);  
    }
}
