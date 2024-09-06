/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semanticdriftmetrics;

import semanticdriftmetrics.Constructors.OntProperty;
import semanticdriftmetrics.Constructors.OntClass;
import semanticdriftmetrics.Constructors.Concept;
import semanticdriftmetrics.Exceptions.OntologyCreationException;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
//import org.semanticweb.owlapi.search.EntitySearcher;
import org.semanticweb.owlapi.util.SimpleIRIMapper;

/**
 *
 * @author andreadisst
 */
public class OntologyManager {
    
    public OWLOntology ontology;
    public Set<Concept> concepts;
    public ArrayList<OntProperty> properties;
    public java.util.List<java.util.Map.Entry<String,String>> individuals;
    public ArrayList<OntClass> tree;
    public OWLDataFactory factory;
    public String name;
    
    public OntologyManager(){
        concepts = new HashSet<>();
        properties = new ArrayList<>();
        factory = OWLManager.getOWLDataFactory();
        tree = new ArrayList<>();
    }
    
    public void loadOntology(OWLOntology ontology){
        this.ontology = ontology;
        String iri = ontology.getOntologyID().getOntologyIRI().toString();
        name = iri.substring(iri.lastIndexOf("/")+1);
        extractInformation();
    }
    
    public void loadURL(String URL) throws OntologyCreationException{
        try{
            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
            manager.setSilentMissingImportsHandling(true);
            IRI iri = IRI.create(URL);
            ontology = manager.loadOntologyFromOntologyDocument(iri);
            String IRI = ontology.getOntologyID().getOntologyIRI().toString();
            name = IRI.substring(IRI.lastIndexOf("/")+1);
            extractInformation();
        }catch(OWLOntologyCreationException ex){
            throw new OntologyCreationException(ex);
        }
    }
    
    public void loadFile(String path) throws OntologyCreationException{
        try{
            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
            manager.setSilentMissingImportsHandling(true);
            File file = new File(path);
            ontology = manager.loadOntologyFromOntologyDocument(file);
            String IRI = ontology.getOntologyID().getOntologyIRI().toString();
            name = IRI.substring(IRI.lastIndexOf("/")+1);
            extractInformation();
        }catch(OWLOntologyCreationException ex){
            throw new OntologyCreationException(ex);
        }
        
    }
    
    public void extractInformation(){
        Set<OWLClass> classes = ontology.getClassesInSignature();
        properties = extractProperties();
        individuals = extractIndividuals();
        
        for(OWLClass cls : classes){
            String cl = getShortForm(cls.getIRI().toString());
            //if(cl.equals("ComputerBased") || cl.equals("SoftwareBased") || cl.equals("MixedMedia")){ //TODO: remove in final version
                ArrayList<String> labels;
                ArrayList<OntProperty> tripletsAsDomain = new ArrayList<>();
                ArrayList<OntProperty> tripletsAsRange = new ArrayList<>();
                ArrayList<String> instances = new ArrayList<>();
                
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

                concepts.add(new Concept(cls.toString(), cl, labels, tripletsAsDomain, tripletsAsRange, instances));
                
                if(cls.getSuperClasses(ontology).isEmpty())
                    tree.add(getLeaves(cls));
            //}
        }
        
    
    }
    
    
    
    public OntClass getLeaves(OWLClass cls){
        String cl = getShortForm(cls.getIRI().toString());
        ArrayList<OntClass> subclasses = new ArrayList<>();
        
        ArrayList<OWLClass> owlSubclasses = extractSubclasses(cls);
        for(OWLClass subcls : owlSubclasses){
            subclasses.add(getLeaves(subcls));
        }
        
        ArrayList<OntProperty> allProperties = new ArrayList<>();
        for(OntProperty property : properties){
            if(property.getDomain().equals(cl) || property.getRange().equals(cl)){
                allProperties.add(property);
            }
        }
        
        return new OntClass(cls.toString(), cl, subclasses, allProperties);
    }
    
    public ArrayList<String> extractLabels(OWLClass cls){
        ArrayList<String> labels = new ArrayList<>();
        //owlapi 4.1 : EntitySearcher.getAnnotations(cls, ontology, factory.getRDFSLabel()
        for(OWLAnnotation a : cls.getAnnotations(ontology, factory.getRDFSLabel())) {
            OWLAnnotationValue val = a.getValue();
            if(val instanceof OWLLiteral) {
                labels.add(((OWLLiteral) val).getLiteral());
            }
        }
        return labels;
    }
    
    public ArrayList<OWLClass> extractSubclasses(OWLClass cls){
        ArrayList<OWLClass> owlSubclasses = new ArrayList<>();
        Set<OWLClassExpression> expressions = cls.getSubClasses(ontology);
        for(OWLClassExpression expr : expressions){
            owlSubclasses.add(expr.asOWLClass());
        }
        return owlSubclasses;
    }
    
    public ArrayList<OntProperty> extractProperties(){
        ArrayList<OntProperty> properties = new ArrayList<>();
        
        ArrayList<OntProperty> op = extractObjectProperties();
        properties.addAll(op);
        
        ArrayList<OntProperty> dp = extractDataProperties();
        properties.addAll(dp);
        
        return properties;
    }
    
    public ArrayList<OntProperty> extractObjectProperties(){
        ArrayList<OntProperty> objectProperties = new ArrayList<>();
        
        java.util.List<java.util.Map.Entry<String,String>> domainList= new java.util.ArrayList<>();
        java.util.List<java.util.Map.Entry<String,String>> rangeList= new java.util.ArrayList<>();
        
        for (OWLObjectPropertyDomainAxiom op : ontology.getAxioms(AxiomType.OBJECT_PROPERTY_DOMAIN)) {
            for (OWLClass domain : op.getDomain().getClassesInSignature()){
                domainList.add(new java.util.AbstractMap.SimpleEntry<>(op.getProperty().toString(),getShortForm(domain.getIRI().toString())));
            }
        }
        for (OWLObjectPropertyRangeAxiom op : ontology.getAxioms(AxiomType.OBJECT_PROPERTY_RANGE)) { 
            for (OWLClass range : op.getRange().getClassesInSignature()){
                rangeList.add(new java.util.AbstractMap.SimpleEntry<>(op.getProperty().toString(),getShortForm(range.getIRI().toString())));
            }
        }
        
        for (Map.Entry<String, String> domain : domainList){
            for (Map.Entry<String, String> range : rangeList){
                if( domain.getKey().equals(range.getKey()) ){
                    objectProperties.add(new OntProperty(getShortForm(domain.getKey()), domain.getValue(), range.getValue()));
                }
            }
        }
        
        return objectProperties;
    }
    
    public ArrayList<OntProperty> extractDataProperties(){
        ArrayList<OntProperty> dataProperties = new ArrayList<>();
        
        java.util.List<java.util.Map.Entry<String,String>> domainList= new java.util.ArrayList<>();
        java.util.List<java.util.Map.Entry<String,String>> rangeList= new java.util.ArrayList<>();
        
        for (OWLDataPropertyDomainAxiom dp : ontology.getAxioms(AxiomType.DATA_PROPERTY_DOMAIN)) {
            for (OWLClass domain : dp.getDomain().getClassesInSignature()){
                domainList.add(new java.util.AbstractMap.SimpleEntry<>(dp.getProperty().toString(),getShortForm(domain.getIRI().toString())));
            }
        }
        
        for (OWLDataPropertyRangeAxiom dp : ontology.getAxioms(AxiomType.DATA_PROPERTY_RANGE)) { 
            for (OWLDatatype range : dp.getRange().getDatatypesInSignature()){
                rangeList.add(new java.util.AbstractMap.SimpleEntry<>(dp.getProperty().toString(),range.toString()));
            }
        }
        
        for (Map.Entry<String, String> domain : domainList){
            for (Map.Entry<String, String> range : rangeList){
                if( domain.getKey().equals(range.getKey()) ){
                    dataProperties.add(new OntProperty(getShortForm(domain.getKey()), domain.getValue(), range.getValue()));
                }
            }
        }
        
        return dataProperties;
    }
    
    public java.util.List<java.util.Map.Entry<String,String>> extractIndividuals(){
        java.util.List<java.util.Map.Entry<String,String>> instances= new java.util.ArrayList<>();
        
        Set<OWLNamedIndividual> individuals = ontology.getIndividualsInSignature();
        for(OWLNamedIndividual individual : individuals){
            //owlapi 4.1 : EntitySearcher.getTypes(individual, ontology)
            Collection<OWLClassExpression> classes = individual.getTypes(ontology);
            for(OWLClassExpression cl : classes){
                instances.add(new java.util.AbstractMap.SimpleEntry<>(individual.toString(),cl.toString()));
            }
        }
        
        return instances;
    }
    
    public Set<Concept> getConcepts(){
        return concepts;
    }
    
    public String getName(){
        return name;
    }
    
    public ArrayList<OntClass> getTree(){
        return tree;
    }
    
    public String getShortForm(String iri){
        if(iri.contains("#")){
            iri = iri.substring(iri.lastIndexOf("#")+1);
        }
        iri = iri.replace(">", "");
        return iri;
    }
}
