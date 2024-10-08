/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.util.Comparator;

/**
 *
 * @author giuseppe
 */
public class NodeComparator implements Comparator<Node> {

    public NodeComparator() {
    }
    
    @Override
    public int compare(Node n1, Node n2) { 
        if (n1.wholeSimilarityValue < n2.wholeSimilarityValue) 
            return -1; 
        else if (n1.wholeSimilarityValue > n2.wholeSimilarityValue) 
            return 1; 
        return 0;  
    } 
    
}
