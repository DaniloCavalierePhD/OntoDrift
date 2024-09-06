/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.util.ArrayList;
import semanticdriftmetrics.Constructors.OntProperty;
import semanticdriftmetrics.Simmetrics;

/**
 *
 * @author giuseppe
 */
public class MySimmetrics extends Simmetrics{
        
    
    public double JaccardToStringSets(ArrayList<String> one, ArrayList<String> two){
        double intersection = 0.0;
        double union = 0.0;
        if (one.size()<two.size()){
            for(String x : one){
                for(String y : two){
                    if(x.equals(y)){
                        intersection++;
                        break;
                    }
                }
            }
        }
        else{                 
            for(String x : two){
                for(String y : one){
                    if(x.equals(y)){
                        intersection++;
                        break;
                    }
                }
            }           
        }
        if (one.size()==two.size())
            union = one.size();
        else
            union = one.size() + two.size() - intersection;        
        return intersection / union;
    }
    
    
    public double JaccardToTriplets(ArrayList<OntProperty> one, ArrayList<OntProperty> two, boolean domain){
        double intersection = 0;
        double true_intersection = 0;
        for(OntProperty x : one){
            for(OntProperty y : two){
                if(domain){
                    if( x.getName().equals(y.getName()) && x.getRange().equals(y.getRange()) ){
                        intersection++;
                    }
                }else{
                    if( x.getName().equals(y.getName()) && x.getDomain().equals(y.getDomain()) ){
                        intersection++;                        
                    }
                }
                
                //intersection will increase by half-matched triplets, resulting to larger number than truly different triplets aka union
                if( x.getName().equals(y.getName()) && x.getDomain().equals(y.getDomain()) && x.getRange().equals(y.getRange())){
                    true_intersection++;
                }
            }
        }
        
        double union = one.size() + two.size() - true_intersection;
        return intersection / union;
    }
    
}
