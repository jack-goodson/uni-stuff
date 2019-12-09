import java.awt.*;
import java.util.Random;
import java.math.*;
public class MovingChecker extends MovingRectangle{
    Random rand = new Random();
    int  xNumBlock = rand.nextInt(10) + 1;
    int  yNumBlock = rand.nextInt(10) + 1;

    public MovingChecker(){
        super(0, 0, defaultWidth, defaultHeight, defaultMarginWidth, defaultMarginHeight,defaultBorderColor,  defaultFillColor, defaultPath);
    }

    public MovingChecker(int x, int y, int w, int h, int mw, int mh, Color c, Color fc, int pathType){
        super(x, y, w, h, mw, mh, c, fc, pathType); //call to super class constructor

    }

    public void draw(Graphics g){

        if(xNumBlock == 1){ //so can't have single square checker
            xNumBlock +=1;
        }if(yNumBlock == 1){
            yNumBlock +=1;
        }
        final Graphics2D canvas = (Graphics2D) g;
        int tlx = (int) topLeft.getX();
        int tly = (int) topLeft.getY();
        double xCheckerWidth = width / xNumBlock; //width of checker on the x axis
        xCheckerWidth = Math.round(xCheckerWidth);
        double yCheckerHeight = height / yNumBlock; //width of check on the y axis
        yCheckerHeight = Math.round(yCheckerHeight);
        boolean draw = true ; //draw boolean to alternate between checker colours
        for(int i = 0; i < height; i += yCheckerHeight){ //Draw checkers along the x axis and the drop down to a level each iteration
            for(int x = 0; x < width; x +=xCheckerWidth){
                if(draw == true){
                    canvas.setPaint(borderColor);
                    canvas.drawRect(tlx + x ,tly, (int) xCheckerWidth,(int)yCheckerHeight);
                    canvas.fillRect(tlx + x ,tly,(int) xCheckerWidth,(int)yCheckerHeight);
                }else{
                    canvas.setPaint(fillColor);
                    canvas.drawRect(tlx + x ,tly,(int) xCheckerWidth,(int)yCheckerHeight);
                    canvas.fillRect(tlx + x ,tly,(int) xCheckerWidth,(int)yCheckerHeight);
                }
                draw = !draw;
            }
            tly += yCheckerHeight;
            draw = !draw;
        }
        drawHandles(canvas);
    }

    public boolean contains(Point mousePt){
        return((topLeft.x <= mousePt.x) && (topLeft.y <= mousePt.y));
    }


}