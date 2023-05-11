//package auds.aud9.day2;
//
//import java.util.*;
//
//class Candidate{
//    String city;
//    String code;
//    String name;
//    int age;
//
//    public Candidate(String city, String code, String name, int age) {
//        this.city = city;
//        this.code = code;
//        this.name = name;
//        this.age = age;
//    }
//
//    public String getCity() {
//        return city;
//    }
//
//    public void setCity(String city) {
//        this.city = city;
//    }
//
//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public int getAge() {
//        return age;
//    }
//
//    public void setAge(int age) {
//        this.age = age;
//    }
//
//    @Override
//    public String toString() {
//        return String.format("%s %s %d", code,name,age);
//    }
//}
//
//class Audition{
//    Map<String, Map<String, Candidate>> participantByCity;
//
//    public Audition() {
//        this.participantByCity = new HashMap<>();
//    }
//
//    public void addParticpant(String city, String code, String name, int age) {
//        Candidate candidate = new Candidate(city, code, name, age);
//
//        // something missing here
//
////        Map<String, Candidate> participantsInCity = participantByCity.get(city);
////        if(!participantByCity.containsKey(city)){
////            participantByCity.put(city, new HashMap<>());
////        }
//
//        participantByCity.putIfAbsent(city, new HashMap<>());
//        Map<String, Candidate> participantsInTheCity = participantByCity.get(city);
//        participantsInTheCity.putIfAbsent(code, candidate);
//    }
//
//    public void listByCity(String city){
//        Map<String, Candidate> participantsInTheCity = participantByCity.get(city);
//        Comparator<Candidate> comparator = Comparator.comparing(Candidate::getName);
//        // something here
//    }
//}
//
//public class AuditionTest {
//    public static void main(String[] args) {
//        Audition audition = new Audition();
//        List<String> cities = new ArrayList<String>();
//        Scanner scanner = new Scanner(System.in);
//        while (scanner.hasNextLine()) {
//            String line = scanner.nextLine();
//            String[] parts = line.split(";");
//            if (parts.length > 1) {
//                audition.addParticpant(parts[0], parts[1], parts[2],
//                        Integer.parseInt(parts[3]));
//            } else {
//                cities.add(line);
//            }
//        }
//        for (String city : cities) {
//            System.out.printf("+++++ %s +++++\n", city);
//            audition.listByCity(city);
//        }
//        scanner.close();
//    }
//}