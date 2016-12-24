/*
 * Sparse matrix implementation
 */
class matrix{
    matrix(){

    }

    class node{
        public node left;
        public node right;
        public node above;
        public node below;
        public node header;
        
        void delete(){
            left.right = right;
            right.left = left;
            above.below = below;
            below.above = above;
        }

        void insert(){
            left.right = this;
            right.left = this;
            above.below = this;
            below.above = this;
        }
    }

    public static void main(String [] args){
        System.out.println("test");
    }
}
