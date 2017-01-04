import java.util.LinkedList;
import java.util.Stack;

class Algx{
    Matrix m;

    Algx(Matrix m){
        this.m = m;
    }

    /*
     * removes a row and all columns associated with the row
     */
    Stack<Node> removeRow(Node rowheader){
        Stack<Node> order = new Stack<Node>;
        Node temp = rowheader.right;

        while(temp != rowheader){
            order.push(temp.colheader);
            m.deleteCol(temp.colheader);
        }

        order.push(rowheader);
        m.deleteRow(rowheader);

        return order;
    }

    /*
     * reimplements a row and all columns associated with the row
     */
    void insertRow(Stack<Node> order){
        m.reinsertRow(order.pop());

        while(!order.empty(){
            m.reinsertCol(order.pop());
        }
    }


    Result solve(Result partial){
        Node header = m.optimalColumn();
        LinkedList<Node> rows = m.optimalRows(header);
        
        for(Node rowheader : rows){
            Stack<Node> order = new Stack<Node>;

            Node temp = rowheader.right;
            while(temp != rowheader){
                order.push(temp.colheader);
                m.deleteCol(temp.colheader);


            }
        }


    }
    
    public static void main(String [] args){
        System.out.println("Testing...");

    }

}
