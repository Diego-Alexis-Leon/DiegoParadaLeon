import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    static int getX(int  r, int t){
        double cos0 = t * 0.0174533;

        int x = (int)(r*Math.cos(cos0));
        return x;
    }
    static int getY(int  r, int t){
        double sin0 = t * 0.0174533;
        int y = (int)(r*Math.sin(sin0));
        return y;
    }
    static void drawCircleVoid( int ratio, int origenX, int origenY, BufferedImage image){
        int cordY;
        int cordX;
        for (int angle=0; angle<360; angle++){
            cordY=getY(ratio,angle)+origenY;
            cordX=getX(ratio,angle)+origenX;
            //System.out.println("angle: "+angle);
            //System.out.println("cordX: "+cordX+ " cordY: "+cordY);
            image.setRGB(cordX, cordY, Color.white.getRGB());
        }
    }
    static void drawLine(int origenX, int origenY, int lon, int time , BufferedImage image){
        int x;
        int y;
        for (int p=lon; p>0; p--){
            x=getX(p,time)+origenX;
            y=getY(p,time)+origenY;
            image.setRGB(x, y, Color.white.getRGB());
        }
    }
    public static void main(String[] args) {
        int width=400;
        int height= (width/4)*3;
        int center[]={width/2,height/2};
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Scanner scanner = new Scanner(System.in);
        //image.setRGB(bace, heigth, Color.red.getRGB());

        int r=100;


        drawCircleVoid(r,center[0],center[1],image);
        System.out.println("Introduce minutes: ");
        int minutes=scanner.nextInt();
        int lonMinutes=40;
        minutes=(minutes*6)-90;

        System.out.println("Introduce hour: ");
        int hour=scanner.nextInt();
        int lonHour= 70;
        hour=(hour*30)-90+minutes/2;

        drawLine(center[0],center[1],lonMinutes,minutes, image);
        drawLine(center[0],center[1],lonHour, hour,image );

        File outputImage = new File("image.jpg");

        try{
            ImageIO.write(image, "jpg", outputImage);
        }catch (IOException e){
            throw new RuntimeException(e);
        }

    }
}