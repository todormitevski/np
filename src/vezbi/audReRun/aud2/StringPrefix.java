package vezbi.audReRun.aud2;

import java.util.stream.IntStream;

public class StringPrefix {
    public static boolean isPrefix(String str1, String str2){
        if(str1.length() > str2.length()) return false;

        //primitive data types: char int double float
        //can be compared with != instead of equals() method
        //wrapper classes Character Double Integer with equals()

        /*
        //java8 streams solution
        return IntStream.range(0, str1.length())
                .allMatch(i -> str1.charAt(i) == str2.charAt(i));
         */

        for(int i=0;i<str1.length();i++){
            if(str1.charAt(i) != str2.charAt(i))
                return false;
        }
        return true;
    }

    //.startsWith() also works
    public static void main(String[] args) {
        System.out.println(isPrefix("David", "DavidMonster"));
        System.out.println(isPrefix("David", "DavedMonster"));
        System.out.println(isPrefix("David", "DavadMonster"));
    }
}
