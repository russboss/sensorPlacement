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
public class Sampler {

    static ArrayList<Point> points;

    public static void generate(double minx, double miny, double maxx, double maxy) {
        points = new ArrayList();
        for (int i = 0; i < Params.numSamples; i++) {
            points.add(new Point(minx, miny, maxx, maxy));
        }
    }

    public static double area(ArrayList<Circle> circles) {
        Point min = Tools.minPoint(circles);
        Point max = Tools.maxPoint(circles);
        double tarea = (max.x - min.x) * (max.y - min.y);

        double in = 0;
        double tot = Params.numSamples;

        ArrayList<Thread> threads = new ArrayList();
        ArrayList<AreaThread> athreads = new ArrayList();

        for (int i = 0; i < Params.sampleThreads; i++) {
            AreaThread at = new AreaThread(min, max, circles);
            Thread t = new Thread(at);
            athreads.add(at);
            threads.add(t);
            t.start();
            /*Point p = new Point(min.x, min.y, max.x, max.y);
             for (Circle c : circles) {
             if (c.contains(p)) {
             in++;
             break;
             }
             }*/

        }
        
        for(Thread t : threads) {
            try {
                t.join();
            } catch(Exception e) { }
        }
        
        for(AreaThread t : athreads) {
            in += t.in;
        }

        double ratio = in / tot;
        double area = ratio * tarea;
        return area;
    }

    public static class AreaThread implements Runnable {

        private Point min;
        private Point max;
        private ArrayList<Circle> circles;
        public int in;

        public AreaThread(Point min, Point max, ArrayList<Circle> circles) {
            this.min = min;
            this.max = max;
            this.circles = circles;
        }

        @Override
        public void run() {
            for (int i = 0; i < Params.numSamples/Params.sampleThreads; i++) {
                Point p = new Point(min.x, min.y, max.x, max.y);
                for (Circle c : circles) {
                    if (c.contains(p)) {
                        in++;
                        break;
                    }
                    
                }
            }
        }
    }
}
