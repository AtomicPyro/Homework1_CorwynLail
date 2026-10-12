//------------------------------------------------------
// Homework 1
// Written by: Corwyn Lail 23805366
// ECE 373 Object-Oriented Software Design, Section 001 - Fall 2026
// The University of Arizona
//------------------------------------------------------
package edu.arizona.ece373.hw1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link MatrixOutlinePrinter} and {@link BoolMatrixPrinter} (Task 2).
 *
 * <p>These tests verify the outline format, boolean cell rendering,
 * invalid-value handling, and overridden behavior.</p>
 */
class MatrixPrinterTest {

    @Test
    @DisplayName("the outline printer prints an outline of the right size for a 3x4 matrix")
    void outlinePrinterFramesThreeByFour() {
        MatrixPrinter printer = new MatrixOutlinePrinter();
        String expected = ""
                + "+----+\n"
                + "|    |\n"
                + "|    |\n"
                + "|    |\n"
                + "+----+\n";

        assertEquals(expected, printer.print(new Matrix(3, 4)));
    }

    @Test
    @DisplayName("the boolean printer rejects a matrix that holds a value other than 0 or 1")
    void boolPrinterRejectsIllegalValues() {
        Matrix illegal = new Matrix(new int[][] {{0, 1}, {1, 2}});

        assertThrows(
                IllegalArgumentException.class,
                () -> new BoolMatrixPrinter().print(illegal));
    }

    @Test
    @DisplayName("the boolean printer renders the example from the handout")
    void boolPrinterRendersHandoutExample() {
        Matrix matrix = new Matrix(new int[][] {
                {0, 0, 1, 0},
                {0, 0, 1, 0},
        });

        String expected = ""
                + "+----+\n"
                + "|0010|\n"
                + "|0010|\n"
                + "+----+\n";

        assertEquals(expected, new BoolMatrixPrinter().print(matrix));
    }

    @Test
    @DisplayName("the boolean printer renders a second matrix of your choice")
    void boolPrinterRendersASecondMatrix() {
        Matrix matrix = new Matrix(new int[][] {
                {1, 0, 1},
                {0, 1, 0},
        });

        String expected = ""
                + "+---+\n"
                + "|101|\n"
                + "|010|\n"
                + "+---+\n";

        assertEquals(expected, new BoolMatrixPrinter().print(matrix));
    }

    @Test
    @DisplayName("a BoolMatrixPrinter used through the MatrixOutlinePrinter type still prints values")
    void overridingBeatsTheStaticType() {
        MatrixOutlinePrinter printer = new BoolMatrixPrinter();
        Matrix matrix = new Matrix(new int[][] {
                {1, 0},
                {0, 1},
        });

        String expected = ""
                + "+--+\n"
                + "|10|\n"
                + "|01|\n"
                + "+--+\n";

        assertEquals(expected, printer.print(matrix));
    }
}
