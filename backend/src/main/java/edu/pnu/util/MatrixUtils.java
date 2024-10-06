package edu.pnu.util;

import org.apache.commons.math3.linear.RealMatrix;

public class MatrixUtils {

    public static double[] getColumnMaxValues(RealMatrix matrix) {
        int numCols = matrix.getColumnDimension();
        double[] maxValues = new double[numCols];
        for (int j = 0; j < numCols; j++) {
            maxValues[j] = Double.NEGATIVE_INFINITY;
            for (int i = 0; i < matrix.getRowDimension(); i++) {
                double value = matrix.getEntry(i, j);
                if (value > maxValues[j]) {
                    maxValues[j] = value;
                }
            }
        }
        return maxValues;
    }

    public static double[] getColumnMinValues(RealMatrix matrix) {
        int numCols = matrix.getColumnDimension();
        double[] minValues = new double[numCols];
        for (int j = 0; j < numCols; j++) {
            minValues[j] = Double.POSITIVE_INFINITY;
            for (int i = 0; i < matrix.getRowDimension(); i++) {
                double value = matrix.getEntry(i, j);
                if (value < minValues[j]) {
                    minValues[j] = value;
                }
            }
        }
        return minValues;
    }
}
