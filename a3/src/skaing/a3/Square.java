package skaing.a3;

public class Square extends Shape {
    private float width;

    public Square() {
        super();
        this.setType(Type.SQUARE);
        this.width = 0.0f;
    }

    public Square(float width) {
        this.setType(Type.SQUARE);
        this.setWidth(width);
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = Math.max(0.0f, width);
    }

    public double getArea() {
        return (width * width);
    }

    @Override
    public String toString() {
        return String.format("%s Area: %-8.2f| Width: %-7.2f|", super.toString(),
                getArea(), this.width);
    }
}
