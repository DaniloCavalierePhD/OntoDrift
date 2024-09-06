/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

/**
 *
 * @author giuseppe
 */
public class TablesVisualization {
    
        LinkedHashMap<String, PriorityQueue<Node>> conceptMatched;
        ArrayList<MyConcept> conceptToAnalyze;
        boolean dynamic;
        ArrayList<String> versions;
        ArrayList<JTable> createTables;
        String firstOntology;

    TablesVisualization(LinkedHashMap<String, PriorityQueue<Node>> conceptMatched, MyConcept conceptToAnalyze, boolean dynamic, String firstOntology) {
         this.conceptMatched=conceptMatched;
         this.dynamic=dynamic;
         this.conceptToAnalyze=new ArrayList();
         this.conceptToAnalyze.add(conceptToAnalyze);
         this.versions=new ArrayList();
         createTables=new ArrayList();
         this.firstOntology=firstOntology;
    }
    
    public void tablesView(){
        int i=0;
        this.createTables = createTables();
        final JFrame frame = new JFrame("Tables");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(createTables.size()+1,1,15,15));        
        JPanel panelHeader = new JPanel();
        if(dynamic)
            panelHeader.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Dynamic Analysis - "+this.conceptToAnalyze.get(0).getName().substring(this.conceptToAnalyze.get(0).getName().lastIndexOf("/")+1)+" - DBpedia "+this.firstOntology.substring(this.firstOntology.lastIndexOf("_")+1, this.firstOntology.lastIndexOf(".")), TitledBorder.CENTER,TitledBorder.CENTER));
        else
           panelHeader.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Static Analysis - "+this.conceptToAnalyze.get(0).getName().substring(this.conceptToAnalyze.get(0).getName().lastIndexOf("/")+1)+" - DBpedia "+this.firstOntology.substring(this.firstOntology.lastIndexOf("_")+1, this.firstOntology.lastIndexOf(".")), TitledBorder.CENTER,TitledBorder.CENTER));
      
        panelHeader.setLayout(new FlowLayout());
        
        JLabel imageLabel=new JLabel();        
        ImageIcon image = new ImageIcon(new ImageIcon("./DBpediaLogoFull.png").getImage().getScaledInstance(125, 79, Image.SCALE_DEFAULT));
        imageLabel.setIcon(image);  
        JButton homeButton=new JButton("HOME");        
        JButton chainButton=new JButton("Semantic drift assessment");        
        panelHeader.add(imageLabel);
        panelHeader.add(chainButton);
        panelHeader.add(homeButton);
        
        frame.add(panelHeader);
        
        for(final JTable table:createTables){   
            JPanel panel = new JPanel();
            panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "DBpedia "+this.versions.get(i).substring(this.versions.get(i).lastIndexOf("_")+1, this.versions.get(i).lastIndexOf(".")), TitledBorder.CENTER,TitledBorder.TOP));
            JTableHeader header = table.getTableHeader();             
            panel.setLayout(new BorderLayout());
            panel.add(header, BorderLayout.NORTH);
            panel.add(table, BorderLayout.CENTER);
            table.addMouseListener(new MouseAdapter(){
                public void mouseClicked(MouseEvent evnt) {
                    String fromTable="";
                    TableModel model = table.getModel();                     
                    int selectedRow = table.getSelectedRow();                      
                    try{
                        fromTable=model.getValueAt(selectedRow,0).toString();
                    }catch(ArrayIndexOutOfBoundsException e){
                        System.out.println();
                    } 
                    int indexOf = createTables.indexOf(table);
                    String key1="";
                    if(dynamic && indexOf>0)
                       key1 = versions.get(indexOf-1);
                    else
                       key1 = firstOntology;                   
                    String key2 = versions.get(indexOf);
                    PriorityQueue<Node> nodes = conceptMatched.get(key2);
                    Node nodeToAnalyze=null;
                    for(Node n:nodes){                  
                        if(n.comparedConcept.getName().contains(fromTable)){
                            nodeToAnalyze=n;
                        }
                    }
                    if(!dynamic){    
                      SetsResultView start=new SetsResultView(conceptToAnalyze.get(0),nodeToAnalyze,key1,key2);
                      start.create();
                    }
                    else{
                        SetsResultView start=new SetsResultView(conceptToAnalyze.get(indexOf),nodeToAnalyze,key1,key2);
                        start.create();
                    }
                }
            });         
                table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
                @Override
                public Component getTableCellRendererComponent(JTable table,
                        Object value, boolean isSelected, boolean hasFocus, int row, int col) {

                    super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                    int rowToPaint=4;  
                    double status1 =  Double.parseDouble(table.getModel().getValueAt(0, 9).toString());
                    double status2 = Double.parseDouble(table.getModel().getValueAt(1, 9).toString());
                    double status3 = Double.parseDouble(table.getModel().getValueAt(2, 9).toString());

                    if (status3>=status1 && status3>=status2) {
                        rowToPaint=2;
                    }                                         
                    else 
                        if (status2>=status1 && status2>status3) {
                            rowToPaint=1;
                          }   
                        else
                            rowToPaint=0;
                    if (rowToPaint==row){
                        setBackground(Color.BLUE);
                        setForeground(Color.WHITE);
                    }
                    else{
                        setBackground(table.getBackground());
                        setForeground(table.getForeground());                 
                    }                
                    return this;
                }   
            });
                panel.add(table);
                frame.add(panel);
                i++;               
            }  
            chainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //frame.setVisible(false);
                ChainCreator chain=new ChainCreator(conceptMatched,conceptToAnalyze.get(0).getName(),dynamic,firstOntology);
                chain.chainVisualization();
                
                
                }
            });
            
            homeButton.addActionListener(new ActionListener() {
                
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new StartJFrame().setVisible(true);

                }
                });
                
            }
            });
            
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true); 
    }
    
    
    public ArrayList<JTable> createTables(){
        MyConcept maxConcept=null;
        double maxSimilarity=0.0;
        DecimalFormatSymbols symbol = new DecimalFormatSymbols();
	symbol.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("0.0000");
        df.setDecimalFormatSymbols(symbol);
        String []columnsName = {"Concept","URI","Labels","Intensional","SubClasses","SuperClasses","Comments","EQClasses","Extensional","All Aspects"};
        ArrayList<JTable> tables=new ArrayList();
        Set<Map.Entry<String, PriorityQueue<Node>>> entrySet = conceptMatched.entrySet();
        for(Map.Entry<String, PriorityQueue<Node>> node:entrySet){            
            versions.add(node.getKey());
            String[][] data = new String[3][10];
            int i=0;
            for(Node value:node.getValue()){
                data[i][0]=value.getNameComparedConcept().getName().substring(value.getNameComparedConcept().getName().lastIndexOf("/")+1);
                data[i][1]=df.format(value.getNameSimilarityValue());
                if (value.getLabelsSimilarityValue()==-1)
                    data[i][2]="-";
                else
                    data[i][2]=df.format(value.getLabelsSimilarityValue());
                if (value.getIntSimilarityValue()==-1)
                    data[i][3]="-";
                else
                    data[i][3]=df.format(value.getIntSimilarityValue());
                if (value.getSubClassesSimilarityValue()==-1)
                    data[i][4]="-";
                else
                    data[i][4]=df.format(value.getSubClassesSimilarityValue());
                if (value.getSuperClassesSimilarityValue()==-1)
                    data[i][5]="-";
                else
                    data[i][5]=df.format(value.getSuperClassesSimilarityValue());
                if (value.getCommentsSimilarityValue()==-1)
                    data[i][6]="-";
                else
                    data[i][6]=df.format(value.getCommentsSimilarityValue());
                if (value.getEqClassesSimilarityValue()==-1)
                    data[i][7]="-";
                else
                    data[i][7]=df.format(value.getEqClassesSimilarityValue());
                if (value.getExtSimilarityValue()==-1)
                    data[i][8]="-";
                else
                    data[i][8]=df.format(value.getExtSimilarityValue());              
               data[i][9]=df.format(value.getWholeSimilarityValue());            
               i++;
               
               if (dynamic){    
                    if(value.wholeSimilarityValue>maxSimilarity){
                        maxSimilarity=value.wholeSimilarityValue;
                        maxConcept = value.comparedConcept;      
                    }          
                }         
            }
            this.conceptToAnalyze.add(maxConcept);
            maxSimilarity=0.0;
            JTable table = new JTable(data,columnsName);
            table.getColumnModel().getColumn(0).setMinWidth(200);
            table.getColumnModel().getColumn(0).setMaxWidth(230);
            for(int j=1;j<=8;j++){
            table.getColumnModel().getColumn(j).setMinWidth(100);
            table.getColumnModel().getColumn(j).setMaxWidth(130);
            }
            tables.add(table);  
        }
        return tables;
    }
    
    
  
    
}
