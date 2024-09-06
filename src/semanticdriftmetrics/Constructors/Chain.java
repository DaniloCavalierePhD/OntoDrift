package semanticdriftmetrics.Constructors;

import java.util.ArrayList;

/**
 *
 * @author andreadisst
 */
public class Chain {
    
    private final String initialConcept;
    private final ArrayList<Link> links;
    private String lastLink;
    
    /**
    * Constructor 
    * @param initialConcept This is the first concept of the chain.
    */
    public Chain(String initialConcept){
        this.initialConcept = initialConcept;
        links = new ArrayList<>();
        lastLink = "";
    }
    
    /**
    * This method adds a new link to the chain. 
    * @param from  This is the name of the first ontology being compared.
    * @param to  This is the name of the second ontology being compared.
    * @param pair  This is a ConceptPair between the two ontologies.
    */
    public void addLink(Version from, Version to, ConceptPair pair){
        Link link = new Link(from, to, pair);
        links.add(link);
        lastLink = pair.getTo();
    }
    
    /**
    * @return This method returns the name of the second concept of the last link of the chain.
    */
    public String getLastLink(){
        return lastLink;
    }
    
    /**
    * @return This method returns all links of the chain.
    */
    public ArrayList<Link> getLinks(){
        return links;
    }
    
    /**
    * @return This method returns the first concept of the first link of the chain.
    */
    public String getInitialConcept(){
        return initialConcept;
    }
    
}
