import java.awt.*;

public class MovingShapePattern extends MovingShape{
    public MovingShapePattern(){
        super(0, 0, defaultWidth, defaultHeight, defaultMarginWidth, defaultMarginHeight,defaultBorderColor,  defaultFillColor, defaultPath);
    }

    public MovingShapePattern(int x, int y, int w, int h, int mw, int mh, Color c, Color fc, int pathType){
        super(x, y, w, h, mw, mh, c, fc, pathType);
    }

    public void draw(Graphics g){
        final Graphics2D inner = (Graphics2D) g;
        int tlx = (int) topLeft.getX();
        int tly = (int) topLeft.getY();

        inner.setPaint(borderColor);
        inner.drawOval(tlx-1,tly-1, width+2, height+2);
        inner.setPaint(fillColor);
        inner.fillOval(tlx,tly, width, height);

        inner.setPaint(borderColor);
        inner.drawOval(tlx - (width/2),tly - (height/2), width, height);
        inner.drawOval(tlx +(width/2),tly + (height/2), width, height);

        inner.drawOval(tlx + (width/2),tly - (height/2), width, height);
        inner.drawOval(tlx -(width/2),tly + (height/2), width, height);

        inner.drawRect(tlx, tly, width * 2, height * 2);
        inner.drawRect(tlx - width, tly - height, width * 2, height * 2);



        drawHandles(inner);

    }

    public boolean contains(Point mousePt){
        return((topLeft.x <= mousePt.x) && (topLeft.y <= mousePt.y));
    }


}