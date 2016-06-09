


/**
 *
 * @author KarthikMaharajan
 */
import java.io.*;
import java.util.*;

public class bbst {
    static TreeNode root; //root of the Red Black tree
    private static final boolean RED = true; //boolean variable to indicate color of the node, true for red nodes
    private static final boolean BLACK = false; //boolean variable to indicate color of the node, false for black nodes
    
    public class TreeNode {
        int id; // event ID
        int count; // number of active events with the given ID.
        TreeNode parent, leftChild, rightChild;//leftchild, rightchild and parent of node
        boolean isRed; //the color of the node

            //Constructor to create nodes
            TreeNode(int key, int count) {
                
		this.id = key;
		this.count = count;
			
		this.isRed = BLACK; //color set to black by default
                this.leftChild=this.rightChild=null;//left child, right child and parent of a node set to null when node is created
                this.parent=null;
                
            }
            
            
            
    }
    
    //recursive function to create redblack tree from sorted array in O(n) time
    public TreeNode arraytobst(int[] id,int[] count,int start, int end,int height){
        if (start > end) {
            return null;
        }
 
        /* Get the middle element and make it root */
        int mid = (start + end) / 2;
        TreeNode node = new TreeNode(id[mid],count[mid]);
        
 
        /* Recursively construct the left subtree and make it
         left child of root */
        node.leftChild = arraytobst(id,count,start, mid-1,height-1);
        //set parent pointer of leftchild
        if(node.leftChild!=null){
            node.leftChild.parent=node;
          
        }
 
        /* Recursively construct the right subtree and make it
         right child of root */
        node.rightChild = arraytobst(id,count,mid+1, end,height-1);
        
        //Set parent pointer of rightchild
        
         if(node.rightChild!=null){
            node.rightChild.parent=node;
        }
         
         //convert to red black tree
         if(height==0)
             node.isRed=true;
      
         
        return node;//Returning root of the red black tree tree
        
    }
   
    //function to return count of event with given event ID
    public int count(int id){
        TreeNode targetNode=iterativeSearch(id);
        if(targetNode!=null)
            return targetNode.count;
        else
            return 0;
    }
    
    //function to perform tree search on red black tree
    public TreeNode iterativeSearch(int id){
         TreeNode target=root;
         while(target!=null && id!=target.id){
             if(id<target.id)
                 target=target.leftChild;
             else
                 target=target.rightChild;
             
                 
         }
         return target;
     }
    
    //function to increase count of event with given ID by m
    public int increase(int id,int m){
         TreeNode curNode=iterativeSearch(id);
         if(curNode!=null){
             curNode.count=curNode.count+m;
             return curNode.count;
         }
         else{
             insertNode(id,m);//calling insert if event is not present in the counter
             return m;
         }
             
     }
    
    //function to insert node into red black tree
    public void insertNode(int id,int count){
         TreeNode z=new TreeNode(id,count);// z is the node to be inserted
         TreeNode y=null;//pointer y is a trailing pointer for x
         TreeNode x=root;//pointer x traces a downward path to find correct position to insert z
         while(x!=null){
             y=x;
             if(z.id<x.id)
                 x=x.leftChild;
             else
                 x=x.rightChild;
            }
         z.parent=y;
         if(y==null)
             root=z;
         else if(z.id<y.id)
             y.leftChild=z;
         else
             y.rightChild=z;
         z.leftChild=null;
         z.rightChild=null;
         z.isRed=true;
         insertfixup(z);//call insert fixup to fix red-black tree properties
         
         
     }
     //function to fix red-black tree properties after normal insert
     public void insertfixup(TreeNode z){//z is the newly inserted node
         TreeNode y;//y is the uncle node of z
         
         while(z.parent.isRed==true){
             if(z.parent==z.parent.parent.leftChild){
                 y=z.parent.parent.rightChild;
                 if(y!=null && y.isRed==true){
                     z.parent.isRed=false;
                     y.isRed=false;
                     z.parent.parent.isRed=true;
                     z=z.parent.parent;
                     
                     
                 }
                 else if(z==z.parent.rightChild){
                     z=z.parent;
                     leftRotate(z);
                 }
                 else{
                    z.parent.isRed=false;
                    z.parent.parent.isRed=true;
                    rightRotate(z.parent.parent);
                 }
                  
                
                 
                 
             }
             else{
                 y=z.parent.parent.leftChild;
                 if(y!=null && y.isRed==true){
                     z.parent.isRed=false;
                     y.isRed=false;
                     z.parent.parent.isRed=true;
                     z=z.parent.parent;
                     
                     
                 }
                 else if(z==z.parent.leftChild){
                     z=z.parent;
                     rightRotate(z);
                 }
                 else{
                    z.parent.isRed=false;
                    z.parent.parent.isRed=true;
                    leftRotate(z.parent.parent);
                 }
                 
                 
                 
             }
            
                 
             if(z==root)
                 break;
         }
          root.isRed=false;
     }
     
     //function to perform left rotation
     public void leftRotate(TreeNode x){
         TreeNode y=x.rightChild;//set y
         x.rightChild=y.leftChild;//turn y's left subtree into x's right subtree
         if(y.leftChild!=null)
             y.leftChild.parent=x;//link x's parent to y
         y.parent=x.parent;
         if(x.parent==null)
             root=y;
         else if(x==x.parent.leftChild)
             x.parent.leftChild=y;
         else 
             x.parent.rightChild=y;
         y.leftChild=x;//put x on y's left
         x.parent=y;
         
         
     }
     
     //function to perform right rotation
     public void rightRotate(TreeNode y){
         TreeNode x=y.leftChild;//set x
         y.leftChild=x.rightChild;//turn x's right subtree into y's left subtree
         if(x.rightChild!=null)
             x.rightChild.parent=y;//link y's parent to x
         x.parent=y.parent;
         if(y.parent==null)
             root=x;
         else if(y==y.parent.rightChild)
             y.parent.rightChild=x;
         else 
             y.parent.rightChild=x;
         x.rightChild=y;//put y on x's right
         y.parent=x;
         
         
     }
     
     //function to decrease count of event with given ID by m
     public int decrease(int id,int m){
         TreeNode curNode=iterativeSearch(id);
         if(curNode!=null){
             curNode.count=curNode.count-m;
             if(curNode.count>0)
                 return curNode.count;
             else{
                 deleteNode(id,m);//delete node if event count is less than or equal to zero
                 return 0;
                 
             }
                 
             
             
         }
             
         
         else
             return 0;//return 0 if node is not present
     }
     
     
     //function to delete node if event count is less than or equal to zero
     public void deleteNode(int id,int m){
         TreeNode z=iterativeSearch(id);//z is the node to be deleted
         
         TreeNode y=z;//y is used to keep track of z's successor
         TreeNode x=null;
         
         boolean yoriginalcolor=y.isRed;//this variable to keep track of y's original color
         if(z.leftChild==null){
             x=z.rightChild;
             transplant(z,z.rightChild);
         }
         else if(z.rightChild==null){
             x=z.leftChild;
             transplant(z,z.leftChild);
         }
         else{
                     y=treeMinimum(z.rightChild);
                     yoriginalcolor=y.isRed;
                     x=y.rightChild;
                     if(y.parent==z){
                         if(x!=null){
                            x.parent=y;
                         }
                     }
                        
                     if(y.parent!=z){
                        transplant(y,y.rightChild);
                        y.rightChild=z.rightChild;
                        y.rightChild.parent=y;
                     
                     }
                     transplant(z,y);
                     y.leftChild=z.leftChild;
                     y.leftChild.parent=y;
                     y.isRed=z.isRed;
                     
                     
                     }
         if(yoriginalcolor==false && x!=null)
             deletefixup(x);//delete fix up is called to reverse violation of red black tree properties
             
         
         
     }
     
     //Delete fix up is used to preserve red-black tree properties after deletion of the node 
     public void deletefixup(TreeNode x){
         while(x!=root && x.isRed==false){//node x is neither red nor black and hence fix up is necessary
             if(x==x.parent.leftChild){
                 TreeNode w=x.parent.rightChild;//w is a pointer to the sibling of x
                 if(w.isRed==true){
                     w.isRed=false;
                     x.parent.isRed=true;
                     leftRotate(x.parent);
                     w=x.parent.rightChild;
                     
                 }
                 else if(w.leftChild.isRed==false && w.rightChild.isRed==false){
                     w.isRed=true;
                     x=x.parent;
                 }
                 else if(w.rightChild.isRed==false){
                     w.leftChild.isRed=false;
                     w.isRed=true;
                     rightRotate(w);
                     w=x.parent.rightChild;
                 }
                 else{
                     w.isRed=x.parent.isRed;
                     x.parent.isRed=false;
                     w.rightChild.isRed=false;
                     leftRotate(x.parent);
                     x=root;
                 }
                    
                 
             }
             else{
                 TreeNode w=x.parent.leftChild;
                 if(w.isRed==true){
                     w.isRed=false;
                     x.parent.isRed=true;
                     rightRotate(x.parent);
                     w=x.parent.leftChild;
                     
                 }
                 else if(w.rightChild.isRed==false && w.leftChild.isRed==false){
                     w.isRed=true;
                     x=x.parent;
                 }
                 else if(w.leftChild.isRed==false){
                     w.rightChild.isRed=false;
                     w.isRed=true;
                     leftRotate(w);
                     w=x.parent.leftChild;
                 }
                 else{
                     w.isRed=x.parent.isRed;
                     x.parent.isRed=false;
                     w.leftChild.isRed=false;
                     rightRotate(x.parent);
                     x=root;
                 }
                 
             }
             
         }
         x.isRed=false;
         
     }
     
     
     //function to find the minimum element in the tree
     public TreeNode treeMinimum(TreeNode x){
         
             
        
         while(x.leftChild!=null)
             x=x.leftChild;
        
         return x;
      }
     
     //function to find the maximum element in the tree
     
     public TreeNode treeMaximum(TreeNode x){
         
             
        
         while(x.rightChild!=null)
             x=x.rightChild;
        
         return x;
      }
     
     //TRANSPLANTreplaces one subtree as a child of its parent with another subtree.
     public void transplant(TreeNode u,TreeNode v){
         if(u.parent==null)
             root=v;
         else if(u==u.parent.leftChild)
             u.parent.leftChild=v;
         else
             u.parent.rightChild=v;
         if(v!=null)
             v.parent=u.parent;
             
                 
         
         
     }
     
     //function to print sum of counts of events between id1 and id2 inclusive
     public int inRange(int id1,int id2){
         int countSum=rangeImp(root,id1,id2);
         return countSum;
         
     }
     
     
     public int rangeImp(TreeNode rootnode,int id1,int id2){
        
        if(rootnode==null)
            return 0;
        
    
        
 
        // Special Optional case for improving efficiency
        if (rootnode.id == id2 && rootnode.id == id1)
            return rootnode.count;
 
        // If current node is in range, then include it in count and
        // recur for left and right children of it
        if (rootnode.id <= id2 && rootnode.id >= id1)
            return rootnode.count + rangeImp(rootnode.leftChild, id1, id2) + rangeImp(rootnode.rightChild, id1, id2);
 
        // If current node is smaller than low, then recur for right
        // child
        else if (rootnode.id < id1)
            return rangeImp(rootnode.rightChild,id1,id2);
 
        // Else recur for left child
        else 
            return rangeImp(rootnode.leftChild,id1,id2);
         
     }
     
     //function to print the ID and count of the event with the lowest ID greater than the given ID
     public void next(int id){
         TreeNode currentNode=iterativeSearch(id);
         if(currentNode!=null){
             TreeNode nextNode=null;
         TreeNode y;
         if(currentNode!=null && currentNode.rightChild!=null)
             nextNode=treeMinimum(currentNode.rightChild);
         else{
             y=currentNode.parent;
             while(y!=null && currentNode==y.rightChild){
                currentNode=y;
                y=y.parent;
             }
             nextNode=y;
         
         }
         if(nextNode!=null)
             System.out.println(nextNode.id + " " + nextNode.count);
         else
             System.out.println("0 0");
         }
         else{
             TreeNode currentNodeNoKey=nextNoKey(id);//if the given ID is not present in the counter then nextNoKey is used to find the next event
             if(currentNodeNoKey!=null)
                 System.out.println(currentNodeNoKey.id + " " + currentNodeNoKey.count);
             else
                 System.out.println("0 0");
                 
                 
                 
             
             
         }
         
     }
     
      //function to print the ID and count of the event with the greatest ID lesser than the given ID
     public void previous(int id){
         TreeNode currentNode=iterativeSearch(id);
         if(currentNode!=null){
             TreeNode previousNode=null;
         TreeNode y;
         if(currentNode!=null && currentNode.leftChild!=null)
             previousNode=treeMaximum(currentNode.leftChild);
         else{
             y=currentNode.parent;
             while(y!=null && currentNode==y.leftChild){
                currentNode=y;
                y=y.parent;
             
         }
             previousNode=y;
         
         }
         if(previousNode!=null)
             System.out.println(previousNode.id + " " + previousNode.count);
         else
             System.out.println("0 0");
             
         }
         else{
              TreeNode currentNodeNoKey=previousNoKey(id);//if the given ID is not present in the counter then previousNoKey is used to find the previous event
             if(currentNodeNoKey!=null)
                 System.out.println(currentNodeNoKey.id + " " + currentNodeNoKey.count);
             else
                 System.out.println("0 0");
             
         }
         
         
     }
     
     //function to find next event if the given ID is not present in the counter
     public TreeNode nextNoKey(int id){
       
         TreeNode x=root;
         TreeNode parentx;
        
         while(x!=null && id!=x.id){
             if(id<x.id){
                 parentx=x;
                 x=x.leftChild;
                 if(x==null)
                     return parentx;
                 
              }
             else
             {
                 parentx=x;
                 x=x.rightChild;
                 if(x==null){
                     while(parentx!=null && parentx.id<id){
                         parentx=parentx.parent;
                         
                     }
                     if(parentx!=null)
                         return parentx;
                     else 
                         return null;
                 }
                     
                 
                     
                 
                 
             }
                 
             
                 
         }
         return x;
         
     }
    
    //function to find previous event if the given ID is not present in the counter
    public TreeNode previousNoKey(int id){
       
         TreeNode x=root;
         TreeNode parentx;
        
         while(x!=null && id!=x.id){
             if(id<x.id){
                 parentx=x;
                 x=x.leftChild;
                 if(x==null){
                     while(parentx!=null && parentx.id>id){
                         parentx=parentx.parent;
                         
                     }
                     if(parentx!=null)
                         return parentx;
                     else 
                         return null;
                 }
                     
                 
                 
              }
             else
             {
                 parentx=x;
                 x=x.rightChild;
                 if(x==null)
                     return parentx;
                 
                 
                     
                 
                 
             }
                 
             
                 
         }
         return x;
         
     }
    
    
    public static void main(String[] args){
        
        try {
            //using buffered reader to read IDs and counts from input file
            FileReader fr=new FileReader(args[0]);
            BufferedReader br=new BufferedReader(fr);
            String Line=null;
            int EventNumber = Integer.parseInt(br.readLine());
            
            
            //Creating array of event IDs and array of event counts
            
            int idarray[]=new int[EventNumber];
            int countarray[]=new int[EventNumber];
            
            int i=0;
            while((Line=br.readLine())!=null){
               
                String[] details = Line.split("\\s+");
                String id = details[0];
                String count = details[1];
                int y = Integer.parseInt(id);
                int z = Integer.parseInt(count);
                idarray[i]=y;
                countarray[i]=z;
                i++;
                
             }
                   
             int height=(int)(Math.log(EventNumber)/Math.log(2));//calculating the height of the balanced binary search tree 
             bbst tree=new bbst();//red black tree object
             root=tree.arraytobst(idarray,countarray,0,EventNumber-1,height);//creating red-black tree from sorted array of event IDs and tree height
             
             //Reading commands from standard input stream
             BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
             String command;
             while((command=in.readLine())!=null){
                 //System.out.println("Enter the event counter operation");
                 
                 
                 
                 String[] commandarray = command.split("\\s+");
                 
                 //using switch case for correct function call
                 switch(commandarray[0]){
                     case "increase":
                         
                         int idInc=Integer.parseInt(commandarray[1]);
                         int mInc=Integer.parseInt(commandarray[2]);
                         int increasedCount=tree.increase(idInc,mInc);//count after increase
                         System.out.println(increasedCount);
                         break;
                     case "reduce":
                         int idDec=Integer.parseInt(commandarray[1]);
                         int mDec=Integer.parseInt(commandarray[2]);
                         int reducedCount=tree.decrease(idDec,mDec);//count after reduce
                         System.out.println(reducedCount);
                         break;
                     case "count":
                         int idCount=Integer.parseInt(commandarray[1]);
                         int eventCount=tree.count(idCount);//count of ID
                         System.out.println(eventCount);
                         break;
                     case "inrange":
                         int low=Integer.parseInt(commandarray[1]);
                         int high=Integer.parseInt(commandarray[2]);
                         int inrangeSum=tree.inRange(low,high);//sum of counts between low and high inclusive
                         System.out.println(inrangeSum);
                         break;
                     case "next":
                         int nextId=Integer.parseInt(commandarray[1]);//ID of next event
                         tree.next(nextId);
                         break;
                     case "previous":
                         int previousId=Integer.parseInt(commandarray[1]);//ID of previous event
                         tree.previous(previousId);
                         break;
                     case "quit":
                          System.exit(0);//exit the program if user input is 'quit'
                         
                         
                                 
                 }
                     
                 
             }
                     
             
             
                
   
         }
        //catch construct to catch exceptions
        catch (Exception e) {         
            e.printStackTrace();
        }
       
    }
    
    

    
    
}
