import java.util.HashSet;

/*
 * Internal class to represent a partial solution
 */

class Result{
    public boolean isSol;
    public HashSet<Node> rows;
    
    Result(boolean isSol, HashSet<Node> rows){
        this.isSol = isSol;
        this.rows = rows;
    }

    public int[] getResult(){
        int[] out = new int[rows.size()];
        int counter = 0;
        for(Node header : rows){
            out[counter] = header.rowcolnum;
            counter++;
        }
        return out;
    }
    
    public void print(){
        System.out.println("result: " + isSol);
        for(Node row : rows){
            System.out.println(row.rowcolnum);
        }
    }
}
