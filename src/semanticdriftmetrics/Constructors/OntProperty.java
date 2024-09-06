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
public class OntProperty {
    
    private final String name;
    private final String domain;
    private final String range;
    
    public OntProperty(String aName, String aDomain, String aRange){
        name = aName;
        domain = aDomain;
        range = aRange;
    }
    
    public String getName(){
        return name;
    }
    
    public String getDomain(){
        return domain;
    }
    
    public String getRange(){
        return range;
    }
    
}
