/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semanticdriftmetrics.Constructors;

import semanticdriftmetrics.Constructors.ConceptPair;

/**
 *
 * @author andreadisst
 */
public class Link {
    
    private final Version from;
    private final Version to;
    private final ConceptPair pair;
    
    /**
    * Constructor 
    * @param from This is the name of the first ontology being compared.
    * @param to This is the name of the second ontology being compared.
    * @param pair This is the ConceptPair that creates a link between the two ontologies.
    */
    public Link(Version from, Version to, ConceptPair pair){
        this.from = from;
        this.to = to;
        this.pair = pair;
    }
    
    /**
    * @return This method returns the name of the first ontology being compared. 
    */
    public String getFrom(){
        return from.getName();
    }
    
    /**
    * @return This method returns the name of the second ontology being compared. 
    */
    public String getTo(){
        return to.getName();
    }
    
    /**
    * @return This method returns the ConceptPair that creates a link between the two ontologies.
    */
    public ConceptPair getPair(){
        return pair;
    }
    
}
