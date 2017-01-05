import java.util.LinkedList;
import java.util.HashSet;
import java.util.Stack;

class Algx{
    Matrix m;

    Algx(Matrix m){
        this.m = m;
    }

    /*
     * removes a row and all columns/rows associated with the row
     */
    Stack<Node> deleteStep(Node rowheader){
        Stack<Node> order = new Stack<Node>();
        Node temp = rowheader.right;

        while(temp != rowheader){
            Node colit = temp.colheader.below;
            
            while(colit != temp.colheader){
                if(colit.rowheader != rowheader){
                    order.push(colit.rowheader);
                    m.delRow(colit.rowheader);
                }
                colit = colit.below;
            }

            order.push(temp.colheader);
            m.delCol(temp.colheader);
            temp = temp.right;
        }

        order.push(rowheader);
        m.delRow(rowheader);

        return order;
    }

    /*
     * reimplements a row and all columns/rows associated with the row
     */
    void insertStep(Stack<Node> order){
        while(!order.empty()){
            Node header = order.pop();
            if(header.isrowheader){
                m.reinsertRow(header);
            } else if(header.iscolheader){
                m.reinsertCol(header);
            } else {
                System.out.println("Error in insertStep()");
            }
        }
    }


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
            Stack<Node> order = deleteStep(rowheader);

            Result partialSol= solve();

            if(partialSol.isSol){
                partialSol.rows.add(rowheader);
                return partialSol;

            } else{
                insertStep(order);
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
