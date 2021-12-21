import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Question Class.
 */
public class Question {

    //private String question;    // The question to be asked
    private List<String> question;    // The question to be asked
    private String[] answers = new String[4];   // The choices for answering
    private String rightAnswer;   // The right answer either a, b, c, or d
    List<List<String>> questions = new ArrayList<>();
    private int questionPos = 1;
    private Scanner sc;
    private File file;

    public Question() throws FileNotFoundException {
        //file = new File("C:\\Users\\User\\Desktop\\Just\\resource\\Questions.csv");
        file = new File("../resources/Questions.csv");
        sc = new Scanner(file);
        while (sc.hasNextLine()) {
            questions.add(getRecordFromLine(sc.nextLine()));
        }
        //questions = sc.nextLine().split(";");


    }

    /**
     *  reutrn all CSV Question and answers.
     */
    private List<String> getRecordFromLine(String line) {
        List<String> values = new ArrayList<String>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(";");
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        return values;
    }

    /**
     *  reutrn current Question.
     */
    public String getQuestion() {
        question = questions.get(questionPos); // sets the value of question
        return this.question.get(0).toString();
    }

    /**
     *  reutrn all Answer option of an Question.
     */

    public String[] getAnswers() {
        for (int i = 0; i < 4; i++) {
            //answers[i] = questions[i + 1];
            answers[i]  = questions.get(questionPos).get(i + 1).toString();

        }
        return this.answers;
    }

    public void setQuestionPos() {
        this.questionPos++;
    }

    public String getRightAnswer() {
        rightAnswer = questions.get(questionPos).get(5).toString(); // sets the value of right answer
        return this.rightAnswer;
    }
}