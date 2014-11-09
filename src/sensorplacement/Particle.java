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
public class Particle extends Individual {

    double[] v;

    double[] bestx;
    double bestLifetime = -Double.MAX_VALUE;
    double bestCoverage = -Double.MAX_VALUE;

    public Particle(int d) {
        super(d);
        init();
    }

    public Particle(double[] x) {
        super(x);
        init();
    }
    
    private void init() {
        //adjust position so that topology is valid
        Topology t = new Topology(x, true);
        coverage = t.coverage();
        lifetime = t.lifetime();
        
        v = new double[x.length];
        for(int i=0; i<x.length; i++) {
            v[i] = Tools.randomClamped()/40;
        }
        
        bestx = x.clone();
        bestLifetime = lifetime;
        bestCoverage = coverage;
    }

    public void updatePosition(Particle g) {
        for (int i = 0; i < v.length; i++) {
            double vnew = v[i];
            vnew = Math.random() * Params.phi1 * (bestx[i] - x[i])
                    + Math.random() * Params.phi2 * (g.bestx[i] - x[i])
                    + vnew * Params.w;

            v[i] = vnew;

        }

        for (int i = 0; i < x.length; i++) {
            x[i] += v[i];
        }
    }

    public void updateFitness(PSO pso) {

        //adjust position so that topology is valid
        Topology c = new Topology(x, true);
        x = c.getCoordinates();
        coverage = c.coverage();
        lifetime = c.lifetime();
        
        //update personal best
        if(Tools.dominates(coverage, lifetime, bestCoverage, bestLifetime)) {
            bestCoverage = coverage;
            bestLifetime = lifetime;
        }
        
        //update elites
        boolean elite = false;
        ArrayList<Particle> elites = pso.elites;
        for(int i=0; i<elites.size(); i++) {
            Particle e = elites.get(i);
            if(Tools.dominates(coverage, lifetime, e.bestCoverage, e.bestLifetime)) {
                elites.remove(i);
                elite = true;
                i--;
            }
            if(!Tools.dominates(e.bestCoverage, e.bestLifetime, coverage, lifetime)) {
                elite = true;
            }
        }
        
        if(elite && !elites.contains(this)) {
            elites.add(this);
        }
        
        //count the number of particles that this dominates
        f = 0;
        for(Particle p : pso.swarm) {
            if(Tools.dominates(bestCoverage, bestLifetime, p.bestCoverage, p.bestLifetime)) {
                f++;
            }
        }   
    }
}
