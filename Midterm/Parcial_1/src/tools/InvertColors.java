package tools;

import java.awt.*;
import java.awt.image.BufferedImage;

public class InvertColors {
    private BufferedImage image;
    private int p1X;
    private int p1Y;
    private int p2X;
    private int p2Y;


    public InvertColors(BufferedImage image, int p1X,int p1Y, int p2X, int p2Y) {
        this.image = image;
        this.p1X = p1X;
        this.p1Y = p1Y;
        this.p2X = p2X;
        this.p2Y = p2Y;
    }

    /*
    * observes each pixel of the image
    * and places it in the same coordinate
    * as it was with the inverted color
    */
    public void invert(){
        Color color;
        int red,green,blue;
        for (int y=p1Y; y<p2Y; y++){
            for (int x=p1X; x<p2X; x++){
                color = new Color(image.getRGB(x,y));

                red = 255 - color.getRed();
                green = 255 - color.getGreen();
                blue = 255 - color.getBlue();

                color = new Color(red,green,blue);
                image.setRGB(x,y,color.getRGB());
            }
        }
    }
}
