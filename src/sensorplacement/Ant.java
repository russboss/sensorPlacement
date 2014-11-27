/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sensorplacement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import sensorplacement.Graph;

/**
 *
 * @author Russ
 */
public class Ant {
    Node currentNode = null;
    int id = -1;
    ArrayList<Node> antSolutionNode = null;
    ArrayList<Edge> antSolutionEdge = null;
    Graph g;
    Random rand = new Random();
    // nextInt is normally exclusive of the top value,
    // so add 1 to make it inclusive
    //int randomNum = rand.nextInt((max - min) + 1) + min;
    
    public Ant(int i){
        id = i;
    }
    public void setID(int i){
        id = i;
    }
    public Ant(Graph inG, Node startNode){
        g = inG;
        
        currentNode = startNode;
    }
    
    
    public void setGraph(Graph a){
        g = a;
        ArrayList<Node> arr = g.nodes;
        //pick random node based on the list in nodes stored in 
        currentNode = arr.get(rand.nextInt(arr.size()));
    }
    
    public Graph getGraph(){
        return g;
    }
    
    public ArrayList<Node> releaseTheAnt(){
        resetLists();
        antSolutionNode.add(currentNode);
        int runcount = 0;
        Node nextNode = null;
        Edge rndEdge = null;
        boolean moveNext = true;
        boolean visitedHecn = false;
        while( !((visitedHecn == true && antSolutionNode.size() >= Params.numSensors) || (runcount > Params.numSensors * Params.infiniteAntMult) )) {
            //currentNode.edges
            //System.out.println(currentNode + "::runcount" + runcount);
            //random edge
            rndEdge = chooseDirection();
            
            nextNode = rndEdge.target;
            
            moveNext = addToLists(currentNode, nextNode,rndEdge);
            if (moveNext== true){
                //add to node list
                if(antSolutionNode.indexOf(nextNode) == -1){
                    antSolutionNode.add(nextNode);
                    runcount++;
                }
                currentNode = nextNode;
            }
            if(currentNode.id == "hecn"){
                visitedHecn = true;
            }
          
        }
        return antSolutionNode;
    }

    
    public Edge chooseDirection(){
        ArrayList<Edge> edgeList = new ArrayList();
        double maxDouble = 0.0;
        double rnd;
        double value = 0.0;
        double tempCoverage = 0.0;
        Topology tempTopology = null;
        ArrayList<Node> tempCoverageNodeList = (ArrayList<Node>)antSolutionNode.clone();//new ArrayList();
        List<Double> prob = new ArrayList<Double>();
        //randomNodeIndex = rand.nextInt( currentNode.edges.size() );
        //tempCoverageNodeList.add(currentNode);
        //update to use pheromone levels to choose a good edges !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //Edge ret = currentNode.edges.get(randomNodeIndex);

        //grab a list of edges
        
        for(Edge edge : currentNode.edges){

            tempCoverageNodeList.add(edge.target);
            tempTopology = new Topology(convertNodes(tempCoverageNodeList)); //inheritance of the node,sensor,point would make this happy

            tempCoverage = tempTopology.coverage();
            maxDouble += edge.pheromone * tempCoverage + 0.0001;
            prob.add(edge.pheromone * tempCoverage + 0.0001);
            edgeList.add(edge);
            tempCoverageNodeList.remove(edge.target);
            
        }
        
        rnd = rand.nextDouble() * maxDouble;
        
        //cycle through all the attached edges and when the sum of weights is 
        //greater than rnd, our edge
        //System.out.println("Rnd: "+ rnd + " Max: "+maxDouble);
        for (int i=0; i<prob.size(); i++){
            value += prob.get(i);
            //System.out.print("value: "+ value);
            if(value > rnd){
                //System.out.println(" found: "+i);
                return edgeList.get(i);
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
    
    private void resetLists(){
        antSolutionNode = new ArrayList();
        antSolutionEdge = new ArrayList();
    }
    @Override
    public String toString(){
        String str = "";
//        if (antSolutionNode != null){
//            for(int i=0; i<antSolutionNode.size(); i++){
//                str += antSolutionNode.get(i) + " *** ";
//            }
//        }
        return "ID: "+id+"\n"+"Node: "+ currentNode + "\nList: " + str;
    }

    public ArrayList<Sensor> convertNodes(ArrayList<Node> nodes){        
        ArrayList<Sensor> sensors = new ArrayList();
        for(Node node : nodes){
            if(node.id != "hecn"){
                sensors.add(node.sensor);
            }
        }
        return sensors;
    }


    
}
