/*
 * Sparse matrix implementation
 */
class matrix{
    matrix(){

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
        // if the node is a colheader
        public int elements;

        void deleteVertical(){
            above.below = below;
            below.above = above;

            if(!iscolheader && !isrowheader){
                colheader.elements--;
            }
        }
        
        void deleteHorizontal(){
            left.right = right;
            right.left = left;
        }

        void insertVertical(){
            //left.right = this;
            //right.left = this;
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
    }

    /*
     * Takes a node header and deletes the column along with all 
     * conflicting rows
     */
    void delCol(Node colheader){
        Node temp = colheader.below;
        while(temp != colheader){
            delRow(temp.rowheader);
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
            temp.deleteVertical();
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

    public static void main(String [] args){
        System.out.println("test");
    }
}
