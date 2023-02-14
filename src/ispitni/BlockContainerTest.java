package ispitni;

import java.util.*;
import java.util.stream.Collectors;

class BlockContainer<T extends Comparable<T>>{
    int size;
    List<Set<T>> block;

    public BlockContainer(int size) {
        this.size = size;
        block = new ArrayList<Set<T>>();
    }

    public void add(T a){
        if(block.size() == 0){
            Set<T> set = new TreeSet<T>();
            set.add(a);
            block.add(set);
        } else{
            Set<T> set = block.get(block.size()-1); //last set

            if(set.size() < size){
                set.add(a);
            } else{
                set = new TreeSet<T>();
                set.add(a);
                block.add(set);
            }
        }
    }

    public boolean remove(T a){
        boolean successfulRemoval = false;
        if(block.size() != 0){
            Set<T> set = block.get(block.size() - 1);
            successfulRemoval = set.remove(a);
            if(set.size() == 0){
                block.remove(block.size() - 1);
            }
        }
        return successfulRemoval;
    }

    public void sort(){
        List<T> sortedList = new ArrayList<T>();
        for(int i=0;i<block.size();++i){
            Iterator<T> iterator = block.get(i).iterator();
            while(iterator.hasNext()){
                sortedList.add(iterator.next());
            }
        }
        Collections.sort(sortedList);
        block = new ArrayList<Set<T>>();
        for(T element : sortedList){
            add(element);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<block.size();++i){
            sb.append(block.get(i).toString());
            if(i < block.size() - 1){
                sb.append(",");
            }
        }
        return sb.toString();
    }
}

public class BlockContainerTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int size = scanner.nextInt();
        BlockContainer<Integer> integerBC = new BlockContainer<Integer>(size);
        scanner.nextLine();
        Integer lastInteger = null;
        for(int i = 0; i < n; ++i) {
            int element = scanner.nextInt();
            lastInteger = element;
            integerBC.add(element);
        }
        System.out.println("+++++ Integer Block Container +++++");
        System.out.println(integerBC);
        System.out.println("+++++ Removing element +++++");
        integerBC.remove(lastInteger);
        System.out.println("+++++ Sorting container +++++");
        integerBC.sort();
        System.out.println(integerBC);
        BlockContainer<String> stringBC = new BlockContainer<String>(size);
        String lastString = null;
        for(int i = 0; i < n; ++i) {
            String element = scanner.next();
            lastString = element;
            stringBC.add(element);
        }
        System.out.println("+++++ String Block Container +++++");
        System.out.println(stringBC);
        System.out.println("+++++ Removing element +++++");
        stringBC.remove(lastString);
        System.out.println("+++++ Sorting container +++++");
        stringBC.sort();
        System.out.println(stringBC);
    }
}
