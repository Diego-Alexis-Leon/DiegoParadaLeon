package formulas;

public class ChangeCartesianToPolar {
    private double r;
    private double angle;

    public ChangeCartesianToPolar(){
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public void change(double x, double y){
        setR(Math.sqrt(x*x+y*y));
        setAngle(Math.toDegrees(Math.atan2(y,x)));
    }
}
