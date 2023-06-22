//package ispitni_re.newExams;
//
//import java.io.OutputStream;
//import java.io.PrintStream;
//import java.io.PrintWriter;
//import java.util.*;
//
//class InvalidOperationException extends Exception{
//    public InvalidOperationException(String answer) {
//        super(answer);
//    }
//}
//
//interface Question{
//    String getText();
//    int getPoints();
//    String getAnswer();
//    int getI();
//    double calcPoints(String answer);
//}
//
//class MC implements Question{
//    String text;
//    int points;
//    String answer;
//    int i;
//
//    public MC(String text, int points, String answer, int i) {
//        this.text = text;
//        this.points = points;
//        this.answer = answer;
//        this.i = i;
//    }
//
//    @Override
//    public String getText() {
//        return text;
//    }
//
//    @Override
//    public int getPoints() {
//        return points;
//    }
//
//    @Override
//    public String getAnswer() {
//        return answer;
//    }
//
//    @Override
//    public int getI() {
//        return i;
//    }
//
//    @Override
//    public double calcPoints(String answer) {
//        if(this.answer.equals(answer))
//            return points;
//        else return -((double)points * 20 / 100);
//    }
//
//    @Override
//    public String toString() {
//        return String.format("Multiple Choice Question: %s Points %d Answer: %s",
//                text,points,answer);
//    }
//}
//
//class TF implements Question{
//    String text;
//    int points;
//    String answer;
//    int i;
//
//    public TF(String text, int points, String answer,int i) {
//        this.text = text;
//        this.points = points;
//        this.answer = answer;
//        this.i = i;
//    }
//
//    @Override
//    public String getText() {
//        return text;
//    }
//
//    @Override
//    public int getPoints() {
//        return points;
//    }
//
//    @Override
//    public String getAnswer() {
//        return answer;
//    }
//
//    @Override
//    public int getI() {
//        return i;
//    }
//
//    @Override
//    public double calcPoints(String answer) {
//        if(this.answer.equals(answer))
//            return points;
//        else return 0;
//    }
//
//    @Override
//    public String toString() {
//        return String.format("True/False Question: %s Points: %d Answer: %s",
//                text,points,answer);
//    }
//}
//
//class Quiz{
//    List<MC> mcQuestions;
//    List<TF> tfQuestions;
//    List<Question> allQuestions;
//    int i;
//
//    public Quiz() {
//        mcQuestions = new ArrayList<>();
//        tfQuestions = new ArrayList<>();
//        allQuestions = new ArrayList<>();
//        i = 1;
//    }
//
//    public void addQuestion(String questionData) throws InvalidOperationException {
//        Set<String> allowedAnswers = new HashSet<>(Arrays.asList("A","B","C","D","E"));
//        String[] parts = questionData.split(";");
//        if(parts[0].equals("MC")) {
//            if(!allowedAnswers.contains(parts[3])){
//                throw new InvalidOperationException(
//                        String.format("%s is not allowed option for this question",parts[3])
//                );
//            } else{
//                MC mcQuestion = new MC(parts[1],Integer.parseInt(parts[2]),parts[3],i++);
//                mcQuestions.add(mcQuestion);
//            }
//        } else{
//            TF tfQuestion = new TF(parts[1],Integer.parseInt(parts[2]),parts[3],i++);
//            tfQuestions.add(tfQuestion);
//        }
//    }
//
//    public void printQuiz(PrintStream os) {
//        PrintWriter pw = new PrintWriter(os);
//        allQuestions.addAll(mcQuestions);
//        allQuestions.addAll(tfQuestions);
//        allQuestions.stream()
//                .sorted(Comparator.comparing(Question::getPoints)
//                        .reversed()
//                        .thenComparing(Question::getI))
//                .forEach(pw::println);
//        pw.flush();
//    }
//
//    public void answerQuiz(List<String> answers, OutputStream os) throws InvalidOperationException {
//        PrintWriter pw = new PrintWriter(os);
//        allQuestions.addAll(mcQuestions);
//        allQuestions.addAll(tfQuestions);
//        if(answers.size() != allQuestions.size())
//            throw new InvalidOperationException("Answers and questions must be of same length!");
//        allQuestions.sort(Comparator.comparing(Question::getI));
//        double total = 0.0;
//        for(int i=0;i<answers.size();i++){
//            pw.println(String.format(
//                    "%d. %.2f",
//                    allQuestions.get(i).getI(),
//                    allQuestions.get(i).calcPoints(answers.get(i))
//            ));
//            total += allQuestions.get(i).calcPoints(answers.get(i));
//        }
//        pw.println(String.format("Total points: %.2f",total));
//        pw.flush();
//    }
//}
//
//public class QuizTest {
//    public static void main(String[] args) {
//
//        Scanner sc = new Scanner(System.in);
//
//        Quiz quiz = new Quiz();
//
//        int questions = Integer.parseInt(sc.nextLine());
//
//        for (int i=0;i<questions;i++) {
//            try {
//                quiz.addQuestion(sc.nextLine());
//            } catch (InvalidOperationException e) {
//                System.out.println(e.getMessage());
//            }
//        }
//
//        List<String> answers = new ArrayList<>();
//
//        int answersCount =  Integer.parseInt(sc.nextLine());
//
//        for (int i=0;i<answersCount;i++) {
//            answers.add(sc.nextLine());
//        }
//
//        int testCase = Integer.parseInt(sc.nextLine());
//
//        if (testCase==1) {
//            quiz.printQuiz(System.out);
//        } else if (testCase==2) {
//            try {
//                quiz.answerQuiz(answers, System.out);
//            } catch (InvalidOperationException e) {
//                System.out.println(e.getMessage());
//            }
//        } else {
//            System.out.println("Invalid test case");
//        }
//    }
//}
