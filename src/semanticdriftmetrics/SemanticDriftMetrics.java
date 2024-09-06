/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semanticdriftmetrics;

import java.io.FileNotFoundException;
import semanticdriftmetrics.Constructors.OntProperty;
import semanticdriftmetrics.Constructors.OntClass;
import semanticdriftmetrics.Constructors.AverageDrift;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import semanticdriftmetrics.Constructors.Version;
import semanticdriftmetrics.Constructors.VersionPairs;
import semanticdriftmetrics.Exceptions.OntologyCreationException;

/**
 *
 * @author andreadisst
 */
public class SemanticDriftMetrics {

    public static void main(String[] args) throws OntologyCreationException {

        //Example
        // Ontologies can be found at SemanticDriftMetrics/versions/20xx/tate_20xx.owl
        String path = System.getProperty("user.dir") + "\\versions\\";

        SemanticDrift sd = new SemanticDrift();
        try {
           
            sd.addVersionFile("/Users/giuseppe/Desktop/DBdrift/versions/2011/tate_2011.owl");
            sd.addVersionFile("/Users/giuseppe/Desktop/DBdrift/versions/2012/tate_2012.owl");
            /*
            sd.addVersionFile(path + "2004\\tate_2004.owl");
            sd.addVersionFile(path + "2006\\tate_2006.owl");
            sd.addVersionFile(path + "2007\\tate_2007.owl");
            sd.addVersionFile(path + "2008\\tate_2008.owl");
            sd.addVersionFile(path + "2010\\tate_2010.owl");
            sd.addVersionFile(path + "2011\\tate_2011.owl");
            sd.addVersionFile(path + "2012\\tate_2012.owl");
            sd.addVersionFile(path + "2013\\tate_2013.owl");*/
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SemanticDriftMetrics.class.getName()).log(Level.SEVERE, null, ex);
        }

        /*ArrayList<Version> versions = sd.getVersions();
            for(Version version : versions){
            System.out.println(version.getName());
            ArrayList<OntClass> tree = version.getTree();
            for(OntClass leaf : tree){
            print(leaf);
            }
        }*/
        
        ArrayList<AverageDrift> avgds = sd.getAverageDrift();
        for (AverageDrift avgd : avgds) {
            System.out.println(avgd.getFrom());
            System.out.println(avgd.getTo());
            System.out.println(avgd.getLabel());
            System.out.println(avgd.getIntension());
            System.out.println(avgd.getExtension());
            System.out.println(avgd.getWhole());
            System.out.println("-------");
        }

        /*ArrayList<AverageDrift> avgdhs = sd.getAverageDriftChain();
         for(AverageDrift avgd : avgdhs){
         System.out.println(avgd.getFrom());
         System.out.println(avgd.getTo());
         System.out.println(avgd.getLabel());
         System.out.println(avgd.getIntension());
         System.out.println(avgd.getExtension());
         System.out.println(avgd.getWhole());
         System.out.println("-------");
         }*/
        
        /*ArrayList<Chain> chains = sd.getWholeChains();
         for(Chain chain : chains){
         System.out.println("Chain for " + chain.getInitialConcept());
         ArrayList<Link> links = chain.getLinks();
         for(Link link : links){
         System.out.println(link.getFrom()+"#"+link.getPair().getFrom());
         System.out.println(link.getTo()+"#"+link.getPair().getTo());
         System.out.println(link.getPair().getStability());
         }
         }*/
        
        /*ArrayList<RankedConcept> rankedConcepts = sd.getWholeRanking();
         for(RankedConcept rankedConcept : rankedConcepts){
         System.out.println(rankedConcept.getRank());
         System.out.println(rankedConcept.getChain().getInitialConcept());
         System.out.println(rankedConcept.getStrength());
         System.out.println("-------");
         }*/
        
        ArrayList<VersionPairs> pairs = sd.getWholeVersionPairs();
        for(VersionPairs pair : pairs){
            System.out.println(pair.getFrom());
            System.out.println(pair.getTo());
            System.out.print(String.format("%20s",""));
            for(String to : pair.getXAxis()){
            System.out.print(String.format("%20s",to));
            }
            System.out.println(String.format("%20s",""));
            for(String from : pair.getYAxis()){
            System.out.print(String.format("%20s",from));
            for(String to : pair.getXAxis()){
            System.out.print(String.format("%20s",pair.getStabilityForPair(from, to)));
            }
            System.out.println(String.format("%20s",""));
            }
        }
    }

    private static void print(OntClass leaf) {
        System.out.println(leaf.getName());
        for (OntProperty property : leaf.getProperties()) {
            System.out.println(property.getName() + "<" + property.getDomain() + "," + property.getRange() + ">");
        }
        for (OntClass subleaf : leaf.getSubclasses()) {
            print(subleaf);
        }
    }

}
