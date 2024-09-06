/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.util.ArrayList;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLClass;
import semanticdriftmetrics.Aspects;
import semanticdriftmetrics.Constructors.Concept;
import semanticdriftmetrics.Simmetrics;

/**
 *
 * @author giuseppe
 */
public class MyAspects extends Aspects {
        
    MySimmetrics mySim;
    double averageValue;
    int sizeTotal;
    MyConcept one;
    MyConcept two;    
    
    public MyAspects(MyConcept one, MyConcept two){
        mySim = new MySimmetrics();
        averageValue=0.0;
        sizeTotal=0;
        this.one=one;
        this.two=two;
    }
    
    public double nameCompare(){
        double value=0.0;      
        if(one.getIRI().equals(two.getIRI()))
            value=1.0;       
        sizeTotal=sizeTotal+2;
        averageValue=averageValue+(value*2);       
        return value;
    }
    
    
    public double label(){
        double jaccardValue=0.0;       
        if(!one.getLabels().isEmpty() || !two.getLabels().isEmpty())               
            jaccardValue = mySim.JaccardToStringSets(one.getLabels(), two.getLabels()); //compare whole set label
        sizeTotal=sizeTotal+one.getLabels().size()+two.getLabels().size();
        averageValue=averageValue+((one.getLabels().size()+two.getLabels().size())*jaccardValue);       
        return jaccardValue;
    }
    
    
   
    public double intensional(){       
        double valueFinal=0.0;     
        double jaccardAsDomain=0.0;
        double jaccardAsRange=0.0;
        
        if(!one.getPropertiesAsDomain().isEmpty() || !two.getPropertiesAsDomain().isEmpty()){
            jaccardAsDomain = mySim.JaccardToTriplets(one.getPropertiesAsDomain(), two.getPropertiesAsDomain(), true);
            double sizeAsDomain = one.getPropertiesAsDomain().size() + two.getPropertiesAsDomain().size();        
            if(!one.getPropertiesAsRange().isEmpty() || !two.getPropertiesAsRange().isEmpty()){
                jaccardAsRange = mySim.JaccardToTriplets(one.getPropertiesAsRange(), two.getPropertiesAsRange(), false);
                double sizeAsRange = one.getPropertiesAsRange().size() + two.getPropertiesAsRange().size();              
                valueFinal = ( (sizeAsDomain * jaccardAsDomain) + (sizeAsRange * jaccardAsRange) ) / (sizeAsDomain + sizeAsRange); //weighted average
            }else{
                valueFinal = jaccardAsDomain;
            }
        }else if(!one.getPropertiesAsRange().isEmpty() || !two.getPropertiesAsRange().isEmpty()){
            jaccardAsRange = mySim.JaccardToTriplets(one.getPropertiesAsRange(), two.getPropertiesAsRange(), false);           
            valueFinal = jaccardAsRange;
        }
        sizeTotal=sizeTotal+one.getPropertiesAsDomain().size() + two.getPropertiesAsDomain().size() + one.getPropertiesAsRange().size() + two.getPropertiesAsRange().size(); 
        averageValue=averageValue+(one.getPropertiesAsDomain().size() + two.getPropertiesAsDomain().size())*jaccardAsDomain + (one.getPropertiesAsRange().size() + two.getPropertiesAsRange().size())* jaccardAsRange;     
        return valueFinal;
    }
    
    public double subClasses(){
        double jaccardValue=0.0;
        if(!one.getSubClasses().isEmpty() || !two.getSubClasses().isEmpty()){      
            ArrayList<String> classesOntologyA=new ArrayList<>();
            ArrayList<String> classesOntologyB=new ArrayList<>();
            for(OWLClass cls:one.getSubClasses()){
                //classesOntologyA.add(cls.toString().substring(cls.toString().lastIndexOf("#")+1)); 
                classesOntologyA.add(cls.toString());
            }
            for(OWLClass cls:two.getSubClasses()){
                //classesOntologyB.add(cls.toString().substring(cls.toString().lastIndexOf("#")+1)); 
                classesOntologyB.add(cls.toString());
            }        
        jaccardValue=mySim.JaccardToStringSets(classesOntologyA, classesOntologyB); //compare whole set subclass
        }
        sizeTotal=sizeTotal+one.getSubClasses().size()+two.getSubClasses().size();
        averageValue=averageValue+(one.getSubClasses().size()+two.getSubClasses().size())*jaccardValue;
        return jaccardValue;
    }    
    
    
    public double superClasses(){
        double jaccardValue=0.0;
        if(!one.getSuperClasses().isEmpty() || !two.getSuperClasses().isEmpty()){    
            ArrayList<String> classesOntologyA=new ArrayList<>();
            ArrayList<String> classesOntologyB=new ArrayList<>();
            for(OWLClass cls:one.getSuperClasses()){
                classesOntologyA.add(cls.toString());
                //classesOntologyA.add(cls.toString().substring(cls.toString().lastIndexOf("#")+1));
            }
            for(OWLClass cls:two.getSuperClasses()){
                classesOntologyB.add(cls.toString());
                //classesOntologyB.add(cls.toString().substring(cls.toString().lastIndexOf("#")+1));
            }
            jaccardValue=mySim.JaccardToStringSets(classesOntologyA, classesOntologyB); //compare whole set superclass
        }
        sizeTotal=sizeTotal+one.getSuperClasses().size()+two.getSuperClasses().size();
        averageValue=averageValue+(one.getSuperClasses().size()+two.getSuperClasses().size())*jaccardValue;
        return jaccardValue;
    }  
    
    
    
    public double commentsMatch(){
        double jaccardValue=0.0;
        if(!one.getComments().isEmpty() || !two.getComments().isEmpty()){
            ArrayList<String> commentsOntologyA=new ArrayList<>();
            ArrayList<String> commentsOntologyB=new ArrayList<>();

            for(OWLAnnotationValue value:one.getComments()){
                commentsOntologyA.add(value.toString());            
            }
            for(OWLAnnotationValue value:two.getComments()){
                commentsOntologyB.add(value.toString());            
            }
            jaccardValue=mySim.JaccardToStringSets(commentsOntologyA, commentsOntologyB); //compare whole set superclass
        }
        sizeTotal=sizeTotal+one.getComments().size()+two.getComments().size();
        averageValue=averageValue+(one.getComments().size()+two.getComments().size())*jaccardValue;
        return jaccardValue;
    }  

    double eqClasses() {
        double jaccardValue=0.0;
        if(!one.getEqClasses().isEmpty() || !two.getEqClasses().isEmpty()){
            ArrayList<String> classesOntologyA=new ArrayList<>();
            ArrayList<String> classesOntologyB=new ArrayList<>();
            for(OWLClass cls:one.getEqClasses()){
                classesOntologyA.add(cls.toString());            
            }
            for(OWLClass cls:two.getEqClasses()){
                classesOntologyB.add(cls.toString());            
            }
            jaccardValue=mySim.JaccardToStringSets(classesOntologyA, classesOntologyB); //compare whole set equivalent class
        }
        sizeTotal=sizeTotal+one.getEqClasses().size()+two.getEqClasses().size();
        averageValue=averageValue+(one.getEqClasses().size()+two.getEqClasses().size())*jaccardValue;
        return jaccardValue;
    }  
    
    public double extensional(){
        double jaccardValue=0.0;
        if(!one.getInstances().isEmpty() || !two.getInstances().isEmpty()){
            jaccardValue=mySim.JaccardToStringSets(one.getInstances(), two.getInstances());
        }
        sizeTotal=sizeTotal+one.getInstances().size()+two.getInstances().size();
        averageValue=averageValue+(one.getInstances().size()+two.getInstances().size())*jaccardValue;
        return jaccardValue;
    }
    
    
    double wholeConcepts(){
        return averageValue/sizeTotal;
    }
    
}
