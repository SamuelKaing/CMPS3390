package skaing.a3;

import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        //RightTriangle r = new RightTriangle(6.77f, 6.77f);
        Scanner scan = new Scanner(System.in);
        Random rand = new Random();

        System.out.print("How many shapes would you like? ");
        int numShapes = scan.nextInt();

        Shape[] shapes = new Shape[numShapes];

        for (int i = 0; i < numShapes; i++) {
            int select = rand.nextInt(5);
            switch (select) {
                case 0:
                    shapes[i] = new Circle(rand.nextFloat() * 20.0f);
                    break;
                case 1:
                    shapes[i] = new Oval(rand.nextFloat() * 20.0f, rand.nextFloat() * 20.0f);
                    break;
                case 2:
                    shapes[i] = new Square(rand.nextFloat() * 20.0f);
                    break;
                case 3:
                    shapes[i] = new Rectangle(rand.nextFloat() * 20.0f, rand.nextFloat() * 20.0f);
                    break;
                case 4:
                    shapes[i] = new RightTriangle(rand.nextFloat() * 20.0f, rand.nextFloat() * 20.0f);
                    break;
            }
        }

        for (Shape s : shapes) {
            System.out.println(s);
        }

    }
}
