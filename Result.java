import java.util.HashSet;

/*
 * Internal class to represent a partial solution
 */
class Result{
    private boolean isSol;
    private HashSet<Node> rows;
    
    // basic ctor
    Result(boolean isSol, HashSet<Node> rows){
        this.isSol = isSol;
        this.rows = rows;
    }

    /*
     * returns whether result is solution or not
     */
    public boolean isSol(){
        return isSol;
    }

    /*
     * adds a rowheader to partial solution
     */
    public void addRow(Node rowheader){
        rows.add(rowheader);
    }

    /*
     * returns the resulting header numbers in a int array
     */
    public int[] getResult(){
        int[] out = new int[rows.size()];
        int counter = 0;
        for(Node header : rows){
            out[counter] = header.rowcolnum;
            counter++;
        }
        return out;
    }
    
    /*
     * prints out the Result object
     */
    public void print(){
        System.out.println("result: " + isSol);
        for(Node row : rows){
            System.out.println(row.rowcolnum);
        }
    }
}
