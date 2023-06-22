package ispitni_re.new_exams;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

class InvalidOperationException extends Exception{
    public InvalidOperationException(String message) {
        super(message);
    }
}

abstract class Question{
    String text;
    int points;

    public Question(String text, int points) {
        this.text = text;
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    abstract double calcPoints(String answer);
}

class MCQuestion extends Question{
    String answer;

    public MCQuestion(String text, int points, String answer) {
        super(text, points);
        this.answer = answer;
    }

    @Override
    double calcPoints(String answer) {
        if(this.answer.equals(answer))
            return points;
        else return - points * 0.2;
    }

    @Override
    public String toString() {
        return String.format(
                "Multiple Choice Question: %s Points %d Answer: %s",
                text,points,answer
        );
    }
}

class TFQuestion extends Question{
    String answer;

    public TFQuestion(String text, int points, String answer) {
        super(text, points);
        this.answer = answer;
    }

    @Override
    double calcPoints(String answer) {
        if(this.answer.equals(answer))
            return points;
        else return 0;
    }

    @Override
    public String toString() {
        return String.format(
                "True/False Question: %s Points: %d Answer: %s",
                text,points,answer
        );
    }
}

class AnsweredQuestion{
    int id;
    static int idSeed = 1;
    double points;

    public AnsweredQuestion(double points) {
        this.id = idSeed++;
        this.points = points;
    }

    public int getId() {
        return id;
    }

    public double getPoints() {
        return points;
    }

    @Override
    public String toString() {
        return String.format("%d. %.2f",id,points);
    }
}

class Quiz{
    List<Question> questions;
    List<AnsweredQuestion> answeredQuestions;

    public Quiz() {
        questions = new ArrayList<>();
        answeredQuestions = new ArrayList<>();
    }

    public void addQuestion(String questionData) throws InvalidOperationException {
        String[] parts = questionData.split(";");
        String type = parts[0];
        String text = parts[1];
        int points = Integer.parseInt(parts[2]);
        String answer = parts[3];
        if(type.equals("MC")){
            List<String> possibleAnswers = List.of("A","B","C","D","E");
            if(!possibleAnswers.contains(answer))
                throw new InvalidOperationException(
                        String.format("%s is not allowed option for this question",answer)
                );
            questions.add(new MCQuestion(text,points,answer));
        } else {
            questions.add(new TFQuestion(text,points,answer));
        }
    }

    public void printQuiz(OutputStream os) {
        PrintWriter pw = new PrintWriter(os);
        questions.stream()
                .sorted(Comparator.comparing(Question::getPoints)
                        .reversed())
                .forEach(pw::println);
        pw.flush();
    }

    public double calcTotalPointsFromAnswered(){
        return answeredQuestions.stream()
                .mapToDouble(AnsweredQuestion::getPoints)
                .sum();
    }

    public void answerQuiz(List<String> answers, OutputStream os) throws InvalidOperationException {
        if(answers.size() != questions.size())
            throw new InvalidOperationException("Answers and questions must be of same length!");

        for(int i=0;i< questions.size();i++)
            answeredQuestions.add(new AnsweredQuestion(questions.get(i).calcPoints(answers.get(i))));

        PrintWriter pw = new PrintWriter(os);
        answeredQuestions.forEach(pw::println);
        pw.printf("Total points: %.2f",calcTotalPointsFromAnswered());
        pw.flush();
    }
}

public class QuizTestRe {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Quiz quiz = new Quiz();

        int questions = Integer.parseInt(sc.nextLine());

        for (int i=0;i<questions;i++) {
            try {
                quiz.addQuestion(sc.nextLine());
            } catch (InvalidOperationException e) {
                System.out.println(e.getMessage());
            }
        }

        List<String> answers = new ArrayList<>();

        int answersCount =  Integer.parseInt(sc.nextLine());

        for (int i=0;i<answersCount;i++) {
            answers.add(sc.nextLine());
        }

        int testCase = Integer.parseInt(sc.nextLine());

        if (testCase==1) {
            quiz.printQuiz(System.out);
        } else if (testCase==2) {
            try {
                quiz.answerQuiz(answers, System.out);
            } catch (InvalidOperationException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Invalid test case");
        }
    }
}
