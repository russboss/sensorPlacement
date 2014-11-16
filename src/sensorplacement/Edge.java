/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sensorplacement;

/**
 *
 * @author nathan
 */
public class Edge {

    public final Node target;
    public double weight;
    public double pheromone;
    
    public Edge(Node target, double weight) {
        this.target = target;
        this.weight = weight;
    }
}
