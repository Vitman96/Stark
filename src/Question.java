public class Question {

    private String question;    // The question to be asked
    private String[] answers = new String[4];   // The choices for answering
    private char rightAnswer;   // The right answer either a, b, c, or d

    public String getQuestion() {
        return this.question;
    }

    public String[] getAnswers() {
        return this.answers;
    }
}