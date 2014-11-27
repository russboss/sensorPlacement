/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sensorplacement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/**
 *
 * @author nathan
 */
public class Graph {
    
    ArrayList<Node> nodes = new ArrayList();

    public void computePaths(Node source) {
        source.minDistance = 0.;
        PriorityQueue<Node> vertexQueue = new PriorityQueue<Node>();
        vertexQueue.add(source);

        while (!vertexQueue.isEmpty()) {
            Node u = vertexQueue.poll();

            // Visit each edge exiting u
            for (Edge e : u.edges) {
                Node v = e.target;
                double weight = e.weight;
                double distanceThroughU = u.minDistance + weight;
                if (distanceThroughU < v.minDistance) {
                    vertexQueue.remove(v);
                    v.minDistance = distanceThroughU;
                    v.previous = u;
                    vertexQueue.add(v);
                }
            }
        }
    }

    public ArrayList<Node> getShortestPathTo(Node target) {
        ArrayList<Node> path = new ArrayList<Node>();
        for (Node vertex = target; vertex != null; vertex = vertex.previous) {
            path.add(vertex);
        }
        //Collections.reverse(path);
        return path;
    }
    
    public ArrayList<Node> getShortestPathTo(String from, String to) {
        Node source = this.getNode(from);
        Node target = this.getNode(to);
        //computePaths(source);
        ArrayList<Node> path = getShortestPathTo(target);
        //resetNodes();
        return path;
    }
    
    public void resetNodes() {
        
        for(Node n : this.nodes) {
            n.minDistance = Double.MAX_VALUE;
            n.previous = null;
        }
    }
    
    public void addNode(Node n) {
        nodes.add(n);
    }
    
    public Node getNode(String id) {
        for(Node n : nodes) {
            if(n.id.equals(id)) return n;
        }
        return null;
    }

    
    
    public void cleanNodeByEdgePheromone(double threshold){
        ArrayList<Node> newList = new ArrayList();
        ArrayList<Edge> edgeList = new ArrayList();
        Edge edgeReturn = null;
        
        boolean reachedThreashold = false;
                
        Node currentNode = getNode("hecn");

        
        
        while(newList.size() < Params.numSensors){
            for(Edge edge : currentNode.edges){
                if(edge.pheromone >= Params.cleanThreashold && !newList.contains(edge.target) ){
                    edgeList.add(edge);
                }
            }
            
            Collections.sort(edgeList);
            
            
            
            
            
        }//end while
        
        
    }
}




/*
Fruit fruit;
for(int i=0;i<100;i++)
{
  fruit = new fruit();
  fruit.setname(...);
  fruits.add(fruit);
}

//Sorting
Collections.sort(fruits, new Comparator<Fruit>() {
        @Override
        public int compare(Fruit  fruite1, Fruit  fruite2)
        {

            return  fruite1.fruitName.compareTo(fruite2.fruitName);
        }
    });
*/