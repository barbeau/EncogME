/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.encog.util;

/**
 * This class implements math methods used in Encog that are part of the regular Java SE libraries
 * but not available in Java ME.
 *
 * pow() function under BSD license from: http://today.java.net/pub/a/today/2007/11/06/creating-java-me-math-pow-method.html
 * exp() from: http://martin.ankerl.com/2007/02/11/optimized-exponential-functions-for-java/
 * @author Sean Barbeau
 */
public class Math {

    
    /**
     * Returns the value of the first argument raised to the power of the second argument.
     *  (under BSD license from: http://today.java.net/pub/a/today/2007/11/06/creating-java-me-math-pow-method.html)
     * @param a the base
     * @param b the exponent
     * @return the value a^b.
     */
    public static double pow(double a, double b) {
        // true if base is greater than 1
        boolean gt1 = (java.lang.Math.sqrt((a - 1) * (a - 1)) <= 1) ? false : true;

        int oc = -1; // used to alternate math symbol (+,-)
        int iter = 20; // number of iterations
        double p, x, x2, sumX, sumY;

        // is exponent a whole number?
        if ((b - java.lang.Math.floor(b)) == 0) {
            // return base^exponent
            p = a;
            for (int i = 1; i < b; i++) {
                p *= a;
            }
            return p;
        }

        x = (gt1) ? (a / (a - 1)) : // base is greater than 1
                (a - 1); // base is 1 or less

        sumX = (gt1) ? (1 / x) : // base is greater than 1
                x; // base is 1 or less

        for (int i = 2; i < iter; i++) {
            // find x^iteration
            p = x;
            for (int j = 1; j < i; j++) {
                p *= x;
            }

            double xTemp = (gt1) ? (1 / (i * p)) : // base is greater than 1
                    (p / i); // base is 1 or less

            sumX = (gt1) ? (sumX + xTemp) : // base is greater than 1
                    (sumX + (xTemp * oc)); // base is 1 or less

            oc *= -1; // change math symbol (+,-)
        }

        x2 = b * sumX;

        sumY = 1 + x2; // our estimate

        for (int i = 2; i <= iter; i++) {
            // find x2^iteration
            p = x2;
            for (int j = 1; j < i; j++) {
                p *= x2;
            }

            // multiply iterations (ex: 3 iterations = 3*2*1)
            int yTemp = 2;
            for (int j = i; j > 2; j--) {
                yTemp *= j;
            }

            // add to estimate (ex: 3rd iteration => (x2^3)/(3*2*1) )
            sumY += p / yTemp;
        }

        return sumY; // return our estimate
    }

    /**
     * Returns Euler's number e raised to the power of a double value.
     * from http://martin.ankerl.com/2007/02/11/optimized-exponential-functions-for-java/
     * @param val the exponent to raise e to
     * @return the value ea, where e is the base of the natural logarithms
     */
   public static double exp(double val) {
          final long tmp = (long) (1512775 * val + (1072693248 - 60801));
          return Double.longBitsToDouble(tmp << 32);
   }
}
