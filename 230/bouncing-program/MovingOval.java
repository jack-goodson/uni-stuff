import java.awt.*;

public class MovingOval extends MovingRectangle{
    public MovingOval(){
        super(0, 0, defaultWidth, defaultHeight, defaultMarginWidth, defaultMarginHeight,defaultBorderColor,  defaultFillColor, defaultPath);
    }

    public MovingOval(int x, int y, int w, int h, int mw, int mh, Color c, Color fc, int pathType){
        super(x, y, w, h, mw, mh, c, fc, pathType); //call to super class constructor
    }

    public void draw(Graphics g){

        final Graphics2D inner = (Graphics2D) g;
        int tlx = (int) topLeft.getX();
        int tly = (int) topLeft.getY();
        inner.setPaint(borderColor);
        inner.drawOval(tlx,tly, width, height);
        inner.fillOval(tlx,tly, width, height);
        inner.setPaint(fillColor);
        inner.drawOval(tlx+1,tly+1, width-2, height-2); //draw slightly smaller oval to give effect of border
        inner.fillOval(tlx+1,tly+1, width-2, height-2);
        drawHandles(inner);

    }

    public boolean contains(Point mousePt){
        int tlx = (int) topLeft.getX(); //formula to detect if contains
        int tly = (int) topLeft.getY();
        Point EndPt = new Point(tlx + width, tly + height);
        double dx = (2 * mousePt.x - topLeft.getX() - EndPt.x) / (double) width;
        double dy = (2 * mousePt.y - topLeft.getY() - EndPt.y) / (double) height;
        return dx * dx + dy * dy < 1.0;
    }


}