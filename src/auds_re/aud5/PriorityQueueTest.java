package auds_re.aud5;

import java.util.ArrayList;
import java.util.List;

class PriorityQueueElement<T> implements Comparable<PriorityQueueElement<T>>{
    T element;
    int priority;

    public PriorityQueueElement(T element, int priority) {
        this.element = element;
        this.priority = priority;
    }

    public T getElement() {
        return element;
    }

    public int getPriority() {
        return priority;
    }

    public void setElement(T element) {
        this.element = element;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "PriorityQueueElement{" +
                "element=" + element +
                ", priority=" + priority +
                '}';
    }

    @Override
    public int compareTo(PriorityQueueElement<T> o) {
        return Integer.compare(this.priority, o.priority);
    }
}

class PriorityQueue<T>{
    List<PriorityQueueElement<T>> elements;

    public PriorityQueue() {
        elements = new ArrayList<>();
    }

    public void add(T item, int priority){
        PriorityQueueElement<T> newElem = new PriorityQueueElement<>(item,priority);
        int i = 0;
        for(i=0;i< elements.size();i++){
            if(newElem.compareTo(elements.get(i)) <= 0) break;
        }
        elements.add(i, newElem);
    }

    public T remove(){
        if(elements.size() == 0) return null;
        return elements.remove(elements.size() - 1).getElement();
    }
}

public class PriorityQueueTest{
    public static void main(String[] args) {
        PriorityQueue<String> priorityQueue = new PriorityQueue<>();
        priorityQueue.add("middle1",49);
        priorityQueue.add("middle2",50);
        priorityQueue.add("middle3",51);
        priorityQueue.add("top",100);
        priorityQueue.add("bottom",10);
        String element;
        while((element = priorityQueue.remove()) != null)
            System.out.println(element);
    }
}
