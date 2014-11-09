/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sensorplacement;

import java.util.Random;

/**
 *
 * @author nathan
 */
public class Point {
    double x, y;
    
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public Point(double minx, double miny, double maxx, double maxy) {
        Random r = new Random();
        this.x = (Math.random()*(maxx-minx)) + minx;
        this.y = (Math.random()*(maxy-miny)) + miny;
    }
    
    public double d(Point p) {
        double xsq = Math.pow((p.x-this.x), 2);
        double ysq = Math.pow((p.y-this.y), 2);
        double d = Math.sqrt(xsq+ysq);
        return d;
    }
}
