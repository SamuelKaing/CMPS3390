package skaing.a3;

public class Main {

    public static void main(String[] args) {
        Rectangle r = null;
        try {
            r = new Rectangle(6.77f, 6.77f);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(r);
    }
}
