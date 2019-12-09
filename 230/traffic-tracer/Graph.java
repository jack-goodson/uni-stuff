import java.awt.*;


public class Graph {
    private int x1;
    private int x2;
    private int y1;
    private int y2;

    private String label = "";
    private int posX;
    private int posY;
    /**
     *Never used other than when initializing graph arrays
     **/

    public Graph(){

    }
    /**
     *constructor to pass in X and Y axis points
     * @param x1 x pos for axis
     * @param y1 y pos for axis
     * @param x2 x end pos for axis
     * @param y2 y end pos for axis
     **/

    public Graph(int x1, int y1, int x2, int y2) { // Constructor

        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
    /**
     *overloaded constructor for drawing labels
     * @param label text to display as a label
     * @param posX x position of label
     * @param posY y position of label
     **/

    public Graph(String label, int posX, int posY){ //  Overloaded method to draw Labels
        this.label = label;
        this.posX = posX;
        this.posY = posY;
    }

    // Paint the Graph items

    /**
     *Paint the graphics passed into this class
     * @param g the graphics control
     **/
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawLine(x1, y1, x2, y2);
        g2.drawString(label, posX, posY);

    }
}
