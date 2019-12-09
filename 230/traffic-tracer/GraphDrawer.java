//DrawingPanel
import java.awt.Graphics;
import java.awt.event.ActionEvent;

import java.util.ArrayList;

import javax.swing.JPanel; //painting panel

import java.awt.event.ActionListener;
public class GraphDrawer extends JPanel implements ActionListener {


    private ArrayList<Graph> toDraw;
    private ArrayList<Graph> toPlot;

    /**
     * constructor for graphdrawer
     * @param toDraw array of labels and axis points
     * @param toPlot array of dataplots to draw
     **/
    public GraphDrawer(ArrayList<Graph> toDraw, ArrayList<Graph> toPlot){
        this.toDraw = toDraw;
        this.toPlot = toPlot;
    }


    /**
     *calls paint on all of the objects in the arrays
     *@param g the graphics control
     **/
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        for(Graph f: toDraw){
            f.paint(g);
        }

        for(Graph f: toPlot){
            f.paint(g);
        }


    }

    /**
     *called when an action in performed
     *@param arg0 must be taken as an argument
     **/
    @Override
    public void actionPerformed(ActionEvent arg0) {
        repaint();  // calls paintComponent()
        // (default behaviour for JPanel)
    }
}
