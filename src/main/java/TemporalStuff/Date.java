package TemporalStuff;
/*
 * Brant Eckert, 11/13/20
 * Basic functions for a date.
 */
public class Date {
    int month = 0;
    int max_month = 12;
    int year = 0;
    int day = 0;
    int max_day = 30;
    String dateSpecial = "";

    public Date(int nmonth, int nday, int nyear){
        month = nmonth;
        day = nday;
        year = nyear;
    }

    public Date(int nmonth, int nday, int nyear, String ndateSpecial){
        this(nmonth, nday, nyear);
        dateSpecial = ndateSpecial;
    }

    public Date(int nmonth, int nday, int nyear, int nmaxM, int nmaxD){
        this(nmonth, nday, nyear);
        max_month = nmaxM;
        max_day = nmaxD;
    }
    public Date(int nmonth, int nday, int nyear, int nmaxM, int nmaxD, String ndateSpecial){
        this(nmonth, nday, nyear, ndateSpecial);
        max_month = nmaxM;
        max_day = nmaxD;
    }

    /**
     * Go to the next day.
     */
    public void advanceDay(){
        day++;
        if(day >= max_day){
            day = 1;
            month++;
            if(month >= max_month) {
                month = 1;
                year++;
            }
        }
    }

    /**
     * Translate the date into a string.
     * @return Datestring.
     */
    public String toString() {
        String end = month + "/" + day + "/" + year;
        if(!dateSpecial.equals(""))
            end += " " + dateSpecial;
        return end;
    }
}
