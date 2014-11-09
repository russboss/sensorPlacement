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
public class PSO {
    ArrayList<Particle> swarm;
    ArrayList<Particle> elites;
    int g;
    
    public PSO() {
        int d = Params.numSensors * 2;
        swarm = new ArrayList();
        elites = new ArrayList();
        g = 0;
        
        //create parents
        for (int i = 0; i < Params.popsize; i++) {
            Particle p = new Particle(d);
            p.updateCoverageLifetime();
            swarm.add(p);
        }
        
        for(Particle p : swarm) {
            p.updateFitness(this);
        }
        
        initElites();
        
    }
    
    private void initElites() {
        for (Particle p1 : swarm) {
            boolean dominated = false;
            for (Particle p2 : swarm) {
                if (p2.dominates(p1)) {
                    dominated = true;
                    break;
                }
            }
            if(!dominated) elites.add(p1);
        }
    }
    
    public void solve() {
        for (int i = 0; i < Params.I; i++) {
            System.out.println(avg());
            for(Particle particle : swarm) {
                particle.updateFitness(this);
                particle.updatePosition(getBest());
            }
            //double f = (getBest().bestCoverage + getBest().bestLifetime)/2;
            //System.out.println(f);
        }
        
        Topology t = new Topology(getBest().bestx, true);
        TopologyDrawer.draw(t);
    }
    
    private double avg() {
        double clavg = 0;
        for(Particle p : swarm) {
            clavg += (p.coverage + p.lifetime)/2.0;
        }
        return clavg/swarm.size();
    }
    
    public Particle getBest(){
        double bestf = 0;
        Particle best = swarm.get(0);
        
        for(Particle p : elites) {
            if(p.f>bestf) {
                bestf = p.f;
                best = p;
            }
        }
        
        return best;
    }
}
