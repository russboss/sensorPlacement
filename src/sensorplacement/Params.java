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
    
    public static int numMaxRuns = 10;
            
            
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
    
    
    
    //ACO
    
    //maximum and minimum levels of pheromone on an edge 
    //100.00 is chosen for simpler graidient management

    public static int numAnts = 3;
    
    public static int numNodeMult = 3;
    
    
    public static double phLevelMax = 1.00;
    public static double phLevelMin = 0.10;
    public static double phLevelInit = 0.5;
    public static double cleanThreashold = 0.2;
    
    public static double phLevelFade = 0.02;
    public static double phLevelMark = 0.11;
    
    public static int infiniteAntMult = 2;
    

    
}
