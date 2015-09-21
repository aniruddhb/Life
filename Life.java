import java.util.*;
import java.io.*;

public class Life {
    boolean[][] currentState;
    boolean[][] nextState;
    
    public Life(String fileName){
        Scanner in = null;
        try {
            in = new Scanner(new File(fileName));
        } catch (IOException io){
            // io.printStackTrace();
        }
        
        int rowNum, colNum;
        
        rowNum = in.nextInt();
        colNum = in.nextInt();
        
        currentState = new boolean[rowNum][colNum];
        nextState = new boolean[rowNum][colNum];
        
        while (in.hasNext()){
          int rowPos = in.nextInt();
          int colPos = in.nextInt();
          currentState[rowPos][colPos] = true;
        }
    }
    
    public void runLife(int numGenerations){
        for (int generationCount = 0; generationCount < numGenerations; generationCount++){
            for (int outer = 0; outer < currentState.length; outer++){
                for (int inner = 0; inner < currentState[0].length; inner++){
                    int amountOfNeighbors = countNeighbors(outer, inner);
                    
                    if (currentState[outer][inner] == false && (amountOfNeighbors == 3)){
                        nextState[outer][inner] = true;
                    }
                
                    if (currentState[outer][inner] == true && (amountOfNeighbors < 2)){
                        nextState[outer][inner] = false;
                    }
                
                    if (currentState[outer][inner] == true && (amountOfNeighbors >= 4)){
                        nextState[outer][inner] = false;
                    }
                
                    if (currentState[outer][inner] == true && (amountOfNeighbors == 2 || amountOfNeighbors == 3)){
                        nextState[outer][inner] = true;
                    }   
                }
            }
            copyNextState();
            clearNextState();
        }
    }
    
    public void nextGeneration(){
        runLife(1);
    }
    
    public int countNeighbors(int xPos, int yPos){
        int neighborCount = 0;
        int[][] possibleNeighborPositions = {{1, 1}, {1, 0}, {1, -1}, {0, 1}, {0, -1}, {-1, 1}, {-1, 0}, {-1, -1}};
        
        for (int[] possibleLocation : possibleNeighborPositions){
            int newXPos = xPos + possibleLocation[0];
            int newYPos = yPos + possibleLocation[1];
            
            boolean pointInBounds = checkBoundary(newXPos, newYPos);
            boolean isAlive = false;
            
            if (pointInBounds == true){
                isAlive = checkIfLive(newXPos, newYPos);
            }
            
            if (pointInBounds == true && isAlive == true){
                neighborCount++;
            }
        }
        
        return neighborCount;
    }
    
    public boolean checkBoundary(int xFinal, int yFinal){
        if ((xFinal >= 0 && xFinal < currentState.length) && (yFinal >= 0 && yFinal < currentState.length)){
            return true;
        } else {
            return false;
        }
    }
    
    public boolean checkIfLive(int xFinal, int yFinal){
        if (currentState[xFinal][yFinal] == true){
            return true;
        } else {
            return false;
        }
    }
    
    public void copyNextState(){
        for (int outer = 0; outer < nextState.length; outer++){
            for (int inner = 0; inner < nextState[0].length; inner++){
              currentState[outer][inner] = nextState[outer][inner];
            }
        }
    }
    
    public void clearNextState(){
        for (int outer = 0; outer < nextState.length; outer++){
            for (int inner = 0; inner < nextState[0].length; inner++){
                nextState[outer][inner] = false;
            }
        }
    }
    
    public int rowCount(int row){
        int cellsInRow = -1;
        if (row >= 0 && row < currentState.length){
            cellsInRow = 0;
            for (int inner = 0; inner < currentState.length; inner++){
                if (currentState[row][inner] == true){
                   cellsInRow++;
                }
            }
        }
        return cellsInRow;
    }
    
    public int colCount(int col){
        int cellsInColumn = -1;
        if (col >= 0 && col < currentState.length){
            cellsInColumn = 0;
            for (int outer = 0; outer < currentState.length; outer++){
                if (currentState[outer][col] == true){
                    cellsInColumn++;
                }
            }
        }
        return cellsInColumn;
    }
    
    

  public int totalCount(){
        int totalCells = 0;
        for (int outer = 0; outer < currentState.length; outer++){
            for (int inner = 0; inner < currentState[0].length; inner++){
                if (currentState[outer][inner] == true){
                    totalCells++;
                }
            }
        }
        return totalCells;
  }
    
    public void printBoard(){
        System.out.print("\t");
        for (int printColumns = 0; printColumns < currentState.length; printColumns++){
            if (printColumns >= 9){
                System.out.print((printColumns) % 10);
            } else { System.out.print(printColumns);
            }
        }
        System.out.println();
        
        for (int printRows = 0; printRows < currentState.length; printRows++){
            System.out.print(printRows);
            System.out.print("\t");
            for (int inner = 0; inner < currentState[0].length; inner++){
                if (currentState[printRows][inner] == true){
                    System.out.print("*");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
    
    public static void main(String[] args){
        Life life = new Life("life100.txt");
        life.runLife(5);
        life.printBoard();
        System.out.println("\nNumber of Cells in Row 9 ---> " + life.rowCount(9));
        System.out.println("\nNumber of Cells in Column 9 ---> " + life.colCount(9));
        System.out.println("\nNumber of living organisms ---> " + life.totalCount());
    }
}
