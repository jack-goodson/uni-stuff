import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class DataPlots extends Graph {

    private int x1;
    private int x2;
    private int y1;
    private int y2;

    /**
     *Constructor for drawing the data plots
     * @param x1 starting x point
     * @param y1 starting y point
     * @param x2 point to draw to on x
     * @param y2 point to draw to on y
     **/

    public DataPlots(int x1, int y1, int x2, int y2){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    /**
     *Overiden paint method to draw rectangles
     * @param g the graphics control
     **/

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawRect(x1, y1, x2, y2);

    }




}