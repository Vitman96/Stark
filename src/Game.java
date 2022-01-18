import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;



public class Game {

    private boolean gameRunning = true;
    private ArrayList<Question> questionSet;
    private ArrayList<Player>  players;
    private BufferedReader br = null;
    private BufferedReader leaderBoardBuffer = null;
    private Player player;
    private int [] caseLevels = {100,200,400,500,1000,2000,5000,10000,20000,30000,50000,100000,200000,500000,1000000};
    private int currentLevel = 0;
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
            }else
            {
                player.cashLevel = caseLevels[currentLevel];
                currentLevel++;
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
        writeScoreBoard();
        System.exit(0);
    }


    /**
     * Show High Score Board.
     */
    public void showScoreBoard() {
        System.out.println("\n\n\n SCORE BOARD");
        //Load Score DB
        loadLoaderboard();





        for (Player pos : players) {
            System.out.println(pos.ranking + "\t" + pos.name + "\t" + pos.cashLevel);
        }

    }

    /**
     * Write new leaderboadr CSV with current Player
     * Generates a number of question objects and saves them in questionSet.
     */

    public void writeScoreBoard()
    {
        //load Player Rank
        loadLoaderboard();

        int ranking = 0;
        // calc current Player level
        for (Player pos : players) {
          if(player.cashLevel < pos.cashLevel)
          {
              player.ranking = pos.ranking;
              ranking = pos.ranking;
          }
        }

        players.add(player);



        try {
            File file = new File("../resources/Test.csv");
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            for (Player pos : players)
            {
                bw.write(pos.ranking+";"+pos.name+";"+pos.cashLevel);
                bw.newLine();
            }

            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
            br = new BufferedReader(new FileReader("../resources/Questions.csv"));
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

    private void loadLoaderboard(){
        try {
            leaderBoardBuffer = new BufferedReader(new FileReader("../resources/Leaderboard.csv"));
            String row = "";
            players = new ArrayList<>();
            while ((row = leaderBoardBuffer.readLine()) != null) {
                String[] data = row.split(";");
                int ranking = Integer.parseInt(data[1]);
                String playersName = data[2].toString();
                int cashLevel = Integer.parseInt(data[3]);
                players.add(new Player(playersName, cashLevel, ranking));

                // do something with the data
            }

            players.sort(Comparator.comparing(a -> a.ranking));
            leaderBoardBuffer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}