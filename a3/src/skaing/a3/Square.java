package skaing.a3;

/**
 * Square class represents a square with all equal sides
 * Samuel Kaing
 * version 1.0
 */

public class Square extends Shape {
    private float width;

    /**
     * Default constructor sets the type and width
     */
    public Square() {
        super();
        this.setType(Type.SQUARE);
        this.width = 0.0f;
    }

    /**
     * Override constructor sets teh type and width
     *
     * @param width float represents the width of the square
     */
    public Square(float width) {
        super();
        this.setType(Type.SQUARE);
        this.setWidth(width);
    }

    /**
     * Gets the width of a square
     *
     * @return float that represents the width of the square
     */
    public float getWidth() {
        return width;
    }

    /**
     * Sets the width of a square=Math.max(0.0f, width)
     *
     * @param width float that represents the width of the square
     */
    public void setWidth(float width) {
        this.width = Math.max(0.0f, width);
    }

    /**
     * Function that returns the area of a square
     *
     * @return double that represents the area of a square
     */
    public double getArea() {
        return (width * width);
    }

    /**
     * Override of the toString function to print shapes nicely
     *
     * @return String that represents all properties of a shape
     */
    @Override
    public String toString() {
        return String.format("%s Area: %-8.2f| Width: %-7.2f|", super.toString(),
                getArea(), getWidth());
    }
}
