import java.io.IOException;

/**
 * Question Class.
 */
public class Question {

    private String question;    // The question to be asked
    private String[] answers = new String[4];   // The choices for answering
    private String rightAnswer;   // The right answer either a, b, c, or d
    private static int counter = 1;
    private int id;


    public Question(java.io.BufferedReader br) {
        String[] temp = null;
        try {
            temp = br.readLine().split(";");
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.question = temp[0];
        this.rightAnswer = temp[5];
        for (int i = 0; i < 4; i++) {
            this.answers[i] = temp[i + 1];
        }
        this.id = counter;
        counter++;
    }

    /**
     *  reutrn current Question.
     */
    public String getQuestion() {
        return this.question;
    }

    /**
     *  reutrn all Answer option of an Question.
     */
    public String[] getAnswers() {
        return this.answers;
    }

    public String getRightAnswer() {
        return this.rightAnswer;
    }
}