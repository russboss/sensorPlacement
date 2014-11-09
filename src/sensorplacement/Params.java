/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sensorplacement;

/**
 *
 * @author nathan
 */
public class Params {
    
    public static int numSensors = 25;
    public static double Rs = 4;
    public static double Rc = 2;
    
    //energy for each node
    public static int E = 10;
    
    public static double gridsize = 20;
    public static double xsize = gridsize;
    public static double ysize = gridsize;
    
    public static double scalingFactor = 28;
    
    public static int numSamples = 10000; //4
    public static int sampleThreads = 4;
    
    //MOGA
    public static double m = 0.1;
    public static int popsize = 50;
    public static int I = 50;
    
    //PSO
    public static double phi1 = 1.49618;
    public static double phi2 = 1.49618;
    public static double w = 0.7298;
}
