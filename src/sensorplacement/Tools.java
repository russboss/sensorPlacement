/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sensorplacement;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author nathan
 */
public class Tools {

    public static Random r = new Random();

    public static Point minPoint(ArrayList<Circle> circles) {
        double minx = Double.MAX_VALUE;
        double miny = Double.MAX_VALUE;

        for (Circle c : circles) {
            double x = c.x - c.r;
            double y = c.y - c.r;
            if (x < minx) {
                minx = x;
            }
            if (y < miny) {
                miny = y;
            }
        }

        return new Point(minx, miny);
    }

    public static Point maxPoint(ArrayList<Circle> circles) {
        double maxx = 0;
        double maxy = 0;

        for (Circle c : circles) {
            double x = c.x + c.r;
            double y = c.y + c.r;
            if (x > maxx) {
                maxx = x;
            }
            if (y > maxy) {
                maxy = y;
            }
        }

        return new Point(maxx, maxy);
    }

    private static Edge[] extendEdgeArray(Edge[] array) {
        Edge[] temp = array.clone();
        array = new Edge[array.length + 1];
        System.arraycopy(temp, 0, array, 0, temp.length);
        return array;
    }

    public static void printPath(ArrayList<Node> nodes) {
        System.out.println("------------------ Printing Path ------------------");
        for (Node n : nodes) {
            System.out.println(n.id);
        }
    }

    public static double randomClamped() {
        return Math.random() - Math.random();
    }

    public static boolean dominates(double c1, double l1, double c2, double l2) {
        if ((c1 >= c2 && l1 > l2)
         || (c1 > c2 && l1 >= l2)) {
            return true;
        } else {
            return false;
        }
    }
}
