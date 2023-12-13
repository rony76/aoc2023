package org.nalda.adventofcode2023.mirrors;

import java.util.List;

public class Mirror {
    private final MatrixSymbol[][] matrix;

    public Mirror(MatrixSymbol[][] matrix) {
        this.matrix = matrix;
    }

    static Mirror fromStringList(List<String> input) {
        MatrixSymbol[][] matrix = new MatrixSymbol[input.size()][input.get(0).length()];
        for (int row = 0; row < input.size(); row++) {
            String s = input.get(row);
            for (int column = 0; column < s.length(); column++) {
                matrix[row][column] = MatrixSymbol.fromChar(s.charAt(column));
            }
        }
        return new Mirror(matrix);
    }

    public long getValue() {
        int candidateCol = findVerticalMirror();
        if (candidateCol > -1) return candidateCol;

        int candidateRow = findHorizontalMirror();
        if (candidateRow > -1) return candidateRow;

        throw new RuntimeException("Please, check what's going on");
    }

    private int findVerticalMirror() {
        final int width = matrix[0].length;
        final int height = matrix.length;
        for (int candidateCol = 1; candidateCol < width; candidateCol++) {
            int checkColumnCount = Math.min(candidateCol, width - candidateCol);

            int differences = 0;
            for (int col = 0; col < checkColumnCount && differences <= 1; col++) {
                int leftCol = candidateCol - checkColumnCount + col;
                int rightCol = candidateCol + checkColumnCount - 1 - col;
                differences += columnsDifferences(height, leftCol, rightCol);
            }

            if (differences == 1) {
                return candidateCol;
            }
        }
        return -1;
    }

    private int findHorizontalMirror() {
        final int width = matrix[0].length;
        final int height = matrix.length;
        for (int candidateRow = 1; candidateRow < height; candidateRow++) {
            int checkRowCount = Math.min(candidateRow, height - candidateRow);

            int differences = 0;
            for (int row = 0; row < checkRowCount && differences <= 1; row++) {
                int upperRow = candidateRow - checkRowCount + row;
                int lowerRow = candidateRow + checkRowCount - 1 - row;
                differences += rowsDifferences(width, upperRow, lowerRow);
            }

            if (differences == 1) {
                return 100 * candidateRow;
            }
        }
        return -1;
    }

    private int columnsDifferences(int height, int leftCol, int rightCol) {
        int result = 0;
        for (int row = 0; row < height; row++) {
            if (matrix[row][leftCol] != matrix[row][rightCol]) {
                result++;
            }
        }

        return result;
    }

    private int rowsDifferences(int width, int upperRow, int lowerRow) {
        int result = 0;
        for (int col = 0; col < width; col++) {
            if (matrix[upperRow][col] != matrix[lowerRow][col]) {
                result++;
            }
        }

        return result;
    }

    public enum MatrixSymbol {
        ASH,
        ROCKS;

        static MatrixSymbol fromChar(char c) {
            switch (c) {
                case '#':
                    return ROCKS;
                case '.':
                    return ASH;
                default:
                    throw new IllegalArgumentException("Unknown symbol: " + c);
            }
        }
    }
}
