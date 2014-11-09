/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sensorplacement;

import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author nathan
 */
public class Chromosome extends Individual {

    public Chromosome(int d) {
        super(d);
    }

    public Chromosome(double[] x) {
        super(x);
    }

    public void mutate() {
        for (int i = 0; i < x.length; i++) {
            if (Math.random() < Params.m) {
                x[i] = Math.random() * Params.gridsize;
            }
        }
    }

    public Chromosome crossover(Chromosome parent) {
        int split = Tools.r.nextInt(x.length - 1) + 1;
        double[] childx = new double[x.length];

        for (int i = 0; i < split; i++) {
            childx[i] = x[i];
        }

        for (int i = split; i < x.length; i++) {
            childx[i] = parent.x[i];
        }

        Chromosome child = new Chromosome(childx);
        return child;
    }


}
