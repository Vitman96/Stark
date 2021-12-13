public class Game{

    /**
     * Prints out the Question and the choices for answering
     * @param question
     */
    public void askQuestion(Question question){
        System.out.println(question.getQuestion());
        System.out.println("A: " + question.getAnswers[0]);
        System.out.println("B: " + question.getAnswers[1]);
        System.out.println("C: " + question.getAnswers[2]);
        System.out.println("D: " + question.getAnswers[3]);
    }
}