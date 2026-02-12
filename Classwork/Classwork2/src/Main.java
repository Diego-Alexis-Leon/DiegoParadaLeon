import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) {
        BufferedImage image = new BufferedImage(500,500,BufferedImage.TYPE_INT_RGB);

        Triangle triangle = new Triangle(250,100,100,400,400,400);
        Color color = new Color(255,255,255);
        for (int x=0; x<500; x++){
            for (int y=0; y<500; y++){
                if (triangle.angles(x, y)){
                    color = triangle.color();
                    image.setRGB(x, y, color.getRGB());
                }
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