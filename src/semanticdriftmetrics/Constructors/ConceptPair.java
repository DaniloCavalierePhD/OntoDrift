package semanticdriftmetrics.Constructors;

import semanticdriftmetrics.Constants.Constants;
import semanticdriftmetrics.Constructors.Concept;

/**
 *
 * @author andreadisst
 */
public class ConceptPair {
    
    private final Concept from;
    private final Concept to;
    private final double stability;
    
    /**
    * Constructor 
    * @param from This is the first concept being compared.
    * @param to This is the second concept being compared.
    * @param stability This is the stability value between the two compared concepts. 
    */
    public ConceptPair(Concept from, Concept to, double stability){
        this.from = from;
        this.to = to;
        this.stability = stability;
    }
    
    /**
    * @return This method returns the name of the first concept being compared. 
    */
    public String getFrom(){
        return from.getName();
    }
    
    /**
    * @return This method returns the IRI of the first concept being compared. 
    */
    public String getFromIRI(){
        return from.getIRI();
    }
    
    /**
    * @return This method returns the name of the second concept being compared. 
    */
    public String getTo(){
        return to.getName();
    }
    
    /**
    * @return This method returns the stability value between the two compared concepts, in String format. 
    */
    public String getStability(){
        return Constants.formatter.format(stability);
    }
    
    /**
    * @return This method returns the stability value between the two compared concepts. 
    */
    public double getStabilityValue(){
        return stability;
    }
    
}
