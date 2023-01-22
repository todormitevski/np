package aud8;

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
    public static int count1(Collection<Collection<String>> c, String str){
        int br = 0;
        for(Collection<String> collection: c){
            for(String element: collection){
                if(element.equalsIgnoreCase(str))
                    br++;
            }
        }
        return br;
    }

    // functional programming way (streams)
    private static int count2(Collection<Collection<String>> c, String str){
        return (int) c.stream()
                .flatMap(each -> each.stream())
                .filter(string -> string.equalsIgnoreCase(str))
                .count();
    }

    public static void main(String[] args) {
        // yeah
    }
}
