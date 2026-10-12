//------------------------------------------------------
// Homework 1
// Written by: Corwyn Lail 23805366
// ECE 373 Object-Oriented Software Design, Section 001 - Fall 2026
// The University of Arizona
//------------------------------------------------------
package edu.arizona.ece373.hw1;

/**
 * A Conway's Game of Life field.
 *
 * <p>The field is a {@link Matrix} in which {@code 1} means the cell is
 * alive and {@code 0} means it is dead. Cells outside the field always
 * count as dead, so the field does not wrap around at the edges.</p>
 *
 * <p>The rules for one generation are:</p>
 * <ul>
 *   <li>A live cell with fewer than two live neighbours dies.</li>
 *   <li>A live cell with two or three live neighbours survives.</li>
 *   <li>A live cell with more than three live neighbours dies.</li>
 *   <li>A dead cell with exactly three live neighbours becomes alive.</li>
 * </ul>
 */
public class GameOfLife extends Matrix {

    /** Value of a cell that is alive. */
    public static final int ALIVE = 1;

    /** Value of a cell that is dead. */
    public static final int DEAD = 0;

    /**
     * Creates an empty game field of the given size; every cell starts dead.
     *
     * @param rows the height of the field, must be at least 1
     * @param columns the width of the field, must be at least 1
     * @throws IllegalArgumentException if rows or columns is less than 1
     */
    public GameOfLife(int rows, int columns) {
        super(rows, columns);
    }

    /**
     * Stamps a shape onto the field with its top-left cell at the given coordinate.
     *
     * @param shape the shape to place, must not be {@code null}
     * @param row the field row that receives row 0 of the shape
     * @param column the field column that receives column 0 of the shape
     * @throws IllegalArgumentException if the shape is null, a coordinate is
     *                                  negative, or the shape does not fit
     *                                  entirely on the field
     */
    public void addShape(Shape shape, int row, int column) {
        if (shape == null) {
            throw new IllegalArgumentException("Shape must not be null.");
        }

        if (row < 0 || column < 0) {
            throw new IllegalArgumentException(
                    "Shape coordinates must not be negative.");
        }

        if (row > getRows() - shape.getRows()
                || column > getColumns() - shape.getColumns()) {
            throw new IllegalArgumentException(
                    "Shape must fit entirely within the field.");
        }

        for (int shapeRow = 0; shapeRow < shape.getRows(); shapeRow++) {
            for (int shapeColumn = 0;
                 shapeColumn < shape.getColumns(); shapeColumn++) {
                set(row + shapeRow, column + shapeColumn,
                        shape.get(shapeRow, shapeColumn));
            }
        }
    }

    /**
     * Advances the field by one generation.
     *
     * <p>The entire next generation is computed separately before it
     * replaces the current field.</p>
     */
    public void step() {
        int[][] next = new int[getRows()][getColumns()];

        for (int row = 0; row < getRows(); row++) {
            for (int column = 0; column < getColumns(); column++) {
                int neighbours = countLiveNeighbours(row, column);
                int current = get(row, column);

                if (neighbours == 3
                        || (current == ALIVE && neighbours == 2)) {
                    next[row][column] = ALIVE;
                } else {
                    next[row][column] = DEAD;
                }
            }
        }

        setData(next);
    }

    /**
     * Counts how many of the eight cells around a coordinate are alive.
     *
     * @param row the row of the cell whose neighbours are counted
     * @param column the column of the cell whose neighbours are counted
     * @return a number between 0 and 8
     */
    public int countLiveNeighbours(int row, int column) {
        int count = 0;

        for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
            for (int columnOffset = -1;
                 columnOffset <= 1; columnOffset++) {

                // Do not count the cell itself.
                if (rowOffset == 0 && columnOffset == 0) {
                    continue;
                }

                int neighbourRow = row + rowOffset;
                int neighbourColumn = column + columnOffset;

                // Cells beyond the field boundaries are dead.
                if (neighbourRow < 0 || neighbourRow >= getRows()
                        || neighbourColumn < 0
                        || neighbourColumn >= getColumns()) {
                    continue;
                }

                if (get(neighbourRow, neighbourColumn) == ALIVE) {
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * Showcase for Task 4: builds a field holding a blinker and a glider
     * and steps it a fixed number of generations.
     *
     * @param args optional; args[0] is the number of generations to run,
     *             default 20
     * @throws InterruptedException if the animation is interrupted
     *                              while sleeping between frames
     */
    public static void main(String[] args) throws InterruptedException {
        int generations = 20;

        if (args.length > 0) {
            generations = Integer.parseInt(args[0]);
        }

        if (generations < 0) {
            throw new IllegalArgumentException(
                    "Number of generations must not be negative.");
        }

        GameOfLife game = new GameOfLife(20, 40);
        game.addShape(Shape.blinker(), 5, 5);
        game.addShape(Shape.glider(), 10, 20);

        MatrixPrinter printer = new BoolMatrixPrinter();

        for (int generation = 0;
             generation <= generations; generation++) {
            System.out.println("Generation " + generation);
            System.out.print(printer.print(game));

            if (generation < generations) {
                Thread.sleep(250);
                game.step();
            }
        }
    }
}
