
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import semanticdriftmetrics.Exceptions.OntologyCreationException;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

/**
 *
 * @author giuseppe
 */
public class SingleConceptSelectionJFrameProvaTate extends javax.swing.JFrame {
    public ArrayList<String> ontologiesToAnalyze;
    public String nameToAnalyze;
    /**
     * Creates new form NewJFrame1
     */
    public SingleConceptSelectionJFrameProvaTate() {
        initComponents();
        this.setLocationRelativeTo(null);
        groupButton();
        ontologiesToAnalyze=new ArrayList();
        nameToAnalyze="";
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jCheckBox5 = new javax.swing.JCheckBox();
        jCheckBox6 = new javax.swing.JCheckBox();
        jCheckBox7 = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jRadioButton3 = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jCheckBox1.setText("DBpedia 3.7");

        jCheckBox2.setText("DBpedia 3.8");

        jCheckBox3.setText("DBpedia 3.9");

        jCheckBox4.setText("DBpedia 2015-04");

        jCheckBox5.setText("DBpedia 2015-10");

        jCheckBox6.setText("DBpedia 2016-04");

        jCheckBox7.setText("DBpedia 2016-10");

        jButton1.setText("Confirm");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Please Select at least two DBpedia versions" }));
        jComboBox1.setToolTipText("");

        jLabel1.setText("Select DBpedia versions to perform Semantic Drift:");

        jButton3.setText("HOME");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton2.setText("Start");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel2.setText("");

        jRadioButton1.setText("Dynamic Compare");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        jRadioButton2.setText("Static Compare");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        jLabel4.setText("");

        jRadioButton3.setText("reverse");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(205, 205, 205)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(98, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(105, 105, 105)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jCheckBox7)
                                    .addComponent(jCheckBox6)
                                    .addComponent(jCheckBox5)
                                    .addComponent(jCheckBox4)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jCheckBox3)
                                        .addComponent(jCheckBox2)
                                        .addComponent(jCheckBox1))
                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jRadioButton1)
                                    .addComponent(jRadioButton2)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(29, 29, 29)
                                        .addComponent(jRadioButton3))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(54, 54, 54)
                                .addComponent(jLabel2))
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(110, 110, 110)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(jLabel1)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jButton3)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jRadioButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButton2)
                        .addGap(27, 27, 27)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(44, 44, 44))
        );

        ImageIcon image = new ImageIcon(new ImageIcon("./DBpediaLogoFull.png").getImage().getScaledInstance(125, 79, Image.SCALE_DEFAULT));
        jLabel4.setIcon(image);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        
        int loadIndex=0;
        
        
        
        String [] ontoPaths ={"/Users/giuseppe/Desktop/DBdrift/versions/2003/tate_2003.owl","/Users/giuseppe/Desktop/DBdrift/versions/2004/tate_2004.owl","/Users/giuseppe/Desktop/DBdrift/versions/2006/tate_2006.owl",
            "/Users/giuseppe/Desktop/DBdrift/versions/2007/tate_2007.owl","/Users/giuseppe/Desktop/DBdrift/versions/2011/tate_2011.owl","/Users/giuseppe/Desktop/DBdrift/versions/2012/tate_2012.owl","/Users/giuseppe/Desktop/DBdrift/versions/2013/tate_2013.owl"};
        
        boolean [] controlArray={jCheckBox1.isSelected(),jCheckBox2.isSelected(),jCheckBox3.isSelected(),jCheckBox4.isSelected(),jCheckBox5.isSelected(),jCheckBox6.isSelected(),jCheckBox7.isSelected()};    
        
        if(jRadioButton3.isSelected()){
            for(int i=controlArray.length-1;i>=0;i--){
                if (controlArray[i]){
                    ontologiesToAnalyze.add(ontoPaths[i]);
                    if (ontologiesToAnalyze.size()==1)
                        loadIndex=i;                                                
                }
            }
        }
        else{
            for(int i=0;i<controlArray.length;i++){
                if (controlArray[i]){
                    ontologiesToAnalyze.add(ontoPaths[i]);
                    if (ontologiesToAnalyze.size()==1)
                        loadIndex=i;                                                
                }
            }
        }
        
        if (ontologiesToAnalyze.size()<2){
            JOptionPane.showMessageDialog(rootPane, "Please Select at least two DBpedia versions");
            ontologiesToAnalyze.clear();
            return;
            
        }
        
          
        jCheckBox1.setEnabled(false);
        jCheckBox2.setEnabled(false);
        jCheckBox3.setEnabled(false);
        jCheckBox4.setEnabled(false);
        jCheckBox5.setEnabled(false);
        jCheckBox6.setEnabled(false);
        jCheckBox7.setEnabled(false);
        jRadioButton3.setEnabled(false);
//        jLabel2.setText("Select a Concept from DBpedia " + this.ontologiesToAnalyze.get(0).substring(this.ontologiesToAnalyze.get(0).lastIndexOf("_")+1,this.ontologiesToAnalyze.get(0).lastIndexOf(".")));

        jComboBox1.removeAllItems();
        

        
        int indexName=0;       
        MyOntologyManager ontologyA = new MyOntologyManager();
        try {
            ontologyA.loadFile(ontoPaths[loadIndex]);
        } catch (OntologyCreationException ex) {
            Logger.getLogger(SingleConceptSelectionJFrameProvaTate.class.getName()).log(Level.SEVERE, null, ex);
        }
        Set<MyConcept> allConcepts = ontologyA.getAllConcepts();

        for(MyConcept concept:allConcepts){
            String name = concept.getName();          
            indexName=name.lastIndexOf("/");       
            jComboBox1.addItem(name.substring(indexName+1));

            
        }
        jRadioButton1.setSelected(true);
        jButton1.setEnabled(false);
    
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        this.setVisible(false);       
        java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
            new StartJFrame().setVisible(true);

        }
        });
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        boolean dynamic=true;
        String firstOntology="";
        int indexOnt=0;
        String ontologies="";
        int result=0;
        
        if (ontologiesToAnalyze.size()<2){
            JOptionPane.showMessageDialog(rootPane, "Please Select at least two DBpedia versions");
            ontologiesToAnalyze.clear();
            return;            
        }     
        nameToAnalyze = jComboBox1.getSelectedItem().toString();
        
        for (int i=0;i<ontologiesToAnalyze.size();i++){
            indexOnt=ontologiesToAnalyze.get(i).lastIndexOf("/");
            if(i==0)
                firstOntology = ontologiesToAnalyze.get(i).substring(indexOnt+1);
            else    
            ontologies=ontologies+"\n"+ontologiesToAnalyze.get(i).substring(indexOnt+1);
        
        }
        result = JOptionPane.showConfirmDialog(null, 
        "Semantic drift will be perform on Concept \""+nameToAnalyze+"\" from "+ firstOntology+".\nSemantic drift will be on these DBpedia versions: "
                + ontologies+". \nAre you sure?","Message", JOptionPane.YES_NO_OPTION);
        if(result == JOptionPane.NO_OPTION) {           
            jButton1.setEnabled(true);
            ontologiesToAnalyze.clear();
            jComboBox1.removeAllItems();
            jComboBox1.addItem("Please Select at least two DBpedia versions");
            jCheckBox1.setEnabled(true);
            jCheckBox2.setEnabled(true);
            jCheckBox3.setEnabled(true);
            jCheckBox4.setEnabled(true);
            jCheckBox5.setEnabled(true);
            jCheckBox6.setEnabled(true);
            jCheckBox7.setEnabled(true);
            jRadioButton3.setEnabled(true);
            jLabel2.setText("");
        }
        else{            
            jButton1.setEnabled(false);
            jButton2.setEnabled(false);
            jButton3.setEnabled(false);
            jComboBox1.setEnabled(false);
            if (jRadioButton1.isSelected()){
                dynamic=true;
            }
            else
                dynamic=false;
            jRadioButton1.setEnabled(false);
            jRadioButton2.setEnabled(false);
            SearchInDifferentOntologyProvaTate search=new SearchInDifferentOntologyProvaTate(ontologiesToAnalyze,nameToAnalyze,dynamic);
            try {
                search.loadOntologies();
                
            } catch (OntologyCreationException ex) {
                Logger.getLogger(SingleConceptSelectionJFrameProvaTate.class.getName()).log(Level.SEVERE, null, ex);
            }       
            TablesVisualization tables = new TablesVisualization(search.conceptMatched,search.conceptToAnalyzeStatic,dynamic,firstOntology);
            tables.tablesView();
            this.setVisible(false);
        }          
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton2ActionPerformed
    
    
   
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SingleConceptSelectionJFrameProvaTate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SingleConceptSelectionJFrameProvaTate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SingleConceptSelectionJFrameProvaTate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SingleConceptSelectionJFrameProvaTate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SingleConceptSelectionJFrameProvaTate().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox6;
    private javax.swing.JCheckBox jCheckBox7;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    // End of variables declaration//GEN-END:variables

    private void groupButton() {
        ButtonGroup groupRadio=new ButtonGroup();
        groupRadio.add(jRadioButton1);
        groupRadio.add(jRadioButton2);
        }

   
}
