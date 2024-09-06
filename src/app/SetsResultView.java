package app;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.JViewport;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import semanticdriftmetrics.Constructors.OntProperty;

 public class SetsResultView{
     
    MyConcept conceptFirstOntology; 
    Node conceptSecondOntology;
    String nameFirstOntology;
    String nameSecondOntology;
    
    SetsResultView(MyConcept conceptFirstOntology, Node conceptSecondOntology, String nameFirstOntology, String nameSecondOntology) {
        this.conceptFirstOntology=conceptFirstOntology;
        this.conceptSecondOntology=conceptSecondOntology;
        this.nameFirstOntology=nameFirstOntology;
        this.nameSecondOntology=nameSecondOntology;
        
    }
     
    public static class JTextPaneCellEditor extends AbstractCellEditor implements TableCellEditor, KeyListener
    {
        private JViewport viewport;
        private JTable table;
        private int row;
        private JTextPane pane;

        public JTextPaneCellEditor(){
            viewport = new JViewport();
            pane = new JTextPane();
            viewport.add(pane);
            pane.addKeyListener(this);
        }

        @Override public Object getCellEditorValue(){
            return pane.getText();
        }

        @Override public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column){
            this.table = table;
            this.row = row;
            pane.setText(value.toString());
            int newHeight = (int)pane.getPreferredSize().getHeight();
            if(table.getRowHeight(row) < newHeight){
                table.setRowHeight(row, newHeight);
            }
            return pane;
        }

        public boolean isCellEditable(EventObject e){
            if (e instanceof MouseEvent) {
                return ((MouseEvent)e).getClickCount() >= 2;
            }
            return true;
        }

        @Override public void keyTyped(KeyEvent e){
            table.setRowHeight(row, (int)pane.getPreferredSize().getHeight());
        }

        @Override public void keyPressed(KeyEvent e){
            if(e.getKeyCode() == KeyEvent.VK_ENTER){
                stopCellEditing();
            }
        }

        @Override public void keyReleased(KeyEvent e){
        }

        
    }

    private static class JTextPaneCellRenderer extends JViewport implements TableCellRenderer{
        JTextPane pane;
        JTextPaneCellRenderer(){
            pane = new JTextPane();
            add(pane);
        }

        @Override 
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
            pane.setText(value.toString());
            table.setRowHeight(row, (int)pane.getPreferredSize().getHeight());
            return this;
        }
    }

    public void create(){
        
        String []columnsName={"Aspects","Similarity","Dbpedia "+this.nameFirstOntology.substring(this.nameFirstOntology.lastIndexOf("_")+1,this.nameFirstOntology.lastIndexOf(".")),"Dbpedia "+this.nameSecondOntology.substring(this.nameSecondOntology.lastIndexOf("_")+1,this.nameSecondOntology.lastIndexOf("."))};
        String[][] data = new String[10][4];
         
        int firstDomainSize=this.conceptFirstOntology.getPropertiesAsDomain().size();
        ArrayList<String> firstDomain=new ArrayList();
        for(OntProperty property:this.conceptFirstOntology.getPropertiesAsDomain()){
            firstDomain.add("<"+property.getDomain().substring(property.getDomain().lastIndexOf("/")+1)+" - "+property.getName().substring(property.getName().lastIndexOf("/")+1)+" - "+property.getRange().substring(property.getRange().lastIndexOf("/")+1));
       }
        
        int firstRangeSize=this.conceptFirstOntology.getPropertiesAsRange().size();
   
        ArrayList<String> firstRange=new ArrayList();
        for(OntProperty property:this.conceptFirstOntology.getPropertiesAsRange()){
            firstRange.add("<"+property.getDomain().substring(property.getDomain().lastIndexOf("/")+1)+" - "+property.getName().substring(property.getName().lastIndexOf("/")+1)+" - "+property.getRange().substring(property.getRange().lastIndexOf("/")+1));
          
        }
        
        ArrayList<String> secondDomain=new ArrayList();
        for(OntProperty property:this.conceptSecondOntology.comparedConcept.getPropertiesAsDomain()){
            secondDomain.add("<"+property.getDomain().substring(property.getDomain().lastIndexOf("/")+1)+" - "+property.getName().substring(property.getName().lastIndexOf("/")+1)+" - "+property.getRange().substring(property.getRange().lastIndexOf("/")+1));
        }

        ArrayList<String> secondRange=new ArrayList();
        for(OntProperty property:this.conceptSecondOntology.comparedConcept.getPropertiesAsRange()){
            secondRange.add("<"+property.getDomain().substring(property.getDomain().lastIndexOf("/")+1)+" - "+property.getName().substring(property.getName().lastIndexOf("/")+1)+" - "+property.getRange().substring(property.getRange().lastIndexOf("/")+1));
        }
        
        
        data[0][0]="Concept:";
        data[1][0]="URI:";
        data[2][0]="Label:";
        data[3][0]="Intensional:\n(Domain & Range)";
        data[4][0]="Sub Classes:";
        data[5][0]="Super Classes:";
        data[6][0]="Comments:";
        data[7][0]="EQ Classes:";
        data[8][0]="Extensional:";
        data[9][0]="All aspects:";
        
        
        data[0][2]=this.conceptFirstOntology.getName().substring(this.conceptFirstOntology.getName().lastIndexOf("/")+1);             
        data[1][2]=this.conceptFirstOntology.getIRI().substring(1,this.conceptFirstOntology.getIRI().length()-1);
        data[2][2]=this.conceptFirstOntology.getLabels().toString()+"\n\n";
        data[3][2]="Domain:\n"+firstDomain.toString()+"\nRange:\n"+firstRange.toString()+"\n\n\n\n\n\n";
        data[4][2]=this.conceptFirstOntology.getSubClasses().toString()+"\n";
        data[5][2]=this.conceptFirstOntology.getSuperClasses().toString()+"\n\n";
        data[6][2]=this.conceptFirstOntology.getComments().toString()+"\n\n\n\n\n";
        data[7][2]=this.conceptFirstOntology.getEqClasses().toString()+"\n\n";
        data[8][2]=this.conceptFirstOntology.getInstances().toString()+"\n\n";
        data[9][2]="-";
               
        
        data[0][3]=this.conceptSecondOntology.comparedConcept.getName().substring(this.conceptSecondOntology.comparedConcept.getName().lastIndexOf("/")+1);        
        data[1][3]=this.conceptSecondOntology.comparedConcept.getIRI().substring(1,this.conceptSecondOntology.comparedConcept.getIRI().length()-1);
        data[2][3]=this.conceptSecondOntology.comparedConcept.getLabels().toString();
        data[3][3]="Domain:\n"+secondDomain.toString()+"\nRange:\n"+secondRange.toString();
        data[4][3]=this.conceptSecondOntology.comparedConcept.getSubClasses().toString();
        data[5][3]=this.conceptSecondOntology.comparedConcept.getSuperClasses().toString();
        data[6][3]=this.conceptSecondOntology.comparedConcept.getComments().toString();
        data[7][3]=this.conceptSecondOntology.comparedConcept.getEqClasses().toString(); 
        data[8][3]=this.conceptSecondOntology.comparedConcept.getInstances().toString();
        data[9][3]="-";
        
        DecimalFormat df = new DecimalFormat("0.0000");
        data[0][1]="-";     
        data[1][1]=df.format(this.conceptSecondOntology.nameSimilarityValue);
        if(this.conceptSecondOntology.labelsSimilarityValue==-1)
            data[2][1]="-";
        else
            data[2][1]=df.format(this.conceptSecondOntology.labelsSimilarityValue);
        
        if(this.conceptSecondOntology.intSimilarityValue==-1)
            data[3][1]="-";
        else
        data[3][1]=df.format(this.conceptSecondOntology.intSimilarityValue);
        
        if(this.conceptSecondOntology.subClassesSimilarityValue==-1)
            data[4][1]="-";
        else
        data[4][1]=df.format(this.conceptSecondOntology.subClassesSimilarityValue);
        
        if(this.conceptSecondOntology.superClassesSimilarityValue==-1)
            data[5][1]="-";
        else
        data[5][1]=df.format(this.conceptSecondOntology.superClassesSimilarityValue);
        
        if(this.conceptSecondOntology.commentsSimilarityValue==-1)
            data[6][1]="-";
        else
        data[6][1]=df.format(this.conceptSecondOntology.commentsSimilarityValue);
        
        if(this.conceptSecondOntology.eqClassesSimilarityValue==-1)
            data[7][1]="-";
        else
        data[7][1]=df.format(this.conceptSecondOntology.eqClassesSimilarityValue);
        
        if(this.conceptSecondOntology.extSimilarityValue==-1)
            data[8][1]="-";
        else
        data[8][1]=df.format(this.conceptSecondOntology.extSimilarityValue);
        
        data[9][1]=df.format(this.conceptSecondOntology.wholeSimilarityValue);
 
        
        final JTable table=new JTable(data,columnsName);
        
        
        table.setDefaultRenderer(Object.class, new JTextPaneCellRenderer());
        table.setDefaultEditor(Object.class, new JTextPaneCellEditor());
        table.setShowGrid(true);
        table.getColumnModel().getColumn(0).setMinWidth(200);
        table.getColumnModel().getColumn(0).setMaxWidth(230);
        table.getColumnModel().getColumn(1).setMaxWidth(100);


        final JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        JPanel panelHeader=new JPanel();
        panelHeader.setLayout(new FlowLayout());        
        
        JButton closeButton=new JButton("Return to Tables");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);                              
            }
        });
        JLabel imageLabel=new JLabel();        
        ImageIcon image = new ImageIcon(new ImageIcon("./DBpediaLogoFull.png").getImage().getScaledInstance(125, 79, Image.SCALE_DEFAULT));
        imageLabel.setIcon(image);
        panelHeader.add(imageLabel);
        JPanel panel=new JPanel();

        panel.add(closeButton);

        JPanel panelTable = new JPanel();
        JTableHeader header = table.getTableHeader(); 
        panelTable.setLayout(new BorderLayout());
        panelTable.add(header, BorderLayout.NORTH);
        panelTable.add(table, BorderLayout.CENTER);
        panelTable.add(table);
        frame.add(panelHeader,BorderLayout.NORTH);
        frame.add(panelTable,BorderLayout.CENTER);       
        frame.add(panel,BorderLayout.SOUTH);
        //test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.setSize(2000, 1000);//table.getSize().height+500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        /*
        for(int k=0;k<10;k++){
            System.out.println(data[k][0]);
            System.out.println(data[k][1]);
            System.out.println(data[k][2]);
            System.out.println(data[k][3]+"\n");
        }*/
        
    }
}