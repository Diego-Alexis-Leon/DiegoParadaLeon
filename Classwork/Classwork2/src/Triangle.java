import java.awt.*;

public class Triangle {
    private double alfa;
    private double beta;
    private double gamma;
    private int aX;
    private int aY;
    private int bX;
    private int bY;
    private int cX;
    private int cY;
    private double denom;

    Triangle(int aX, int aY, int bX, int bY, int cX, int cY){
        this.aX = aX;
        this.aY = aY;
        this.bX = bX;
        this.bY = bY;
        this.cX = cX;
        this.cY = cY;
    }


    public boolean angles(int x,int y){
        this.denom = ((bY-cY)*(aX-cX)+(cX-bX)*(aY-cY));
        this.alfa=((bY-cY)*(x-cX)+(cX-bX)*(y-cY))/denom;
        this.beta=((cY-aY)*(x-cX)+(aX-cX)*(y-cY))/denom;
        this.gamma=1-alfa-beta;

        if(alfa >=0 && beta>=0 && gamma >=0){
            return  true;
        }else {
            return false;
        }
    }

    public Color color(){
        int r = (int)(255*beta);
        int g = (int)(255*gamma);
        int b = (int)(255*alfa);
        //System.out.println("Color: "+r+" "+g+" "+b);
        return new Color(r,g,b);
    }
}
