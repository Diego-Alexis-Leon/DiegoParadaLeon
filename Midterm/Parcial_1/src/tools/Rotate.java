package tools;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Scanner;

public class Rotate {
    private BufferedImage image;
    private BufferedImage imageRotate;
    private int p1X;
    private int p1Y;
    private int p2X;
    private int p2Y;
    private int deg;

    Rotate(BufferedImage image, BufferedImage imageRotate, int p1X,int p1Y, int p2X, int p2Y, int deg){
        this.image = image;
        this.imageRotate =imageRotate;
        this.p1X = p1X;
        this.p1Y = p1Y;
        this.p2X = p2X;
        this.p2Y = p2Y;
        this.deg = deg;
    }

    public BufferedImage rotate(){

        //changes all pixels within the selected area to white pixels
        for (int y=p1Y; y<p2Y; y++){
            for (int x=p1X; x<p2X; x++){
                image.setRGB(x,y, Color.white.getRGB());
            }
        }
        int newY,newX;
        switch (deg){
            case 90:
                int centerX=((p1X+p2X)-(p2Y-p1Y))/2;
                int centerY=((p1Y+p2Y)-(p2X-p1X))/2;

                /*
                This is used to identify if
                the entire image was selected
                 to rotate and avoid errors
                */

                if (image.getWidth()==imageRotate.getHeight() && image.getHeight()==imageRotate.getWidth()){
                    centerX = 0;
                    centerY = 0;
                }

                /*
                * newX and newY go through the cycle
                * from 0 to the maximum size in the
                * case of selecting the entire image
                * or from p1 to p2 while X and Y go through
                * each pixel of the image that you want
                * to rotate, this is different depending
                * on how many degrees you want to rotate.
                */

                newY =centerY;
                for (int x=imageRotate.getWidth()-1; x>=0; x--){
                    newX = centerX;
                    for (int y=0; y<imageRotate.getHeight(); y++){

                        if (newX>=0 && newX<=image.getWidth() && newY>=0 && newY<=image.getHeight()) {
                            image.setRGB(newX, newY, imageRotate.getRGB(x, y));
                        }
                        newX++;
                    }
                    newY++;
                }
                break;
            case 180:
                newY =p1Y;
                for (int y=imageRotate.getHeight()-1; y>=0; y--){
                    newX = p1X;
                    for (int x=imageRotate.getWidth()-1; x>=0; x--){

                        image.setRGB(newX,newY,imageRotate.getRGB(x,y));
                        newX++;
                    }
                    newY++;
                }
                break;
            case 270:
                int centerA=((p1X+p2X)-(p2Y-p1Y))/2;
                int centerB=((p1Y+p2Y)-(p2X-p1X))/2;
                if (image.getWidth()==imageRotate.getHeight() && image.getHeight()==imageRotate.getWidth()){
                    centerA = 0;
                    centerB = 0;
                }
                newY =centerB;
                for (int x=0; x<imageRotate.getWidth(); x++){
                    newX = centerA;
                    for (int y=imageRotate.getHeight()-1; y>=0; y--){

                        image.setRGB(newX,newY,imageRotate.getRGB(x,y));
                        newX++;
                    }
                    newY++;
                }
                break;
        }
        return image;
    }
}
