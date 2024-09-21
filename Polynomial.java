
public class Polynomial {
  // Constructors
  private double[] coefficients;

  public Polynomial() {
    this.coefficients = new double[] { 0 };
  }

  public Polynomial(double[] userCoefficients) {
    this.coefficients = userCoefficients;
  }

  // Methods
  public Polynomial add(Polynomial addedPolynomial) {
    int maxLength = Math.max(this.coefficients.length, addedPolynomial.coefficients.length);
    double [] addedCoefficients = new double[maxLength];

    for (int i = 0; i < maxLength; i++){
      if(i >= this.coefficients.length){
        addedCoefficients[i] = addedPolynomial.coefficients[i];
      }
      else if (i >= addedPolynomial.coefficients.length){
        addedCoefficients[i] = this.coefficients[i];
      }
      else {
        addedCoefficients[i] = this.coefficients[i] + addedPolynomial.coefficients[i];
      }
    }

    return new Polynomial(addedCoefficients);
  }

  public double evaluate(double x) {
    double sum = 0;
    for (int i = 0; i < this.coefficients.length; i++) {
      sum += this.coefficients[i] * Math.pow(x, i);
    }
    return sum;
  }

  public boolean hasRoot(double x) {
    double result = evaluate(x);
    return result == 0;
  }

  public static void main(String[] args) {
    return;
  }
}
