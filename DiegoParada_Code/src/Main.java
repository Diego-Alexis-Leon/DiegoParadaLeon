
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import java.util.Scanner;
public class Main {
    public static double y;
    public static double z;

    public static void square(Scanner scanner) {
        System.out.printf("How long is one side:");
        y= scanner.nextInt();
        System.out.println("Perimeter= "+ y*4);
        System.out.println("Area= "+ y*y);
    }
    public static void rectangle(Scanner scanner) {
        System.out.printf("Enter the height:");
        y= scanner.nextInt();
        System.out.printf("enter the base:");
        z=scanner.nextInt();
        System.out.println("Perimeter= "+ ((y*2)+(z*2)));
        System.out.println("Area= "+ y*z);
    }
    public static void triangle(Scanner scanner) {
        System.out.printf("Enter the height:");
        y= scanner.nextInt();
        System.out.printf("enter the base:");
        z=scanner.nextInt();

        System.out.println("Perimeter= "+( z+Math.sqrt(Math.pow(z/2,2))+y*y));
        System.out.println("Area= "+ (y*z)/2);
    }
    public static void circle(Scanner scanner) {
        System.out.printf("Enter the radius:");
        y= scanner.nextInt();
        System.out.println("Perimeter= "+ y*2*Math.PI);
        System.out.println("Area= "+ y*y*Math.PI);
    }
    public static void pentagon(Scanner scanner) {
        System.out.printf("How long is one side:");
        y= scanner.nextInt();
        double a=y/1.453;
        System.out.println("Perimeter= "+ y*5);
        System.out.println("Area= "+ (a*(y*5))/2);
    }
    public static int pentagram(int a) {
        return a;
    }
    public static int semicircle(int a) {
        return a;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int x=1;
        while (x!=0) {
            System.out.println("1- square");
            System.out.println("2- rectangle");
            System.out.println("3- triangle");
            System.out.println("4- circle");
            System.out.println("5- pentagon");
            System.out.println("6- exit");
            //System.out.println("7- ");
            System.out.printf("Enter a number to select a figure or 6 to exit:");
             x = scanner.nextInt();

            switch (x) {
                case 1: //Square
                    square(scanner);
                    break;
                case 2:
                    rectangle(scanner);
                    break;
                case 3:
                    triangle(scanner);
                    break;
                case 4:
                    circle(scanner);
                    break;
                case 5:
                    pentagon(scanner);
                    break;
                case 6:
                    x=0;
                    System.out.println("end . . .");
                    break;
                default:
                    System.out.println("Invalid option");
            }
            System.out.println();
        }

    }
}