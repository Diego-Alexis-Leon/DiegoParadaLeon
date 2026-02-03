import java.util.Scanner;

public class Main{

    static String calculate(int width,int height,Scanner scanner){
        String result="0x0";
        System.out.println("select a Aspect ratio:");
        System.out.println("1- 4:3");
        System.out.println("2- 16:9");
        System.out.println("3- 16:10");
        int option=scanner.nextInt();
        switch (option){
            case 1:
                if (width==0 && height>0){
                    result= String.valueOf(height*4/2)+"x"+String.valueOf(height);
                    return result;
                }
                if (height==0 && width>0){
                    result= String.valueOf(width)+"x"+String.valueOf(width*3/4);
                    return result;
                }
                break;
            case 2:
                if (width==0 && height>0){
                    result= String.valueOf(height*16/9) +"x"+String.valueOf(height);
                    return result;
                }
                if (height==0 && width>0){
                    result= String.valueOf(width)+"x"+String.valueOf(width*9/16);
                    return result;
                }
                break;
            case 3:
                if (width==0 && height>0){
                    result= String.valueOf(height*16/10) +"x"+String.valueOf(height);
                    return result;
                }
                if (height==0 && width>0){
                    result= String.valueOf(width)+"x"+String.valueOf(width*10/16);
                    return result;
                }
                break;
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int option=0;
        int valor=0;
        while (option!=3){
            System.out.println("Select how calculate the aspect ratio: ");
            System.out.println("1- with the width");
            System.out.println("2- with the height");
            System.out.println("3- exit");
            option=scanner.nextInt();
        switch (option){
            case 1:
                System.out.print("Introduce the width: ");
                valor=scanner.nextInt();
                System.out.println(calculate(valor,0,scanner));

                break;
            case 2:
                System.out.print("Introduce the height: ");
                valor=scanner.nextInt();
                System.out.println(calculate(0,valor,scanner));
                break;
            case 3:
                System.out.println("end...");
                break;
        }
    }
    }
}