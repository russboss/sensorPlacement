/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sensorplacement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import sensorplacement.Sensor.SensorComparator;

/**
 *
 * @author nathan
 */
public class Topology {

    ArrayList<Sensor> sensors = new ArrayList();
    Graph g;
    Sensor hecn;

    public Topology(ArrayList<Sensor> sensors) {
        hecn = new Sensor(Params.xsize / 2, Params.ysize / 2);
        hecn.id = "hecn";
        this.sensors = sensors;
        constructGraph();
        
    }
    
    public Topology() {
        hecn = new Sensor(Params.xsize / 2, Params.ysize / 2);
        hecn.id = "hecn";
        
        sensors = new ArrayList();
        for(int i=0; i<Params.numSensors; i++) {
            double x = Params.xsize * Math.random();
            double y = Params.ysize * Math.random();
            Sensor s = new Sensor(x,y);
            Sensor n = getClosestNode(s);
            
            //if s cannot communicate with n
            if(!s.Rc.contains(n.point())) {
                Point c = n.closestPointOfComm(s.point());
                s = new Sensor(c.x, c.y);
            } 
            sensors.add(s);
        }
        
        constructGraph();
        
    }

    
    public Topology(int numSensors) {
        hecn = new Sensor(Params.xsize / 2, Params.ysize / 2);
        hecn.id = "hecn";
        
        sensors = new ArrayList();
        for(int i=0; i<numSensors; i++) {
            double x = Params.xsize * Math.random();
            double y = Params.ysize * Math.random();
            Sensor s = new Sensor(x,y);
            Sensor n = getClosestNode(s);
            
            //if s cannot communicate with n
            if(!s.Rc.contains(n.point())) {
                Point c = n.closestPointOfComm(s.point());
                s = new Sensor(c.x, c.y);
            } 
            sensors.add(s);
        }
        
        constructGraph();
        
    }
        
    public Topology(double[] posvector, boolean randomize) {
        hecn = new Sensor(Params.xsize / 2, Params.ysize / 2);
        hecn.id = "hecn";
        
        ArrayList<Sensor> presensors = new ArrayList();
        int j=0;
        for(int i=0; i<Params.numSensors; i++) {
            double x = posvector[j];
            j++;
            double y = posvector[j];
            j++;
            
            Sensor s = new Sensor(x,y);
            presensors.add(s);
        }
        
        Collections.sort(presensors, new SensorComparator());
        
        sensors = new ArrayList();
        for(int i=0; i<Params.numSensors; i++) {
            
            Sensor s = presensors.get(i);
            Sensor n = getClosestNode(s);
            
            //if s cannot communicate with n
            if(randomize) {
                while(!s.Rc.contains(n.point())) {
                    Point c = s.point();
                    double deltax = Math.random()*(n.Rc.x > c.x ? 1 : -1);
                    double deltay = Math.random()*(n.Rc.y > c.y ? 1 : -1);
                    
                    s = new Sensor(c.x+deltax, c.y+deltay);
                }
            } else if(!s.Rc.contains(n.point())) {
                Point c = n.closestPointOfComm(s.point());
                s = new Sensor(c.x, c.y);
            }
            sensors.add(s);
        }
        
        constructGraph();
        
    }
    

    public double coverage() {
        ArrayList<Circle> srange = new ArrayList();
        for (Sensor s : sensors) {
            if(isConnected(s, hecn)) srange.add(s.Rs);
        }
        if(srange.isEmpty()) return 0;
        else return Sampler.area(srange)/(Params.xsize*Params.ysize);
    }

    public double lifetime() {
        double t = 0;
        while (hasEnergy()) {
            if (transmit()) {
                t++;
            }
        }
        double lifetime = t / Params.E;
        return lifetime;
    }

    public boolean transmit() {
        
        for (Sensor s : sensors) {
            g.computePaths(g.getNode("hecn"));
            LinkedList<Sensor> route = getRoute(s);
            g.resetNodes();
            if(!route.isEmpty()) route.pop();
            if (!s.transmit(route)) {
                return false;
            }
        }
        
        
        return true;
    }

    private LinkedList<Sensor> getRoute(Sensor sensor) {
        //this.constructGraph();
        LinkedList<Sensor> route = new LinkedList();
        //update graph weights
        for (int i = 0; i < sensors.size() - 1; i++) {
            Sensor s = sensors.get(i);
            Node n = g.getNode(s.id);
            double w = 1 / (s.energy+0.01);
            n.setWeights(w);
        }
        
        ArrayList<Node> path = g.getShortestPathTo("hecn", sensor.id);
        
        
        for(Node n : path) {
            
            if(n.id.equals("hecn")) break;
            
            //route.add(getSensor(n.id));
            route.add(n.sensor);
        }

        return route;
    }

    private boolean hasEnergy() {
        for (Sensor s : sensors) {
            if (s.energy <= 0) {
                return false;
            }
        }
        return true;
    }

    public void constructGraph() {
        g = new Graph();
        for (Sensor s : sensors) {
            g.addNode(new Node(s));
        }

        g.addNode(new Node(hecn));
        for (int i = 0; i < sensors.size(); i++) {
            Sensor s1 = sensors.get(i);
            Node n1 = g.getNode(s1.id);
            double w1 = 1 / (s1.energy+0.01);
            for (int j = i; j < sensors.size(); j++) {
                Sensor s2 = sensors.get(j);
                Point p2 = new Point(s2.Rc.x, s2.Rc.y);

                Node n2 = g.getNode(s2.id);
                double w2 = 1 / (s2.energy+0.01);
                if (s1.Rc.contains(p2)) {
                    Edge e1 = new Edge(n1, w1);
                    Edge e2 = new Edge(n2, w2);
                    n1.addEdge(e2);
                    n2.addEdge(e1);
                }
            }

            if (s1.Rc.contains(hecn.point())) {
                Node nh = g.getNode("hecn");
                Edge e1 = new Edge(n1, w1);
                Edge eh = new Edge(nh, 0);
                n1.addEdge(eh);
                nh.addEdge(e1);
            }
        }

    }
    
    public Sensor getSensor(String id) {
        for(Sensor s : sensors) {
            if(s.id.equals(id)) return s;
        }
        return null;
    }
    
    public Sensor getClosestNode(Sensor s) {
        double mind = s.point().d(hecn.point());
        Sensor mins = hecn;
        
        for(Sensor other : sensors) {
            double d = s.point().d(other.point());
            if(d<mind) {
                mind = d;
                mins = other;
            }
        }
        
        return mins;
    }
    
    public boolean isConnected(Sensor s1, Sensor s2) {
        g.computePaths(g.getNode(s1.id));
        int path = g.getShortestPathTo(s1.id, s2.id).size();
        g.resetNodes();
        if(path==1) return false;
        else return true;   
    }
    
    public boolean fullyConnected() {
        g.computePaths(g.getNode(hecn.id));
        for(Sensor s : sensors) {
            int path = g.getShortestPathTo("hecn", s.id).size();
            if(path<=1) return false;
        }
        return true;
    }
    
    public double[] getCoordinates() {
        int d =  this.sensors.size() * 2;
        double[] c = new double[d];
        
        int i=0;
        for(Sensor s : sensors) {
            c[i++] = s.point().x;
            c[i++] = s.point().y;
        }
        return c;
    }
}
