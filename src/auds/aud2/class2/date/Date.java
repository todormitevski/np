package auds.aud2.class2.date;

import java.util.Objects;

public class Date implements Comparable<Date>{
    private static final int FIRST_YEAR=1800; //FINAL cannot be changed (constant)
    private static final int LAST_YEAR=2500;
    private static final int DAYS_IN_YEAR=365;

    private static final int[] daysofMonth={31,28,31,30,31,30,31,31,30,31,30,31};
    private static final int[] daysTillFirstOfMonth;
    private static final int[] daysTillFirstOfYear;

    private int days;

    //STATIC BLOCK, used for initialization for static variables
    static{
        daysTillFirstOfMonth=new int[12];
        for(int i=1;i<=12;i++){
            daysTillFirstOfMonth[i]=daysTillFirstOfMonth[i-1]+daysofMonth[i-1];
        }

        int totalYears=LAST_YEAR-FIRST_YEAR+1;
        daysTillFirstOfYear=new int[totalYears];
        int currentYear=FIRST_YEAR;

        for(int i=1;i<totalYears;i++){
            daysTillFirstOfYear[i]=daysTillFirstOfYear[i-1]+DAYS_IN_YEAR+1;
            if(isLeapYear(currentYear)) {
                daysTillFirstOfYear[i]++;
            }
            currentYear++;
        }
    }

    public Date(int days) {
        this.days = days;
    }

    public Date(int day, int month, int year){
        if(isDateInvalid(year))
            throw new RuntimeException();
        int days=0;
        days+=daysTillFirstOfYear[year-FIRST_YEAR];
        days+=daysTillFirstOfMonth[month-1];
        if(isLeapYear(year) && month>=2)
            days++;
        days+=day;
        this.days=days;
    }

    private boolean isDateInvalid(int year){
        return year<FIRST_YEAR||year>LAST_YEAR;
    }

    private static boolean isLeapYear(int year){
        return (year%400==0 || (year%4==0 && year%100!=0));
    }

    public Date increment(int days){
        return new Date(this.days+days);
    }

    public int subtract(Date date){
        return Math.abs(this.days-date.days);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Date date = (Date) o;
        return days == date.days;
    }

    @Override
    public int hashCode() {
        return Objects.hash(days);
    }


    @Override
    public int compareTo(Date o) {
        return Integer.compare(this.days,o.days); //this. always on the left side
    }

    @Override
    public String toString() {
        int allDays=days;
        int i;
        for(i=0;i<daysTillFirstOfYear.length;i++){ //i to total years
            if(daysTillFirstOfYear[i]>=allDays) break;
        }

        allDays-=daysTillFirstOfYear[i-1];
        int year=i-1+FIRST_YEAR;
        if(isLeapYear(year))
            allDays--;

        for(i=0;i<daysTillFirstOfMonth.length;i++){
            if(daysTillFirstOfMonth[i]>=allDays) break;
        }

        int month=i;
        allDays-=daysTillFirstOfMonth[i-1];

        return String.format("%02d.%02m.%4d", allDays, month, year);
    }
}
