import java.util.LinkedList;
import java.util.HashSet;
import java.util.Stack;

/* 
 * Algx Object that implements Algoritm X to solve exact cover problems
 */
class Algx{
    Matrix m;

    //ctor
    Algx(Matrix m){
        this.m = m;
    }

    /* Solves the matrix and returns a Result object
     */
    Result solve(){
        //partial solution is actually full solution

        if(m.empty()){
            return new Result(true, new HashSet<Node>());
        }

        Node header = m.optimalColumn();

        if(header.isrowheader && header.iscolheader){ 
            System.out.println("Error");
        }

        LinkedList<Node> rows = m.optimalRows(header);
        
        for(Node rowheader : rows){
            Stack<Node> order = m.deleteStep(rowheader);

            Result partialSol= solve();

            if(partialSol.isSol()){
                partialSol.addRow(rowheader);
                return partialSol;

            } else{
                m.insertStep(order);
                continue;
            }
        }

        return new Result(false, null);
    }

    public static void main(String [] args){
        System.out.println("Testing...");

        LinkedList<Coord> values = new LinkedList<Coord>();
        values.add(new Coord(1,1));
        values.add(new Coord(2,1));
        values.add(new Coord(5,2));
        values.add(new Coord(6,2));
        values.add(new Coord(4,3));
        values.add(new Coord(5,3));
        values.add(new Coord(1,4));
        values.add(new Coord(2,4));
        values.add(new Coord(3,4));
        values.add(new Coord(3,5));
        values.add(new Coord(4,5));
        values.add(new Coord(4,6));
        values.add(new Coord(5,6));
        values.add(new Coord(1,7));
        values.add(new Coord(3,7));
        values.add(new Coord(5,7));
        values.add(new Coord(6,7));

        Matrix m = new Matrix();
        m.createMatrix(6, 7, values);
        
        Algx solver = new Algx(m);
        Result result = solver.solve();

        System.out.println("successful termination");
        result.print();
    }

}
