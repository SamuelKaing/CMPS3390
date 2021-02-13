package skaing.a4;

import skaing.a3.Shape;

/**
 * MergeSort class extends Thread and merge sorts Shape arrays
 * @author Samuel Kaing
 * @version 1.0
 */
public class MergeSort extends Thread{
    private Shape[] shapes1;
    private Shape[] shapes2;
    private Shape[] sortedShapes;

    /**
     * Constructor of MergeSort.  Sets shapes1 shapes2 and sortedShapes
     * @param s1 Shape[] that represents the first array MergeSort will sort
     * @param s2 Shape[] that represents the second array MergeSort will sort
     */
    public MergeSort(Shape[] s1, Shape[] s2){
        this.shapes1 = s1;
        this.shapes2 = s2;
        this.sortedShapes = new Shape[s1.length + s2.length];
    }

    /**
     * Function that merge sorts shapes1 and shapes2
     */
    @Override
    public void run() {
        System.out.println("Merge Thread Started");
        int i = 0; // Current index of shapes 1
        int j = 0; // Current index of shapes 2
        int k = 0; // Current index of sortedShapes

        while(i < shapes1.length && j < shapes2.length){
            if(this.shapes1[i].getArea() < this.shapes2[j].getArea()){
                this.sortedShapes[k++] = this.shapes1[i++];
            } else {
                this.sortedShapes[k++] = this.shapes2[j++];
            }
        }

        while(i < shapes1.length) {
            this.sortedShapes[k++] = this.shapes1[i++];
        }

        while(j < shapes2.length) {
            this.sortedShapes[k++] = this.shapes2[j++];
        }
        System.out.println("Merge Thread Complete");

    }

    /**
     * Getter for sortedShapes array
     * @return Shape[] that is filled with sorted shapes
     */
    public Shape[] getSortedShapes() {
        return sortedShapes;
    }
}
