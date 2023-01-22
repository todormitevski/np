package aud7;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

class InvalidPickerArguments extends Exception{
    public InvalidPickerArguments(String message) {
        super(message);
    }
}

class FinalistPicker{
    int finalists;
    static Random RANDOM = new Random();

    public FinalistPicker(int finalists) {
        this.finalists = finalists;
    }

    public List<Integer> pick (int n) throws InvalidPickerArguments {
        if(n>finalists || n<=0){
            throw new InvalidPickerArguments("Invalid argument");
        }

        List<Integer> pickedFinalists = new ArrayList<>();

        return RANDOM.ints(2*n,1,finalists+1)
                .boxed() //converts to string of ints
                .distinct() //holds only unique
                .limit(n) //hold first n (limits)
                .collect(Collectors.toList()); //collects to list

//        while(pickedFinalists.size()!=n){
//            int pick = RANDOM.nextInt(finalists)+1; //+1 to shift from 0-29 to 1-30 for the nextInt range
//            if(!pickedFinalists.contains(pick)){
//                pickedFinalists.add(pick);
//            }
//        }
//        return pickedFinalists;
    }
}

public class FinalistTest {
    public static void main(String[] args) {
        FinalistPicker picker = new FinalistPicker(5);
        try {
            System.out.println(picker.pick(3));
        } catch (InvalidPickerArguments e) {
            System.out.println(e.getMessage());
        }
    }
}
