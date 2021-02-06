package skaing.a3;

public class Circle extends Shape {
    private float radius;

    public Circle() {
        super();
        this.setType(Type.CIRCLE);
        this.radius = 0.0f;
    }

    public Circle(float radius) {
        this.setType(Type.CIRCLE);
        this.setRadius(radius);
    }

    public float getRadius() {
        return this.radius;
    }

    public void setRadius(float radius) {
        this.radius = Math.max(0.0f, radius);
    }

    public double getArea() {
        return (3.14 * (this.radius * this.radius));
    }

    @Override
    public String toString() {
        return String.format("%s Area: %-7.2f| Radius: %-6.2f|",
                super.toString(), this.getArea(), this.radius);
    }
}
