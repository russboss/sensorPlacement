/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sensorplacement;

import java.util.ArrayList;
import java.util.Random;
import sensorplacement.Graph;

/**
 *
 * @author Russ
 */
public class Ant {
    Node currentNode = null;
    ArrayList<Node> antSolution;
    Graph g;
    Random rand = new Random();
    // nextInt is normally exclusive of the top value,
    // so add 1 to make it inclusive
    //int randomNum = rand.nextInt((max - min) + 1) + min;
    
    public Ant(Graph inG){
        
        g = inG;

        
        //pick random node based on the list in nodes stored in 
        ArrayList<Node> arr = g.nodes;
        currentNode = arr.get(rand.nextInt(arr.size()));
       
    }

    public Ant(Graph inG, Node startNode){
        g = inG;
        currentNode = startNode;

    }
    
    
    
    public ArrayList<Node> releaseTheAnt(){
        antSolution.add(currentNode);
        
        
        
        
        return antSolution;
    }

    @Override
    public String toString(){
        String str = "";
        if (antSolution != null){
            for(int i=0; i<antSolution.size(); i++){
                str += antSolution.get(i) + " *** ";
            }
        }
        return "Node: "+ currentNode + "\nList: " + str;
    }
}
