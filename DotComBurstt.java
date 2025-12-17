import java.util.*;
import java.io.*;

public class DotComBurst {
    private int numOfGuess = 0;
    private ArrayList<DotCom> dotComLists = new ArrayList<DotCom>();
    private GameHelper helper = new GameHelper();

void setUpGame(){
DotCom a = new DotCom();
a.setName("PETS.COM");
DotCom b = new DotCom();
b.setName("TREE.COM");
DotCom c = new DotCom();
c.setName("HI.COM");
dotComLists.add(a);
dotComLists.add(b);
dotComLists.add(c);

System.out.println("Your goal is to sink all three dot coms");
System.out.println("PETS.COM , TREE.COM,HI.COM");
System.out.println("Your goal is to sink all the dot coms in minimum number of guesses");

   for(DotCom dotComToSet : dotComLists){
    ArrayList<String> newLocation = helper.placeDotCom(3);
    dotComToSet.setLocationCells(newLocation);
   }
}
void startPlaying(){
    while(!dotComLists.isEmpty()){
       String userGuess = helper.getUserInput("Enter a guess: ");
        checkUserGuess(userGuess);
    }
    finishGame();
}
private void checkUserGuess(String userGuess){
    numOfGuess++;    
    String result = "miss";
    for(DotCom dotComToTest : dotComLists){
        result = dotComToTest.checkYourself(userGuess);
        if(result.equals("hit")) break;
        if(result.equals("kill")){
        dotComLists.remove(dotComToTest);
        break;
        }
    }
    System.out.println(result);

}
void finishGame(){
    System.out.println("GAME OVER!!!  All dot coms are dead.");
    if(numOfGuess < 15){
        System.out.println("Congratulations!!! It only took you "+ numOfGuess+ "guesses");
    }else{
        System.out.println("You took a lots of guesses....");
    }
}
public class DotCom{
    private ArrayList<String> locationCells;
    private String name;

    public void setLocationCells(ArrayList<String> loc){
        locationCells = loc;
    }
    public void setName(String n){
        name = n;
    }
    public String checkYourself(String userInput){
        String result = "miss";
        int index = locationCells.indexOf(userInput);
        if(index >= 0){
            locationCells.remove(index);
            if(locationCells.isEmpty()){
                result = "kill";
                System.out.println("Ouch! You sunk "+ name);
            }else{
                result = "hit";
            }
        }

        return result;
    }
}
public class GameHelper{
    private static final String alphabet = "abcdefg";
    private int gridLength = 7;
    private int gridSize = 49;
    private int[] grid = new int[gridSize];
    private int comCount = 0;

    public String getUserInput(String prompt){
        String inputLine = null;
        System.out.println(prompt + " ");
        try{
            BufferedReader is = new BufferedReader(new InputStreamReader(System.in));
            inputLine = is.readLine();
            if(inputLine.length() == 0) 
            return null;
        }catch(IOException e){
            System.out.println("IOException: "+ e);
        }
        return inputLine.toLowerCase();
    }
     public ArrayList<String> placeDotCom(int comSize){
            ArrayList<String> alphaCells = new ArrayList<String>();
            String temp = null;
            int [] coords = new int[comSize];
            int attempts = 0;
            boolean success = false;
            int location = 0;

            comCount++;
            int incr = 1;
            if((comCount % 2) == 1){
                incr = gridLength;
            }
            while(! success & attempts++ < 200 ){
                location = (int)(Math.random() * gridSize);
                int x = 0;
                success = true;
                while(success && x < comSize){
                    if(grid[location] == 0){
                        coords[x++] = location;
                        location += incr;
                        if(location >= gridSize){
                             success = false;
                            }
                        if(x > 0 && (location % gridLength == 0)){ 
                            success = false;
                        }
                    }else {
                            success = false;
                        }
                    }
                }
                int x = 0;
                int row = 0;
                int column = 0;
                while(x < comSize){
                    grid[coords[x]] = 1;
                    row = (int)(coords[x] / gridLength);
                    column = coords[x] % gridLength;
                    temp = String.valueOf(alphabet.charAt(column));
                    alphaCells.add(temp.concat(Integer.toString(row))); 
                    x++;
                }
        
        return alphaCells;
    }
}

 public static void main(String[] args) {
    DotComBurst game = new DotComBurst();
    game.setUpGame();
    game.startPlaying();
  }
}
