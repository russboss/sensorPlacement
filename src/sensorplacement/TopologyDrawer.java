/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sensorplacement;

import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;

/**
 *
 * @author nathan
 */
public class TopologyDrawer extends Component {

    static int windowcount = 0;
    Topology t;

    public TopologyDrawer(Topology t) {
        this.t = t;
    }

    public static void draw(Topology t) {
        windowcount++;
        if(windowcount>3) return;
        
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new TopologyDrawer(t));
        int frameWidth = (int) (Params.xsize * Params.scalingFactor*1.2);

        int frameHeight = (int) (Params.ysize * Params.scalingFactor*1.2);

        frame.setSize(frameWidth, frameHeight);

        frame.setVisible(true);
    }

    public void paint(Graphics g) {

        // Retrieve the graphics context; this object is used to paint shapes
        Graphics2D g2d = (Graphics2D) g;

        // Draw an oval that fills the window
        /**
         * The coordinate system of a graphics context is such that the origin
         * is at the northwest corner and x-axis increases toward the right
         * while the y-axis increases toward the bottom.
         */
        //g2d.drawLine(x, y, w, h);
        Graph graph = t.g;
        for (Sensor s : t.sensors) {
            int cx = (int) ((s.Rc.x) * Params.scalingFactor);
            int cy = (int) ((s.Rc.y) * Params.scalingFactor);

            int x = (int) ((s.Rc.x - s.Rc.r) * Params.scalingFactor);
            int y = (int) ((s.Rc.y - s.Rc.r) * Params.scalingFactor);
            int r = (int) (s.Rc.r * Params.scalingFactor) * 2;

            g2d.setColor(Color.CYAN);
            g2d.drawOval(x, y, r, r);
            g2d.setColor(Color.RED);
            g2d.drawOval(cx, cy, 3, 3);

            Node n = graph.getNode(s.id);

            for (Edge e : n.edges) {
                String id = e.target.id;
                int x2 = 0;
                int y2 = 0;
                if (id.equals("hecn")) {
                    x2 = (int) ((t.hecn.point().x) * Params.scalingFactor);
                    y2 = (int) ((t.hecn.point().y) * Params.scalingFactor);
                } else {
                    Sensor s2 = t.getSensor(id);
                    x2 = (int) ((s2.Rc.x) * Params.scalingFactor);
                    y2 = (int) ((s2.Rc.y) * Params.scalingFactor);
                }
                g2d.drawLine(cx, cy, x2, y2);
            }

        }

        int x = (int) ((t.hecn.point().x) * Params.scalingFactor);
        int y = (int) ((t.hecn.point().y) * Params.scalingFactor);
        g2d.setColor(Color.BLUE);
        g2d.fillOval(x-1, y-1, 3, 3);
        g2d.drawOval(x-1, y-1, 3, 3);

    }
}
