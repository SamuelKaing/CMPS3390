package skaing.a3;

/**
 * Shape class is the base class for all other Shape Types
 * Samuel Kaing
 * Version 1.0
 */

public class Shape {
    private Type type;

    /**
     * Default constructor set the type to shape
     */
    public Shape() {
        this.setType(Type.SHAPE);
    }

    /**
     * Gets the type of the shape
     *
     * @return Type enum representing the type of the shape
     */
    public Type getType() {
        return this.type;
    }

    /**
     * Sets the type of the shape
     *
     * @param type Type enum representing the type of the shape
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * Override of the toString function to print shapes nicely
     *
     * @return String that represents all properties of a shape
     */
    @Override
    public String toString() {
        return String.format("Type: %-15s|", getType());
    }

}


