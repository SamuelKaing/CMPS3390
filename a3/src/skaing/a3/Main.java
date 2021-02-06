package skaing.a3;

public class Main {

    public static void main(String[] args) {
        Oval oval = null;
        try {
            oval = new Oval(2f, 2f);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(oval);
    }
}
