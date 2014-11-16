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
    //ArrayList<Particle> swarm;
    ArrayList<Ant> ants = new ArrayList();
    int startSensors =Params.numSensors;
    
    Graph g;
    
    public ACO() {
        startSensors = Params.numSensors * Params.numNodeMult;
        
    }
    
    
    public void solve() {

        Topology pos = new Topology(startSensors);
        g = pos.g;
        //init ants
        //init edges

//        //Printing edge Weights by Node
//        for(Node node : g.nodes){
//            System.out.println(g.getNode(node.id));
//            for(Edge edge : g.getNode(node.id).edges){
//                System.out.print(edge.weight + " ");
//            }
//            System.out.println();
//        }
        Ant ant;
        for(int i=0; i<Params.numAnts; i++){
            ant = new Ant(g);
            ants.add(ant);
            break; //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!REmove Me For More than 1 ant
        }
        
        
        
        printAnts();
        
        
        //while not end condition //numColonyRuns
//            for (Ant ant : ants) {
//        
//        
//            }
            
            //local search procedure //??
            
            
            //update all edge weights
            
        //end while not end condition
        

        
        TopologyDrawer.draw(pos);
    }

    
    
    public void printAnts(){
        for (int i=0;i<ants.size(); i++){
            System.out.println( ants.get(i) );
        }
        
    }
}
