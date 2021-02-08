package skaing.a3;

/**
 * Circle extends Shape and represents a perfect circle
 * Samuel Kaing
 * version 1.0
 */

public class Circle extends Shape {
    private float radius;

    /**
     * Default constructor that sets type to circle and radius to 0.0f
     */
    public Circle() {
        super();
        this.setType(Type.CIRCLE);
        this.radius = 0.0f;
    }

    /**
     * Override constructor that sets type to circle and radius=Math.max(0.0f, radius)
     *
     * @param radius float representing the radius of a circle
     */
    public Circle(float radius) {
        super();
        this.setType(Type.CIRCLE);
        this.setRadius(radius);
    }

    /**
     * Gets the radius of a circle
     *
     * @return float representing the radius of a circle
     */
    public Float getRadius() {
        return this.radius;
    }

    /**
     * Sets the radius of a circle to Math.max(0.0f, radius)
     *
     * @param radius float representing the radius of a circle
     */
    public void setRadius(float radius) {
        this.radius = Math.max(0.0f, radius);
    }

    /**
     * Function to return the area of a circle
     *
     * @return double representing the area of a circle
     */
    public double getArea() {
        return (3.14 * (this.radius * this.radius));
    }

    /**
     * Override of the toString function to print shapes nicely
     *
     * @return String that represents all properties of a shape
     */
    @Override
    public String toString() {
        return String.format("%s Area: %-8.2f| Radius: %-6.2f|",
                super.toString(), this.getArea(), getRadius());
    }
}
