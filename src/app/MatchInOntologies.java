/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Set;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;
import semanticdriftmetrics.Aspects;
import semanticdriftmetrics.Constructors.Concept;
import semanticdriftmetrics.Constructors.OntProperty;
import semanticdriftmetrics.Exceptions.OntologyCreationException;

/**
 *
 * @author giuseppe
 */
public class MatchInOntologies {
   
    ArrayList<ArrayList<MyConcept>> conceptsMatchName;
    ArrayList<MyConcept> conceptFromOntologyAWithoutNameMatch;
    ArrayList<MyConcept> conceptFromOntologyBWithoutNameMatch;
    MyOntologyManager firstOntology;
    MyOntologyManager secondOntology;
    String firstOntologyPath;
    String secondOntologyPath;
    ArrayList<Node> resultsNode;
    
    public MatchInOntologies(String path1, String path2){   
        conceptsMatchName = new ArrayList<ArrayList<MyConcept>>();
        conceptFromOntologyAWithoutNameMatch = new ArrayList<MyConcept>();
        conceptFromOntologyBWithoutNameMatch = new ArrayList<MyConcept>();
        firstOntology = new MyOntologyManager();       
        secondOntology = new MyOntologyManager();
        firstOntologyPath=path1;
        secondOntologyPath=path2;
        resultsNode=new ArrayList();
        
    }
    public void loadOntologies() throws OntologyCreationException{
        firstOntology.loadFile(firstOntologyPath);
        secondOntology.loadFile(secondOntologyPath);   
    }
    
    public void searchAllConceptName() throws OntologyCreationException {
        boolean searchNameInOtherOntology=true;
        double wholeSimilarityValue=0.0;
        loadOntologies();
        createArrayListOntologyBWithoutNameMatch();
        Set<MyConcept> setOntologyConceptsA = firstOntology.getAllConcepts();
        for (MyConcept concept : setOntologyConceptsA) {
            searchNameInOtherOntology = searchNameInOtherOntology(concept);
            if (!searchNameInOtherOntology) {
                conceptFromOntologyAWithoutNameMatch.add(concept);
            }
        }      
        searchSimilarity();
        for (Node node:this.resultsNode){
            wholeSimilarityValue=wholeSimilarityValue+node.wholeSimilarityValue;
        }
        String similarityValue = Double.toString(wholeSimilarityValue/this.resultsNode.size());
        VersionsDriftResults results=new VersionsDriftResults(this.conceptFromOntologyAWithoutNameMatch,this.conceptFromOntologyBWithoutNameMatch,this.resultsNode,this.firstOntology,this.secondOntology);
        VennDiagrams.createVenn(this.firstOntology,this.secondOntology,this.conceptsMatchName.size(),similarityValue,results);
    }
    
    public void createArrayListOntologyBWithoutNameMatch() {
        Set<MyConcept> setOntologyConceptsB = secondOntology.getAllConcepts();
        for (MyConcept concept : setOntologyConceptsB) {
            conceptFromOntologyBWithoutNameMatch.add(concept);
            
        }                   
    }
    
    
    public boolean searchNameInOtherOntology(MyConcept conceptFromOntologyA) {
        ArrayList<MyConcept> conceptsWithSameName = new ArrayList<>();
        Set<MyConcept> setOntologyConceptsB = secondOntology.getAllConcepts();
        for (MyConcept concept : setOntologyConceptsB){
            if(concept.getName().equals(conceptFromOntologyA.getName())){
                conceptsWithSameName.add(conceptFromOntologyA);
                conceptsWithSameName.add(concept);
                conceptsMatchName.add(conceptsWithSameName);
                conceptFromOntologyBWithoutNameMatch.remove(concept);
                return true;
            }                   
        }
        return false;
    }
    
    public void searchSimilarity() {
      
        double nameSim=0.0;
        double labelSim = 0.0;
        double intSim = 0.0;
        double extSim = 0.0;
        double subSim = 0.0;
        double superSim = 0.0;
        double commentsSim = 0.0;
        double eqSim = 0.0;
        double whole = 0.0;  
        
        for(ArrayList<MyConcept> conceptSameName:this.conceptsMatchName) {    
            MyAspects asp = new MyAspects(conceptSameName.get(0), conceptSameName.get(1));
            nameSim = asp.nameCompare();
                if (!conceptSameName.get(0).getLabels().isEmpty() || !conceptSameName.get(1).getLabels().isEmpty())
                    labelSim = asp.label();  
                else
                    labelSim=-1;
                if (conceptSameName.get(0).getPropertiesAsDomain().isEmpty() && conceptSameName.get(1).getPropertiesAsDomain().isEmpty() && conceptSameName.get(0).getPropertiesAsRange().isEmpty() && conceptSameName.get(1).getPropertiesAsRange().isEmpty())
                    intSim = -1;
                else
                    intSim=asp.intensional();
                if (!conceptSameName.get(0).getSubClasses().isEmpty() || !conceptSameName.get(1).getSubClasses().isEmpty())
                    subSim = asp.subClasses();
                else
                    subSim=-1;
                if (!conceptSameName.get(0).getSuperClasses().isEmpty() || !conceptSameName.get(1).getSuperClasses().isEmpty())
                    superSim = asp.superClasses();
                else
                    superSim=-1;
                if (!conceptSameName.get(0).getComments().isEmpty() || !conceptSameName.get(1).getComments().isEmpty())    
                    commentsSim = asp.commentsMatch();
                else
                    commentsSim=-1;
                if (!conceptSameName.get(0).getEqClasses().isEmpty() || !conceptSameName.get(1).getEqClasses().isEmpty())
                    eqSim = asp.eqClasses();
                else
                    eqSim=-1;
                if (!conceptSameName.get(0).getInstances().isEmpty() || !conceptSameName.get(1).getInstances().isEmpty())
                    extSim = asp.eqClasses();
                else
                    extSim=-1;
                whole=asp.wholeConcepts();
            Node node=new Node(conceptSameName.get(0),nameSim,labelSim,intSim,subSim,superSim,commentsSim,eqSim,extSim,whole);
            resultsNode.add(node);        
     
        }
    }
    
}
