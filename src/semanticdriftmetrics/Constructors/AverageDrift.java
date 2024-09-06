/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semanticdriftmetrics.Constructors;

/**
 *
 * @author andreadisst
 */
public class AverageDrift {
    
    private final Version from;
    private final Version to;
    private final String label;
    private final String intension;
    private final String extension;
    private final String whole;
    
    /**
    * Constructor 
    * @param from This is the name of the first ontology being compared.
    * @param to This is the name of the second ontology being compared.
    * @param label This is the Label stability between the two ontologies.
    * @param intension This is the Intension stability between the two ontologies.
    * @param extension This is the Extension stability between the two ontologies.
    * @param whole This is the Whole stability between the two ontologies.
    */
    public AverageDrift(Version from, Version to, String label, String intension, String extension, String whole){
        this.from = from;
        this.to = to;
        this.label = label;
        this.intension = intension;
        this.extension = extension;
        this.whole = whole;
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
    * @return This method returns the value of Label stability for the two compared ontologies.
    */
    public String getLabel(){
        return label;
    }
    
    /**
    * @return This method returns the value of Intension stability for the two compared ontologies.
    */
    public String getIntension(){
        return intension;
    }
    
    /**
    * @return This method returns the value of Extension stability for the two compared ontologies.
    */
    public String getExtension(){
        return extension;
    }
    
    /**
    * @return This method returns the value of Whole stability for the two compared ontologies.
    */
    public String getWhole(){
        return whole;
    }
}
