/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sensorplacement;

import java.util.ArrayList;
import java.util.UUID;

/**
 *
 * @author nathan
 */
public class SensorPlacement {
    public static void test1() {
        ArrayList<Sensor> sensors = new ArrayList();
        //top left spoke
        //spokes are in order from edge to center
        sensors.add(new Sensor(3.1, 9.8));
        sensors.add(new Sensor(4.5, 8.2));
        sensors.add(new Sensor(4.1, 6.1));
        
        //bottom left spoke
        sensors.add(new Sensor(4.1, 2.1));
        sensors.add(new Sensor(6.1, 3));
        sensors.add(new Sensor(7.2, 4.1));
        
        //right spoke
        sensors.add(new Sensor(10.1, 7.5)); sensors.add(new Sensor(10, 4)); 
        sensors.add(new Sensor(9.4, 6)); 
        sensors.add(new Sensor(7.5, 6.9)); 
        
        //other
        Sensor s1 = new Sensor(11, 11);
        s1.id = "s1";
        Sensor s2 = new Sensor(10, 10);
        s2.id = "s2";
        Sensor s3 = new Sensor(9, 11);
        s3.id = "s3";
        
        Sensor s4 = new Sensor(9, 9);
        s4.id = "s4";
        
        sensors.add(s1);
        sensors.add(s2);
        sensors.add(s3);
        sensors.add(s4);
        
        //more to test
        sensors.add(new Sensor(8.4, 6.1)); 
        sensors.add(new Sensor(7.3, 8.2)); 
        sensors.add(new Sensor(6.2, 7.3)); 
        sensors.add(new Sensor(5.1, 6.4)); 
        
        sensors.add(new Sensor(2.4, 6.1)); 
        sensors.add(new Sensor(3.3, 7.2)); 
        sensors.add(new Sensor(4.2, 5.3)); 
        sensors.add(new Sensor(5.1, 4.4)); 
        
        sensors.add(new Sensor(1.4, 4.1)); 
        sensors.add(new Sensor(3.3, 3.2)); 
        sensors.add(new Sensor(6.2, 2.3)); 
        sensors.add(new Sensor(3.1, 1.4)); 
        
        sensors.add(new Sensor(7.4, 4.1)); 
        sensors.add(new Sensor(8.3, 3.2)); 
        sensors.add(new Sensor(7.2, 4.3)); 
        sensors.add(new Sensor(6.1, 5.4)); 
        
        sensors.add(new Sensor(1.4, 7.1)); 
        sensors.add(new Sensor(2.3, 8.2)); 
        sensors.add(new Sensor(3.2, 7.3)); 
        sensors.add(new Sensor(4.1, 6.4));
        
        
        System.out.println("main sensors: " + sensors.size());
        Topology t = new Topology(sensors);
        
        
        System.out.println("lifetime: " + t.lifetime());
        System.out.println("coverage: " + t.coverage());
        TopologyDrawer.draw(t);
        
        
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        /*Circle c1 = new Circle(10,10,1);
        Circle c2 = new Circle(10,11,1);
        Circle c3 = new Circle(11,10,1);
        
        ArrayList<Circle> c = new ArrayList();
        c.add(c1);
        //c.add(c2);
        c.add(c3);
        
        System.out.println("area: " + Sampler.area(c));
        System.out.println(UUID.randomUUID().toString());*/
        //test1();
        /*double angle = Math.asin(0.57142857);
        double x = 4.9*Math.sin(angle);
        System.out.println(x);*/
        
        //Topology t = new Topology();
        //TopologyDrawer.draw(t);
        
        //Topology c = new Topology(t.getCoordinates(), false);
        //TopologyDrawer.draw(c);
        
        //test1();
        //MOGA ga = new MOGA();
        //ga.solve();
        PSO pso = new PSO();
        pso.solve();
    }
    
}
