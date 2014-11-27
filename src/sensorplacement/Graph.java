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
public class Graph implements Cloneable{
    
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

    
    
    public ArrayList<Node> cleanNodeByEdgePheromone(double threshold){
        ArrayList<Node> newList = new ArrayList();
        ArrayList<Edge> edgeList = new ArrayList();
        Edge edgeChoose = null;
                
        Node currentNode = getNode("hecn");
        newList.add(currentNode);
        
        
        while(newList.size() < Params.numSensors){
            for(Edge edge : currentNode.edges){
//                System.out.print(edge);
                if(edge.pheromone >= Params.cleanThreashold && !newList.contains(edge.target) ){
                    edgeList.add(edge);
//                    System.out.print(" added");
                }
//                System.out.println();
            }

            Collections.sort(edgeList);

            System.out.println("edge List Size: " + edgeList.size() );
            
            edgeChoose = edgeList.get(edgeList.size()-1 );
//            System.out.println("best: " + edgeChoose );   //this is the edge with the highest pheromone
            currentNode = edgeChoose.target;
            newList.add(currentNode);
            edgeList.remove(edgeChoose);

            
//            for(Node node :newList ){
//                System.out.print(node + " ");
//                
//            }
            System.out.println("solution size: " + newList.size());
//            System.out.println("====================");
        }//end while
        
        
        //clean the edges from the nodes
        
        for(Node node: newList){
            node.clearEdges();
            for(Edge edge :node.edges ){
                System.out.println(edge);
            }
        }
        
        //return cleaned list of nodes
        return newList;
    }
    
    
    
    @Override
    public Object clone() {
        Graph ret = new Graph();
        //ret.nodes = (ArrayList<Node>)nodes.clone();//new ArrayList();
        ret.nodes = new ArrayList();
        for(Node node: nodes){
            ret.nodes.add( (Node)node.clone() );
        }
        return ret;
    }

    
    
    
}


