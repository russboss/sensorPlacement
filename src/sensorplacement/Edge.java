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
public class Edge implements Comparable, Cloneable{

    public final Node target;
    public double weight;
    public double pheromone = Params.phLevelInit;
    
    public Edge(Node target, double weight) {
        this.target = target;
        this.weight = weight;
    }
    
    
    public String toString(){
        String ret = "target: " + target + " weight: "+ weight + " pheromone: " +pheromone;
        return ret;
    }
    
    public double getPheromone(){
        return pheromone;
    }
 
    @Override
    public int compareTo(Object o) {
        //write code here for compare name
        Edge a = (Edge)o;
        if(pheromone < a.getPheromone() ){
            return -1;
        }
        else if (pheromone > a.getPheromone() ){
            return 1;
        }
        else{
            return 0;
        }
    }

    
    
    @Override
    public Object clone() {
        Edge ret = new Edge(target,weight);
        ret.pheromone  = pheromone;

        return ret;
    }    
    
}

