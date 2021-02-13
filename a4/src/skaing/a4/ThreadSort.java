package skaing.a4;

import skaing.a3.Shape;

/**
 * ThreadSort class extends Thread and uses bubble sort on Shape[]
 * @author Samuel Kaing
 * @version 1.0
 */
public class ThreadSort extends Thread {
    private Shape[] tShapes;

    /**
     * Constructor that sets tShapes and copies it for multithread sorting
     * @param shapes Shape[] that contains shapes to be sorted
     * @param lowBounds int that represents the first element in an array
     * @param upperBounds int that represents the last element in an array
     */
    public ThreadSort(Shape[] shapes, int lowBounds, int upperBounds) {
        this.tShapes = new Shape[upperBounds - lowBounds];

        System.arraycopy(shapes, lowBounds, this.tShapes, 0, (upperBounds - lowBounds));
    }

    /**
     * Override of run() that uses bubble sort on tShapes
     */
    @Override
    public void run() {
        System.out.println("Thread started");
        int n = this.tShapes.length;
        Shape tmp;
        for(int i=0; i<n; i++){
            for(int j=1; j<n; j++){
                if(this.tShapes[j-1].getArea() > this.tShapes[j].getArea()){
                    // Swap
                    tmp = this.tShapes[j-1];
                    this.tShapes[j-1] = this.tShapes[j];
                    this.tShapes[j] = tmp;
                }
            }
        }
        System.out.println("Thread complete");
    }

    /**
     * Gets tShapes array
     * @return Shape[] that contains sorted shapes
     */
    public Shape[] gettShapes() {
        return tShapes;
    }
}
