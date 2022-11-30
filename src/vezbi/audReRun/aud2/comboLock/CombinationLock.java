package vezbi.audReRun.aud2.comboLock;

public class CombinationLock {
    private int combination;
    private boolean isOpen;
    private static int DEFAULT_COMBINATION = 100;

    public CombinationLock(int combination){
        this.combination = combination;
        if(isCombinationValid(combination))
            this.combination = combination;
        else this.combination = DEFAULT_COMBINATION;

        this.isOpen = false;
    }

    private boolean isCombinationValid(int combination){
        return combination >= 100 && combination <= 999;
    }

    public boolean open(int combination){
        this.isOpen = (this.combination == combination);
        return this.isOpen;
    }

    public boolean changeCombination(int oldCombination, int newCombination){
        if(open(oldCombination) && isCombinationValid(newCombination)){
            this.combination = newCombination;
            return true;
        }
        return false;
    }

    public void lock(){
        this.isOpen = false;
    }

    public static void main(String[] args) {
        CombinationLock validLock = new CombinationLock(234);

        System.out.println("TESTING isOpen()");
        System.out.println(validLock.isOpen);

        System.out.println("TESTING open()");
        System.out.println(validLock.open(233));
        System.out.println(validLock.open(236));
        System.out.println(validLock.open(234));

        validLock.lock();

        System.out.println("TESTING changeCombination()");
        System.out.println(validLock.changeCombination(234, 777));
        System.out.println(validLock.open(777));

        System.out.println("TESTING INVALID LOCK");
        CombinationLock invalidLock = new CombinationLock(234567);

        System.out.println("TESTING isOpen()");
        System.out.println(invalidLock.isOpen);

        System.out.println("TESTING open()");
        System.out.println(invalidLock.open(103));
        System.out.println(invalidLock.open(236));
        System.out.println(invalidLock.open(100));

        invalidLock.lock();

        System.out.println("TESTING changeCombination()");
        System.out.println(invalidLock.changeCombination(100, 900));
        System.out.println(invalidLock.open(900));
    }
}
