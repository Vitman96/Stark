import java.io.FileNotFoundException;
import java.util.Scanner;

public class Game {



    private static boolean gameRunning = true;
    private static boolean correctAnswer = true;


    public static void main(String[] args) {
        Game game = new Game();
        // MAYBE ERST DANN ERZEUGEN WENN SPIEL GESTARTET WIRD
        /*Player player = new Player();
        Question[] questions;*/



        int menuInputValue = gameWelcomeMenuPrint();
        gameModeSwitcher(menuInputValue);
        //START GAME
        /*while (game.isGameRunning()){
             // TODO

        }*/
    }

    /**
     * Print the Welcome Text and Game Menu.
     */
    public static int gameWelcomeMenuPrint() {

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
     * Switch between the diffrent Game modes from Menu.
     *
     * @param input int
     */
    public static void gameModeSwitcher(int input) {
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

    public static boolean isGameRunning() {
        return gameRunning;
    }


    /**
     * Prints out the Question and the choices for answering.
     *
     * @param question Question Object
     */
    public static void askQuestion(Question question) {
        System.out.println(question.getQuestion());
        System.out.println("A: " + question.getAnswers()[0]);
        System.out.println("B: " + question.getAnswers()[1]);
        System.out.println("C: " + question.getAnswers()[2]);
        System.out.println("D: " + question.getAnswers()[3]);
    }

    /**
     * Terminate the Game.
     */
    public static void endGame() {
        System.out.print("\n\n\n SPIEL BEENDET");
    }


    /**
     * Show High Score Board.
     */
    public static void showScoreBoard() {
        System.out.print("\n\n\n SCORE BOARD");
    }

    /**
     * Start the Game.
     */
    public static void startGame() {

        try {
            //Get Player Name
            System.out.print("BITTE GEBEN SIE IHREN NAMEN EIN: ");
            Scanner in = new Scanner(System.in);
            String name = in.nextLine();
            Player player = new Player(name);

            //Welcome Player with his Name
            System.out.println("HERZLICH WILLKOMMEN " + player.getName());

            //LOAD QUESTION
            Question question = new Question();

            //START GAME
            while (isGameRunning())
            {
                askQuestion(question);
                if(correctAnswer(question,player))
                {
                   question.setQuestionPos();
                }else
                {
                    gameRunning = false;
                }
            }
           System.out.println("ENDE");

        }catch (Exception e) {
            System.out.println("ERROR::"+e);
        }

    }

    private static boolean correctAnswer(Question question, Player player) {
        return (question.getRightAnswer().equals(player.getPlayerAnswer()));
    }

}