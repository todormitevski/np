package aud1;

public class FirstClass {
    //access modifiers:
    // private, protected, package(default), public
    //from most to least restrictive

//    private static int a;
//    private double b;

    //static: edno za cela klasa
    //koga se menuva a, za site obj ke se smeni
    //pri izmeni na b, ne se menuva sekade
//    public static int rand(){
//        return 3;
//    }

    public static void main(String[] args) {
        //System e klasa (golema bukva)
        //out e field vo podatocna klasa
//        FirstClass fc=new FirstClass();
//        System.out.println(fc.a);
        System.out.println("Hi! I'm the println method");
        System.out.print("Sup. I'm the print method");
        System.out.print(", and I have no next line");
        System.out.printf("\n%d", 55);
    }
}
