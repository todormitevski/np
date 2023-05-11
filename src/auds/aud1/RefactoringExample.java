package auds.aud1;

public class RefactoringExample {
    //original code
//    public int countAllNumberDivisibleWithDigitsSum(int start,int end){
//        int count=0;
//
//        for(int i=start;i<=end;i++){
//            int sumOfDigits=0;
//            int temp=i;
//
//            while(temp>0){
//                sumOfDigits+=(temp%10);
//                temp/=10;
//            }
//
//            if(i%sumOfDigits==0){
//                count++;
//            }
//        }
//        return count;
//    }

    //refactored
    public int countAllNumberDivisibleWithDigitsSum(int start,int end){
        int count=0;

        for(int i=start;i<=end;i++){
            if(i%getSumOfDigits(i)==0){
                count++;
            }
        }
        return count;
    }

    private static int getSumOfDigits(int num) {
        int sum=0;
        while(num>0){
            sum +=(num%10);
            num/=10;
        }
        return sum;
    }
}
