import java.util.LinkedList;

class Converter{
    
    public Matrix convertSudoku(int[][] grid){
        Matrix out = createComplete();
        for(int i = 0; i < 9; i++){
            for(int j = 0; j<9; j++){
                if(grid[i][j] != 0){
                    Node header = getrow(i,j, grid[i][j], out);
                    out.deleteStep(header);
                }
            }
        }
        return out;
    }
    
    private Node getrow(int i, int j, int value, Matrix m){
        int index = 81*i + 9*j + 1+ value;
        return m.getNthRow(index);
    }

    /*
     * returns the sparse matrix representing all possible combinations
     */
    private Matrix createComplete(){
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
        for(int i = 0; i<8;i++){
            //j corresponds with the column
            for(int j = 0; j<8; j++){
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

                int gridbox = 3*81*9 + 9*i + j;
                for(int choice = 0; choice<8;choice++){

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
        out.createMatrix(9*81, 3*81*9+81, connections);
        return out;
    }


    /*
     * returns the box number of the cell
     */
    int getBox(int row, int col){
        // 1 2 3
        // 4 5 6 
        // 7 8 9

        return 3*(row/3) +(col/3)+1;
    }

    public static void main(String[] args){
        Converter convert = new Converter();
        Matrix sudoku = convert.createComplete();
        sudoku.print();
        System.out.println("done");
    }
}
