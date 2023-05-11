package ispitni_re;

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
        return String.format("%s %s %d",code,name,age);
    }
}

class Audition{
    public Map<String,Map<String,Participant>> participantsMap;

    public Audition() {
        participantsMap = new HashMap<String,Map<String,Participant>>();
    }

    public void addParticpant(String city, String code, String name, int age) {
        Participant participant = new Participant(city,code,name,age);
        participantsMap.putIfAbsent(city,new HashMap<String,Participant>());
        participantsMap.computeIfPresent(city, (k,v) -> {
            v.putIfAbsent(code,participant);
            return v;
        });
    }


    public void listByCity(String city) {
        participantsMap.entrySet().stream()
                .filter(i -> i.getKey().equals(city))
                .forEach(i -> {
                    Map<String,Participant> map = i.getValue();
                    List<Participant> list = new ArrayList<Participant>();
                    map.entrySet().stream()
                            .forEach(j -> {
                                list.add(j.getValue());
                            });
                    list.stream()
                            .sorted(Comparator.comparing(Participant::getName)
                                    .thenComparing(Participant::getAge))
                            .forEach(System.out::println);
                });
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