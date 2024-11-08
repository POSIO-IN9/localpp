package edu.pnu.service;

import edu.pnu.domain.Edu_list;
import edu.pnu.repository.ListRepository;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

@Service
public class TopsisService {

    @Autowired
    private ListRepository eduListRepository;

    @Setter
    private double[] weights;

    public List<Edu_list> calculateTopsis(String codePart) {
        List<Edu_list> eduLists = eduListRepository.findByNcsCodeFirstSix(codePart);
        if (eduLists.isEmpty()) {
            throw new IllegalStateException("No data available to process");
        }

        
        List<DataRow> data = new ArrayList<>();

        for (Edu_list edu : eduLists) {
            double[] values = {edu.getPs(), edu.getStarrating(), edu.getEmp()};
            data.add(new DataRow(edu.getCourse_id().toString(), values));
        }

        int numRows = data.size();
        int numCols = data.get(0).getValues().length;

        double[][] matrix = new double[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            matrix[i] = data.get(i).getValues();
        }
        RealMatrix realMatrix = new Array2DRowRealMatrix(matrix);

        // 행렬 정규화
        RealMatrix normalizedMatrix = normalizeMatrix(realMatrix);

        // 가중치 적용
        RealMatrix weightedMatrix = applyWeights(normalizedMatrix);

        // 이상적 솔루션 및 비이상적 솔루션 식별
        double[] idealSolution = getIdealSolution(weightedMatrix);
        double[] antiIdealSolution = getAntiIdealSolution(weightedMatrix);

        // 이상적 및 비이상적 솔루션과의 거리 계산
        double[] distanceToIdeal = calculateDistance(weightedMatrix, idealSolution);
        double[] distanceToAntiIdeal = calculateDistance(weightedMatrix, antiIdealSolution);

        // 이상적 솔루션과의 유사도 계산
        double[] similarityToIdeal = calculateSimilarity(distanceToIdeal, distanceToAntiIdeal);

        
        return updateEduListsWithScores(eduLists, similarityToIdeal);
    }

    private RealMatrix normalizeMatrix(RealMatrix matrix) {
        int numCols = matrix.getColumnDimension();
        double[][] normalizedData = new double[matrix.getRowDimension()][numCols];

        for (int j = 0; j < numCols; j++) {
            double max = DoubleStream.of(matrix.getColumn(j)).max().orElse(1.0);
            double min = DoubleStream.of(matrix.getColumn(j)).min().orElse(0.0);
            for (int i = 0; i < matrix.getRowDimension(); i++) {
                normalizedData[i][j] = (matrix.getEntry(i, j) - min) / (max - min);
            }
        }
        return new Array2DRowRealMatrix(normalizedData);
    }

    private RealMatrix applyWeights(RealMatrix matrix) {
        double[][] weightedData = new double[matrix.getRowDimension()][matrix.getColumnDimension()];
        for (int i = 0; i < matrix.getRowDimension(); i++) {
            for (int j = 0; j < matrix.getColumnDimension(); j++) {
                weightedData[i][j] = matrix.getEntry(i, j) * weights[j];
            }
        }
        return new Array2DRowRealMatrix(weightedData);
    }

    private double[] getIdealSolution(RealMatrix matrix) {
        int numCols = matrix.getColumnDimension();
        double[] idealSolution = new double[numCols];
        for (int j = 0; j < numCols; j++) {
            idealSolution[j] = DoubleStream.of(matrix.getColumn(j)).max().orElse(0.0);
        }
        return idealSolution;
    }

    private double[] getAntiIdealSolution(RealMatrix matrix) {
        int numCols = matrix.getColumnDimension();
        double[] antiIdealSolution = new double[numCols];
        for (int j = 0; j < numCols; j++) {
            antiIdealSolution[j] = DoubleStream.of(matrix.getColumn(j)).min().orElse(0.0);
        }
        return antiIdealSolution;
    }

    private double[] calculateDistance(RealMatrix matrix, double[] solution) {
        return IntStream.range(0, matrix.getRowDimension())
                .mapToDouble(i -> Math.sqrt(IntStream.range(0, matrix.getColumnDimension())
                        .mapToDouble(j -> Math.pow(matrix.getEntry(i, j) - solution[j], 2))
                        .sum()))
                .toArray();
    }

    private double[] calculateSimilarity(double[] distanceToIdeal, double[] distanceToAntiIdeal) {
        return IntStream.range(0, distanceToIdeal.length)
                .mapToDouble(i -> distanceToAntiIdeal[i] / (distanceToIdeal[i] + distanceToAntiIdeal[i]))
                .toArray();
    }

    private List<Edu_list> updateEduListsWithScores(List<Edu_list> eduLists, double[] similarityToIdeal) {
        Integer[] indices = IntStream.range(0, similarityToIdeal.length).boxed().toArray(Integer[]::new);
        Arrays.sort(indices, (i1, i2) -> Double.compare(similarityToIdeal[i2], similarityToIdeal[i1]));

        List<Edu_list> sortedEduLists = new ArrayList<>();
        for (int i : indices) {
            Edu_list edu = eduLists.get(i);
            double score = Math.round(similarityToIdeal[i] * 100.0) / 100.0;
            edu.setScore(score);  // 스코어 설정
            sortedEduLists.add(edu);
        }

        return sortedEduLists;
    }

    @Getter
    private static class DataRow {
        private final String courseId;
        private final double[] values;

        public DataRow(String courseId, double[] values) {
            this.courseId = courseId;
            this.values = values;
        }
    }
}
