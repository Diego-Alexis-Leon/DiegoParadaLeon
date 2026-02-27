package tools;

import java.awt.image.BufferedImage;
import java.util.Scanner;

public class Tool {
    /*
    * Static methods create an
    * object of the corresponding
    * class by carrying out the
    * logic and methods of each class.
    */

    public static void invetcolors(BufferedImage image, int x1, int y1, int x2, int y2){
        InvertColors imageInvert = new InvertColors(image,x1,y1,x2,y2);
        imageInvert.invert();
    }

    public static BufferedImage trim(BufferedImage image,int x1, int y1, int x2, int y2){
        Trim imageTrim = new Trim(image,x1,y1,x2,y2);
        return imageTrim.cut();
    }

    /*
    * In the rotation method, an object
    * of the trim class is used to know
    * which part of the image will be rotated
     */
    public static BufferedImage rotate(BufferedImage image, int x1, int y1, int x2, int y2, int deg){
        Trim trim = new Trim(image,x1,y1,x2,y2);
        BufferedImage imageTrim = trim.cut();

        /*
         This is used to identify if
          the entire image was selected
           to rotate and avoid errors
        */

        if (image.getWidth()-1==imageTrim.getWidth() && image.getHeight()-1==imageTrim.getHeight() && deg !=180){
            image = new BufferedImage(y2,x2, BufferedImage.TYPE_INT_RGB);
            int z=x2;
            x2=y2;
            y2=z;
        }
        /*
        * This object is initialized
        * with the image that will be
        * modified, the part of the image
        * that will be rotated, the
        * coordinates where the image that
        * will be rotated will be placed and
        * the degrees that will rotate it.
        *
        * */
        Rotate imageRotate = new Rotate(image,imageTrim,x1,y1,x2,y2,deg);
        image =imageRotate.rotate();
        return image;
    }

}
