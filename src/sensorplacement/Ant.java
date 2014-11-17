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
    ArrayList<Node> antSolutionNode = new ArrayList();
    ArrayList<Edge> antSolutionEdge = new ArrayList();;
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
        antSolutionNode.add(currentNode);

        Node nextNode = null;
        Edge rndEdge = null;
        boolean moveNext = true;        
        while(currentNode.id != "hecn"){
            //currentNode.edges

            
            //random edge
            rndEdge = chooseDirection();
            
            
            nextNode = rndEdge.target;

            moveNext = addToLists(currentNode, nextNode,rndEdge);
            if (moveNext== true){
                currentNode = nextNode;
            }
        }
        return antSolutionNode;
    }

    
    public Edge chooseDirection(){
        ArrayList<Edge> edgeList = new ArrayList();
        double maxDouble = 0.0;
        int randomNodeIndex = 0;
        double rnd;
        double value = 0.0;
        //randomNodeIndex = rand.nextInt( currentNode.edges.size() );
        
        //update to use pheromone levels to choose a good edges !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //Edge ret = currentNode.edges.get(randomNodeIndex);

        //grab a list of edges
        for(Edge edge : currentNode.edges){
            maxDouble += edge.pheromone;
            edgeList.add(edge);
        }
        
        rnd = rand.nextDouble() * maxDouble;
        
        //cyccle through all the attached edges and when the sum of weights is 
        //greater than rnd, our edge
        for (Edge temp : edgeList){
            value += temp.pheromone;
            if(value > rnd){
                return temp;
            }
        }

        return null;
    }
    
    /**
     * adds the nextNode, nextEdge and the edge that connects from nextNode to Source
     * @param source source node
     * @param nextNode target node
     * @param nextEdge edge that connects source and target
     * @return true if successful false if one edge does not exist
     */
    private boolean addToLists(Node source, Node nextNode, Edge nextEdge){
        //add to node list
        if(antSolutionNode.indexOf(nextNode) == -1){
            antSolutionNode.add(nextNode);
        }
        
        //add both edges
        if (antSolutionEdge.indexOf(nextEdge) == -1){
            Edge a = getEdgePair(source, nextNode);
            if(a != null){
                antSolutionEdge.add( a );
                antSolutionEdge.add( nextEdge );
            }
            else{
                return false;
            }
        }
        return true;
    }
    
    
    /**
     * Gets the edge that leads from Target to Source
     * @param source Source node
     * @param target target node
     * @return edge that leads from target to source
     */
    public Edge getEdgePair(Node source,Node target){
        for(Edge edge : target.edges){
            if (edge.target == source){
                return edge;
            }
        }
        return null;
    }
    
    @Override
    public String toString(){
        String str = "";
        if (antSolutionNode != null){
            for(int i=0; i<antSolutionNode.size(); i++){
                str += antSolutionNode.get(i) + " *** ";
            }
        }
        return "Node: "+ currentNode + "\nList: " + str;
    }
}
