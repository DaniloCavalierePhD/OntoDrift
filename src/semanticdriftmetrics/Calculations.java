/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semanticdriftmetrics;

import semanticdriftmetrics.Constants.Constants;
import semanticdriftmetrics.Constructors.Version;
import semanticdriftmetrics.Constructors.ConceptPair;
import semanticdriftmetrics.Constructors.Concept;
import java.util.ArrayList;
import java.util.Set;

/**
 *
 * @author andreadisst
 */
public class Calculations {
    
    private final Aspects asp;
    
    public Calculations(){
        asp = new Aspects();
    }
    
    //******************************************************
    //*
    //* AVERAGE MORPHING CHAINS
    //*
    //******************************************************/
    
    public double getAVGLabel(Version one, Version two){
        Set<Concept> concepts1 = one.getManager().getConcepts();
        Set<Concept> concepts2 = two.getManager().getConcepts();
        double sum = 0;
        for(Concept concept1 : concepts1){
            for(Concept concept2 : concepts2){
                sum = sum + asp.label(concept1, concept2);
            }
        }
        if(concepts1.isEmpty() || concepts2.isEmpty()){
            return 0;
        }else{
            return sum / (concepts1.size() * concepts2.size());
        }
    }
    
    public double getAVGIntension(Version one, Version two){
        Set<Concept> concepts1 = one.getManager().getConcepts();
        Set<Concept> concepts2 = two.getManager().getConcepts();
        double sum = 0;
        for(Concept concept1 : concepts1){
            for(Concept concept2 : concepts2){
                sum = sum + asp.intensional(concept1, concept2);
            }
        }
        if(concepts1.isEmpty() || concepts2.isEmpty()){
            return 0;
        }else{
            return sum / (concepts1.size() * concepts2.size());
        }
    }
    
    public double getAVGExtension(Version one, Version two){
        Set<Concept> concepts1 = one.getManager().getConcepts();
        Set<Concept> concepts2 = two.getManager().getConcepts();
        double sum = 0;
        for(Concept concept1 : concepts1){
            for(Concept concept2 : concepts2){
                sum = sum + asp.extensional(concept1, concept2);
            }
        }
        if(concepts1.isEmpty() || concepts2.isEmpty()){
            return 0;
        }else{
            return sum / (concepts1.size() * concepts2.size());
        }
    }
    
    public double getAVGWhole(Version one, Version two){
        Set<Concept> concepts1 = one.getManager().getConcepts();
        Set<Concept> concepts2 = two.getManager().getConcepts();
        double sum = 0;
        for(Concept concept1 : concepts1){
            for(Concept concept2 : concepts2){
                sum = sum + asp.whole(concept1, concept2);
            }
        }
        if(concepts1.isEmpty() || concepts2.isEmpty()){
            return 0;
        }else{
            return sum / (concepts1.size() * concepts2.size());
        }
    }
    
    //******************************************************
    //*
    //* AVERAGE HYBRID
    //*
    //******************************************************/
    
    public double getAVGChain(Version one, Version two, String type){
        ArrayList<ConceptPair> pairs = new ArrayList<>();
        
        switch(type){
            case "LABEL": pairs = getLabelMaxPairs(one, two); break;
            case "INTENSION": pairs = getIntensionMaxPairs(one, two); break;
            case "EXTENSION": pairs = getExtensionMaxPairs(one, two); break;
            case "WHOLE": pairs = getWholeMaxPairs(one, two); break;
        }
        
        double sum = 0;
        for(ConceptPair pair : pairs){
            sum = sum + pair.getStabilityValue();
        }
        if( sum > 0 && pairs.size() > 0){
            return sum / pairs.size();
        }else{
            return 0;
        }
    }
    
    public double getAVGLabelChain(Version one, Version two){
        return getAVGChain(one, two, Constants.LABEL);
    }
    
    public double getAVGIntensionChain(Version one, Version two){
        return getAVGChain(one, two, Constants.INTENSION);
    }
    
    public double getAVGExtensionChain(Version one, Version two){
        return getAVGChain(one, two, Constants.EXTENSION);
    }
    
    public double getAVGWholeChain(Version one, Version two){
        return getAVGChain(one, two, Constants.WHOLE);
    }
    
    //******************************************************
    //*
    //* ALL PAIRS
    //*
    //******************************************************/
    
    public ArrayList<ConceptPair> getLabelPairs(Version one, Version two){
        ArrayList<ConceptPair> results = new ArrayList<>();
        Set<Concept> concepts1 = one.getManager().getConcepts();
        Set<Concept> concepts2 = two.getManager().getConcepts();
        for(Concept concept1 : concepts1){
            for(Concept concept2 : concepts2){
                results.add(new ConceptPair(concept1, concept2, asp.label(concept1, concept2)));
            }
        }
        return results;
    }
    
    public ArrayList<ConceptPair> getIntensionPairs(Version one, Version two){
        ArrayList<ConceptPair> results = new ArrayList<>();
        Set<Concept> concepts1 = one.getManager().getConcepts();
        Set<Concept> concepts2 = two.getManager().getConcepts();
        for(Concept concept1 : concepts1){
            for(Concept concept2 : concepts2){
                results.add(new ConceptPair(concept1, concept2, asp.intensional(concept1, concept2)));
            }
        }
        return results;
    }
    
    public ArrayList<ConceptPair> getExtensionPairs(Version one, Version two){
        ArrayList<ConceptPair> results = new ArrayList<>();
        Set<Concept> concepts1 = one.getManager().getConcepts();
        Set<Concept> concepts2 = two.getManager().getConcepts();
        for(Concept concept1 : concepts1){
            for(Concept concept2 : concepts2){
                results.add(new ConceptPair(concept1, concept2, asp.extensional(concept1, concept2)));
            }
        }
        return results;
    }
    
    public ArrayList<ConceptPair> getWholePairs(Version one, Version two){
        ArrayList<ConceptPair> results = new ArrayList<>();
        Set<Concept> concepts1 = one.getManager().getConcepts();
        Set<Concept> concepts2 = two.getManager().getConcepts();
        for(Concept concept1 : concepts1){
            for(Concept concept2 : concepts2){
                results.add(new ConceptPair(concept1, concept2, asp.whole(concept1, concept2)));
            }
        }
        return results;
    }
    
    //******************************************************
    //*
    //* MAX PAIRS (FOR CHAIN)
    //*
    //******************************************************/
    
    public ArrayList<ConceptPair> getLabelMaxPairs(Version one, Version two){
        ArrayList<ConceptPair> results = new ArrayList<>();
        
        Set<Concept> concepts1 = one.getManager().getConcepts();
        Set<Concept> concepts2 = two.getManager().getConcepts();
        for(Concept concept1 : concepts1){
            double maxValue = 0; Concept maxConcept = null;
            for(Concept concept2 : concepts2){
                double sim = asp.label(concept1, concept2);
                if(sim > maxValue){
                    maxValue = sim;
                    maxConcept = concept2;
                }
            }
            if(maxValue > 0){
                results.add(new ConceptPair(concept1, maxConcept, maxValue));
            }
        }
        return results;
    }
    
    public ArrayList<ConceptPair> getIntensionMaxPairs(Version one, Version two){
        ArrayList<ConceptPair> results = new ArrayList<>();
        
        Set<Concept> concepts1 = one.getManager().getConcepts();
        Set<Concept> concepts2 = two.getManager().getConcepts();
        for(Concept concept1 : concepts1){
            double maxValue = 0; Concept maxConcept = null;
            for(Concept concept2 : concepts2){
                double sim = asp.intensional(concept1, concept2);
                if(sim > maxValue){
                    maxValue = sim;
                    maxConcept = concept2;
                }
            }
            if(maxValue > 0){
                results.add(new ConceptPair(concept1, maxConcept, maxValue));
            }
        }
        return results;
    }
        
    public ArrayList<ConceptPair> getExtensionMaxPairs(Version one, Version two){
        ArrayList<ConceptPair> results = new ArrayList<>();
        
        Set<Concept> concepts1 = one.getManager().getConcepts();
        Set<Concept> concepts2 = two.getManager().getConcepts();
        for(Concept concept1 : concepts1){
            double maxValue = 0; Concept maxConcept = null;
            for(Concept concept2 : concepts2){
                double sim = asp.extensional(concept1, concept2);
                if(sim > maxValue){
                    maxValue = sim;
                    maxConcept = concept2;
                }
            }
            if(maxValue > 0){
                results.add(new ConceptPair(concept1, maxConcept, maxValue));
            }
        }
        return results;
    }
        
    public ArrayList<ConceptPair> getWholeMaxPairs(Version one, Version two){
        ArrayList<ConceptPair> results = new ArrayList<>();
        
        Set<Concept> concepts1 = one.getManager().getConcepts();
        Set<Concept> concepts2 = two.getManager().getConcepts();
        for(Concept concept1 : concepts1){
            double maxValue = 0; Concept maxConcept = null;
            for(Concept concept2 : concepts2){
                double sim = asp.whole(concept1, concept2);
                if(sim > maxValue){
                    maxValue = sim;
                    maxConcept = concept2;
                }
            }
            if(maxValue > 0){
                results.add(new ConceptPair(concept1, maxConcept, maxValue));
            }
        }
        return results;
    }
    
}
