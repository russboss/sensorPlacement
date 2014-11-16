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
public class ACO {
    ArrayList<Particle> swarm;
    ArrayList<Ant> ants;
    int startSensors =Params.numSensors;
    
    int g;
    
    public ACO() {
        startSensors = Params.numSensors * Params.numNodeMult;
        
    }
    
    
    public void solve() {

        Topology pos = new Topology(startSensors);
        //init ants
        //init edges
        for(Node node : pos.g.nodes){
            System.out.println(pos.g.getNode(node.id));
            for(Edge edge : pos.g.getNode(node.id).edges){
                System.out.print(edge.weight + " ");
            }
            System.out.println();
        }
        //while not end condition
//            for (Ant ant : ants) {
//        
//        
//            }
            
            //local search procedure //??
            
            
            //update all edge weights
            
        //end while not end condition
        

        
        TopologyDrawer.draw(pos);
    }
    
}
