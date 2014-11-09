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
public class Circle {

    double x, y, r;

    public Circle(double x, double y, double r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public Circle(double x, double y) {
        this.x = x;
        this.y = y;
        this.r = Params.Rs;
    }

    public boolean intersects(Circle other) {
        double xsqr = Math.pow((x - other.x), 2);
        double ysqr = Math.pow((y - other.y), 2);
        double rsqr0 = Math.pow((r - other.r), 2);
        double rsqr1 = Math.pow((r + other.r), 2);

        return (rsqr0 <= (xsqr + ysqr) && (xsqr + ysqr) <= rsqr1);
    }

    public boolean contains(Point p) {
        double dist = Math.sqrt(Math.pow((x - p.x), 2) + Math.pow((y - p.y), 2));
        return dist <= r;
    }

    /*public boolean intersects(Circle c2) {
        double dist = Math.sqrt(Math.pow((x - c2.x), 2) + Math.pow((y - c2.y), 2));
        if (Math.abs((r - c2.r)) <= dist && dist <= (r + c2.r)) {
            return true;
        } else {
            return false;
        }
    }*/
}
