import java.lang.*;
import java.util.*;
import java.util.Arrays;
import java.lang.Boolean;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;
import java.io.FileWriter;

public class  SlidingPuzzle {
  public static Hashtable<Integer, Boolean> ht = new Hashtable<Integer, Boolean>();
  public String[] puzzle = new String[9];
  public String currState;
  public int maxNodes = 20000;
  File file;
  
  
  SlidingPuzzle(){
   this.randomizeState(10); 
  }
  
  
  public void setState(String state) {
    
    // Remove spaces
    state = state.replaceAll("\\s", "");
    for (int i = 0; i < state.length(); i++) {
      puzzle[i] = Character.toString(state.charAt(i));
    }
    this.currState = state;
    
  }
  
  public void printState() {
    System.out.println("| " + puzzle[0] + " " + puzzle[1] + " " + puzzle[2] + " |\n" 
                         + "| " + puzzle[3] + " " + puzzle[4] + " " + puzzle[5] + " |\n" 
                         + "| " + puzzle[6] + " " + puzzle[7] + " " + puzzle[8] + " |");
  }
  
  public String move(String direction) {
    
    // Find where the empty tile is
    int location = 0;
    for (int i = 0; i < puzzle.length; i++) {
      if (puzzle[i].equals("b")) {
        location = i;
      }
    }
    
    // If the empty tile is on the left most space, tell user its an invalid move
    if ((direction.equals("Left") || direction.equals("left"))
          && (location == 0 || location == 3 || location == 6)) {
      return "Invalid move";
    }
    
    // Move the "tile" left
    else if (direction.equals("Left") || direction.equals("left")) {
      String old = puzzle[location - 1];
      puzzle[location - 1] = "b";
      puzzle[location] = old;
      setPuzzle(puzzle);
      return "Valid move";
      
    }
    
    // If the empty tile is on the right most space, tell user its an invalid move
    if ((direction.equals("Right") || direction.equals("right"))
          && (location == 2 || location == 5 || location == 8)) {
      return "Invalid move";
    }
    // Move the "tile" right
    else if (direction.equals("Right") || direction.equals("right")) {
      String old = puzzle[location + 1];
      puzzle[location + 1] = "b";
      puzzle[location] = old;
      setPuzzle(puzzle);
      return "Valid move";
    }
    
    // If the empty tile is on the top most space, tell user its an invalid move
    if ((direction.equals("Up") || direction.equals("up")) && (location == 0 || location == 1 || location == 2)) {
      return "Invalid move";
    }
    // Move the "tile" up
    else if (direction.equals("Up") || direction.equals("up")) {
      String old = puzzle[location - 3];
      puzzle[location - 3] = "b";
      puzzle[location] = old;
      setPuzzle(puzzle);
      return "Valid move";
    }
    
    // If the empty tile is on the bottom most space, tell user its an invalid move
    if ((direction.equals("Down") || direction.equals("down"))
          && (location == 6 || location == 7 || location == 8)) {
      return "Invalid move";
    }
    // Move the "tile" down
    else if (direction.equals("Down") || direction.equals("down")) {
      String old = puzzle[location + 3];
      puzzle[location + 3] = "b";
      puzzle[location] = old;
      setPuzzle(puzzle);
      return "Valid move";
    }
    
    return "\n";
  }
  
  public void randomizeState(int n) {
    setState("b12 345 678");
    
    for (int i = 0; i < n; i++) {
      Random rand = new Random();
      
      
      // Picks a random number between 1-4 inclusive
      int event = rand.nextInt(4);
      event+=1;
      
      switch (event) {
        case 1:
          this.move("left");
          break;
        case 2:
          this.move("right");
          break;
        case 3:
          this.move("down");
          break;
        case 4:
          this.move("up");
          break;
      }
    }
    // System.out.println(isSolvable());
  }
  
//  public int isSolvable(){
//    int count = 0;
//    for (int i = 0; i < 9; i++){
//      for (int j = i+1; j < 9; j++){
//        if ( !getPuzzle()[i].equals("b") && !getPuzzle()[j].equals("b") && 
//            Integer.valueOf(getPuzzle()[i]) > Integer.valueOf(getPuzzle()[j]) ) count++;
//      }
//    }
//    return count;
//  }
  
  
  public String[] getPuzzle() {
    return this.puzzle;
  }
  
  public void setPuzzle(String[] newPuzzle){
    for(int i = 0; i < newPuzzle.length ; i++){
      this.puzzle[i] = newPuzzle[i]; 
    }
  }
  
  
  public String getState(){
    StringBuffer sb = new StringBuffer();
    for(int i = 0; i < puzzle.length; i++) {
      sb.append(puzzle[i]);
    }
    String str = sb.toString(); 
    currState = str;
    return currState;
  }
  
  public void maxNodes(int maxNodes){
    this.maxNodes  = maxNodes;
  }
  
  
  
  
  public ArrayList<String> solveAStar(String heurstic){
    ht = new Hashtable<Integer, Boolean>();
    ArrayList<String> path = new ArrayList<String>();
    int moves = 0;
    int expNodes = 1;
    PriorityQueue<SlidingNode> queue = new PriorityQueue<SlidingNode>();
    
    SlidingNode startNode = new SlidingNode(this.getPuzzle(),null,heurstic,0);
    
    
    ht.put(startNode.getState().hashCode(),true);
    queue.add(startNode);
    String[] correctPuzzle = { "b", "1", "2", "3", "4", "5", "6", "7", "8" };
    
    boolean found = false;
    int tooMuch = 0;
    while (!found) {
      
      SlidingNode node = queue.poll(); // First thing in queue with lowest heurstic so far
      
      if(ht.size() > maxNodes){
        System.out.println("Reached the max number of nodes to expand: " + maxNodes); 
        return path;
      }
      
      // Check if its the goal state
      if(Arrays.equals(node.getPuzzle(),correctPuzzle)){
        
       // System.out.println("Found the goal state");
        // Return the path back
        while(node.getParent() != null){
          path.add(node.getDirection());
          node = node.getParent();
          moves++;
        }
        Collections.reverse(path);
        System.out.println("Effective branching factor (approx): " + Math.pow(ht.size(),1.0/moves));
        System.out.println("Number of moves it took: " + moves);
        System.out.println("Number of expanded nodes: " + expNodes);
        found = true;
        
      } else {
        
        // Look through the other neighbors
        node.createChildren();
        for(int i = 0; i < node.children.length; i++){
          if( node.children[i] != null){
            expNodes++;
            queue.add(node.children[i]);
            
          }
          
          
          // Rinse and repeat
        }
        
      }
    }
    // System.out.println(ht.size());
    return path;
  }
  
  public ArrayList<String> solveBeamSearch(int k){
    ht = new Hashtable<Integer, Boolean>();
    SlidingNode array[] = new SlidingNode[k];
    PriorityQueue<SlidingNode> queue = new PriorityQueue<SlidingNode>();
    ArrayList<String> path = new ArrayList<String>();
    int moves = 0;
    int expNodes = 0;
    SlidingNode startNode = new SlidingNode(this.getPuzzle(),null,"h2",0);
    array[0] = startNode;
    String[] correctPuzzle = { "b", "1", "2", "3", "4", "5", "6", "7", "8" };
    boolean found = false;
    
    while(!found){
      
      if( ht.size() > maxNodes){
        System.out.println("Reached max number of nodes: " + maxNodes);
        return path;
      }
      
      
      SlidingNode node = array[0];
      
      // Check if the node is solved.
      if(Arrays.equals(node.getPuzzle(),correctPuzzle)){
        //System.out.println("Found the goal state");
        // Return the path back
        while(node.getParent() != null){
          
          path.add(node.getDirection());
          node = node.getParent();
          moves++;
        }
        Collections.reverse(path);
        System.out.println("Effective branching factor (approx): " + Math.pow(ht.size(),1.0/moves));
        System.out.println("Number of moves it took: " + moves);
        System.out.println("Number of expanded nodes: " + expNodes);
        
        found = true;
      } else {
        
        for(int i = 0; i < array.length; i++){
          // Create children for everything in the array
          if (array[i] != null){
            node = array[i];
            node.createChildren();
            
            // Loop through every single child created and add to queue
            for (int j = 0; j < node.children.length; j++){
              if(node.children[j] != null){
                
                queue.add(node.children[j]); // Sort the best of the children
                
                expNodes++;
                
              }
            }
          }
          
          
        }
        // Clear the array, then add k things from queue.
        Arrays.fill(array,null);
        for(int x = 0; x < k; x++){
          array[x] = queue.poll();
        }
        queue.clear();
        
      }
      
      
      
    }
    // System.out.println(ht.size());
    return path;
  }
  
  
  
  
  
  // This method was only used to help with conducting experiments, does not affect the rest of the code.
  public void experiments(int number){
    ArrayList<String> a1path = new ArrayList<String>();
    ArrayList<String> a2path = new ArrayList<String>();
    ArrayList<String> bpath = new ArrayList<String>();
    ArrayList<String> b1000path = new ArrayList<String>();
    int a1moves = 0;
    int a2moves = 0;
    int bmoves = 0;
    int a1nodes = 0;
    int a2nodes = 0;
    int bnodes = 0;
    double a1null = 0;
    double a2null = 0;
    double bnull = 0;
    double b1000null = 0;
    
    // First experiment, just see which one is best.
    if(number == 1){ 
      this.maxNodes(20000);
      System.out.println("A Star with h1");
      
      for(int i = 0; i < 100; i++){
      
        this.randomizeState(1000);
        a1path =  this.solveAStar("h1");
        a1nodes = a1nodes + ht.size();
        
        if(a1path != null)
          a1moves = a1moves + a1path.size();
    

        a2path = this.solveAStar("h2"); 
        a2nodes = a2nodes + ht.size();
        
        if(a2path != null)
          a2moves = a2moves + a2path.size();
 
        bpath = this.solveBeamSearch(100);
        bnodes = bnodes + ht.size();
        
        if(bpath != null)
          bmoves = bmoves + bpath.size();
      }
      
      
      System.out.println("\nA star with h1 on average did: " + a1moves/100 + " moves " + a1nodes/100 + " expanded nodes");
      System.out.println("A star with h2 on average did: " + a2moves/100 + " moves " + a2nodes/100 + " expanded nodes") ;
      System.out.println("Beam on average did: " + bmoves/100 + " moves " + bnodes/100 + " expanded nodes");
    } 
    //Second experiment, percentage as maxNodes increases.
    else if( number == 2){
      
      maxNodes(20000);
      for(int i = 0; i < 100; i++){
        this.randomizeState(1000);
        
        a1path = this.solveAStar("h1");
        if ( a1path.size() == 0){
          a1null++;
        }
        
        a2path = this.solveAStar("h2");
        if (a2path.size() == 0){
          a2null++;
        }
        
        bpath = this.solveBeamSearch(100);
        if (bpath.size() == 0){
          bnull++;
        }
        b1000path = this.solveBeamSearch(1000);
        if(b1000path.size() == 0)
          b1000null++;
      }
      
      System.out.println("Percentage of solved A* with h1: " +(100 - a1null) + " with max nodes at " + maxNodes);
      System.out.println("Percentage of solved A* with h2: " + (100 - a2null) + " with max nodes at " + maxNodes);
      System.out.println("Percentage of solved Beam search: " + (100 - bnull) + " with max nodes at " + maxNodes);
      System.out.println("Percentage of solved Beam search 1000: " + (100 - b1000null) + " with max nodes at " + maxNodes);
      
    }
    else if(number == 3){
      double a1solve = 0;
      double a2solve = 0;
      double beamsolve = 0;
      int times = 500;
      
      for(int i = 0; i < times; i++){
        randomizeState(1000);
        a1path = this.solveAStar("h1");
        if(a1path.size() != 0)
          a1solve++;
        a2path = this.solveAStar("h2");
        if(a2path.size() != 0)
          a2solve++;
        
        bpath = this.solveBeamSearch(100);
        if(bpath.size() != 0)
          beamsolve++;
      }
      
      System.out.println("Percent solved of A* h1: " + (a1solve/times) * 100);
      System.out.println("Percent solved of A* h2: " + (a2solve/times) * 100);
      System.out.println("Percent solved of Beam 100: " + (beamsolve/times) * 100);
      
      
      
    }
    
    
    
    
  }
  
  
  
  
  
  public static void main(String[] args)  {
    
    SlidingPuzzle slide = new SlidingPuzzle();
    
    BufferedReader reader;
    try {
      
      
      // Read from given file
      reader = new BufferedReader(new FileReader("C:\\Users\\jctol\\Downloads\\test4.txt"));
      
      String[] strArray = new String[100];
      int index = 0;
      String line = reader.readLine();
      while (line != null) {
        strArray[index] = line;
        line = reader.readLine();
        index++;
      }
      reader.close();
      
      
      try {
        // Write to file
        
        FileWriter writer = new FileWriter("P1 response.txt");
        
        for(int i = 0; i < index; i++){
          String [] splitArray = strArray[i].split("\\s",3);
          ArrayList<String> solved = new ArrayList<String>();
          
          
          
          if(splitArray[0].equals("setState")){
            slide.setState(splitArray[1] + splitArray[2]);
          } else if(splitArray[0].equals("move")){
            writer.write(slide.move(splitArray[1]) + "\n");
          } else if(splitArray[0].equals("printState")){
            writer.write("| " + slide.puzzle[0] + " " + slide.puzzle[1] + " " + slide.puzzle[2] + " |\n" 
                           + "| " + slide.puzzle[3] + " " + slide.puzzle[4] + " " + slide.puzzle[5] + " |\n" 
                           + "| " + slide.puzzle[6] + " " + slide.puzzle[7] + " " + slide.puzzle[8] + " |\n");
          } else if(splitArray[0].equals("randomizeState")){
            slide.randomizeState(Integer.parseInt(splitArray[1]));
          } else if(splitArray[0].equals("maxNodes")){
            slide.maxNodes(Integer.parseInt(splitArray[1]));
          }else if(splitArray[0].equals("solve")){
            //System.out.println("Second index: " + splitArray[1] + "\n" + "Third Index: ");
            if(splitArray[1].equals("A-star")){
              solved = slide.solveAStar(splitArray[2]);
              writer.write("Number of moves: " + solved.size() + "\n" + "Moves to do: " + solved + "\n");
            }
            if(splitArray[1].equals("beam")){
              solved = slide.solveBeamSearch(Integer.parseInt(splitArray[2]));
              writer.write("Number of moves: " + solved.size() + "\n" + "Moves to do: " + solved + "\n");
            }
          }
        }
        writer.close();
        
        
      } catch(IOException e1){
        e1.printStackTrace();
      }
    }catch (IOException e) {
      e.printStackTrace();
    }  
    
    
    
    
  }
  
}
