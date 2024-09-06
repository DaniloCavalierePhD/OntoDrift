/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.util.ArrayList;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLClass;
import semanticdriftmetrics.Constructors.Concept;
import semanticdriftmetrics.Constructors.OntProperty;

/**
 *
 * @author giuseppe
 */
public class MyConcept extends Concept{
    private final ArrayList<OWLClass> subClasses;
    private final ArrayList<OWLClass> superClasses;
    private final ArrayList<OWLAnnotationValue> comments;
    private final ArrayList<OWLClass> eqClasses;
    public MyConcept(){
        subClasses=new ArrayList<>();
        superClasses=new ArrayList<>();
        comments=new ArrayList<>();
        eqClasses=new ArrayList<>();
  }
    public MyConcept(String anIRI, String aName, ArrayList<String> someLabels, ArrayList<OntProperty> somePropertiesAsDomain, ArrayList<OntProperty> somePropertiesAsRange, ArrayList<String> someInstances, ArrayList<OWLClass> someSubClasses,ArrayList<OWLClass> someSuperClasses,ArrayList<OWLAnnotationValue> someComments,ArrayList<OWLClass> someEqClasses){
      IRI = anIRI;
      name = aName;
      labels = someLabels;
      propertiesAsDomain = somePropertiesAsDomain;
      propertiesAsRange = somePropertiesAsRange;
      instances = someInstances;
      subClasses = someSubClasses;
      superClasses = someSuperClasses;
      comments = someComments;
      eqClasses = someEqClasses;
      instances = someInstances;
  }

    
    public ArrayList<OWLClass> getSubClasses() {
        return subClasses;
    }
    
    public ArrayList<OWLClass> getSuperClasses() {
        return superClasses;
    }
    
    public ArrayList<OWLAnnotationValue> getComments() {
        return comments;
    }
    
    public ArrayList<OWLClass> getEqClasses() {
        return eqClasses;
    }
}
