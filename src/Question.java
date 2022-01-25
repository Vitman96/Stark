import java.io.IOException;

/**
 * Question Class.
 */
public class Question {

    private String question;                    // The question to be asked
    private String[] answers = new String[4];   // The choices for answering
    private String rightAnswer;                 // The right answer either a, b, c, or d
    private static int counter = 1;             // What number this question ist
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

    public static ArrayList<Question> generateQuestionSet(ArrayList<String> questionList) {
        ArrayList<Question> questionSet = new ArrayList<>();
        for (int i = 0; i < questionList.size(); i++) {
            questionSet.add(new Question(questionList.get(i)));
        }
        return questionSet;
    }

    /**
     *  return current Question.
     */
    public String getQuestion() {
        return this.question;
    }

    /**
     *  return all Answer option of a Question.
     */
    public String[] getAnswers() {
        return this.answers;
    }

    public String getRightAnswer() {
        return this.rightAnswer;
    }
}