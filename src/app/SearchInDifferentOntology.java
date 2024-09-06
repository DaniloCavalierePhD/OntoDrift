/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import semanticdriftmetrics.Exceptions.OntologyCreationException;

/**
 *
 * @author giuseppe
 */
public class SearchInDifferentOntology {
    
    ArrayList<String> ontologiesToLoad;
    ArrayList<MyOntologyManager> ontologies;
    String nameToAnalyze;
    MyConcept conceptToAnalyze;
    boolean dynamic=true;
    LinkedHashMap<String,PriorityQueue<Node>> conceptMatched;
    MyConcept conceptToAnalyzeStatic;
    
    
    

    SearchInDifferentOntology(ArrayList<String> ontologiesToLoad,String nameToAnalyze,boolean dynamic) {
        this.ontologiesToLoad=ontologiesToLoad;
        this.ontologies=new ArrayList();
        this.nameToAnalyze=nameToAnalyze;
        this.conceptToAnalyze=null;
        this.dynamic=dynamic;
        this.conceptMatched=new LinkedHashMap();
        this.conceptToAnalyzeStatic=null;
    }
    
    public void loadOntologies() throws OntologyCreationException{           
        for(String ontToLoad:ontologiesToLoad) { 
            MyOntologyManager ontology = new MyOntologyManager();
            ontology.loadFile(ontToLoad);
            ontologies.add(ontology);
        }    
        searchConceptInFirstOntology();
        //ChainCreator chain=new ChainCreator(this.conceptMatched);
        //chain.chainVisualization();
    }
    
    public void searchConceptInFirstOntology() {
        String string="";
        int indexName=0;
        Set<MyConcept> conceptsFromFirstOntology = ontologies.get(0).getAllConcepts();
        for(MyConcept concept:conceptsFromFirstOntology){           
            if(concept.getName().contains("/ontology/")){
            indexName = concept.getName().lastIndexOf("/");          
                if (nameToAnalyze.equalsIgnoreCase(concept.getName().substring(indexName+1))){
                    conceptToAnalyze=concept;
                    conceptToAnalyzeStatic=concept;
                    break;
                }
            }
        }    
        for(int j=1;j<ontologies.size();j++){
            searchSim(ontologies.get(j));       
        }
        
        //ChainCreator chain=new ChainCreator(this.conceptMatched);
        //chain.chainVisualization();
        
    }
    
    
    public void searchSim(MyOntologyManager ontologyToCompare) {
        
        Comparator<Node> comparator = new NodeComparator();
        PriorityQueue<Node> pQueue = new PriorityQueue<>(3,comparator);       
        
        double nameSim;
        double labelSim;
        double intSim;
        double subSim;
        double superSim;
        double commentsSim;
        double eqSim;
        double extSim;
        double whole;  
        
        Set<MyConcept> allConcepts = ontologyToCompare.getAllConcepts();        
        for (MyConcept conceptToCompare : allConcepts) {              
            if (conceptToCompare.getName().contains("/ontology/")){
                MyAspects asp = new MyAspects(conceptToAnalyze, conceptToCompare);  
                nameSim = asp.nameCompare();
                if (!conceptToAnalyze.getLabels().isEmpty() || !conceptToCompare.getLabels().isEmpty())
                    labelSim = asp.label();  
                else
                    labelSim=-1;
                if (conceptToAnalyze.getPropertiesAsDomain().isEmpty() && conceptToCompare.getPropertiesAsDomain().isEmpty() && conceptToAnalyze.getPropertiesAsRange().isEmpty() && conceptToCompare.getPropertiesAsRange().isEmpty())                
                    intSim=-1;
                else
                    intSim = asp.intensional();;
                if (!conceptToAnalyze.getSubClasses().isEmpty() || !conceptToCompare.getSubClasses().isEmpty())
                    subSim = asp.subClasses();
                else
                    subSim=-1;
                if (!conceptToAnalyze.getSuperClasses().isEmpty() || !conceptToCompare.getSuperClasses().isEmpty())
                    superSim = asp.superClasses();
                else
                    superSim=-1;
                if (!conceptToAnalyze.getComments().isEmpty() || !conceptToCompare.getComments().isEmpty())    
                    commentsSim = asp.commentsMatch();
                else
                    commentsSim=-1;
                if (!conceptToAnalyze.getEqClasses().isEmpty() || !conceptToCompare.getEqClasses().isEmpty())
                    eqSim = asp.eqClasses();
                else
                    eqSim=-1;
                if (!conceptToAnalyze.getInstances().isEmpty() || !conceptToCompare.getInstances().isEmpty())
                    extSim = asp.extensional();
                else
                    extSim=-1;
                
                whole = asp.wholeConcepts();
                if (pQueue.size()<3){ 
                    pQueue.offer(new Node(conceptToCompare,nameSim,labelSim,intSim,subSim,superSim,commentsSim,eqSim,extSim,whole)); 

                }     
                else if (pQueue.peek().getWholeSimilarityValue()<whole){
                    pQueue.poll();
                    pQueue.offer(new Node(conceptToCompare,nameSim,labelSim,intSim,subSim,superSim,commentsSim,eqSim,extSim,whole));                     
                }                           
            }
        }
        double maxSimilarity=0.0;
        if (dynamic){
            for(Node n:pQueue){               
                if(n.wholeSimilarityValue>maxSimilarity){
                    maxSimilarity=n.wholeSimilarityValue;
                    this.conceptToAnalyze=n.comparedConcept;
                }
            } 
            
        }         
        conceptMatched.put(ontologyToCompare.getName(), pQueue); 
        //System.out.println(conceptMatched.size());        
    }       
}
