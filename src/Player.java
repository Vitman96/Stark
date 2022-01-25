import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

public class Player {

    public String name; //Name of Player for UI and Score  Board
    public int cashLevel; //How much Cash has Player won currently

    //Create Player Class with Custom Name
    public Player(String name) {
        this.name = name;
        this.cashLevel = 0;
    }

    // Create Default Player Class if Player name is Set
    public Player() {
        this.name = "Demo Player";
        this.cashLevel = 0;
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
        if (validAnswer(answer)) {
            return answer;
        } else {
            return changeAnswer(answer);
        }
    }

    // Checks if the given answer was valid
    public boolean validAnswer(String answer) {
        boolean valid = false;
        switch (answer) {
            case "A":
            case "B":
            case "C":
            case "D":
            case "a":
            case "b":
            case "c":
            case "d":
                valid = true;
                break;
            case "HELP":
                Desktop desktop = java.awt.Desktop.getDesktop();
                try {
                    desktop.browse(new URI("https://de.wikihow.com/Kl%C3%BCger-werden"));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                System.out.println("Versuchs doch nach diesen Tipps nochmal ;)");
                valid = false;
                break;
            default:
                System.out.println("Ungültige Eingabe!");
                valid = false;
                break;
        }
        return valid;
    }

    // Lets the player change his answer, the new answer will also be checked if it is valid
    public String changeAnswer(String answer) {
        System.out.println("Neue Eingabe:");
        Scanner in = new Scanner(System.in);
        answer = in.nextLine().toUpperCase();
        if (validAnswer(answer)) {
            return answer;
        } else {
            return changeAnswer(answer);
        }
    }
}