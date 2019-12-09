import java.awt.*;

public class MovingGradient extends MovingRectangle{
    public MovingGradient(){
        super(0, 0, defaultWidth, defaultHeight, defaultMarginWidth, defaultMarginHeight,defaultBorderColor,  defaultFillColor, defaultPath);
    }

    public MovingGradient(int x, int y, int w, int h, int mw, int mh, Color c, Color fc, int pathType){
        super(x, y, w, h, mw, mh, c, fc, pathType); //call to super class constructor

    }

    public void draw(Graphics g){
        final Graphics2D inner = (Graphics2D) g;
        int tlx = (int) topLeft.getX(); //x of the top left
        int tly = (int) topLeft.getY();//y of the top right

        GradientPaint gradient = new GradientPaint(tlx, tly, fillColor, width + tlx, height + tly, borderColor, true );
        inner.setPaint(gradient);
        inner.drawRect(tlx,tly, width, height);
        inner.fillRect(tlx, tly, width, height);


        drawHandles(inner);

    }

    public boolean contains(Point mousePt){
        return((topLeft.x <= mousePt.x) && (topLeft.y <= mousePt.y));
    }


}