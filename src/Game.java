import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Game {

    private boolean gameRunning = true;
    private ArrayList<Question> questionSet;
    private BufferedReader br = null;
    private Player player;
    private int[] values = {50, 100, 200, 300, 500, 1000, 2000, 4000, 8000,
                            16000, 32000, 64000, 125000, 500_000, 1_000_000};
    private int value = 0;
    private static int position = 0;

    public static void main(String[] args) {
        Game game = new Game();

        int menuInputValue = game.gameWelcomeMenuPrint();
        game.gameModeSwitcher(menuInputValue);

        game.gameLoop();
    }

    private void gameLoop() {
        showTable();
        for (Question i : questionSet) {
            askQuestion(i);
            if (!correctAnswer(i, player)) {
                getValue(false);
                answerFeedback(false);
                endGame();
            }
            getValue(true);
            answerFeedback(true);
        }
    }

    /**
     * Calculate the amount of money.
     *
     * @param correctAnswer boolean
     */
    private void getValue(boolean correctAnswer) {
        if (!correctAnswer) {
            if (value < 500) {
                value = 0;
            } else if (value < 16000) {
                value = 500;
            } else {
                value = 16000;
            }
        } else {
            value = value + values[position];
            position++;
        }
        showTable();
        System.out.println("Your money : " + value);
    }

    /**
     * Show the price Table.
     */
    private void showTable() {
        System.out.format("+-----------------+----------+%n");
        System.out.format("| Values          | Position |%n");
        System.out.format("+-----------------+----------+%n");
        for (int i = values.length - 1; i >= 0; i--) {
            if (i == position) {
                System.out.printf("| %-15d | %-8s |%n", values[i], "<<<<<<<<");
            } else {
                System.out.printf("| %-15d | %-8s |%n", values[i], "");
            }
        }
        System.out.format("+-----------------+----------+%n");
    }


    /**
     * Print the Welcome Text and Game Menu.
     */
    public int gameWelcomeMenuPrint() {

        //GAME WELCOME MENUE
        System.out.println("############### HERZLICH WILLKOMMEN ZU ################");
        System.out.println("############### WER WIRD MILLIONÄR? ################");
        System.out.println("############### BY TEAM STARK ################");


        System.out.println("\n\n\nBITTE WÄHLEN SIE AUS");
        System.out.println("[1]  SPIEL STARTEN");
        System.out.println("[2]  SCORE BOARD");
        System.out.println("[3]  SPIEL BEENDEN");

        System.out.print("\n\n\nIHRE EINGABE: ");
        Scanner in = new Scanner(System.in);
        int input = in.nextInt();
        return  input;
    }

    /**
     * Switch between the different Game modes from Menu.
     *
     * @param input int
     */
    public  void gameModeSwitcher(int input) {
        switch (input) {
            case 1:
                startGame();
                break;
            case 2:
                showScoreBoard();
                break;
            default:
                endGame();
                break;
        }
    }

    /**
     * Prints out the Question and the choices for answering.
     *
     * @param question Question Object
     */
    public void askQuestion(Question question) {
        System.out.println(question.getQuestion());
        System.out.println("A: " + question.getAnswers()[0]);
        System.out.println("B: " + question.getAnswers()[1]);
        System.out.println("C: " + question.getAnswers()[2]);
        System.out.println("D: " + question.getAnswers()[3]);
    }

    /**
     * Terminate the Game.
     */
    public void endGame() {
        System.out.print("\n\n\n SPIEL BEENDET");
        System.exit(0);
    }


    /**
     * Show High Score Board.
     */
    public static void showScoreBoard() {
        System.out.print("\n\n\n SCORE BOARD");
    }

    /**
     * Start the Game.
     * Generates a number of question objects and saves them in questionSet.
     */
    public void startGame() {

        //Get Player Name
        System.out.print("BITTE GEBEN SIE IHREN NAMEN EIN: ");
        Scanner in = new Scanner(System.in);
        String name = in.nextLine();
        player = new Player(name);
        //Welcome Player with his Name
        System.out.println("HERZLICH WILLKOMMEN " + player.getName());

        // generate QuestionSet
        String path = "resources/Questions.csv";
        if (System.getProperty("os.name") == "Mac OS X") {
            path = "../" + path;
        }
        ArrayList<String> fileContent = readWholeFile(path);
        questionSet = Question.generateQuestionSet(pickRandomMember(fileContent, 3));

    }

    /**
     * This function reads in a whole file using a Buffered Reader.
     *
     * @param path The path of the file to be read.
     * @return ArrayList with each line of the file.
     */
    public ArrayList<String> readWholeFile(String path) {
        ArrayList<String> allLines = new ArrayList<>();
        String line;
        try {
            br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                allLines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allLines;
    }

    /**
     * Picks a number of random members out of an ArrayList and
     * returns an ArrayList with the picked members.
     *
     * @param originalList An ArrayList of type String
     * @param pickNumber The number of random members to pick
     * @return An ArrayList with the picked members
     */
    public ArrayList<String> pickRandomMember(ArrayList<String> originalList, int pickNumber) {
        ArrayList<String> newList = new ArrayList<>();
        for (int i = 0; i < pickNumber; i++) {
            int randomInt = ThreadLocalRandom.current().nextInt(0, originalList.size());
            newList.add(originalList.get(randomInt));
            originalList.remove(randomInt);
        }
        return newList;
    }

    private boolean correctAnswer(Question question, Player player) {
        return (question.getRightAnswer().equals(player.getPlayerAnswer()));
    }

    private void answerFeedback(boolean correctAnswer) {
        if (correctAnswer) {
            System.out.println("Ihre Antwort war richtig!");
        } else {
            System.out.println("Ihre Antwort war leider falsch!");
        }
    }
}