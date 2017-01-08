
/*
 * Example usage of Algorithm X
 */
class Example{
    public static void main(String[] args){
        int[][] basicgrid = new int[9][9];
        int[][] resultgrid;
        Result result;
        Algx solver;

        //solving empty sudoku grid
        Matrix empty = SudokuUtility.convertSudoku(basicgrid);
        solver = new Algx(empty);
        result = solver.solve();

        System.out.println("Solving empty Sudoku Grid:\n");
        resultgrid = SudokuUtility.getResultGrid(result);
        SudokuUtility.printResultGrid(resultgrid);
        System.out.println("");

        //setting up basic sudoku puzzle
        basicgrid[0][0] = 6;
        basicgrid[0][2] = 7;
        basicgrid[0][3] = 3;
        basicgrid[0][4] = 9;
        basicgrid[0][5] = 2;
        basicgrid[1][1] = 2;
        basicgrid[2][0] = 5;
        basicgrid[2][2] = 8;
        basicgrid[2][6] = 4;
        basicgrid[3][1] = 6;
        basicgrid[3][2] = 5;
        basicgrid[3][5] = 9;
        basicgrid[4][3] = 8;
        basicgrid[4][4] = 7;
        basicgrid[4][5] = 3;
        basicgrid[5][3] = 4;
        basicgrid[5][6] = 7;
        basicgrid[5][7] = 9;
        basicgrid[6][2] = 4;
        basicgrid[6][6] = 9;
        basicgrid[6][8] = 3;
        basicgrid[7][7] = 5;
        basicgrid[8][3] = 9;
        basicgrid[8][4] = 5;
        basicgrid[8][5] = 1;
        basicgrid[8][6] = 2;
        basicgrid[8][8] = 6;

        System.out.println("Solving the sudoku puzzle:\n");
        SudokuUtility.printResultGrid(basicgrid);
        System.out.println("");

        Matrix puzzle = SudokuUtility.convertSudoku(basicgrid);
        solver = new Algx(puzzle);
        result = solver.solve();
        resultgrid = SudokuUtility.getResultGrid(result);
        
        //merging original values and solution values
        int[][] solution = SudokuUtility.merge(resultgrid, basicgrid);

        System.out.println("\nSolution:\n");
        SudokuUtility.printResultGrid(solution);

    }
}
