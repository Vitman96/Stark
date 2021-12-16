public class Question {

    private String question;    // The question to be asked
    private String[] answers = new String[4];   // The choices for answering
    private String rightAnswer;   // The right answer either a, b, c, or d
    String[] questions;
    private Scanner sc;
    private File file;

    public Question() {

    }

    public String getQuestion() {
        return this.question;
    }

    public String[] getAnswers() {
        return this.answers;
    }
}