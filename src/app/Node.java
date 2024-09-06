/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

/**
 *
 * @author giuseppe
 */
public class Node {
    public MyConcept comparedConcept;
    public double nameSimilarityValue;
    public double labelsSimilarityValue;
    public double intSimilarityValue;    
    public double subClassesSimilarityValue;
    public double superClassesSimilarityValue;
    public double commentsSimilarityValue;
    public double eqClassesSimilarityValue;
    public double extSimilarityValue;
    public double wholeSimilarityValue;
    
    
    public Node(MyConcept comparedConcept, double nameSimilarityValue,double labelsSimilarityValue,double intSimilarityValue,double subClassesSimilarityValue,double superClassesSimilarityValue,double commentsSimilarityValue,double eqClassesSimilarityValue,double extSimilarityValue,double wholeSimilarityValue){     
        this.comparedConcept=comparedConcept;
        this.nameSimilarityValue=nameSimilarityValue;
        this.labelsSimilarityValue=labelsSimilarityValue;
        this.intSimilarityValue=intSimilarityValue;    
        this.subClassesSimilarityValue=subClassesSimilarityValue;
        this.superClassesSimilarityValue=superClassesSimilarityValue;
        this.commentsSimilarityValue=commentsSimilarityValue;
        this.eqClassesSimilarityValue=eqClassesSimilarityValue;
        this.wholeSimilarityValue=wholeSimilarityValue;
        this.extSimilarityValue=extSimilarityValue;
    }

    public MyConcept getNameComparedConcept() {
        return comparedConcept;
    }

    public double getNameSimilarityValue() {
        return nameSimilarityValue;
    }

    public double getLabelsSimilarityValue() {
        return labelsSimilarityValue;
    }

    public double getIntSimilarityValue() {
        return intSimilarityValue;
    }

    public double getSubClassesSimilarityValue() {
        return subClassesSimilarityValue;
    }

    public double getSuperClassesSimilarityValue() {
        return superClassesSimilarityValue;
    }

    public double getCommentsSimilarityValue() {
        return commentsSimilarityValue;
    }

    public double getEqClassesSimilarityValue() {
        return eqClassesSimilarityValue;
    }

    public double getExtSimilarityValue() {
        return extSimilarityValue;
    }

    public double getWholeSimilarityValue() {
        return wholeSimilarityValue;
    }
}
