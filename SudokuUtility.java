
import java.util.LinkedList;
import java.util.HashSet;

/*
 * Utility class that aids in converting Sudoku problems to equivalent 
 * open cover problems.
 */
class SudokuUtility{
    
    /*
     * converts a unsolved sudoku grid to its sparse matrix equivalent
     */
    public static Matrix convertSudoku(int[][] grid){
        Matrix out = createComplete();
        for(int i = 0; i < 9; i++){
            for(int j = 0; j<9; j++){
                if(grid[i][j] != 0){
                    Node header = getrow(i,j, grid[i][j], out);
                    if(header == null){
                        System.out.println("header is null");
                    }
                    out.deleteStep(header);
                }
            }
        }
        return out;
    }

    /*
     * returns the resulting grid from a Result object
     */
    public static int[][] getResultGrid(Result result){
        int[][] display = new int[9][9];
        int[] result_array = result.getResult();

        for(int row : result_array){
            int[] temp = getChoice(row);
            display[temp[0]][temp[1]] = temp[2];
        }
        return display;
    }

    /*
     * prints out the basic resulting grid to terminal
     */
    public static void printResultGrid(int[][] grid){
        for(int i = 0; i<9; i++){
            if(i%3 == 0 && i != 0){
                System.out.println("");
            }

            for(int j = 0; j<9; j++){
                if(j%3 == 0 && j != 0){
                    System.out.print(" ");
                }
                if(grid[i][j] == 0){
                    System.out.print("_");
                } else {
                    System.out.print(grid[i][j]);
                }
            }
            System.out.println("");
        }
    }

    /*
     * Merges two grids and returns the result
     */
    public static int[][] merge(int[][] first, int[][] second){
        int[][] out = new int[9][9];

        for(int i = 0; i<9; i++){
            for(int j = 0; j<9; j++){
                //error case
                if(first[i][j] != 0 && second[i][j] != 0){
                    System.out.println("Error occurred in merge()");
                }
                
                if(first[i][j] != 0){
                    out[i][j] = first[i][j];
                } else {
                    out[i][j] = second[i][j];
                }
            }
        }
        return out;
    }
   
    /*
     * returns the choice corresponding to the row header's rowcolnum
     * output = [i, j, choice]
     */
    private static int[] getChoice(int rownum){
        int[] out = new int[3];

        for(int i = 0; i<9; i++){
            for(int j = 0; j<9; j++){
                int first = 81*i+9*j+1;
                if(rownum - first <= 8 && rownum >= first){
                    out[0] = i;
                    out[1] = j;
                    out[2] = rownum - first + 1;
                    return out;
                }
            }
        }
        return out;
    }

    /*
     * returns the rowheader for the nth row (based on rowcolnum)
     */
    private static Node getrow(int i, int j, int value, Matrix m){
        //index is rowcolnum of row header
        int index = 81*i + 9*j + value;
        return m.getNthRow(index);
    }

    /*
     * returns the sparse matrix representing all possible combinations
     */
    private static Matrix createComplete(){
        /*
         * rows 1~9 represent grid[0][0]
         * rows 81(9)-8 ~ 81(9) represent grid[8][8]
         * col 1~9 represent grid row 1
         * col 9(9-1)+1 ~ 9(9) represent grid row 9
         * col 9(9) + 1 ~ 9 represents grid col 1
         * col 9(9) + 9(9-1)+1 ~ 9(9) represents grid col 9
         * col 9(9) + 9(9) + 1~9 represents grid box 1
         * col 9(9) + 9(9) + 9(9-1)+1 ~ 9(9) represents grid box 9
         * col (3)(81)(9) + 1 represents grid box[0][0]
         * col (3)(81)(9) + 81 represents grid box [8][8]
         */
        LinkedList<Coord> connections = new LinkedList<Coord>();
        //i corresponds with the row
        for(int i = 0; i<9;i++){
            //j corresponds with the column
            for(int j = 0; j<9; j++){
                // 10 => 10(9) - 8
                // first is the first row associated with grid[i][j]
                int first = 81*i + 9*j + 1;

                //1st column that represents the row
                int firstrow = 9*i+1;

                //1st column that represents the grid col
                int firstcol = 81 + 9*j+1;

                //1st col that represents the box
                int box = 2*(81) + 9*getBox(i, j)-8;

                //col that represents the particular grid box[i][j]

                int gridbox = 3*81 + 9*i + j+1;
                for(int choice = 0; choice<9; choice++){

                    //every row must have all 9
                    Coord toadd = new Coord(first+choice, firstrow+choice);
                    connections.add(toadd);

                    //every col must have all 9
                    toadd = new Coord(first+choice, firstcol+choice);
                    connections.add(toadd);

                    toadd = new Coord(first+choice, box+choice);
                    connections.add(toadd);

                    toadd = new Coord(first+choice, gridbox);
                    connections.add(toadd);
                }
            }
        }
        Matrix out = new Matrix();
        out.createMatrix(9*81, 4*81, connections);
        return out;
    }

    /*
     * returns the box number of the cell
     */
    private static int getBox(int row, int col){
        // 1 2 3
        // 4 5 6 
        // 7 8 9
        return 3*(row/3) +(col/3)+1;
    }

    public static void main(String[] args){
        //Converter convert = new Converter();
        Matrix sudoku = createComplete();
        sudoku.print();
        /*
        Algx solver = new Algx(sudoku);
        Result result = solver.solve();
        
        int[][] resultgrid = getResultGrid(result);
        printResultGrid(resultgrid);
        */
    }

}
