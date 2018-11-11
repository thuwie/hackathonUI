package org.hackaton.coffeeservice.prediction;

import java.util.List;

/**
 * Copypasted from https://introcs.cs.princeton.edu/java/97data/LinearRegression.java.html
 */
public class LinearPredictor {
    public static double predict(List<Double> x, List<Double> y) {
        double sumx = 0.0, sumy = 0.0;

        for (int i = 0; i < x.size(); i++) {
            sumx += x.get(i);
            sumy += y.get(i);
        }

        double xbar = sumx / x.size();
        double ybar = sumy / x.size();

        // second pass: compute summary statistics
        double xxbar = 0.0, xybar = 0.0;
        for (int i = 0; i < x.size(); i++) {
            xxbar += (x.get(i) - xbar) * (x.get(i) - xbar);
            xybar += (x.get(i) - xbar) * (y.get(i) - ybar);
        }
        return xybar / xxbar;
    }
}
