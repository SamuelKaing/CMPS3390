package skaing.a3;

public class Rectangle extends Square {
    private final Exception nonRectangleException = new Exception("Width and Height are the same. Use Square Instead.");
    private float height;

    public Rectangle() {
        super();
        this.setType(Type.RECTANGLE);
        this.height = 0.0f;
    }

    public Rectangle(Float width, Float height) throws Exception {
        if (width.equals(height)) {
            throw nonRectangleException;
        }
        this.setType(Type.RECTANGLE);
        this.setHeight(height);
        this.setWidth(width);
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = Math.max(0.0f, height);
    }

    public double getArea() {
        return (getWidth() * this.height);
    }

    @Override
    public String toString() {
        return String.format("%s Height: %-7.2f|", super.toString(), this.height);
    }
}
