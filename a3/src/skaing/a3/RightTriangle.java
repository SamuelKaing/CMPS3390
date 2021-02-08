package skaing.a3;

public class RightTriangle extends Rectangle {
    private float rtHeight;

    public RightTriangle() {
        super();
        this.setType(Type.RIGHTTRIANGLE);
        this.rtHeight = 0.0f;
    }

    public RightTriangle(float width, float height) {
        super();
        this.setType(Type.RIGHTTRIANGLE);
        this.setWidth(width);
        this.setHeight(height);
    }

    @Override
    public float getHeight() {
        return this.rtHeight;
    }

    @Override
    public void setHeight(float height) {
        try {
            super.setHeight(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public double getArea() {
        return ((getHeight() * getWidth()) / 2);
    }
}
