package tools;

import java.awt.image.BufferedImage;
import java.util.Scanner;

public class Trim  {
    private BufferedImage image;
    private int p1X;
    private int p1Y;
    private int p2X;
    private int p2Y;

    Trim(BufferedImage image, int p1X,int p1Y, int p2X, int p2Y){
        this.image = image;
        this.p1X = p1X;
        this.p1Y = p1Y;
        this.p2X = p2X;
        this.p2Y = p2Y;
    }
    /*
    *  This method creates a new
    *  image, loops through each
    *  pixel within the obtained
    *  coordinates, and pastes
    *  them into the new image.
    * */
    public BufferedImage cut(){
        BufferedImage newImage = new BufferedImage(p2X-p1X,p2Y-p1Y,BufferedImage.TYPE_INT_RGB);

        int newY =0;
        for (int y=p1Y; y<p2Y; y++){
            int newX =0;
            for (int x=p1X; x<p2X; x++){

                newImage.setRGB(newX,newY,image.getRGB(x,y));
                newX++;
            }
            newY++;
        }
        return newImage;
    }
}
