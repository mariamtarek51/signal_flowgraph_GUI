import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;

import javax.swing.JPanel;

public class GUI extends JPanel {

    private static final long serialVersionUID = 1L;

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        int numOfNodes = NeedsData.numOfNodes; //number of nodes the user entered
        double[][] gains = NeedsData.segmentsGains; //gains[][] contains the weight in each edge
        //coordiantes variables
        int xDisplace = NeedsData.width / (numOfNodes + 1);
        int yCenter = (NeedsData.height - 120) / 2;
        int nodeRadius = 30; //raduis of the vertex
        int tanBaseUp = (int) (yCenter - nodeRadius);
        int tanBaseDown = (int) (yCenter + nodeRadius);
        //variables needed in case selfloop-source vertex==destinationvertex-
        float selfLoopC1 = yCenter - 4 * nodeRadius;
        float selfLoopC2 = yCenter + 4 * nodeRadius;
        Path2D.Double path = new Path2D.Double();
        Graphics2D g2 = (Graphics2D) g;

        Font font = new Font("Serif", Font.PLAIN, 24);
        g.setFont(font);
        if (numOfNodes > 0) {
            g.setColor(Color.black);
            g.fillOval(xDisplace - nodeRadius, yCenter - nodeRadius,   //create first vertex which is the entry of the graph-R(S)
                    nodeRadius * 2, nodeRadius * 2);
            g.fillOval(xDisplace * (numOfNodes) - nodeRadius, yCenter  //create last vertex c(s)
                    - nodeRadius, nodeRadius * 2, nodeRadius * 2);

            g.setColor(Color.black);
            g.drawString("R(s)", xDisplace - nodeRadius, yCenter - 2
                    * nodeRadius);
            if (numOfNodes > 1)
                g.drawString("C(s)", xDisplace * (numOfNodes) - nodeRadius,
                        yCenter - 2 * nodeRadius);

        }
        g.setColor(Color.black);
        for (int i = 1; i < numOfNodes - 1; i++)   //create the vertices (n-2) as we already have created first anf last vertex
            g.fillOval(xDisplace * (i + 1) - nodeRadius, yCenter - nodeRadius,
                    nodeRadius * 2, nodeRadius * 2);

        g.setColor(Color.white);
        for (int i = 0; i < numOfNodes; i++)  //give number for each vertex in the graph
            g.drawString("" + (i + 1), xDisplace * (i + 1) - nodeRadius + 18,
                    yCenter + 8);
        int x;
        for (int sourcevertex = 0; sourcevertex < numOfNodes; sourcevertex++) {
            for (int destinationVertex = 0; destinationVertex < numOfNodes; destinationVertex++) {
                if (gains[sourcevertex][destinationVertex] != 0) {

                    if (sourcevertex == destinationVertex) { //draw a self loop
                        // drawing arc
                        g.setColor(Color.blue);
                        path = new Path2D.Double(); //create a path
                        path.moveTo(xDisplace * (sourcevertex + 1), tanBaseUp);
                        x = xDisplace * (sourcevertex + 1) - 3 * nodeRadius;
                        path.curveTo(x, selfLoopC1, x, selfLoopC2, xDisplace
                                * (sourcevertex + 1), tanBaseDown);
                        g2.draw(path);

                        x = x + nodeRadius - 5;
                        // drawing arrow
                        path = new Path2D.Double();
                        path.moveTo(x + 12, yCenter - 10);
                        path.lineTo(x, yCenter + 12);
                        path.lineTo(x - 12, yCenter - 10);
                        g2.fill(path);
                        // drawing gain text
                        g.setColor(Color.black);
                        g.drawString(gains[sourcevertex][destinationVertex] + "", xDisplace
                                * (sourcevertex + 1) - nodeRadius, yCenter - 2
                                * nodeRadius);

                    } else if (destinationVertex - sourcevertex == 1) { //destination vertex is next to source vertex so we need to draw a line to connect them
                        // drawing arc
                        g.setColor(Color.blue);
                        g.drawLine((sourcevertex + 1) * xDisplace + nodeRadius, //draw a line between source and destination vertices
                                yCenter, (destinationVertex + 1) * xDisplace - nodeRadius,
                                yCenter);
                        // drawing arrow
                        g.setColor(Color.gray);
                        x = (destinationVertex + sourcevertex + 2) * xDisplace / 2;
                        path = new Path2D.Double();
                        path.moveTo(x, yCenter - 12);
                        path.lineTo(x, yCenter + 12);
                        path.lineTo(x + 24, yCenter);
                        g2.fill(path);
                        // drawing gain text
                        g.setColor(Color.black);
                        g.drawString(gains[sourcevertex][destinationVertex] + "", x, yCenter - 20);

                    } else if (sourcevertex > destinationVertex) { //in case the source node is after the destination node -as feedback-
                        // drawing arc
                        g.setColor(Color.blue);
                        path = new Path2D.Double();//create a path
                        path.moveTo(xDisplace * (sourcevertex + 1), tanBaseDown);
                        x = xDisplace * (destinationVertex + sourcevertex + 2) / 2; //cal new x
                        path.quadTo(x, yCenter + (sourcevertex - destinationVertex) * xDisplace / 2, xDisplace * (destinationVertex + 1), tanBaseDown);
                        g2.draw(path);

                        // drawing arrow
                        g.setColor(Color.gray);
                        path = new Path2D.Double();
                        path.moveTo(x - 12, yCenter + (sourcevertex - destinationVertex) * xDisplace / 4 + 12);
                        path.lineTo(x + 12, yCenter + (sourcevertex - destinationVertex) * xDisplace / 4);
                        path.lineTo(x + 12, yCenter + (sourcevertex - destinationVertex) * xDisplace / 4 + 24);
                        g2.fill(path);

                        // drawing gain text
                        g.setColor(Color.black);
                        g.drawString(gains[sourcevertex][destinationVertex] + "", x - 12, yCenter + (sourcevertex - destinationVertex) * xDisplace / 4 - 6);

                    } else {
                        //in case destination vertex is after source vertex
                        // drawing arc
                        g.setColor(Color.blue);
                        path = new Path2D.Double();
                        path.moveTo(xDisplace * (sourcevertex + 1), tanBaseUp);
                        x = xDisplace * (destinationVertex + sourcevertex + 2) / 2;//new x
                        path.quadTo(x, yCenter - (destinationVertex - sourcevertex) * xDisplace / 2, xDisplace * (destinationVertex + 1), tanBaseUp);
                        g2.draw(path);

                        // drawing arrow
                        g.setColor(Color.gray);
                        path = new Path2D.Double();
                        path.moveTo(x + 12, yCenter - (destinationVertex - sourcevertex) * xDisplace / 4 - 12);
                        path.lineTo(x - 12, yCenter - (destinationVertex - sourcevertex) * xDisplace / 4);
                        path.lineTo(x - 12, yCenter - (destinationVertex - sourcevertex) * xDisplace / 4 - 24);
                        g2.fill(path);
                        // drawing gain text
                        g.setColor(Color.black);
                        g.drawString(gains[sourcevertex][destinationVertex] + "", x - 12, yCenter - (destinationVertex- sourcevertex) * xDisplace / 4 + 24);
                    }
                }
            }

        }
    }

}