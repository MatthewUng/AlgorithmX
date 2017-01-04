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

    public void print(){
        System.out.println("result: " + isSol);
        for(Node row : rows){
            System.out.println(row.rowcolnum);
        }
    }
}
