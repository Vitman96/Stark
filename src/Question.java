import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Question {

    private String question;    // The question to be asked
    private String[] answers = new String[4];   // The choices for answering
    private String rightAnswer;   // The right answer either a, b, c, or d
    String[] questions;
    private Scanner sc;
    private File file;

    public Question() throws FileNotFoundException {
        file = new File("C:\\Users\\User\\Desktop\\Just\\resource\\Questions.csv");
        sc = new Scanner(file);
        questions = sc.nextLine().split(";");
    }

    public String getQuestion() {
        question = questions[0]; // sets the value of question
        return this.question;
    }

    public String[] getAnswers() {
        for (int i = 0; i < 4; i++) {
            answers[i] = questions[i + 1];
        }
        return this.answers;
    }

    public String getRightAnswer() {
        rightAnswer = questions[questions.length - 1]; // sets the value of right answer
        return this.rightAnswer;
    }
}