import java.util.Arrays;
import java.io.*;

public class Polynomial {
  // Constructors
  public double[] coefficients;
  public int[] exponents;

  public Polynomial() {
    this.coefficients = new double[] {};
    this.exponents = new int[] {};
  }

  public Polynomial(double[] userCoefficients, int[] userExponents) {
    this.coefficients = userCoefficients;
    this.exponents = userExponents;
  }

  public Polynomial(File file) {
    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
      String line = reader.readLine();
      if (line == null) {
        this.coefficients = new double[] {};
        this.exponents = new int[] {};
      } else {
        String[] splitLine = line.split("(?=[+-])");
        int numberOfTerms = splitLine.length;
        double[] coefficients = new double[numberOfTerms];
        int[] exponents = new int[numberOfTerms];

        for (int i = 0; i < numberOfTerms; i++) {
          String[] term = splitLine[i].split("[x]");

          if (term.length == 1) {
            coefficients[i] = Double.parseDouble(term[0]);
            exponents[i] = 0;
          } else {
            if (term[0].equals("+") || term[0].equals("")) {
              coefficients[i] = 1;
            } else if (term[0].equals("-")) {
              coefficients[i] = -1;
            } else {
              coefficients[i] = Double.parseDouble(term[0]);
            }
            exponents[i] = Integer.parseInt(term[1]);
          }
        }
        this.coefficients = coefficients;
        this.exponents = exponents;
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // Methods
  private int findExponentIndex(int[] exponentsArray, int targetExponent) {
    for (int i = 0; i < exponentsArray.length; i++) {
      if (exponentsArray[i] == targetExponent) {
        return i;
      }
    }
    return -1;
  }

  private double[] mergeArrays(double[] a1, double[] a2, int length) {
    double[] mergedArray = new double[length];

    for (int i = 0; i < a1.length; i++) {
      mergedArray[i] = a1[i];
    }
    for (int i = 0; i < a2.length; i++) {
      mergedArray[a1.length + i] = a2[i];
    }
    return mergedArray;
  }

  private int[] mergeArrays(int[] a1, int[] a2, int length) {
    int[] mergedArray = new int[length];

    for (int i = 0; i < a1.length; i++) {
      mergedArray[i] = a1[i];
    }
    for (int i = 0; i < a2.length; i++) {
      mergedArray[a1.length + i] = a2[i];
    }
    return mergedArray;
  }

  private int countUniqueElements(int[] array) {
    int uniqueCount = 0;

    for (int i = 0; i < array.length; i++) {
      boolean isUnique = true;

      for (int j = 0; j < i; j++) {
        if (array[i] == array[j]) {
          isUnique = false;
          break;
        }
      }
      if (isUnique) {
        uniqueCount++;
      }
    }

    return uniqueCount;
  }

  private Polynomial removeDuplicates(double[] coefficients, int[] exponents) {
    int correctArraySize = countUniqueElements(exponents);
    double[] correctCoefficients = new double[correctArraySize];
    int[] correctExponents = new int[correctArraySize];
    int uniqueCount = 0;

    for (int i = 0; i < exponents.length; i++) {
      boolean isUnique = true;
      for (int j = 0; j < i; j++) {
        if (exponents[i] == exponents[j]) {
          isUnique = false;
          break;
        }
      }
      if (isUnique) {
        correctCoefficients[uniqueCount] = coefficients[i];
        correctExponents[uniqueCount] = exponents[i];
        uniqueCount++;
      }
    }

    return new Polynomial(correctCoefficients, correctExponents);
  }

  public Polynomial add(Polynomial addedPolynomial) {
    int maxLength = this.coefficients.length + addedPolynomial.coefficients.length;
    double[] joinedCoefficients = mergeArrays(this.coefficients, addedPolynomial.coefficients, maxLength);
    int[] joinedExponents = mergeArrays(this.exponents, addedPolynomial.exponents, maxLength);

    for (int i = 0; i < maxLength; i++) {

      int sharedExponenetIndex = findExponentIndex(Arrays.copyOfRange(joinedExponents, i + 1, maxLength),
          joinedExponents[i]) + i + 1;

      if (sharedExponenetIndex - i - 1 != -1) {
        joinedCoefficients[i] = joinedCoefficients[i] + joinedCoefficients[sharedExponenetIndex];
      } else {
        joinedCoefficients[i] = joinedCoefficients[i];
      }
    }

    return removeDuplicates(joinedCoefficients, joinedExponents);
  }

  public double evaluate(double x) {
    double sum = 0;
    for (int i = 0; i < this.coefficients.length; i++) {
      sum += this.coefficients[i] * Math.pow(x, this.coefficients[i]);
    }
    return sum;
  }

  public boolean hasRoot(double x) {
    double result = evaluate(x);
    return result == 0;
  }

  private Polynomial addPolynomials(Polynomial[] polynomials) {
    if (polynomials.length == 0) {
      return new Polynomial();
    }
    Polynomial result = polynomials[0];
    for (int i = 1; i < polynomials.length; i++) {
      result = result.add(polynomials[i]);
    }
    return result;
  }

  public Polynomial multiply(Polynomial multipliedPolynomial) {
    int multiplicandLength = this.coefficients.length;
    int multiplierLength = multipliedPolynomial.coefficients.length;

    Polynomial[] polynomialProducts = new Polynomial[multiplicandLength];

    for (int i = 0; i < multiplicandLength; i++) {
      double[] multipliedCoefficients = new double[multiplierLength];
      int[] addedExponents = new int[multiplierLength];
      for (int j = 0; j < multiplierLength; j++) {
        double coefficientProduct = this.coefficients[i] * multipliedPolynomial.coefficients[j];
        int exponentSum = this.exponents[i] + multipliedPolynomial.exponents[j];
        multipliedCoefficients[j] = coefficientProduct;
        addedExponents[j] = exponentSum;
      }
      polynomialProducts[i] = new Polynomial(multipliedCoefficients, addedExponents);
    }
    return addPolynomials(polynomialProducts);
  }

  public void saveToFile(String file) {
    File path = new File(file);
    String polynomialText = "";
    try (FileWriter output = new FileWriter(path, false)) {
      for (int i = 0; i < this.coefficients.length; i++) {
        if (this.coefficients[i] > 0) {
          if (i > 0) {
            polynomialText += "+";
          }
          polynomialText += this.coefficients[i];
        } else {
          polynomialText += this.coefficients[i];
        }
        if (this.exponents[i] != 0) {
          polynomialText += "x";
          polynomialText += this.exponents[i];
        }
      }
      output.write(polynomialText);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
