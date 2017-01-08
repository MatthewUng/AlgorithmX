import java.util.Stack;
import java.util.LinkedList;

/*
 * Sparse matrix implementation
 */
class Matrix{
    public Node mainnode;
    public int w, h;

    Matrix(){
        mainnode = new Node();
        mainnode.isrowheader = true;
        mainnode.iscolheader = true;
    }

    /*
     * creates the sparse matrix with the given inputs
     */
    void createMatrix(int h, int w, LinkedList<Coord> list){

        this.w = w;
        this.h = h;

        //creating the col headers
        Node temp = mainnode;
        for(int i = 1; i <= w; i++){
            temp.right = new Node();
            temp.right.iscolheader = true;
            temp.right.rowcolnum = i;
            temp.right.colheader = temp.right;

            temp.right.left = temp;
            temp = temp.right;
        }
        temp.right = mainnode;

        //creating the row headers
        temp = mainnode;
        for(int j = 1; j <= h; j++){
            temp.below = new Node();
            temp.below.isrowheader = true;
            temp.below.rowcolnum = j;
            temp.below.rowheader = temp.below;

            temp.below.above = temp;
            temp = temp.below;
        }
        temp.below = mainnode;

        for(Coord c : list){
            Node col = mainnode;
            Node row = mainnode;

            //retrieving the right column/row
            for(int i = 0; i < c.y; i++){
                col = col.right;
            }


            for(int i = 0; i< c.x; i++){
                row = row.below;
            }

            Node tempcol = col;

            while(tempcol.below != col && tempcol.below.rowheader.rowcolnum<c.x){
                tempcol = tempcol.below;
            }

            tempcol.insertbelow(new Node(row, col));
            Node added = tempcol.below;
            added.colheader.elements++;
            
            Node temprow = row;
            while(temprow.right != row && temprow.right.colheader.rowcolnum<c.y){
                temprow = temprow.right;
            }
                
            added.left = temprow;
            added.right = temprow.right;

            temprow.right.left = added;
            temprow.right = added;
        }

    }

    /*
     * Deletes a certain column
     */
    void delCol(Node colheader){
        Node temp = colheader.below;
        while(temp != colheader){
            temp.deleteHorizontal();
            temp = temp.below;
        }

        colheader.deleteHorizontal();
    }

    /*
     * reinserts a column and all rows accompanying it
     */
    void reinsertCol(Node colheader){
        colheader.insertHorizontal();
        Node temp = colheader.below;

        while(temp != colheader){
            temp.insertHorizontal();
            temp = temp.below;
        }
    }

    /*
     * Takes a rowHeader and deletes its associated row
     */
    void delRow(Node rowheader){
        Node temp = rowheader.right;
        while(temp != rowheader){
            temp.deleteVertical();

            temp = temp.right;
        }
        rowheader.deleteVertical();
    }

    /*
     * reinserts a row
     */
    void reinsertRow(Node rowheader){
        rowheader.insertVertical();
        Node temp = rowheader.right;
        while(temp != rowheader){
            temp.insertVertical();
            temp = temp.right;
        }
    }

    /*
     * Returns the column header with the fewest elements
     */
    Node optimalColumn(){
        Node temp = mainnode.right;
        int num = h;
        Node out = mainnode;

        while(temp != mainnode){
            if(temp.elements < num){
                out = temp;
                num = temp.elements;
            }
            temp = temp.right;
        }

        return out;
    }

    /*
     * Given a colheader, returns an arraylist of all the rows associated with 
     * the column
     */
    LinkedList<Node> optimalRows(Node colheader){
        LinkedList<Node> out = new LinkedList<Node>();
        Node temp = colheader.below;
        while(temp != colheader){
            out.add(temp.rowheader);
            temp = temp.below;
        }
        return out;
    }

    /*
     * removes a row and all columns/rows associated with the row
     */
    public Stack<Node> deleteStep(Node rowheader){
        Stack<Node> order = new Stack<Node>();
        Node temp = rowheader.right;

        while(temp != rowheader){
            Node colit = temp.colheader.below;
            
            while(colit != temp.colheader){
                if(colit.rowheader != rowheader){
                    order.push(colit.rowheader);
                    delRow(colit.rowheader);
                }
                colit = colit.below;
            }

            order.push(temp.colheader);
            delCol(temp.colheader);
            temp = temp.right;
        }

        order.push(rowheader);
        delRow(rowheader);

        return order;
    }
    
    /*
     * reimplements a row and all columns/row associated with the row
     */
    public void insertStep(Stack<Node> order){
        while(!order.empty()){
            Node header = order.pop();
            if(header.isrowheader){
                reinsertRow(header);
            } else if(header.iscolheader){
                reinsertCol(header);
            } else {
                System.out.println("Error in insertStep()");
            }
        }
    }

    /*
     * obtains the nth row of the matrix
     */
    public Node getNthRow(int n){
        Node temp = mainnode;
        for(int i = 0; i<n; i++){
            temp = temp.below;
            if(temp.rowcolnum == n){
                return temp;
            } else if(temp == mainnode){
                return null;
            }
        }
        return null;
    }

    /*
     * checks if the matrix is empty or not
     */
    boolean empty(){
        if(mainnode.right == mainnode){ 
            return true;
        } else{
            return false;
        }
    }

    /*
     * prints out the matrix represented
     */
    void print(){
        System.out.println("\nHeight of: "+h);
        System.out.println("Width of: "+w+"\n");

        //iterating through columns
        Node it = mainnode;
        while(it.right != mainnode){
            System.out.print("Column: "+it.right.rowcolnum);
            System.out.println(" with "+it.right.elements+" elements");

            //iterate through specific column
            Node colit = it.right;
            colit.print();
            colit = colit.below;
            while(colit != it.right){
                colit.print();
                colit = colit.below;
            }

            it = it.right;
            System.out.println("");
        }

        //iterating through rows
        it = mainnode;
        while(it.below != mainnode){
            System.out.println("Row: "+it.below.rowcolnum);

            Node rowit = it.below;
            rowit.print();
            rowit = rowit.right;
            while(rowit != it.below){
                rowit.print();
                rowit = rowit.right;
            }

            it = it.below;
            System.out.println("");
        }
    }

    // basic test
    public static void main(String [] args){
        // 6 x 7 
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
        Node header = m.mainnode.below;
        m.delRow(header);
        //m.reinsertRow(header);
        m.print();
    }
}
