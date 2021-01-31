package skaing.a2;
import java.util.Scanner;

/**
 * Main driver class for our a2
 * @author Samuel Kaing
 * @version 1.0
 */

public class Main {

    /**
     * Main entry point for our application
     * @param args String array holding arguments pass in on the command line
     */
    public static void main(String[] args) {
	    Scanner scanner = new Scanner(System.in);

	    System.out.println("Enter either a math problem or quit");
	    System.out.println("Math problem should be in the form of value mathOperator value");
	    System.out.println("i.e. \n 43.1 * 2.2");


		// Loops until user types quit
		String line;
		while(!(line = scanner.nextLine()).equalsIgnoreCase("quit")){
			System.out.println(Calc.evaluate(line));
		}
    }
}