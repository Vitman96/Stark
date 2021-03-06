import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;



public class Game {

    private boolean gameRunning = true;
    private ArrayList<Question> questionSet = new ArrayList<>();
    private ArrayList<Player>  players;
    private BufferedReader br = null;
    private BufferedReader leaderBoardBuffer = null;
    private Player player;
    private String leadBoardSrc = "resources/Leaderboard.csv";

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
            value = values[position];
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
        System.out.println("############### WER WIRD MILLION??R? ################");
        System.out.println("############### BY TEAM STARK ################");


        System.out.println("\n\n\nBITTE W??HLEN SIE AUS");
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
            System.out.printf("%-3d %-20s %-10d\n", pos.ranking, pos.name, pos.cashLevel);
        }
        System.exit(0);
    }

    /**
     * Write new leaderboadr CSV with current Player
     * Generates a number of question objects and saves them in questionSet.
     */

    public void writeScoreBoard() {
        //load Player Rank
        loadLoaderboard();

        int ranking = 0;

        player.cashLevel = value;
        // calc current Player level
        for (Player pos : players) {
            if (ranking == 0) {
                if (player.cashLevel > pos.cashLevel) {
                    player.ranking = pos.ranking;
                    ranking = pos.ranking + 1;
                    pos.ranking = ranking;
                }
            } else if (ranking != 0) {
                ranking = ranking + 1;
                pos.ranking = ranking;
            }
        }

        if (ranking == 0) {
            player.ranking = players.size();
        }



        players.add(player);




        try {
            String path = leadBoardSrc;
            if (System.getProperty("os.name").equals("Mac OS X")) {
                path = "../" + path;
            }
            File file = new File(path);
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            for (Player pos : players) {
                bw.write(pos.ranking + ";" + pos.name + ";" + pos.cashLevel);
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

        // generate QuestionSet
        String path = "resources/Questions.csv";
        if (System.getProperty("os.name").equals("Mac OS X")) {
            path = "../" + path;
        }
        ArrayList<String> fileContent = readWholeFile(path);
        questionSet = Question.generateQuestionSet(pickRandomMember(fileContent, 15));

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


    private void loadLoaderboard() {
        try {
            String path = leadBoardSrc;
            if (System.getProperty("os.name").equals("Mac OS X")) {
                path = "../" + path;
            }
            leaderBoardBuffer = new BufferedReader(new FileReader(path));
            String row = "";
            players = new ArrayList<>();
            while ((row = leaderBoardBuffer.readLine()) != null) {
                String[] data = row.split(";");
                int ranking = Integer.parseInt(data[0]);
                String playersName = data[1].toString();
                int cashLevel = Integer.parseInt(data[2]);
                players.add(new Player(playersName, cashLevel, ranking));

                // do something with the data
            }

            players.sort(Comparator.comparing(a -> a.ranking));
            leaderBoardBuffer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void answerFeedback(boolean correctAnswer) {
        if (correctAnswer) {
            System.out.println("Ihre Antwort war richtig!");
        } else {
            System.out.println("Ihre Antwort war leider falsch!");
        }
    }
}