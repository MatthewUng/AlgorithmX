import java.util.LinkedList;

/*
 * Sparse matrix implementation
 */
class Matrix{
    private Node mainnode = new Node();
    public int w, h;

    Matrix(){

    }
    
    // Internal node class
    class Node{
        public Node left;
        public Node right;
        public Node above;
        public Node below;
        public Node colheader;
        public Node rowheader;

        public boolean iscolheader;
        public boolean isrowheader;
        public int rowcolnum;

        // if the node is a colheader
        public int elements;

        Node(){
            left = this;
            right = this;
            above = this;
            below = this;
        }

        Node(Node row, Node col){
            left = this;
            right = this;
            above = this;
            below = this;
            colheader = col;
            rowheader = row;
        }

        void insertbelow(Node n){
            n.below = this.below;
            n.above = this;
            //n.colheader = colheader;

            below.above = n;
            below = n;
        }

        void deleteVertical(){
            above.below = below;
            below.above = above;

            /*
            if(!iscolheader && !isrowheader){
                colheader.elements--;
            }*/
        }
        
        void deleteHorizontal(){
            left.right = right;
            right.left = left;
        }

        void insertVertical(){
            above.below = this;
            below.above = this;

            if(!iscolheader && !isrowheader){
                colheader.elements++;
            }
        }
        
        void insertHorizontal(){
            left.right = this;
            right.left = this;
        }
        
        void print(){
            if(iscolheader){
                System.out.println("col header #"+rowcolnum);
            } else if(isrowheader){
                System.out.println("row header #"+rowcolnum);
            } else {
                System.out.println("node ("+rowheader.rowcolnum+", "+
                colheader.rowcolnum+")");
            }
        }
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
            temp.right.below = temp.right;
            temp.right.above = temp.right;
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
            temp.below.right = temp.below;
            temp.below.left = temp.below;
            temp = temp.below;
        }
        temp.below = mainnode;

        for(Coord c : list){
            Node col = mainnode;
            Node row = mainnode;

            //retrieving the right column
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
     * Takes a node header and deletes the column along with all 
     * conflicting rows
     */
    void delCol(Node colheader){
        Node temp = colheader.below;
        while(temp != colheader){
            System.out.println("deleting from delCol");
            temp.print();
            delRow(temp.rowheader);
            temp = temp.below;
        }

        colheader.deleteVertical();
    }

    /*
     * reinserts a column and all rows accompanying it
     */
    void reinsertCol(Node colheader){
        colheader.insertHorizontal();
        Node temp = colheader.below;

        while(temp != colheader){
            reinsertRow(temp.rowheader);
            temp = temp.below;
        }
    }

    /*
     * Takes a rowHeader and deletes its associated row
     */
    void delRow(Node rowheader){
        Node temp = rowheader.right;
        while(temp != rowheader){
            System.out.println("deleting from delRow");
            temp.print();
            temp.deleteHorizontal();
            temp.colheader.elements--;
            temp = temp.right;
        }
        rowheader.deleteHorizontal();
    }

    /*
     * reinserts a row
     */
    void reinsertRow(Node rowheader){
        rowheader.insertVertical();
        Node temp = rowheader.right;
        while(temp != rowheader){
            temp.insertVertical();
            temp.colheader.elements++;
            temp = temp.right;
        }
    }

    /*
     * checks if the matrix is empty or not
     */
    boolean empty(){
        if(mainnode.right == mainnode && mainnode.below == mainnode){
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
        values.add(new Coord(5,7));
        values.add(new Coord(6,7));
        

        Matrix m = new Matrix();
        
        m.createMatrix(6, 7, values);
        Node header = m.mainnode.right;
        m.delCol(header);
        m.print();
    }
}
