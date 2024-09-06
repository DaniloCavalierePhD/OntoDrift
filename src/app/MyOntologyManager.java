/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import semanticdriftmetrics.Constructors.Concept;
import semanticdriftmetrics.Constructors.OntProperty;
import semanticdriftmetrics.Exceptions.OntologyCreationException;
import semanticdriftmetrics.OntologyManager;
import sun.reflect.annotation.AnnotationType;

/**
 *
 * @author giuseppe
 */
public class MyOntologyManager extends OntologyManager{
    //public ArrayList<OWLClass> subClasses;
    public Set<MyConcept> concepts;
    
    public MyOntologyManager(){
        concepts = new HashSet<>();
        
    }
    
    
    @Override
    public void loadFile(String path) throws OntologyCreationException{
        int indexLast = 0;
        try{
            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
            manager.setSilentMissingImportsHandling(true);
            File file = new File(path);
            ontology = manager.loadOntologyFromOntologyDocument(file);
            String IRI = ontology.getOntologyID().getOntologyIRI().toString();
            //name = IRI.substring(IRI.lastIndexOf("/")+1);
            indexLast = path.lastIndexOf("/");
            name = path.substring(indexLast+1);
            extractInformation();
        }catch(OWLOntologyCreationException ex){
            throw new OntologyCreationException(ex);
        }

    }
    
    @Override
        public void extractInformation(){
        Set<OWLClass> classes = ontology.getClassesInSignature();
        properties = extractProperties();
        individuals = extractIndividuals();
        
                
                
        for(OWLClass cls : classes){
            String cl = getShortForm(cls.getIRI().toString());
            ArrayList<String> labels;
            ArrayList<OntProperty> tripletsAsDomain = new ArrayList<>();
            ArrayList<OntProperty> tripletsAsRange = new ArrayList<>();
            ArrayList<String> instances = new ArrayList<>();
            ArrayList<OWLClass> owlSubClasses =new ArrayList<>();
            ArrayList<OWLClass> owlSuperClasses =new ArrayList<>();
            labels = extractLabels(cls);
            

            for(OntProperty tri : properties){
                if(tri.getDomain().equals(cl)){
                    tripletsAsDomain.add(tri);
                }
                if(tri.getRange().equals(cl)){
                    tripletsAsRange.add(tri);
                }
            }

            for(Map.Entry<String,String> individual : individuals){
                if(individual.getValue().equals(cls.toString())){
                    instances.add(getShortForm(individual.getKey()));
                }
            }
            owlSubClasses = extractSubclasses(cls); //only first generation
            
            ArrayList<OWLClass> owlSuperclasses = new ArrayList<>();
            owlSuperClasses = extractSuperclasses(cls,owlSuperclasses); //from father to root
            ArrayList<OWLAnnotationValue> owlComments = extractComment(cls);
            ArrayList<OWLClass> equivalentClasses = extractEquivalentClasses(cls);
            concepts.add(new MyConcept(cls.toString(), cl, labels, tripletsAsDomain, tripletsAsRange,instances,owlSubClasses,owlSuperClasses,owlComments,equivalentClasses));

            if(cls.getSuperClasses(ontology).isEmpty())
                tree.add(getLeaves(cls));

        }
        
    
    }

    public Set<MyConcept> getAllConcepts() {
        return concepts;
    }
    
    public ArrayList<OWLClass> extractSuperclasses(OWLClass cls,ArrayList<OWLClass> owlSuperclasses) {
        
        //ArrayList<OWLClass> owlSuperclasses = new ArrayList<>();
        Set<OWLClassExpression> expressions = cls.getSuperClasses(ontology);
        for(OWLClassExpression expr : expressions){
            if (!owlSuperclasses.contains(expr.asOWLClass())){
                owlSuperclasses.add(expr.asOWLClass());}
            extractSuperclasses(expr.asOWLClass(),owlSuperclasses);
            
        }
        return owlSuperclasses;
    }

    public ArrayList<OWLAnnotationValue> extractComment(OWLClass cls) {
        ArrayList<OWLAnnotationValue> comments=new ArrayList();
        OWLDataFactory df = OWLManager.getOWLDataFactory();
        Set<OWLAnnotation> expressions = cls.getAnnotations(ontology,df.getRDFSComment());
        for(OWLAnnotation expr : expressions){
            comments.add(expr.getValue());           
        }
        return comments;
    }    

    public ArrayList<OWLClass> extractEquivalentClasses(OWLClass cls) {
        ArrayList<OWLClass> eqClasses=new ArrayList();
        Set<OWLClassExpression> expressions = cls.getEquivalentClasses(ontology);
        for(OWLClassExpression expr : expressions){
            eqClasses.add(expr.asOWLClass());           
        }
        return eqClasses;
    }    
            
}
