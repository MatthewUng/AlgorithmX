class Converter{
    void Matrix convertSudoku(int[][] grid){

    }

    /*
     * returns the sparse matrix representing all possible combinations
     */
    private Matrix createMax(){
        /*
         * rows 1-9 represent grid[0][0]
         * rows 81(9-1) - 81(9) represent grid[8][8]
         * col 1-9 represent grid row 1
         * col 9(9-1)+1 - 9(9) represent grid row 9
         * col 9(9) + 1-9 represents grid col 1
         * col 9(9) + 9(9-1)+1 - 9(9) represents grid col 9
         * col 9(9) + 9(9) + 1-9 represents grid box 1
         * col 9(9) + 9(9) + 9(9-1)+1 - 9(9) represents grid box 9
         * col (3)(81)(9) + 1 represents grid box[0][0]
         * col (3)(81)(9) + 81 represents grid box [8][8]
         */
    }
}
