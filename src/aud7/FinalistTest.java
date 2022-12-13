package aud7;

import java.util.List;

class InvalidPickerArguments extends Exception{
    public InvalidPickerArguments(String message) {
        super(message);
    }
}

class FinalistPicker{
    int finalists;

    public FinalistPicker(int finalists) {
        this.finalists = finalists;
    }

    public List<Integer> pick(int n) throws InvalidPickerArguments {
        if(n>finalists)
            throw new InvalidPickerArguments("The number n cannot exceed the number of finalists");

    }
}

public class FinalistTest {
    public static void main(String[] args) {

    }
}
