//------------------------------------------------------
// Homework 1
// Written by: Corwyn Lail 23805366
// ECE 373 Object-Oriented Software Design, Section 001 - Fall 2026
// The University of Arizona
//------------------------------------------------------
package edu.arizona.ece373.hw1;

/**
 * A rectangular, mutable matrix of {@code int} values.
 *
 * <p>The array of cells is private. External classes access cells
 * through the public methods provided by this class.</p>
 */
public class Matrix {

    /** The cells stored in this matrix. */
    private int[][] data;

    /**
     * Creates a zero-filled matrix of the given size.
     *
     * @param rows the number of rows, at least 1
     * @param columns the number of columns, at least 1
     * @throws IllegalArgumentException if either dimension is less than 1
     */
    public Matrix(int rows, int columns) {
        if (rows < 1 || columns < 1) {
            throw new IllegalArgumentException(
                    "Rows and columns must be at least 1.");
        }

        data = new int[rows][columns];
    }

    /**
     * Creates a matrix containing a copy of the given array.
     *
     * @param data the initial matrix values
     * @throws IllegalArgumentException if the array is null, empty,
     *         contains a null row, or is not rectangular
     */
    public Matrix(int[][] data) {
        validateData(data);
        this.data = copyData(data);
    }

    /**
     * Returns a copy of the matrix cells.
     *
     * @return a deep copy of the current values
     */
    public int[][] getData() {
        return copyData(data);
    }

    /**
     * Replaces the matrix cells with a copy of the given array.
     *
     * @param data the replacement matrix values
     * @throws IllegalArgumentException if the array is null, empty,
     *         contains a null row, or is not rectangular
     */
    public void setData(int[][] data) {
        validateData(data);
        this.data = copyData(data);
    }

    /**
     * Returns the number of rows.
     *
     * @return the row count
     */
    public int getRows() {
        return data.length;
    }

    /**
     * Returns the number of columns.
     *
     * @return the column count
     */
    public int getColumns() {
        return data[0].length;
    }

    /**
     * Returns the value at a specified cell.
     *
     * @param row the row index
     * @param column the column index
     * @return the value stored at that cell
     * @throws IndexOutOfBoundsException if the coordinate is invalid
     */
    public int get(int row, int column) {
        checkBounds(row, column);
        return data[row][column];
    }

    /**
     * Stores a value at a specified cell.
     *
     * @param row the row index
     * @param column the column index
     * @param value the value to store
     * @throws IndexOutOfBoundsException if the coordinate is invalid
     */
    public void set(int row, int column, int value) {
        checkBounds(row, column);
        data[row][column] = value;
    }

    /**
     * Checks whether an array is a valid rectangular matrix.
     *
     * @param values the array to validate
     * @throws IllegalArgumentException if the array is invalid
     */
    private void validateData(int[][] values) {
        if (values == null || values.length == 0
                || values[0] == null || values[0].length == 0) {
            throw new IllegalArgumentException(
                    "Matrix data must have at least one row and column.");
        }

        int columns = values[0].length;

        for (int[] row : values) {
            if (row == null || row.length != columns) {
                throw new IllegalArgumentException(
                        "Matrix data must be rectangular.");
            }
        }
    }

    /**
     * Creates a deep copy of a two-dimensional array.
     *
     * @param values the array to copy
     * @return a copy containing independent row arrays
     */
    private int[][] copyData(int[][] values) {
        int[][] result = new int[values.length][];

        for (int row = 0; row < values.length; row++) {
            result[row] = values[row].clone();
        }

        return result;
    }

    /**
     * Checks whether a cell coordinate is within the matrix.
     *
     * @param row the row index
     * @param column the column index
     * @throws IndexOutOfBoundsException if either index is invalid
     */
    private void checkBounds(int row, int column) {
        if (row < 0 || row >= getRows()
                || column < 0 || column >= getColumns()) {
            throw new IndexOutOfBoundsException(
                    "Cell coordinate is outside the matrix.");
        }
    }
}
