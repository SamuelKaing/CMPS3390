package skaing.a3;

/**
 * Oval class extends Circle and adds a second radius to represent an oval
 * Samuel Kaing
 * version 1.0
 */

public class Oval extends Circle {
    private final Exception nonOvalException = new Exception("Radius 1 "
            .concat("and 2 are the same. Use Circle Instead."));
    private float radius2;

    /**
     * Default constructor set teh type to circle and the radius and radius2 to 0.0f
     */
    public Oval() {
        super();
        setType(Type.OVAL);
        this.radius2 = 0.0f;
    }

    /**
     * Override constructor that sets type, radius, and radius 2
     *
     * @param radius  float that represents the first radius of an oval
     * @param radius2 float that represents the second radius of an oval
     * @throws Exception nonOvalException if radius and radius2 are the same
     */
    public Oval(Float radius, Float radius2) throws Exception {
        super();
        if (radius.equals(radius2)) {
            throw nonOvalException;
        }
        this.setType(Type.OVAL);
        this.setRadius(radius);
        this.setRadius2(radius2);
    }

    /**
     * Gets the radius of the oval
     *
     * @return float that represents teh second radius of the oval
     */
    public float getRadius2() {
        return radius2;
    }

    /**
     * Sets teh second radius of the oval throws nonOvalException
     *
     * @param radius2 float that represents the second radius of the oval
     * @throws Exception nonOvalException if the radius and radius2 are the same
     */
    public void setRadius2(Float radius2) throws Exception {
        if (getRadius().equals(radius2)) {
            throw nonOvalException;
        }
        this.radius2 = Math.max(0.0f, radius2);
    }

    /**
     * Override of the circle's getArea. Takes into account the second radius in calculation
     *
     * @return double that represents the oval's area
     */
    @Override
    public double getArea() {
        return (3.14 * getRadius() * this.radius2);
    }

    /**
     * Override of the toString function to print shapes nicely
     *
     * @return String that represents all properties of a shape
     */
    @Override
    public String toString() {
        return String.format("%s Radius2: %-6.2f", super.toString(), getRadius2());
    }
}
