
import java.io.*;
import java.util.*;


public class SlidingNode extends SlidingPuzzle implements Comparable<SlidingNode> {
  
  int h1; // num of wrong items
  int h2; // distance from final
  int depth;
  
  public SlidingNode children[] = new SlidingNode[4]; // Stores the 4 different directions
  public SlidingNode parent;
  public String state;
  public String direction;
  public String heurstic;
  // Hashtable to store all children
  
  SlidingNode(String[] puzzle, SlidingNode parent, String heurstic, int depth) { 
    this.setPuzzle(puzzle);
    
    StringBuffer sb = new StringBuffer();
    for(int i = 0; i < puzzle.length; i++) {
      sb.append(puzzle[i]);
    }
    String state = sb.toString();
    this.setState(state);
    this.setParent(parent);
    this.setHeurstic(heurstic);
    if(heurstic.equals("h1") || heurstic.equals("H1")){
      this.evalH1(); 
    } else if (heurstic.equals("h2") || heurstic.equals("H2")){
      this.evalH2(); 
    }
    this.depth = depth;
    //  System.out.println(h1);
  }
  
  public void createChildren() {
    
    // Go through each move
    for (int i = 0; i < 4; i++) {
      SlidingNode child = new SlidingNode(this.getPuzzle(), null, heurstic, 0);
      
      
      if (i == 0) {
        // Check if it can move right
        if (child.move("right").equals("Valid move")) {
          
          // Check if the hashtable has already seen this state.
          // If its a new state add it to the hashtable and create the new node.
          if (!ht.containsKey(child.getState().hashCode())) {
            ht.put(child.getState().hashCode(), true);
            
            // Create a node that will represent the new move made.
            SlidingNode rightChild = new SlidingNode(child.getPuzzle(),this,this.getHeurstic(),this.depth + 1);
            rightChild.setDirection("right");
            children[0] = rightChild;
          }
          
        }
      }
      
      
      if (i == 1) {
        if (!(child.move("left").equals("Invalid move"))) {
          
          child.currState = child.getState();
          
          
          if (!ht.containsKey(child.getState().hashCode())) {
            ht.put(child.getState().hashCode(), true);  
            SlidingNode leftChild = new SlidingNode(child.getPuzzle(),this,this.getHeurstic(),this.depth + 1);
            
            
            leftChild.setDirection("left");
            children[1] = leftChild;
          }
          
        }
        // Put this state in the hashtable if you can
      }
      if (i == 2) {
        if (!(child.move("up").equals("Invalid move"))) {
          
          
          
          if (!ht.containsKey(child.getState().hashCode())) { // Check if the hashtable has seen this state
            ht.put(child.getState().hashCode(), true); // Put it in if it hasn't
            SlidingNode upChild = new SlidingNode(child.getPuzzle(),this,this.getHeurstic(),this.depth + 1);
            
            
            upChild.setDirection("up");
            children[2] = upChild;
          }
        }
        
      }
      if (i == 3) {
        if (!(child.move("down").equals("Invalid move"))) {
          
          
          if (!ht.containsKey(child.getState().hashCode())) {
            ht.put(child.getState().hashCode(), true);
            SlidingNode downChild = new SlidingNode(child.getPuzzle(),this,this.getHeurstic(),this.depth + 1);
            
            downChild.setDirection("down");
            children[3] = downChild;
          }
          
        }
        // Put this state in the hashtable if you can
      }
    }
  }
  
  public SlidingNode getParent(){
    return this.parent; 
  }
  public void setParent(SlidingNode parent){
    this.parent = parent; 
  }
  
  public int evalH1() {
    
    int x = 0;
    String[] correctPuzzle = new String[] { "b", "1", "2", "3", "4", "5", "6", "7", "8" };
    String[] puzzle = this.getPuzzle();
    
    for (int i = 0; i < puzzle.length; i++) {
      if (!puzzle[i].equals(correctPuzzle[i])) {
        x++;
      }
    }
    
    h1 = x;
    //System.out.println(depth);
    return h1 + depth;
  }
  
  
  
  public int evalH2() {
    int x = 0;
    String[] currPuzzle = this.getPuzzle(); // Reference to its current puzzleState
    
    // Loop through the rows 
    for (int k = 0; k < 3; k++) {
      
      // Loop through the columns
      for (int j = 0; j < 3; j++) {
        
        // Calculate the distance for each individual tile
        switch (currPuzzle[(3 * k) + j]) {
          case "1":
            x = x + Math.abs(j - 1) + Math.abs(k - 0);
            
            break;
          case "2":
            x = x + Math.abs(j - 2) + Math.abs(k - 0);
            
            break;
          case "3":
            x = x + Math.abs(j - 0) + Math.abs(k - 1);
            
            break;
          case "4":
            x = x + Math.abs(j - 1) + Math.abs(k - 1);
            
            break;
          case "5":
            x = x + Math.abs(j - 2) + Math.abs(k - 1);
            
            break;
          case "6":
            x = x + Math.abs(j - 0) + Math.abs(k - 2);
            
            break;
          case "7":
            x = x + Math.abs(j - 1) + Math.abs(k - 2);
            
            break;
          case "8":
            x = x + Math.abs(j - 2) + Math.abs(k - 2);
            
            break;
        }
      }
      
    }
    
    h2 = x;
    return h2 + depth;
  }
  
  public boolean isVisited(){
    
    StringBuffer sb = new StringBuffer();
    for(int i = 0; i < this.getPuzzle().length; i++) {
      sb.append(this.getPuzzle()[i]);
    }
    String state = sb.toString();
    this.setState(state);
    int key = this.getState().hashCode();
    if(ht.containsKey(key)){
      return true;
    }else {
      return false; 
    }
  }
  
  public int compareTo(SlidingNode j){
    if( this.getHeurstic().equals("h1") || this.getHeurstic().equals("H1")){
      if (h1 + depth > j.evalH1())
        return 1;
      
      return -1;
    } else{
      if (h2 + depth > j.evalH2())
        return 1;
      
      return -1;
      
    }
  }

  
  
  public String getHeurstic(){
    return this.heurstic; 
  }
  
  public void setHeurstic(String heurstic){
    this.heurstic = heurstic; 
  }
  
  public String getDirection(){
    return this.direction; 
  }
  
  public void setDirection(String direction){
    this.direction = direction; 
  }
  
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    
  }
}

