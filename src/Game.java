public class Game {



    private boolean gameRunning = true;
    private boolean correctAnswer = true;

    public static void main(String[] args) {
        Game game = new Game();
        Player player = new Player();
        Question[] questions;

        while (game.isGameRunning()){

        }
    }

    public boolean isGameRunning() {
        return gameRunning;
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

    public boolean isCorrectAnswer(Question question, Player player) {
        return correctAnswer = (question.getRightAnswer() == player.getPlayerAnswer());
    }
}