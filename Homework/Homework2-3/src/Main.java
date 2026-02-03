import formulas.ChangeCartesianToPolar;
import formulas.ChangePolarToCartesian;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    /*
    static double getX(double  r, double t){
        return r*Math.cos(Math.toRadians(t));
    }
    static double getY(double  r, double t){
        return r*Math.sin(Math.toRadians(t));
    }
    static double[] changePolarToCartesian(double r, double angle){
        double xy[]={0,0};
        xy[0]= getX(r,angle);
        xy[1]= getY(r,angle);
        return xy;
    }
    static double[] changeCartesianToPolar(double x, double y){
        double ra[]={Math.sqrt(x*x+y*y),Math.toDegrees(Math.atan(y/x))};
        return ra;
    }
    */

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double x, y, r, angle;
        //double result[];
        int option =0;
        ChangePolarToCartesian polar = new ChangePolarToCartesian();
        ChangeCartesianToPolar cartesian = new ChangeCartesianToPolar();

        while (option!=3){
            System.out.println("Introduce a number to selec the type conversion: ");
            System.out.println("1- cartesian --> polar");
            System.out.println("2- polar --> cartesian");
            System.out.println("3- exit");
            option = scanner.nextInt();
            switch (option){
                case 1:
                    System.out.println("introduce x: ");
                    x=scanner.nextDouble();
                    System.out.println("introduce y: ");
                    y = scanner.nextDouble();
                    //result=changeCartesianToPolar(coordenateX,coordenateY);
                    cartesian.change(x,y);
                    //System.out.println("The result is: r="+result[0]+" angle="+result[1]);
                    System.out.println("The result is: r="+cartesian.getR()+" angle="+cartesian.getAngle());
                    break;
                case 2:
                    System.out.println("introduce r: ");
                    r=scanner.nextDouble();
                    System.out.println("introduce the angle (0 to 360 grades): ");
                    angle = scanner.nextDouble();
                    //result=changePolarToCartesian(coordenateX,coordenateY);
                    polar.change(r,angle);
                    //System.out.println("The result is: x="+result[0]+" y="+result[1]);
                    System.out.println("The result is: x="+polar.getX()+" y="+polar.getY());
                    break;
                case 3:
                    System.out.println("end...");
                    break;
            }
        }
    }
}