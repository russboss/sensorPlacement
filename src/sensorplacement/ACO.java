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
        //for(int i=0; i<Params.numAnts; i++){
        for(int i=0; i<2; i++){
            ant = new Ant(g);
            ants.add(ant);
            //break; //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!REmove Me For More than 1 ant
        }
        
        
        for(Ant ant2 : ants){
            ant2.releaseTheAnt();
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
        ArrayList<Topology> topologyAR= new ArrayList();
        Topology antT;
        for(Ant ant2 : ants){
             topologyAR.add( new Topology( convertNodes(ant2.antSolutionNode)) ) ;
        }

        
        TopologyDrawer.draw(pos);
        for(Topology antD: topologyAR){
            TopologyDrawer.draw(antD);
        }
    }

    
    public void updatePhTrails(){
        
        
    }
    
    

    public void evaporatePh(){
        for(Node node : g.nodes){
            for(Edge edge : node.edges){
                edge.pheromone -= Params.phLevelFade;
            }
        }
    }
    
    public ArrayList<Sensor> convertNodes(ArrayList<Node> nodes){        
        ArrayList<Sensor> sensors = new ArrayList();
        for(Node node : nodes){
            sensors.add(node.sensor);
        }
        return sensors;
    }
    
    
    
    public void printAnts(){
        for (int i=0;i<ants.size(); i++){
            System.out.println( ants.get(i) );
        }
        
    }
}
