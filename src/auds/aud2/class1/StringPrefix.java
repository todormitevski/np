package auds.aud2.class1;

import java.util.stream.IntStream;

public class StringPrefix {
    public static boolean isPrefix(String first, String second){
        if(first.length()>second.length()){
            return false;
        }
        for(int i=0;i<first.length();i++){
            //primitivni se int, double, char, float...
            if(first.charAt(i)!=second.charAt(i)){ //!= oti e char
                return false;
            }
        }
        return true;
    }

    //java8 streams solution
    public static boolean isPrefixStream(String str1, String str2){
        if(str1.length()>str2.length()){
            return false;
        }
        return IntStream.range(0,str1.length())
                .allMatch(i->str1.charAt(i)==str2.charAt(i));
    }

    public static void main(String[] args) {
        System.out.println(isPrefix("test","testing"));
        System.out.println(isPrefix("test","apple"));
        System.out.println(isPrefix("test","Hanamura"));

        System.out.println(isPrefixStream("Test", "Tester"));
    }
}
