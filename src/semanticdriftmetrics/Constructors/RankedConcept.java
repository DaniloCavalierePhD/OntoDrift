package semanticdriftmetrics.Constructors;

import semanticdriftmetrics.Constructors.Chain;

/**
 *
 * @author andreadisst
 */
public class RankedConcept {
    
    private final String rank;
    private final Chain chain;
    private final String strength;
    
    /**
    * Constructor 
    * @param rank This is the rank of this concept.
    * @param chain This is the chain that begins with this concept.
    * @param strength This is the strength of the chain.
    */
    public RankedConcept(String rank, Chain chain, String strength){
        this.rank = rank;
        this.chain = chain;
        this.strength = strength;
    }
    
    /**
    * @return This method returns the rank of this concept.
    */
    public String getRank(){
        return rank;
    }
    
    /**
    * @return This method returns the chain that begins with this concept.
    */
    public Chain getChain(){
        return chain;
    }
    
    /**
    * @return This method returns the strength value of this chain.
    */
    public String getStrength(){
        return strength;
    }
    
}
