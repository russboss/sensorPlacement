/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sensorplacement;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.UUID;

/**
 *
 * @author nathan
 */
public class Sensor {
    Circle Rs;
    Circle Rc;
    String id;
    int energy;
    
    public Sensor(double x, double y) {
        Rs = new Circle(x,y,Params.Rs);
        Rc = new Circle(x,y,Params.Rc);
        energy = Params.E;
        id = UUID.randomUUID().toString();
    }
    
    public boolean transmit(LinkedList<Sensor> route) {
        if(energy==0) return false;
        
        energy--;
        if(route.isEmpty()) {
            return true;
        }
        
        Sensor next = route.pop();
        
        return next.transmit(route);
    }
    
    public String toString() {
        String str = "(" + Rc.x + ", " + Rc.y + ")";
        return str;
        
    }
    
    public Point point() {
        return new Point(Rc.x, Rc.y);
    }
    
    public Point closestPointOfComm(Point p2) {
        Point p1 = this.point();
        
        double dx = p2.x-p1.x;
        double dy = p2.y-p1.y;
        
        //find angle of line between points
        double a = Math.max(p1.x, p2.x) - Math.min(p1.x, p2.x);
        double o = Math.max(p1.y, p2.y) - Math.min(p1.y, p2.y);
        double h = p1.d(p2);
        double angle = Math.asin(o/h);
        
        //find x and y change to this sensor to obtain closest point
        double xsign = (dx>0) ? 1 : -1;
        double ysign = (dy>0) ? 1 : -1;
        double deltax = (Params.Rc-0.01)*Math.cos(angle)*xsign;
        double deltay = (Params.Rc-0.01)*Math.sin(angle)*ysign;
        
        Point c = new Point(p1.x+deltax,p1.y+deltay);
        
        return c;
    }
    
    
    public static class SensorComparator implements Comparator<Sensor> {

        @Override
        public int compare(Sensor s1, Sensor s2) {
            Point hecn = new Point(Params.xsize / 2, Params.ysize / 2);
            double d1 = hecn.d(s1.point());
            double d2 = hecn.d(s2.point());
            if ((d2 - d1) > 0) {
                return -1;
            }
            if ((d2 - d1) < 0) {
                return 1;
            }
            return 0;
        }
    }
}
