/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sensorplacement;

import java.util.ArrayList;

/**
 *
 * @author nathan
 */
public class Node implements Comparable<Node>, Cloneable{

    public final String id;
    
    //outgoing edges
    public ArrayList<Edge> edges = null;
    public double minDistance = Double.POSITIVE_INFINITY;
    public Node previous;
    public Sensor sensor;

    public Node(Sensor s) {
        this.id = s.id;
        this.sensor = s;
        edges = new ArrayList();
        
    }

    public String toString() {
        return id;
    }

    public int compareTo(Node other) {
        return Double.compare(minDistance, other.minDistance);
    }
    
    public void addEdge(Edge e) {
        
        edges.add(e);
    }
    
    public void setWeights(double w) {
        for(Edge e : edges) {
            e.weight = w;
        }
    }

    /**
     * clears all edges from the node
     */
    public void clearEdges(){
        edges = new ArrayList();
    }
    
    
    /**
     * Gets the edge that leads from Node Target to Source(this Node)
     * @param target target node
     * @return edge that leads from target to this node
     */
    public Edge getEdgePair(Node target){
        for(Edge edge : target.edges){
            if (edge.target == this){
                return edge;
            }
        }
        return null;
    }

    
    @Override
    public Object clone() {
        Node ret = new Node(sensor);
        //ret.edges = (ArrayList<Edge>)edges.clone();//new ArrayList();
        //ret.edges = new ArrayList();
//        for(Edge edge: edges){
//            ret.edges.add( (Edge)edge.clone() );
//        }
        ret.minDistance = minDistance;
        ret.previous = previous;

        return ret;
    }

}
