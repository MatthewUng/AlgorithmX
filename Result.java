/*
 * Internal class to represent a partial solution
 */

class Result{
    public boolean isSol;
    public LinkedList<Node> rows;
    
    Result(boolean isSol, LinkedList<Node> rows){
        this.isSol = isSol;
        this.rows = rows;
    }
}
