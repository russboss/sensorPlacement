/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sensorplacement;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Individual in a population algorithm.
 * Used by PSO and GA.
 * 
 * @author nathan
 */
public class Individual {

    double[] x;
    double f = 0;

    //sensor data
    double lifetime = 0;
    double coverage = 0;

    public Individual(int d) {
        Topology t = new Topology();
        x = t.getCoordinates();
    }

    public Individual(double[] x) {
        this.x = x;
    }

    public Topology toTopology() {
        ArrayList<Sensor> sensors = new ArrayList();

        int i = 0;
        for (int s = 0; s < Params.numSensors; s++) {
            double x = this.x[i];
            i++;
            double y = this.x[i];
            i++;
            Sensor sensor = new Sensor(x, y);
            sensors.add(sensor);
        }

        Topology t = new Topology(sensors);
        return t;
    }

    public void updateCoverageLifetime() {
        Topology t = this.toTopology();
        this.coverage = t.coverage();
        this.lifetime = t.lifetime();
    }

    public boolean dominates(Individual c) {
        if ((coverage >= c.coverage && lifetime > c.lifetime) ||
            (coverage > c.coverage && lifetime >= c.lifetime)) {
            return true;
        } else {
            return false;
        }
    }


}
