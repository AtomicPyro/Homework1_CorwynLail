//------------------------------------------------------
// Homework 1
// Written by: Corwyn Lail 23805366
// ECE 373 Object-Oriented Software Design, Section 001 - Fall 2026
// The University of Arizona
//------------------------------------------------------
package edu.arizona.ece373.hw1;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Tests for {@link Matrix} (Task 1).
 *
 * <p>These tests verify matrix construction, dimensions, data retrieval,
 * and data replacement.</p>
 */
class MatrixTest {

    @ParameterizedTest(name = "new Matrix({0}, {1}) is rejected")
    @CsvSource({"0,4", "4,0", "-1,4", "4,-1", "0,0"})
    @DisplayName("the size constructor reports illegal arguments")
    void sizeConstructorRejectsIllegalDimensions(int rows, int columns) {
        assertThrows(IllegalArgumentException.class,
                () -> new Matrix(rows, columns));
    }

    @Test
    @DisplayName("the array constructor reports illegal arguments")
    void arrayConstructorRejectsIllegalArguments() {
        assertThrows(IllegalArgumentException.class,
                () -> new Matrix(null));
        assertThrows(IllegalArgumentException.class,
                () -> new Matrix(new int[0][0]));
        assertThrows(IllegalArgumentException.class,
                () -> new Matrix(new int[3][0]));
        assertThrows(IllegalArgumentException.class,
                () -> new Matrix(new int[][] {{1, 2}, {3}}));
        assertThrows(IllegalArgumentException.class,
                () -> new Matrix(new int[][] {null}));
    }

    @ParameterizedTest(name = "a {0}x{1} matrix reports {0} rows and {1} columns")
    @CsvSource({"1,1", "2,4", "3,4", "7,2"})
    @DisplayName("the row and column methods return the expected dimensions")
    void reportsItsDimensions(int rows, int columns) {
        Matrix fromSize = new Matrix(rows, columns);
        assertEquals(rows, fromSize.getRows());
        assertEquals(columns, fromSize.getColumns());

        Matrix fromArray = new Matrix(new int[rows][columns]);
        assertEquals(rows, fromArray.getRows());
        assertEquals(columns, fromArray.getColumns());
    }

    @Test
    @DisplayName("getData returns the values that were passed to the constructor")
    void getDataReturnsTheStoredValues() {
        int[][] values = {
                {1, 0, 1},
                {0, 1, 0}
        };

        Matrix matrix = new Matrix(values);
        int[][] actual = matrix.getData();

        assertEquals(2, matrix.getRows());
        assertEquals(3, matrix.getColumns());
        assertArrayEquals(values[0], actual[0]);
        assertArrayEquals(values[1], actual[1]);
    }

    @Test
    @DisplayName("setData replaces the contents of the matrix")
    void setDataReplacesContents() {
        Matrix matrix = new Matrix(new int[][] {
                {1, 2},
                {3, 4}
        });

        int[][] replacement = {
                {5, 6, 7},
                {8, 9, 10},
                {11, 12, 13}
        };

        matrix.setData(replacement);

        assertEquals(3, matrix.getRows());
        assertEquals(3, matrix.getColumns());

        int[][] actual = matrix.getData();
        assertArrayEquals(replacement[0], actual[0]);
        assertArrayEquals(replacement[1], actual[1]);
        assertArrayEquals(replacement[2], actual[2]);
    }
}
