
/*
 * Node class for sparse matrix implementation
 */
class Node{
    public Node left;
    public Node right;
    public Node above;
    public Node below;
    public Node colheader;
    public Node rowheader;

    public boolean iscolheader = false;
    public boolean isrowheader = false;
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
        if(!isrowheader && !iscolheader){
            colheader.elements--;
        }
    }
    
    void deleteHorizontal(){
        left.right = right;
        right.left = left;
    }

    //TODO not sure if needed
    /*
    void delete(){
        deleteVertical();
        deleteHorizontal();
    }*/

    void insertVertical(){
        above.below = this;
        below.above = this;
        if(!isrowheader && !iscolheader){
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

