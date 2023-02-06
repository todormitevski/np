package kolokviumski;

import java.util.*;

class InvalidPositionException extends Exception{
    public InvalidPositionException(int position) {
        super(String.format("Invalid position %d, already taken!",position));
    }
}

class Component implements Comparable<Component>{
    String color;
    int weight;
    Set<Component> inner;

    public Component(String color, int weight) {
        this.color = color;
        this.weight = weight;
        inner = new TreeSet<Component>();
    }

    public static void createString(StringBuilder sb, Component component, int level) {
        for(int i=0;i<level;++i){
            sb.append("---");
        }
        sb.append(String.format("%d:%s\n",component.weight,component.color));
        for(Component c : component.inner){
            createString(sb,c,level + 1);
        }
    }

    void addComponent(Component component){
        inner.add(component);
    }

    void changeColor(int weight, String color){
        if(this.weight < weight){
            this.color = color;
        }
        for(Component comp : inner){
            change(comp,weight,color);
        }
    }

    static void change(Component component, int weight, String color){
        if(component.weight < weight){
            component.color = color;
        }
        for(Component c : component.inner){
            change(c,weight,color);
        }
    }

    @Override
    public int compareTo(Component o) {
        int resultingWeight = this.weight - o.weight;
        if(resultingWeight == 0) return this.color.compareTo(o.color); //.thenComparing()
        return resultingWeight;
    }
}

class Window{
    String name;
    Map<Integer,Component> components;

    public Window(String name) {
        this.name = name;
        components = new TreeMap<Integer,Component>();
    }

    void addComponent(int position, Component component) throws InvalidPositionException {
        if(!components.containsKey(position)){
            components.put(position,component);
        } else{
            throw new InvalidPositionException(position);
        }
    }

    void changeColor(int weight, String color){
        components.forEach((pos,comp) -> {
            comp.changeColor(weight,color);
        });
    }

    void switchComponents(int pos1, int pos2){
        Component tmp1 = components.get(pos1);
        Component tmp2 = components.get(pos2);
        components.put(pos1,tmp2);
        components.put(pos2,tmp1);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("WINDOW %s\n",name));
        for(Map.Entry<Integer,Component> entry : components.entrySet()){
            sb.append(String.format("%d:",entry.getKey()));
            Component.createString(sb,entry.getValue(),0);
        }
        return sb.toString();
    }
}

public class ComponentTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        Window window = new Window(name);
        Component prev = null;
        while (true) {
            try {
                int what = scanner.nextInt();
                scanner.nextLine();
                if (what == 0) {
                    int position = scanner.nextInt();
                    window.addComponent(position, prev);
                } else if (what == 1) {
                    String color = scanner.nextLine();
                    int weight = scanner.nextInt();
                    Component component = new Component(color, weight);
                    prev = component;
                } else if (what == 2) {
                    String color = scanner.nextLine();
                    int weight = scanner.nextInt();
                    Component component = new Component(color, weight);
                    prev.addComponent(component);
                    prev = component;
                } else if (what == 3) {
                    String color = scanner.nextLine();
                    int weight = scanner.nextInt();
                    Component component = new Component(color, weight);
                    prev.addComponent(component);
                } else if(what == 4) {
                    break;
                }

            } catch (InvalidPositionException e) {
                System.out.println(e.getMessage());
            }
            scanner.nextLine();
        }

        System.out.println("=== ORIGINAL WINDOW ===");
        System.out.println(window);
        int weight = scanner.nextInt();
        scanner.nextLine();
        String color = scanner.nextLine();
        window.changeColor(weight, color);
        System.out.println(String.format("=== CHANGED COLOR (%d, %s) ===", weight, color));
        System.out.println(window);
        int pos1 = scanner.nextInt();
        int pos2 = scanner.nextInt();
        System.out.println(String.format("=== SWITCHED COMPONENTS %d <-> %d ===", pos1, pos2));
        window.switchComponents(pos1, pos2);
        System.out.println(window);
    }
}
