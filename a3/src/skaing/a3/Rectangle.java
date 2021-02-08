package skaing.a3;

/**
 * Rectangle extends Square and represents a rectangle
 * where width and height are different
 * Samuel Kaing
 * version 1.0
 */

public class Rectangle extends Square {
    private final Exception nonRectangleException = new Exception("Width and "
            .concat("Height are the same. Use Square Instead."));
    private float height;

    /**
     * Default constructor sets type and height
     */
    public Rectangle() {
        super();
        this.setType(Type.RECTANGLE);
        this.height = 0.0f;
    }

    /**
     * Override constructor sets type, width, and height
     *
     * @param width  float representing the width of the rectangle
     * @param height float representing the height of the triangle
     * @throws Exception nonRectangleException if width and height are the same
     */
    public Rectangle(float width, float height) throws Exception {
        super();
        if (width == height) {
            throw nonRectangleException;
        }
        this.setType(Type.RECTANGLE);
        this.setHeight(height);
        this.setWidth(width);
    }

    /**
     * Gets the height of a rectangle
     *
     * @return float representing the height of a rectangle
     */
    public float getHeight() {
        return this.height;
    }

    /**
     * Function that returns the area of a rectangle
     *
     * @param height float representing the height of a rectangle
     * @throws Exception nonRectangleException if width and height are the same
     */
    public void setHeight(float height) throws Exception {
        if (getWidth() == height) {
            throw nonRectangleException;
        }
        this.height = Math.max(0.0f, height);
    }

    /**
     * Function that returns the area of a rectangle
     *
     * @return double that represents the area of a rectangle
     */
    @Override
    public double getArea() {
        return (getWidth() * this.height);
    }

    /**
     * Override of the toString function to print shapes nicely
     *
     * @return String that represents all properties of a shape
     */
    @Override
    public String toString() {
        return String.format("%s Height: %-7.2f", super.toString(), getHeight());
    }
}
