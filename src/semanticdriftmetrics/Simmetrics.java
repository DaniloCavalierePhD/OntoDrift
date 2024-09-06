/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semanticdriftmetrics;

import semanticdriftmetrics.Constructors.OntProperty;
import info.debatty.java.stringsimilarity.JaroWinkler;
import java.util.ArrayList;
import uk.ac.shef.wit.simmetrics.similaritymetrics.AbstractStringMetric;
import uk.ac.shef.wit.simmetrics.similaritymetrics.Levenshtein;
import uk.ac.shef.wit.simmetrics.similaritymetrics.MongeElkan;

/**
 *
 * @author andreadisst
 */
public class Simmetrics {
    
    public Simmetrics(){}
    
    public float MongeElkanSimilarity(String one, String two){
        AbstractStringMetric metric = new MongeElkan();
        return metric.getSimilarity(one, two);
    }
    
    public double JaroWinklerSimilarity(String one, String two){
        JaroWinkler jw = new JaroWinkler();
        return jw.similarity(one, two);
    }
    
    public double LevenshteinSimilarity(String one, String two){
        Levenshtein jw = new Levenshtein();
        return jw.getSimilarity(one, two);
    }
    
    public double JaccardToStringSets(ArrayList<String> one, ArrayList<String> two){
        double intersection = 0;
        for(String x : one){
            for(String y : two){
                if(x.equals(y)){
                    intersection++;
                }
            }
        }
        double union = one.size() + two.size() - intersection;
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
