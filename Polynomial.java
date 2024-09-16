public class Polynomial {
    private double[] coefficients;

    public Polynomial() {
        coefficients = new double[] {0};
    }

    public Polynomial(double[] coefficients) {
        this.coefficients = coefficients;
    }

    public Polynomial add(Polynomial other) {
        int degree = Math.max(this.coefficients.length, other.coefficients.length);

        double[] coefficientSums = new double[degree];

        int i = 0;
        while (i < this.coefficients.length && i < other.coefficients.length) {
            coefficientSums[i] = this.coefficients[i] + other.coefficients[i];
            i++;
        }

        while (i < this.coefficients.length) {
            coefficientSums[i] = this.coefficients[i];
            i++;
        }

        while (i < other.coefficients.length) {
            coefficientSums[i] = other.coefficients[i];
            i++;
        }

        return new Polynomial(coefficientSums);
    }

    public double evaluate(double x) {
        double result = 0;

        for (int i = 0; i < coefficients.length; i++) {
            result += coefficients[i] * Math.pow(x, i);
        }

        return result;
    }

    public boolean hasRoot(double value) {
        return evaluate(value) == 0;
    }
}