package vezbi.audReRun.aud2;

public class RefactoringExample {

    public int countAllNumbersDivisibleWithDigitsSum(int start, int end){
        int count = 0;

        for(int i=start;i<=end;i++){

            if(i % getSumOfDigits(i) == 0)
                count++;
        }
        return count;
    }

    private static int getSumOfDigits(int num) {
        int sum = 0;
        while(num > 0){
            sum += num % 10;
            num /= 10;
        }
        return sum;
    }
}
