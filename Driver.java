import java.util.Arrays;
import java.io.*;

public class Driver {
  public static void addCase1() {
    // Test 1: Adding two polynomials with the same exponents
    double[] c1 = { 6, -2, 5 };
    int[] e1 = { 0, 1, 3 };

    double[] c2 = { 1, -1, 8 };
    int[] e2 = { 0, 1, 3 };

    double[] expectedC = { 7.0, -3.0, 13.0 };
    int[] expectedE = { 0, 1, 3 };

    Polynomial p1 = new Polynomial(c1, e1);
    Polynomial p2 = new Polynomial(c2, e2);
    Polynomial result = p1.add(p2);

    if (!Arrays.equals(result.coefficients, expectedC) || !Arrays.equals(result.exponents, expectedE)) {
      System.out.println("failed test 1");
      System.out.println("Expected: " + Arrays.toString(expectedC) + Arrays.toString(expectedE));
      System.out.println("Got: " + Arrays.toString(result.coefficients) + Arrays.toString(result.exponents));
    } else {
      System.out.println("passed test 1");
    }
  }

  public static void addCase2() {
    // Test 2: Adding two polynomials with the same exponents different order
    double[] c1 = { 6, 5, -2 };
    int[] e1 = { 3, 0, 5 };

    double[] c2 = { 1, 9, -1, 8 };
    int[] e2 = { 0, 4, 1, 3 };

    double[] expectedC = { 6.0, 9.0, -1.0, 14.0, -2 };
    int[] expectedE = { 0, 4, 1, 3, 5 };

    Polynomial p1 = new Polynomial(c1, e1);
    Polynomial p2 = new Polynomial(c2, e2);
    Polynomial result = p2.add(p1);

    if (!Arrays.equals(result.coefficients, expectedC) || !Arrays.equals(result.exponents, expectedE)) {
      System.out.println("failed test 2");
      System.out.println("Expected: " + Arrays.toString(expectedC) + Arrays.toString(expectedE));
      System.out.println("Got: " + Arrays.toString(result.coefficients) + Arrays.toString(result.exponents));
    } else {
      System.out.println("passed test 2");
    }
  }

  public static void addCase3() {
    // Test 3: Adding two polynomials with the no matching exponents
    double[] c1 = { 48, 18 };
    int[] e1 = { 3, 1 };

    double[] c2 = { -8, -3 };
    int[] e2 = { 4, 2 };

    double[] expectedC = { 48.0, 18.0, -8, -3 };
    int[] expectedE = { 3, 1, 4, 2 };

    Polynomial p1 = new Polynomial(c1, e1);
    Polynomial p2 = new Polynomial(c2, e2);
    Polynomial result = p1.add(p2);

    if (!Arrays.equals(result.coefficients, expectedC) || !Arrays.equals(result.exponents, expectedE)) {
      System.out.println("failed test 3");
      System.out.println("Expected: " + Arrays.toString(expectedC) + Arrays.toString(expectedE));
      System.out.println("Got: " + Arrays.toString(result.coefficients) + Arrays.toString(result.exponents));
    } else {
      System.out.println("passed test 3");
    }
  }

  public static void multCase1() {
    double[] c1 = { 6, -1, 1 };
    int[] e1 = { 0, 1, 2 };

    double[] c2 = { 8, 3 };
    int[] e2 = { 3, 1 };

    double[] expectedC = { 51.0, 18.0, -8.0, -3.0, 8.0 };
    int[] expectedE = { 3, 1, 4, 2, 5 };

    Polynomial p1 = new Polynomial(c1, e1);
    Polynomial p2 = new Polynomial(c2, e2);
    Polynomial result = p1.multiply(p2);

    if (!Arrays.equals(result.coefficients, expectedC) ||
        !Arrays.equals(result.exponents, expectedE)) {
      System.out.println("failed mult test 1");
      System.out.println("Expected: " + Arrays.toString(expectedC) +
          Arrays.toString(expectedE));
      System.out.println("Got: " + Arrays.toString(result.coefficients) +
          Arrays.toString(result.exponents));
    } else {
      System.out.println("passed mult test 1");
    }
  }

  public static void multCase2() {
    // Multiply Test 2: Testing for multiplication by 0
    double[] c1 = { 6, -1, 1 };
    int[] e1 = { 0, 1, 2 };

    double[] expectedC = {};
    int[] expectedE = {};

    Polynomial p1 = new Polynomial(c1, e1);
    Polynomial p2 = new Polynomial();
    Polynomial result = p1.multiply(p2);

    if (!Arrays.equals(result.coefficients, expectedC) ||
        !Arrays.equals(result.exponents, expectedE)) {
      System.out.println("failed mult test 2");
      System.out.println("Expected: " + Arrays.toString(expectedC) +
          Arrays.toString(expectedE));
      System.out.println("Got: " + Arrays.toString(result.coefficients) +
          Arrays.toString(result.exponents));
    } else {
      System.out.println("passed mult test 2");
    }
  }

  public static void main(String[] args) {

    File path = new File("./file.txt");
    Polynomial p1 = new Polynomial(path);
    System.out.println(Arrays.toString(p1.coefficients));
    System.out.println(Arrays.toString(p1.exponents));

    // double[] c1 = { 6, -1, 1 };
    // int[] e1 = { 0, 1, 2 };

    // double[] expectedC = { 14.0, 6.0, -3.0 };
    // int[] expectedE = { 3, 0, 1 };

    // Polynomial p2 = new Polynomial(c1, e1);
    // p2.saveToFile("./file.txt");

    // Add method test cases
    addCase1();
    addCase2();
    addCase3();
    // Multiply method test cases
    multCase1();
    multCase2();
  }
}
