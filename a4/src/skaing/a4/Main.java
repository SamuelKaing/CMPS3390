package skaing.a4;

import skaing.a3.*;

import java.util.Random;
import java.util.Scanner;

/**
 * Main driver for a4
 * @author Samuel Kaing
 * @version 1.0
 */
public class Main {

    public static void main(String[] args) throws Exception {
		Scanner scan = new Scanner(System.in);
		Random rand = new Random();

		System.out.print("Do you want a [S]ingle Thread or [D]ual Thread or [Q]uad Thread "
				+ "[N] Thread? ");
		char selection = scan.next().charAt(0);

		System.out.print("How many shapes do you want to sort? ");
		int count = scan.nextInt();
		Shape[] shapes = new Shape[count];

		for(int i = 0; i<count; i++) {
			int t = rand.nextInt(5);
			switch (t) {
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

		switch(selection) {
			case 's':
			case 'S':
				singleSort(shapes);
				break;
			case 'd':
			case 'D':
				dualSort(shapes);
				break;
			case 'q':
			case 'Q':
				quadSort(shapes);
				break;
			case 'n':
			case 'N':
				System.out.println("How many threads do you want to start?");
				int numThreads = scan.nextInt();
				nSort(shapes, numThreads);
				break;
		}
    }

	/**
	 * Handles single thread sorting of shapes array
	 * @param shapes array of Shape that represents the amount of shapes being sorted by the user
	 * @throws InterruptedException when a thread is waiting, sleeping, or otherwise occupied
	 */
	private static void singleSort(Shape[] shapes) throws InterruptedException {
		ThreadSort threadsort = new ThreadSort(shapes, 0, shapes.length);
		long startTime = System.nanoTime();
		threadsort.start();
		threadsort.join();
		long endTime = System.nanoTime();

		long duration = ((endTime - startTime) / 1000000);

		for (Shape s : threadsort.gettShapes()) {
			System.out.println(s);
		}
		System.out.println("\nSingle Thread Sort Time: " + duration + " milliseconds");
	}

	/**
	 * Handles dual thread sorting of shapes arrau
	 * @param shapes array of Shape that represents the amount of shapes being sorted by the user
	 * @throws InterruptedException when a thread is waiting, sleeping, or otherwise occupied
	 */
	private static void dualSort(Shape[] shapes) throws InterruptedException {
    	int mid = Math.round(shapes.length/2f);

    	ThreadSort t1 = new ThreadSort(shapes, 0, mid);
    	ThreadSort t2 = new ThreadSort(shapes, mid, shapes.length);
		long startTime = System.nanoTime();
    	t1.start();
    	t2.start();

    	t1.join();
    	t2.join();

		MergeSort m = new MergeSort(t1.gettShapes(), t2.gettShapes());
		m.start();
		m.join();
		long endTime = System.nanoTime();
		long duration = ((endTime - startTime) / 1000000);

		for(Shape s : m.getSortedShapes()){
			System.out.println(s);
		}

		System.out.println("\nDual Thread Sort Time: " + duration + " milliseconds");
	}

	/**
	 * Handles quad thread sorting of shapes array
	 * @param shapes array of Shape that represents the amount of shapes being sorted by the user
	 * @throws InterruptedException when a thread is waiting, sleeping, or otherwise occupied
	 */
	private static void quadSort(Shape[] shapes) throws InterruptedException {
    	int shapeCount = shapes.length / 4;
		int shapesRemaining = shapes.length - (shapeCount * 3);

    	ThreadSort[] sortThreads = new ThreadSort[4];
    	long startTime = System.nanoTime();

    	for(int i=0; i<3; i++) {
    		sortThreads[i] = new ThreadSort(shapes, i * shapeCount, i * shapeCount + shapeCount);
    		sortThreads[i].start();
		}
    	sortThreads[3] = new ThreadSort(shapes, shapes.length - shapesRemaining, shapes.length);
    	sortThreads[3].start();

    	for(ThreadSort t : sortThreads){
    		t.join();
		}

    	MergeSort m1 = new MergeSort(sortThreads[0].gettShapes(), sortThreads[1].gettShapes());
    	MergeSort m2 = new MergeSort(sortThreads[2].gettShapes(), sortThreads[3].gettShapes());

    	m1.start();
    	m2.start();
    	m1.join();
    	m2.join();

    	MergeSort m3 = new MergeSort(m1.getSortedShapes(), m2.getSortedShapes());
    	m3.start();
    	m3.join();

    	long endTime = System.nanoTime();
    	long duration = (endTime - startTime) / 1000000;

    	for(Shape s : m3.getSortedShapes()){
    		System.out.println(s);
		}

    	System.out.println("\nQuad Thread Sort Time: " + duration + " milliseconds");
	}

	private static void nSort(Shape[] shapes, int threads) throws Exception {
		if (threads != 1 && (threads % 2) != 0) {
			throw new Exception("Number of Threads must be an even number");
		}

		int shapeCount = shapes.length / threads;
		ThreadSort[] nsortThreads = new ThreadSort[threads];

		for(int i=0; i<threads; i++) {
			nsortThreads[i] = new ThreadSort(shapes, i * shapeCount, i * shapeCount + shapeCount);
			nsortThreads[i].start();
		}

		for(ThreadSort t : nsortThreads) {
			t.join();
		}

		MergeSort[] mSort = new MergeSort[nsortThreads.length];

		for(int i = 0, j = 1; i < shapeCount && j < (shapeCount + 1); i+=2, j+=2) {
			mSort[i] = new MergeSort(nsortThreads[i].gettShapes(), nsortThreads[j].gettShapes());
			mSort[i].start();
		}

		for(Shape s : mSort[1].getSortedShapes()){
			System.out.println(s);
		}


	}
}


