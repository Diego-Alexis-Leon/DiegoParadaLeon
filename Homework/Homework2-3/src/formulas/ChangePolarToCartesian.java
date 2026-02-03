package formulas;

public class ChangePolarToCartesian {
    private double x;
    private double y;

    public ChangePolarToCartesian(){
        this.x = 0;
        this.y = 0;
    }

    public double getX() {
        return x;
    }

    public void setX(double r) {
        this.x = r;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void change(double r, double angle){
        setX(round(r*Math.cos(Math.toRadians(angle))));
        setY(round(r*Math.sin(Math.toRadians(angle))));
    }
    private double round(double value) {
        return Math.round(value * 1000.0) / 1000.0;
    }
}

