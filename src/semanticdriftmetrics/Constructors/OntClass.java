package semanticdriftmetrics.Constructors;

import java.util.ArrayList;

/**
 *
 * @author andreadisst
 */
public class OntClass {
    
    private final String IRI;
    private final String name;
    private final ArrayList<OntClass> subclasses;
    private final ArrayList<OntProperty> properties;
    
    public OntClass(){
        IRI = "";
        name = "";
        subclasses = new ArrayList<>();
        properties = new ArrayList();
    }
    
    public OntClass(String anIRI, String aName, ArrayList<OntClass> someSubclasses, ArrayList<OntProperty> someProperties){
        IRI = anIRI;
        name = aName;
        subclasses = someSubclasses;
        properties = someProperties;
    }
    
    public String getIRI(){
        return IRI;
    }
    
    public String getName(){
        return name;
    }
    
    public ArrayList<OntClass> getSubclasses(){
        return subclasses;
    }
    
    public ArrayList<OntProperty> getProperties(){
        return properties;
    }
    
}
