package skaing.a3;

/**
 * RightTriangle class represents right triangles with sides (width and height)
 * forming a 90 degree angle \nWidth and height may be the same or different
 * Samuel Kaing
 * version 1.0
 */

public class RightTriangle extends Rectangle {
    private float height;

    /**
     * Default constructor sets type and height
     */
    public RightTriangle() {
        super();
        this.setType(Type.RIGHTTRIANGLE);
        this.height = 0.0f;
    }

    /**
     * Override constructor sets type, width, and height
     *
     * @param width  float representing the width of a right triangle
     * @param height float representing the height of a right triangle
     */
    public RightTriangle(float width, float height) {
        super();
        this.setType(Type.RIGHTTRIANGLE);
        this.setWidth(width);
        this.setHeight(height);
    }

    /**
     * Override sets the height of the right triangle
     *
     * @return float representing the height of a right triangle
     */
    @Override
    public float getHeight() {
        return this.height;
    }

    /**
     * Override sets the height of the right triangle
     *
     * @param height float representing the height of a rectangle
     */
    @Override
    public void setHeight(float height) {
        this.height = height;
    }

    /**
     * Override gets the area of the right triangle
     *
     * @return double representing the area of the right triangle
     */
    @Override
    public double getArea() {
        return ((this.getHeight() * getWidth()) / 2);
    }
}
