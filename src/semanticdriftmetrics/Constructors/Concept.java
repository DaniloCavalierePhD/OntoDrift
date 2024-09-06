package semanticdriftmetrics.Constructors;

import java.util.ArrayList;

/**
 *
 * @author andreadisst
 */
public class Concept {
    
    public String IRI;
    public String name;
    public ArrayList<String> labels;
    public ArrayList<OntProperty> propertiesAsDomain;
    public ArrayList<OntProperty> propertiesAsRange;
    public ArrayList<String> instances;
    
    public Concept(){
        IRI = "";
        name = "";
        labels = new ArrayList<>();
        propertiesAsDomain = new ArrayList();
        propertiesAsRange = new ArrayList();
        instances = new ArrayList();
    }
    
    public Concept(String anIRI, String aName, ArrayList<String> someLabels, ArrayList<OntProperty> somePropertiesAsDomain, ArrayList<OntProperty> somePropertiesAsRange, ArrayList<String> someInstances){
        IRI = anIRI;
        name = aName;
        labels = someLabels;
        propertiesAsDomain = somePropertiesAsDomain;
        propertiesAsRange = somePropertiesAsRange;
        instances = someInstances;
    }
    
    public String getIRI(){
        return IRI;
    }
    
    public String getName(){
        return name;
    }
    
    public ArrayList<String> getLabels(){
        return labels;
    }
    
    public ArrayList<OntProperty> getPropertiesAsDomain(){
        return propertiesAsDomain;
    }
    
    public ArrayList<OntProperty> getPropertiesAsRange(){
        return propertiesAsRange;
    }
    
    public ArrayList<String> getInstances(){
        return instances;
    }
    
}
