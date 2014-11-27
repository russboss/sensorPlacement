/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sensorplacement;

import java.util.ArrayList;
import javax.swing.RowFilter;

/**
 *
 * @author nathan
 */
public class ACO {
    //ArrayList<Particle> swarm;
    ArrayList<Ant> ants = new ArrayList();
    int startSensors =Params.numSensors;
    
    //Graph g;
    Graph solution;
    public ACO() {
        startSensors = Params.numSensors * Params.numNodeMult;
        
    }
    
    
    public void solve() {

        double coverage;
        double lifetime;


        Topology pos = new Topology(startSensors);
        
        double tempCoverage;
        solution = (Graph)pos.g.clone();//new ArrayList();
        
        //add edges
        Topology temp = new Topology( convertNodes(solution.nodes) );
        solution = temp.g;
        
        //System.out.println("Cloned");
        //solution = pos.g;
        Ant bestAnt = null;
        double bestCoverage = -0.1;
        

        
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
            ant = new Ant(i);
            ants.add(ant);
        }

        //while not end condition //numColonyRuns
        for(int run =0; run < Params.numMaxRuns; run++){

            //run all ants  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            //choose best ant topology !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            for(Ant ant2 : ants){
                //update the graph for the ant
                ant2.setGraph(solution);
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
            evaporatePh( bestAnt.getGraph() );
            //System.out.println("best ant:\n" + bestAnt + "\n:: " + bestCoverage);

            //if(run %10 == 0){
                System.out.println("best ant coverage: " + bestCoverage);
            //}

            //topologyAR.add( new Topology( convertNodes(bestAnt.antSolutionNode)) ) ;
            solution = bestAnt.getGraph();
        }
        //end while not end condition
        
        
        

        
        TopologyDrawer.draw(pos);
        
        coverage= pos.coverage();
        lifetime = pos.lifetime();                 
        System.out.println("POS Coverage: "+ coverage + " Lifetime: " + lifetime + " Ratio (coverage/LifeTime): " + coverage/lifetime );
        

        Topology antD = new Topology( convertNodes( solution.cleanNodeByEdgePheromone(Params.cleanThreashold) ) );
        TopologyDrawer.draw(antD);
        coverage= antD.coverage();
        lifetime = antD.lifetime();                 
        System.out.println("Coverage: "+ coverage + " Lifetime: " + lifetime + " Ratio (coverage/LifeTime): " + coverage/lifetime );
        

    }

    
    public void layPheromones(ArrayList<Edge> edges){
        for(Edge edge : edges){
            edge.pheromone += Params.phLevelMark;
            if(edge.pheromone > Params.phLevelMax){
                edge.pheromone = Params.phLevelMax;
            }
        }
    }

    public void evaporatePh(Graph a){
        for(Node node : a.nodes){
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
    
    
    
}
