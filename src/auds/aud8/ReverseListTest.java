package auds.aud8;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ReverseListTest {
    public static <T> void reverseList(Collection<T> collection){
        List<T> list = new ArrayList<>(collection);
        for(int i=list.size()-1;i>=0;i--){ // reverse order
            System.out.println(list.get(i));
        }
    }

    // functional way
    public static <T> void reverseList1(Collection<T> collection){
        List<T> list = new ArrayList<>(collection);
        Collections.reverse(list);
        list.forEach(System.out::println);
    }

    public static void main(String[] args) {
        List<Integer> ints = List.of(1,2,3,4,5,6,7,8,9,10);
        reverseList(ints);
        reverseList1(ints);
    }
}
