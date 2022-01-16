import java.util.Scanner;

public class Player {

    public String name; //Name of Player for UI and Score  Board
    public int cashLevel; //How much Cash has Player won currently
    public int rangking;
    //Create Player Class with Custom Name
    public Player(String name) {
        this.name = name;
        this.cashLevel = 0;
        this.rangking = 0;
    }

    // Create Default Player Class if Player name is Set
    public Player() {
        this.name = "Demo Player";
        this.cashLevel = 0;
        this.rangking = 0;
    }

    public Player(String name, int cashLevel, int rangking) {
        this.name = name;
        this.cashLevel = cashLevel;
        this.rangking = rangking;
    }

    // Get Player Name Value
    public String getName() {
        return  name;
    }

    // Get Current cashLevel Value
    public  int getCashLevel() {
        return cashLevel;
    }

    // Change cashLevel Value

    public void setCashLevel(int cashLevel) {
        this.cashLevel = cashLevel;
    }


    // Function to get Answer from Player
    public String getPlayerAnswer() {
        String answer = "";
        Scanner in = new Scanner(System.in);
        System.out.println("Deine Antwort ist:");
        answer = in.nextLine().toUpperCase();

        return answer;
    }
}