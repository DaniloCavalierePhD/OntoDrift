/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semanticdriftmetrics.Constructors;

import semanticdriftmetrics.Constructors.OntClass;
import java.util.ArrayList;
import semanticdriftmetrics.OntologyManager;

/**
 *
 * @author andreadisst
 */
public class Version {
    
    private final OntologyManager manager;
    private final String name;
    
    public Version(OntologyManager manager){
        this.manager = manager;
        this.name = manager.getName();
    }
    
    public OntologyManager getManager(){
        return manager;
    }
    
    public String getName(){
        return name;
    }
    
    public ArrayList<OntClass> getTree(){
        return manager.getTree();
    }
    
}
