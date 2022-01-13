import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    private boolean gameRunning = true;
    private ArrayList<Question> questionSet;
    private BufferedReader br = null;
    private Player player;


    public static void main(String[] args) {
        Game game = new Game();

        int menuInputValue = game.gameWelcomeMenuPrint();
        game.gameModeSwitcher(menuInputValue);

        game.gameLoop();
    }

    private void gameLoop() {
        for (Question i : questionSet) {
            askQuestion(i);
            if (!correctAnswer(i, player)) {
                endGame();
            }
        }
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

        try {
            br = new BufferedReader(new FileReader("resources/Questions.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // generate QuestionSet
        questionSet = new ArrayList();
        for (int i = 0; i < 3; i++) {
            questionSet.add(new Question(br));
        }

        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean correctAnswer(Question question, Player player) {
        return (question.getRightAnswer().equals(player.getPlayerAnswer()));
    }
}