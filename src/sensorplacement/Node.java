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
public class Node implements Comparable<Node> {

    public final String id;
    
    //outgoing edges
    public ArrayList<Edge> edges = null;
    public double minDistance = Double.POSITIVE_INFINITY;
    public Node previous;
    Sensor sensor;

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
    
    
}
