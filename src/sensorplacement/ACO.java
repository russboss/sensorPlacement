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
        Topology temp = null;
        double tempCoverage;
        g = pos.g;
        Ant bestAnt = null;
        double bestCoverage = 0.0;
        ArrayList<Topology> topologyAR= new ArrayList();
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
            ant.setID(i);
            ants.add(ant);
        }

        //while not end condition //numColonyRuns
        for(int run =0; run < Params.numMaxRuns; run++){
        //run all ants  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //choose best ant topology !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        for(Ant ant2 : ants){
            ant2.releaseTheAnt();
            temp = new Topology( convertNodes( ant2.antSolutionNode) );
            tempCoverage = temp.coverage();
            if( temp.coverage() > bestCoverage ){
                bestAnt = ant2;
                bestCoverage = tempCoverage;
            }
        }
        
        
        //local search procedure //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! ???????????????????????
        //update pheromone levels !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        layPheromones(bestAnt.antSolutionEdge);
        //fade pheromone levels !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        evaporatePh();
        //System.out.println("best ant:\n" + bestAnt + "\n:: " + bestCoverage);
        
        if(run %10 == 0){
            System.out.println("best ant coverage: " + bestCoverage);
        }
        
        
        
        topologyAR.add( new Topology( convertNodes(bestAnt.antSolutionNode)) ) ;
        
        
        }
        //end while not end condition
        

        double coverage;
        double lifetime;

        
        TopologyDrawer.draw(pos);
        
        coverage= pos.coverage();
        lifetime = pos.lifetime();                 
        System.out.println("POS Coverage: "+ coverage + " Lifetime: " + lifetime + " Ratio (coverage/LifeTime): " + coverage/lifetime );
        
        //draw the best ant from each run
//        int k=0;
//        for(Topology antD: topologyAR){
//            coverage= antD.coverage();
//            lifetime = antD.lifetime();                 
//            System.out.println("run: "+ k+" Coverage: "+ coverage + " Lifetime: " + lifetime + " Ratio (coverage/LifeTime): " + coverage/lifetime );
//
//            TopologyDrawer.draw(antD);
//            k++;
//        }
        
        
        Topology antD;
        antD = topologyAR.get(topologyAR.size()/2);
        TopologyDrawer.draw(antD);

        antD = topologyAR.get(topologyAR.size()-1);
        TopologyDrawer.draw(antD);

    }

    
    public void layPheromones(ArrayList<Edge> edges){
        for(Edge edge : edges){
            edge.pheromone += Params.phLevelMark;
            if(edge.pheromone > Params.phLevelMax){
                edge.pheromone = Params.phLevelMax;
            }
        }
    }

    public void evaporatePh(){
        for(Node node : g.nodes){
            for(Edge edge : node.edges){
                edge.pheromone -= Params.phLevelFade;
                if(edge.pheromone < Params.phLevelMin){
                    edge.pheromone = Params.phLevelMin;
                }
            }
        }
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
    
    
    
    public void printAnts(){
        for (int i=0;i<ants.size(); i++){
            System.out.println( ants.get(i) );
        }
        
    }
    
}
