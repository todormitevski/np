package aud2.class2.lock;

public class CombinationLock {
    private int combination;
    private boolean isOpen;
    private static int DEFAULT_COMBINATION=100;

    public CombinationLock(int combination){
        if (isCombinationValid(combination)) {
            this.combination = combination;
        } else this.combination=DEFAULT_COMBINATION; //if an invalid combination is set, opens with default 100
        this.isOpen=false;
    }

    private boolean isCombinationValid(int combination){
        return combination>=100 && combination<=999; //needs to be a 3-digit code
    }

    public boolean open(int combination){
        this.isOpen=(this.combination==combination); //if the correct combination is entered, unlock
        return this.isOpen;
    }

    public boolean changeCombination(int oldCombination, int newCombination){
        if(open(oldCombination) && isCombinationValid(newCombination)){
            this.combination=newCombination;
            return true;
        }
        return false;
    }

    public boolean isOpen(){ //check if open
        return isOpen;
    }

    public void lock(){ //locks it
        this.isOpen=false;
    }

    public static void main(String[] args) {
        CombinationLock validLock=new CombinationLock(234);
        CombinationLock invalidLock=new CombinationLock(11111);

        System.out.println("TEST isOpen");
        System.out.println(validLock.isOpen());

        System.out.println("TEST open");
        System.out.println(validLock.open(233));
        System.out.println(validLock.open(236));
        System.out.println(validLock.open(234));
        validLock.lock();

        System.out.println("TEST changeCombination");
        System.out.println(validLock.changeCombination(234,777));
        System.out.println(validLock.open(777));

        //TESTING INVALIDLOCK

        System.out.println("TEST isOpen");
        System.out.println(invalidLock.isOpen());

        System.out.println("TEST open");
        System.out.println(invalidLock.open(103));
        System.out.println(invalidLock.open(236));
        System.out.println(invalidLock.open(100));
        invalidLock.lock();

        System.out.println("TEST changeCombination");
        System.out.println(invalidLock.changeCombination(100,900));
        System.out.println(invalidLock.open(900));
    }
}
