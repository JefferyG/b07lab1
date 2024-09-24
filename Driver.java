import java.io.File;
import java.io.IOException;

public class Driver {
    public static void main(String [] args) {
        String H_LINE = "=".repeat(45);

        System.out.println(H_LINE + "\nTEST BLANK CONSTRUCTOR AND EVALUATE METHOD\n" + H_LINE);

        Polynomial p = new Polynomial();
        System.out.println("p(3) = " + p.evaluate(3) + " (should be 0)");

        System.out.println();
        System.out.println(H_LINE + "\nTEST ARRAY CONSTRUCTOR, ADD & HASROOT METHODS\n" + H_LINE);

        double [] c1 = {6, 5};
        int [] e1 = {0, 3};
        Polynomial p1 = new Polynomial(c1, e1);

        double [] c2 = {-2, -9};
        int [] e2 = {1, 4};
        Polynomial p2 = new Polynomial(c2, e2);

        Polynomial s = p1.add(p2);
        System.out.println("s(0.1) = " + s.evaluate(0.1) + " (should be 5.8041)");
        if(s.hasRoot(1))
            System.out.println("1 is a root of s (this is correct)");
        else
            System.out.println("1 is not a root of s (test failed)");

        System.out.println();
        System.out.println(H_LINE + "\nTEST MULTIPLY METHOD\n" + H_LINE);

        Polynomial product = p1.multiply(p2);
        System.out.println("(p1 * p2)(-1) = " + product.evaluate(-1) + " (should be -7)");
        System.out.println("(p1 * p2)(1) = " + product.evaluate(1) + " (should be -121)");

        System.out.println();
        System.out.println(H_LINE + "\nTEST READING FROM FILE\n" + H_LINE);

        try {
            Polynomial readTest = new Polynomial(new File("readTest.txt"));
            System.out.println("r(0) = " + readTest.evaluate(0) + " (should be 5)");
            System.out.println("r(1) = " + readTest.evaluate(1) + " (should be 9)");
            System.out.println("r(-2) = " + readTest.evaluate(-2) + " (should be 1785)");
        } catch (IOException e) {
            System.out.println("Couldn't read polynomial from file 'readTest.txt'.");
        }

        System.out.println();
        System.out.println(H_LINE + "\nTEST WRITING TO FILE\n" + H_LINE);

        try {
            product.saveToFile("writeTest.txt");
        } catch (IOException e) {
            System.out.println("Couldn't save polynomial to file.");
        }

        try {
            Polynomial writeTest = new Polynomial(new File("writeTest.txt"));
            if (writeTest.evaluate(-1) == -7 && writeTest.evaluate(1) == -121) {
                System.out.println("Polynomial successfully saved.");
            } else {
                System.out.println("Polynomial write to file test failed.");
            }
        } catch (IOException e) {
            System.out.println("Couldn't read polynomial from file 'writeTest.txt'.");
        }
    }
}