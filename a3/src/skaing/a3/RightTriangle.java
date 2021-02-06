package skaing.a3;

public class RightTriangle extends Rectangle {

    public RightTriangle() {
        super();
        this.setType(Type.RIGHTTRIANGLE);
    }

    public RightTriangle(float width, float height) {
        this.setType(Type.RIGHTTRIANGLE);
        this.setWidth(width);
        this.setHeight(height);
    }

    @Override
    public float getHeight() {
        return super.getHeight();
    }
    @Override
    public void setHeight(float height) {
        super.setHeight(height);
    }

    @Override
    public double getArea() {
        return ((getHeight() * getWidth()) / 2);
    }
}
