package auds.aud8;

/*
Да се имплементира следниот метод коj го враќа бројот
на појавување на стрингот str во колекцијата од колекција
од стрингови:

public static int count(Collection<Collection<String>> c,
String str)
Да претпоставиме дека Collection c содржи N
колекции и дека секоја од овие колекции содржи N
објекти. Кое е времето на извршување на вашиот метод?
// O(n) = n^2
Да претпоставиме дека е потребно 2 милисекунди да се
изврши за N = 100. Колку ќе биде времето на извршување
кога N = 300?
 */

import java.util.Collection;

public class CountOccurrencesTest {
    public static int count(Collection<Collection<String>> c, String str){
        int counter = 0;
        for(int i=0;i<c.size();i++){
            for(Collection<String> collection : c){
                for(String elem : collection){
                    if(elem.equals(str)) counter++;
                }
            }
        }
        return counter;
    }

    public static int streamCount(Collection<Collection<String>> c, String str){
        return (int) c.stream()
                .flatMap(collection -> collection.stream())
                .filter(string -> string.equalsIgnoreCase(str))
                .count();
    }

    public static void main(String[] args) {
        //...
    }
}
