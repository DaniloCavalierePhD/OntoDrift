/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import static com.github.jsonldjava.core.JsonLdProcessor.frame;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.TreeLayout;
import edu.uci.ics.jung.graph.DelegateForest;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.OrderedSparseMultigraph;
import edu.uci.ics.jung.graph.DirectedOrderedSparseMultigraph;
import edu.uci.ics.jung.graph.Forest;
import edu.uci.ics.jung.graph.DelegateTree;
import edu.uci.ics.jung.graph.event.GraphEvent;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.VisualizationModel;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.DefaultVertexLabelRenderer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Paint;
import java.awt.PaintContext;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import static javafx.scene.input.KeyCode.V;
import static javafx.scene.paint.Color.color;
import static javafx.scene.paint.Color.color;
import static javafx.scene.paint.Color.color;
import static javafx.scene.paint.Color.color;
import static javafx.scene.paint.Color.color;
import static javafx.scene.paint.Color.color;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import org.apache.commons.collections15.Transformer;


/**
 *
 * @author giuseppe
 */
public class ChainCreator {
    LinkedHashMap<String, PriorityQueue<Node>> conceptMatched;
    String nameToAnalyze;
    boolean dynamic;
    String firstOntology;
    
    
    ChainCreator(LinkedHashMap<String, PriorityQueue<Node>> conceptMatched,String nameToAnalyze,boolean dynamic,String firstOntology) {
        this.conceptMatched=conceptMatched;
        this.nameToAnalyze=nameToAnalyze;
        this.dynamic=dynamic;
        this.firstOntology=firstOntology;
    }
    
    
    public void chainVisualization(){
        final ArrayList <Integer> roots=new ArrayList();
        final ArrayList <Integer> toDelete=new ArrayList();
        final ArrayList <String> labels=new ArrayList();
        int i=1;
        double maxSimilarity=0.0;
        String root="";
        int radice=0;
        int tmp=0;
        System.out.println(conceptMatched.size());
        final Graph<Integer, Node> g= new DelegateForest<Integer, Node>(); 
        nameToAnalyze=nameToAnalyze.substring(nameToAnalyze.lastIndexOf("/")+1);
        labels.add(nameToAnalyze);
        g.addVertex((Integer)0);
        roots.add(0);
        Set<Map.Entry<String, PriorityQueue<Node>>> entrySet = conceptMatched.entrySet();
        for(Map.Entry<String, PriorityQueue<Node>> e:entrySet){
            System.out.println("key: "+e.getKey()+"\nvalues: ");
            for(Node n:e.getValue()){
                g.addVertex((Integer)i);
                g.addEdge(n,radice,i);
                labels.add(n.comparedConcept.getName().substring(n.comparedConcept.getName().lastIndexOf("/")+1));
                //g.addVertex(n.comparedConcept.getName().substring(n.comparedConcept.getName().lastIndexOf("/")+1) + "(" + e.getKey().substring(0, e.getKey().lastIndexOf("."))+")");
                //g.addEdge(n,nameToAnalyze,n.comparedConcept.getName().substring(n.comparedConcept.getName().lastIndexOf("/")+1) + "(" + e.getKey().substring(0, e.getKey().lastIndexOf("."))+")");
                
                if (n.wholeSimilarityValue>maxSimilarity){
                    root=n.comparedConcept.getName().substring(n.comparedConcept.getName().lastIndexOf("/")+1);
                    maxSimilarity=n.wholeSimilarityValue;
                    tmp=i;
                }
                i++;
            }           
            maxSimilarity=0.0;
            roots.add(tmp);
            if(dynamic)
                radice=tmp;           
        }
        
        // nodi "legenda"
        labels.add(firstOntology.substring(0,firstOntology.lastIndexOf(".")));
        g.addVertex((Integer) i);
        i++;
        Set<String> keySet = conceptMatched.keySet();
        for(String key:keySet){
            labels.add(key.substring(0,key.lastIndexOf(".")));
            g.addVertex((Integer) i);
            i++;
        }
        Layout<Integer, Node> layout; //create a layout
        layout = new TreeLayout<Integer, Node>((Forest<Integer, Node>) g,160,120);
        
        if (dynamic){          
            Point2D pd=new Point2D.Double(50, 300);
            layout.setLocation(0, pd);
            int j=1;
            int z=1;
            int k=0;
            for(j=1;j<((keySet.size()))*3;j=j+3){
                Point2D pd1=new Point2D.Double(225*z, 100);
                layout.setLocation(j, pd1);
                Point2D pd2=new Point2D.Double(225*z, 300);
                layout.setLocation(j+1, pd2);
                Point2D pd3=new Point2D.Double(225*z, 500);
                layout.setLocation(j+2, pd3);
                z++;
            }
           
            j=keySet.size()*3;
            z=1;
            Point2D pd4=new Point2D.Double(30, 250);
            layout.setLocation(j+1, pd4);
            toDelete.add(j+1);           
            for(k=j+2;k<j+2+keySet.size();k++){
                Point2D pd5=new Point2D.Double(225*z-50, 50);
                layout.setLocation(k, pd5);
                toDelete.add(k);
                z++;
            }
            g.addVertex(k);
            labels.add("Vertex with Max Similarity in its DBpedia Version");
            g.addVertex(k+1);
            labels.add("Vertex without Max Similarity in its DBpedia Version");
            roots.add(k);
            Point2D pd6=new Point2D.Double(700, 610);
            layout.setLocation(k, pd6);
            Point2D pd7=new Point2D.Double(990, 610);
            layout.setLocation(k+1, pd7);
        }
        else{
            Point2D pd=new Point2D.Double(765, 510);
            layout.setLocation(0, pd);
            int z=1;
            int k=1;
            int j=1;
            for(j=1;j<((keySet.size())*3);j=j+3){
                if(j%2!=0){
                    Point2D pd1=new Point2D.Double(250, 50*z);
                    layout.setLocation(j, pd1);
                    Point2D pd2=new Point2D.Double(250, 50*z+50);
                    layout.setLocation(j+1, pd2);
                    Point2D pd3=new Point2D.Double(250, 50*z+100);
                    layout.setLocation(j+2, pd3);
                }
                else{
                    Point2D pd1=new Point2D.Double(1150, 50*z);
                    layout.setLocation(j, pd1);
                    Point2D pd2=new Point2D.Double(1150, 50*z+50);
                    layout.setLocation(j+1, pd2);
                    Point2D pd3=new Point2D.Double(1150, 50*z+100);
                    layout.setLocation(j+2, pd3);
                    z=z+4;
                }   
            }
            j=keySet.size()*3;
            z=1;
            Point2D pd4=new Point2D.Double(715, 460);
            layout.setLocation(j+1, pd4);
            toDelete.add(j+1); 
            int control=0;
            for(k=j+2;k<j+2+keySet.size();k++){
                if(control%2==0){
                    Point2D pd5=new Point2D.Double(250, 50*z-40);
                    layout.setLocation(k, pd5);
                }
                else{
                    Point2D pd5=new Point2D.Double(1150, 50*z-40);
                    layout.setLocation(k, pd5);                   
                    z=z+4;
                }
                control++;
                toDelete.add(k);            
            }
            g.addVertex(k);
            labels.add("Vertex with Max Similarity in its DBpedia Version");
            g.addVertex(k+1);
            labels.add("Vertex without Max Similarity in its DBpedia Version");
            roots.add(k);
            Point2D pd6=new Point2D.Double(700, 610);
            layout.setLocation(k, pd6);
            Point2D pd7=new Point2D.Double(990, 610);
            layout.setLocation(k+1, pd7);
            }
        
        
        
          
        
        
        
        
        /*for(int j=1;j<((keySet.size()+1)*3);j=j+3){
            Point2D pd1=new Point2D.Double(225*z, 150);
            layout.setLocation(j, pd1);
            Point2D pd2=new Point2D.Double(225*z+25, 300);
            layout.setLocation(j+1, pd2);
            Point2D pd3=new Point2D.Double(225*z+50, 450);
            layout.setLocation(j+2, pd3);
            z++;
        }*/
        //Layout<Integer, String> layout = new CircleLayout(g);
        //layout.setSize(new Dimension(600,600)); // sets the initial size of the space
        //BasicVisualizationServer<Integer,String> vv =new BasicVisualizationServer<Integer,String>((VisualizationModel<Integer, String>) layout); 
        VisualizationViewer<Integer, Node> vv = new VisualizationViewer<Integer, Node>(layout);
        //vv.add(label);
        //label.setBounds(100, 100, 1000, 200);
       // JLabel label2=new JLabel("esempio2");
        //vv.add(label2);
        
        
        //vv.setPreferredSize(new Dimension(650,650)); //Sets the viewing area size
        
        vv.getRenderContext().setVertexLabelTransformer((new ToStringLabeller(){
        @Override
        public String transform(Object v) {

            return labels.get((int)v);
        }}));
        
        vv.getRenderContext().setEdgeLabelTransformer((new ToStringLabeller(){
        @Override
        public String transform(Object v) {
            double wholeSimilarityValue = ((Node)v).wholeSimilarityValue;
            DecimalFormat df = new DecimalFormat("0.0000");
            String str = df.format(wholeSimilarityValue);
            return str;
        }}));
        
        Transformer<Integer, Paint> vertexPaintRed = new Transformer<Integer, Paint>() {
            @Override
            public Paint transform(Integer i) {
                if(roots.contains(i)){
                    return Color.RED;
                }
                return Color.WHITE;
            }
        };
        
        Transformer<Integer, Shape> vertexShapeChange = new Transformer<Integer, Shape>() {
            public Shape transform(Integer i){
       
                Ellipse2D circle = new Ellipse2D.Double(-15, -15, 20, 20);
                // in this case, the vertex is twice as large
                if(toDelete.contains(i)) 
                    return AffineTransform.getScaleInstance(0, 0).createTransformedShape(circle);
                else 
                    return circle;
            }
                
           
        };
        vv.getRenderContext().setVertexShapeTransformer(vertexShapeChange);
        
      
        vv.setPreferredSize(new Dimension(1500,1000)); //Sets the viewing area size
        vv.getRenderContext().setVertexFillPaintTransformer(vertexPaintRed);
        final JFrame frame = new JFrame("DBdrift Graph View");
        JPanel panel = new JPanel();
        JPanel panelButtons = new JPanel();
        JPanel panelLabel = new JPanel();
        JLabel imageLabel=new JLabel();        
        ImageIcon image = new ImageIcon(new ImageIcon("./DBpediaLogoFull.png").getImage().getScaledInstance(125, 79, Image.SCALE_DEFAULT));
        imageLabel.setIcon(image);
        panelLabel.add(imageLabel);
        JButton returnButton=new JButton("Return to Tables");   
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
            frame.setVisible(false);       
            }                       
        });
        panel.add(vv);
        panel.setSize(2000, 500);
        panelButtons.setLayout(new FlowLayout());
        panelButtons.setSize(2000, 200);
        panelButtons.add(returnButton);
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"", TitledBorder.CENTER,TitledBorder.TOP));
         
        frame.setLayout(new BorderLayout());
        frame.add(panelLabel,BorderLayout.NORTH);
        frame.add(panel,BorderLayout.CENTER);
        frame.add(panelButtons,BorderLayout.SOUTH);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        //frame.getContentPane().add(vv);
        //frame.getContentPane().add(label);
        frame.pack();
        frame.setVisible(true);




    }


}