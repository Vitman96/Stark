import java.util.Scanner;
public class Player{

    public String name; //Name of Player for UI and Score  Board
    public int cashLevel; //How much Cash has Player won currently

    //Creat Player Class with Custom Name
    public  class Player(String name)
    {
       this.name = name;
       cashLevel = 0;
    }

    // Create Default Player Class if Player name is Set
    public class Player()
    {
        this.name = "Demo Player";
        cashLevel = 0;
    }

    // Get Player Name Value
    public String getName()
    {
        return  name;
    }

    // Get Current cashLevel Value
    public  int getCashLevel()
    {
        return cashLevel;
    }

    // Change cashLevel Value

    public void setCashLevel(int cashLevel)
    {
        this.cashLevel= cashLevel;
    }


    // Function to get Answer from Player
    public String getPlayerAnswer()
    {
        String answer ="";
        Scanner in = new Scanner(System.in);
        System.out.println("Deine Antwort ist:");
        answer = in.nextLine();

        return answer;
    }
}