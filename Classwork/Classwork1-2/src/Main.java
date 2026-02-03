import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    //Transform polar coordinate to cartesian coordinate
    static int getX(int  r, int t){
        return (int)(r*Math.cos(Math.toRadians(t)));
    }
    static int getY(int  r, int t){
        return (int)(r*Math.sin(Math.toRadians(t)));
    }
    static void drawFullCircle( int ratio, int origenX, int origenY, BufferedImage image){
        int cordY;
        int cordX;
        for (int r=ratio; r>=0; r--) {
            for (int angle = 0; angle < 360; angle++) {
                cordY = getY(r, angle) + origenY;
                cordX = getX(r, angle) + origenX;

                image.setRGB(cordX, cordY, Color.yellow.getRGB());
            }
        }
    }
    static void drawLine(int origenX, int origenY, int lon, int angle , BufferedImage image){
        int x;
        int y;
        angle=360-angle;
        for (int p=lon; p>0; p--){
            x=getX(p,angle)+origenX;
            y=getY(p,angle)+origenY;
            image.setRGB(x, y, Color.red.getRGB());
        }
    }

    static void drawSin(int y,int width, BufferedImage image){
        int yy=0;
        for (int xx=0; xx<400; xx++) {
            yy=(getY(10,xx*8))+y;
            //System.out.println("coorX: " + xx + " coorY: " + yy);
            image.setRGB(xx, yy, Color.green.getRGB());

        }
    }
    static void drawFullSin(int y,int width, BufferedImage image){
        for(int yy=0; yy<50; yy++){
        for (int x=0; x<400; x++) {
            //System.out.println("coorX: " + xx + " coorY: " + yy);
            image.setRGB(x, (getY(10,x*8))+y+yy, Color.green.getRGB());

        }
        }
    }

    public static void main(String[] args) {
        int width=400;
        int height= (width/4)*3;
        int center[]={width/2,height/2};
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        double result=0;
        for(int x=0; x<width; x++){
            for (int y=0; y<height; y++){

                image.setRGB(x, y, Color.white.getRGB());
            }

        }

        drawLine(75,75,50, 45, image );
        drawLine(75,75,50, 135, image );
        drawLine(75,75,50, 225, image );
        drawLine(75,75,50, 315, image );

        drawLine(75,75,75, 0, image );
        drawLine(75,75,75, 90, image );
        drawLine(75,75,75, 180, image );
        drawLine(75,75,75, 270, image );

        drawFullCircle(30,75,75,image);

        drawFullSin(center[0], width,image);

        for(int x=0; x<width; x++){
            for (int y= (center[0]+25); y<height; y++){
                image.setRGB(x, y, Color.green.getRGB());
            }
        }

        File outputImage = new File("image.jpg");

        try{
            ImageIO.write(image, "jpg", outputImage);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}