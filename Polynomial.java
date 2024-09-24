import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;

public class Polynomial {
    private double[] coefficients;
    private int[] exponents;

    public Polynomial() {
        coefficients = new double[] {0};
        exponents = new int[] {0};
    }

    public Polynomial(double[] coefficients, int[] exponents) {
        this.coefficients = coefficients;
        this.exponents = exponents;
    }

    public Polynomial(File f) throws IOException {
        Scanner sc = new Scanner(f);
        String polynomialStr = sc.nextLine();

        sc.close();

        ArrayList<Integer> exponentsAL = new ArrayList<>();
        ArrayList<Double> coefficientsAL = new ArrayList<>();

        String[] polynomialTerms = polynomialStr.split("(?=\\+|-)");

        for (String termStr : polynomialTerms) {
            String[] termComponents = termStr.split("x", 2);
            
            if (termComponents.length == 1) {
                // Constant (i.e. coefficient of x^0)
                coefficientsAL.add(Double.parseDouble(termComponents[0]));
                exponentsAL.add(0);
            } else {
                coefficientsAL.add(Double.parseDouble(termComponents[0]));
                exponentsAL.add(Integer.parseInt(termComponents[1]));
            }
        }

        coefficients = coefficientsAL.stream().mapToDouble(Double::doubleValue).toArray();
        exponents = exponentsAL.stream().mapToInt(Integer::intValue).toArray();
    }

    public Polynomial add(Polynomial other) {
        // Keys represent powers of x
        // Values represent the coefficient of that power of x
        HashMap<Integer, Double> sum = new HashMap<>();

        // Add polynomial "this" to HashMap
        for (int i = 0; i < this.exponents.length; i++) {
            int e = this.exponents[i];
            if (sum.containsKey(e)) {
                sum.put(e, sum.get(e) + this.coefficients[i]);
            }
            else {
                sum.put(e, this.coefficients[i]);
            }
        }
        
        // Add polynomial "other" to HashMap
        for (int i = 0; i < other.exponents.length; i++) {
            int e = other.exponents[i];
            if (sum.containsKey(e)) {
                sum.put(e, sum.get(e) + other.coefficients[i]);
            }
            else {
                sum.put(e, other.coefficients[i]);
            }
        }

        // Convert HashMap to arrays
        int[] sumExponents = sum.keySet().stream().mapToInt(Integer::intValue).toArray();
        Arrays.sort(sumExponents);
        
        double[] sumCoefficients = new double[sum.size()];
        for (int i = 0; i < sum.size(); i++) {
            sumCoefficients[i] = sum.get(sumExponents[i]);
        }

        return new Polynomial(sumCoefficients, sumExponents);
    }

    public Polynomial multiply(Polynomial other) {
        // Keys represent powers of x
        // Values represent the coefficient of that power of x
        HashMap<Integer, Double> product = new HashMap<>();

        // Computation
        for (int i = 0; i < this.exponents.length; i++) {
            for (int j = 0; j < other.exponents.length; j++) {
                int e = this.exponents[i] + other.exponents[j];
                if (product.containsKey(e)) {
                    product.put(e, product.get(e) + this.coefficients[i] * other.coefficients[j]);
                }
                else {
                    product.put(e, this.coefficients[i] * other.coefficients[j]);
                }
            }
        }
        
        // Convert HashMap to arrays
        int[] productExponents = product.keySet().stream().mapToInt(Integer::intValue).toArray();
        Arrays.sort(productExponents);
        
        double[] productCoefficients = new double[product.size()];
        for (int i = 0; i < product.size(); i++) {
            productCoefficients[i] = product.get(productExponents[i]);
        }

        return new Polynomial(productCoefficients, productExponents);
    }

    public double evaluate(double x) {
        double result = 0;

        for (int i = 0; i < coefficients.length; i++) {
            result += coefficients[i] * Math.pow(x, exponents[i]);
        }

        return result;
    }

    public boolean hasRoot(double value) {
        return evaluate(value) == 0;
    }

    public void saveToFile(String filePath) throws IOException {
        FileWriter fw = new FileWriter(filePath);

        for (int i = 0; i < coefficients.length; i++) {
            String term = "";

            if (i != 0 && coefficients[i] > 0) {
                term += "+";
            }
            
            if (exponents[i] == 0) {
                term += String.valueOf(coefficients[i]);
            }
            else {
                term += String.join("x", String.valueOf(coefficients[i]), String.valueOf(exponents[i]));
            }

            fw.write(term);
        }

        fw.close();
    }
}