import java.awt.*;

public class MovingRectangle extends MovingShape{
    public MovingRectangle(){
        super(0, 0, defaultWidth, defaultHeight, defaultMarginWidth, defaultMarginHeight,defaultBorderColor,  defaultFillColor, defaultPath);
    }

    public MovingRectangle(int x, int y, int w, int h, int mw, int mh, Color c, Color fc, int pathType){
        super(x, y, w, h, mw, mh, c, fc, pathType); //call to super class constructor
    }

    public void draw(Graphics g){
        final Graphics2D inner = (Graphics2D) g;
        int tlx = (int) topLeft.getX(); //topleft x of rectangle
        int tly = (int) topLeft.getY(); //topleft y of rectangle
        inner.setPaint(borderColor);
        inner.drawRect(tlx,tly, width, height);
        inner.fillRect(tlx,tly, width, height);
        inner.setPaint(fillColor);
        inner.drawRect(tlx+1,tly+1, width-2, height-2); // draw slightly smaller inner rectangle to give effect of border
        inner.fillRect(tlx+1,tly+1, width-2, height-2);
        drawHandles(inner);

    }

    public boolean contains(Point mousePt){
        return((topLeft.x <= mousePt.x) && (topLeft.y <= mousePt.y));
    }


}