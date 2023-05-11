package auds.aud9;

import java.util.*;

class Participant{
    String city;
    String code;
    String name;
    int age;

    public Participant(String city, String code, String name, int age) {
        this.city = city;
        this.code = code;
        this.name = name;
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return String.format("%s %s %d", code,name,age);
    }


}

class Audition{
    Map<String, Map<String,Participant>> participantsByCity;

    Audition(){
        participantsByCity = new HashMap<>();
    }

    public void addParticpant(String city, String code, String name, int age) {
        Participant participant = new Participant(city, code, name, age);

//        if(!participantsByCity.containsKey(city))
//            participantsByCity.put(city,new HashMap<>());

        //Map<String,Participant> participantsInTheCity = participantsByCity.get(city);
//        if(!participantsInTheCity.containsKey(code))
//            participantsInTheCity.put(code,participant);

        participantsByCity.putIfAbsent(city,new HashMap<>());
        participantsByCity.get(city).putIfAbsent(code,participant); //java 8
    }

    public void listByCity(String city) {
        Comparator<Participant> comparator = Comparator.comparing(Participant::getName).thenComparing(Participant::getAge);
        //if same, then compare by age ^

        participantsByCity.get(city).values().stream()
                .sorted(comparator)
                .forEach(participant -> System.out.println(participant));
    }
}

public class AuditionTest {
    public static void main(String[] args) {
        Audition audition = new Audition();
        List<String> cities = new ArrayList<String>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            if (parts.length > 1) {
                audition.addParticpant(parts[0], parts[1], parts[2],
                        Integer.parseInt(parts[3]));
            } else {
                cities.add(line);
            }
        }
        for (String city : cities) {
            System.out.printf("+++++ %s +++++\n", city);
            audition.listByCity(city);
        }
        scanner.close();
    }
}