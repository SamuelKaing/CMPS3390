package skaing.a3;


public class Oval extends Circle {
    private final Exception nonOvalException = new Exception("Radius 1 and 2 are the same. Use Circle Instead.");
    private float radius2;

    public Oval() {
        super();
        setType(Type.OVAL);
        this.radius2 = 0.0f;
    }

    public Oval(Float radius, Float radius2) throws Exception {
        if (radius.equals(radius2)) {
            throw nonOvalException;
        }
        this.setType(Type.OVAL);
        this.setRadius(radius);
        this.setRadius2(radius2);
    }

    public float getRadius2() {
        return radius2;
    }

    public void setRadius2(float radius2) throws Exception {
        if (getRadius() == radius2) {
            throw nonOvalException;
        }
        this.radius2 = Math.max(0.0f, radius2);
    }

    public double getArea() {
        return (3.14 * getRadius() * this.radius2);
    }

    @Override
    public String toString() {
        return String.format("%s Radius2: %-6.2f|", super.toString(), this.radius2);
    }

}
