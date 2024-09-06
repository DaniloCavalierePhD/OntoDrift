/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semanticdriftmetrics;

import semanticdriftmetrics.Constructors.Concept;

/**
 *
 * @author andreadisst
 */
public class Aspects {
    
    public final Simmetrics sim;
    
    public Aspects(){
        sim = new Simmetrics();
    }
    
    public double label(Concept one, Concept two){
        if(one.getLabels().isEmpty() || two.getLabels().isEmpty()){
            return 0;
        }
        return sim.MongeElkanSimilarity(one.getLabels().get(0), two.getLabels().get(0)); //compare only first label
    }
    
    public double intensional(Concept one, Concept two){
        if(!one.getPropertiesAsDomain().isEmpty() && !two.getPropertiesAsDomain().isEmpty()){
            double jaccardAsDomain = sim.JaccardToTriplets(one.getPropertiesAsDomain(), two.getPropertiesAsDomain(), true);
            double sizeAsDomain = one.getPropertiesAsDomain().size() + two.getPropertiesAsDomain().size();
            
            if(!one.getPropertiesAsRange().isEmpty() && !two.getPropertiesAsRange().isEmpty()){
                double jaccardAsRange = sim.JaccardToTriplets(one.getPropertiesAsRange(), two.getPropertiesAsRange(), false);
                double sizeAsRange = one.getPropertiesAsRange().size() + two.getPropertiesAsRange().size();
                
                return ( (sizeAsDomain * jaccardAsDomain) + (sizeAsRange * jaccardAsRange) ) / (sizeAsDomain + sizeAsRange); //weighted average
            }else{
                return jaccardAsDomain;
            }
        }else if(!one.getPropertiesAsRange().isEmpty() && !two.getPropertiesAsRange().isEmpty()){
            double jaccardAsRange = sim.JaccardToTriplets(one.getPropertiesAsRange(), two.getPropertiesAsRange(), false);
                
            return jaccardAsRange;
        }else{
            return 0;
        }
    }
    
    public double extensional(Concept one, Concept two){
        if(one.getInstances().isEmpty() || two.getInstances().isEmpty()){
            return 0;
        }
        return sim.JaccardToStringSets(one.getInstances(), two.getInstances());
    }
    
    public double whole(Concept one, Concept two){
        return ( label(one,two) + intensional(one,two) + extensional(one,two) ) / 3;
    }
    
}
