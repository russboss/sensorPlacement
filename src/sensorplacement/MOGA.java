/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sensorplacement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author nathan
 */
public class MOGA {

    /**
     * Each parent is randomly mated with another to produce child Each
     * coordinate of the child is modified with probability m Fitness is
     * proportional to the number of individuals that dominate it N individuals
     * with the best fitness are passed to the next generation
     */
    ArrayList<Chromosome> pop;

    public MOGA() {
        int d = Params.numSensors * 2;
        pop = new ArrayList();
        //create parents
        for (int i = 0; i < Params.popsize; i++) {
            Chromosome c = new Chromosome(d);
            c.updateCoverageLifetime();
            pop.add(c);
        }

        updateFitnessValues();
    }

    public void solve() {
        for (int i = 0; i < Params.I; i++) {
            System.out.println(avg());
            createChildren();
            updateFitnessValues();
            sortPop();
            prunePop();
        }
        
        printBest();
    }

    private void sortPop() {
        Collections.sort(pop, new ChromosomeComparator());
    }
    
    private double avg() {
        double clavg = 0;
        for(Chromosome c : pop) {
            clavg += (c.coverage + c.lifetime)/2.0;
        }
        return clavg/pop.size();
    }
    
    private double printBest() {
        double bests = 0;
        Chromosome bestc = pop.get(0);
        for(Chromosome c : pop) {
            Topology t = c.toTopology();
            if(c.f<1) continue;
            
            double score = (c.coverage + c.lifetime)/2.0;
            
            if(!t.fullyConnected()) score = score/2;
            
            System.out.println("score: " + score + " > " + bests);
            if(score>bests) {
                bests = score;
                bestc = c;
            }
        }
        
        Topology t = bestc.toTopology();
        TopologyDrawer.draw(t);
        System.out.println("best: " + bests);
        
        return bests;
    }
    
    public void prunePop() {
        while(pop.size()>Params.popsize) {
            pop.remove(pop.size()-1);
        }
    }

    private void createChildren() {
        for (int i = 0; i < Params.popsize; i++) {
            int j = Tools.r.nextInt(pop.size());
            Chromosome p1 = pop.get(i);
            Chromosome p2 = pop.get(j);
            Chromosome c = p1.crossover(p2);
            c.mutate();
            c.updateCoverageLifetime();
            pop.add(c);
        }
    }

    private void updateFitnessValues() {
        //fitness for c inversly proportional to the number of individuals that dominate c
        for (Chromosome c1 : pop) {
            double sum = 0;
            for (Chromosome c2 : pop) {
                if (c2.dominates(c1)) {
                    sum++;
                }
            }
            double fitness = 1 / (1 + sum);
            c1.f = fitness;
        }
    }

    public class ChromosomeComparator implements Comparator<Chromosome> {

        @Override
        public int compare(Chromosome c1, Chromosome c2) {
            if ((c2.f - c1.f) < 0) {
                return -1;
            }
            if ((c2.f - c1.f) > 0) {
                return 1;
            }
            return 0;
        }
    }
}
