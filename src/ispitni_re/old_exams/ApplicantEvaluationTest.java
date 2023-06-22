//package ispitni_re.old_exams;
//
//import java.util.Scanner;
//
///**
// * I partial exam 2016
// */
//
//class InvalidEvaluation extends Exception {}
//
//interface Evaluator {
//    enum TYPE {
//        NO_CRIMINAL_RECORD,
//        MORE_EXPERIENCE,
//        MORE_CREDIT_SCORE,
//        NO_CRIMINAL_RECORD_AND_MORE_EXPERIENCE,
//        MORE_EXPERIENCE_AND_MORE_CREDIT_SCORE,
//        NO_CRIMINAL_RECORD_AND_MORE_CREDIT_SCORE,
//        INVALID // should throw exception
//    }
//
//    boolean evaluate(Applicant applicant);
//}
//
//class EvaluatorBuilder {
//    public static Evaluator build(Evaluator.TYPE type) throws InvalidEvaluation {
//
//        // вашиот код овде
//        switch (type){
//            case NO_CRIMINAL_RECORD:
//                return new NoCriminalRecordEvaluator();
//            case MORE_EXPERIENCE:
//                return new MoreExperienceEvaluator();
//            case MORE_CREDIT_SCORE:
//                return new MoreCreditScoreEvaluator();
//            case NO_CRIMINAL_RECORD_AND_MORE_EXPERIENCE:
//                return new NoCriminalRecordAndMoreExperienceEvaluator();
//            case MORE_EXPERIENCE_AND_MORE_CREDIT_SCORE:
//                return new MoreExperienceAndMoreCreditScoreEvaluator();
//            case NO_CRIMINAL_RECORD_AND_MORE_CREDIT_SCORE:
//                return new NoCriminalRecordAndMoreCreditScoreEvaluator();
//            default:
//                throw new InvalidEvaluation();
//        }
//    }
//}
//
//// имплементација на евалуатори овде
//class NoCriminalRecordEvaluator implements Evaluator{
//
//    @Override
//    public boolean evaluate(Applicant applicant) {
//        return !applicant.hasCriminalRecord();
//    }
//}
//
//class MoreExperienceEvaluator implements Evaluator{
//
//    @Override
//    public boolean evaluate(Applicant applicant) {
//        return applicant.getEmploymentYears() >= 10;
//    }
//}
//
//class MoreCreditScoreEvaluator implements Evaluator{
//
//    @Override
//    public boolean evaluate(Applicant applicant) {
//        return applicant.getCreditScore() >= 500;
//    }
//}
//
//class NoCriminalRecordAndMoreExperienceEvaluator implements Evaluator{
//
//    @Override
//    public boolean evaluate(Applicant applicant) {
//        return !applicant.hasCriminalRecord() && applicant.getEmploymentYears() >= 10;
//    }
//}
//
//class MoreExperienceAndMoreCreditScoreEvaluator implements Evaluator{
//
//    @Override
//    public boolean evaluate(Applicant applicant) {
//        return applicant.getEmploymentYears() >= 10 && applicant.getCreditScore() >= 500;
//    }
//}
//
//class NoCriminalRecordAndMoreCreditScoreEvaluator implements Evaluator{
//
//    @Override
//    public boolean evaluate(Applicant applicant) {
//        return !applicant.hasCriminalRecord() && applicant.getCreditScore() >= 500;
//    }
//}
//
//class Applicant {
//    private String name;
//
//    private int creditScore;
//    private int employmentYears;
//    private boolean hasCriminalRecord;
//
//    public Applicant(String name, int creditScore, int employmentYears, boolean hasCriminalRecord) {
//        this.name = name;
//        this.creditScore = creditScore;
//        this.employmentYears = employmentYears;
//        this.hasCriminalRecord = hasCriminalRecord;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public int getCreditScore() {
//        return creditScore;
//    }
//
//    public int getEmploymentYears() {
//        return employmentYears;
//    }
//
//    public boolean hasCriminalRecord() {
//        return hasCriminalRecord;
//    }
//
//    @Override
//    public String toString() {
//        return String.format("Name: %s\nCredit score: %d\nExperience: %d\nCriminal record: %s\n",
//                name, creditScore, employmentYears, hasCriminalRecord ? "Yes" : "No");
//    }
//}
//
//public class ApplicantEvaluationTest {
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        String name = scanner.nextLine();
//        int creditScore = scanner.nextInt();
//        int employmentYears = scanner.nextInt();
//        boolean hasCriminalRecord = scanner.nextBoolean();
//        int choice = scanner.nextInt();
//        Applicant applicant = new Applicant(name, creditScore, employmentYears, hasCriminalRecord);
//        Evaluator.TYPE type = Evaluator.TYPE.values()[choice];
//        Evaluator evaluator = null;
//        try {
//            evaluator = EvaluatorBuilder.build(type);
//            System.out.println("Applicant");
//            System.out.println(applicant);
//            System.out.println("Evaluation type: " + type.name());
//            if (evaluator.evaluate(applicant)) {
//                System.out.println("Applicant is ACCEPTED");
//            } else {
//                System.out.println("Applicant is REJECTED");
//            }
//        } catch (InvalidEvaluation invalidEvaluation) {
//            System.out.println("Invalid evaluation");
//        }
//    }
//}
