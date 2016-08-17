package utility.swing.calendar;

import java.util.Hashtable;

/**
 * <pre>
 * Title:        MayoDayTypeDeterminer
 * Description:  Class to determine the mayo calendar status of a day (holiday, payday, color).
 * Copyright:    Copyright (c) 2002
 * Company:      <A HREF="http://www.mayo.edu">Mayo Clinic</A>
 *
 * Last update by $Author: ARMBRUST $
 * On $Date: 2002/03/21 21:13:04 $
 * </pre>
 *
 * The formulas for determining holiday, payday and color were ported fro Steven Monk's
 * javascript implementation of the mayo calendar
 *
 * @author <A HREF="mailto:armbrust.daniel@mayo.edu">Dan Armbrust</A>
 * @author Steven M. Monk
 * @version 1.0 $Revision: 1.2 $
 */

public class MayoDayTypeDeterminer
{
    public static final String HOLIDAY = "HOLIDAY";
    public static final String PAYDAY = "PAYDAY";
    public static final String BLUE = "BLUE";
    public static final String ORANGE = "ORANGE";

    private Hashtable years;

    /**
     * Constructor.  Used in the non-static use case.
     */
    public MayoDayTypeDeterminer()
    {
        years = new Hashtable();
    }

    /**
     * Method getType.  In this non static way of using the class, when a years worth of dates are
     * computed, they are stored in a hashtable for fast lookup.  If you want static access, see the
     * getTypeStatic method @see getTypeStatic(int, int) but the entire years worth of days are computed
     * with every call to the method.
     * @param year - The year of the date you want to know
     * @param dayOfYear - The 1 based day of the year (1 - 366) the type of
     * @return String - A string similar to "HOLIDAY|PAYDAY|BLUE" depending on the type of day.
     * For example -  if (str.indexOf MayoDayTypeDeterminer.HOLIDAY != -1) then it is a holiday.
     */
    public String getType(int year, int dayOfYear)
    {
        dayOfYear = dayOfYear - 1; //convert to 0 based
        if (years.get(new Integer(year)) == null)
        {
            years.put(new Integer(year), getDayArray(year));
        }
        return ((Day[]) years.get(new Integer(year)))[dayOfYear].toString();
    }

    /**
     * Method getTypeStatic.  Returns the type for a date.  Inefficent if you need to lookup more
     * than one date per year.  @see getType(int, int)
     * @param year - The year of the date you want to know
     * @param dayOfYear - The 1 based day of the year (1 - 366) the type of
     * @return String - A string similar to "HOLIDAY|PAYDAY|BLUE" depending on the type of day.
     * For example -  if (str.indexOf MayoDayTypeDeterminer.HOLIDAY != -1) then it is a holiday.
     */
    public static String getTypeStatic(int year, int dayOfYear)
    {
        dayOfYear = dayOfYear - 1; //convert to 0 based
        return getDayArray(year)[dayOfYear].toString();
    }

    /**
     * Method getType.  This is the code that was ported.  Following is the origional
     * documentation (with a few changes to reflect the porting to java....
     *
     *
     *    This code is used to calculate Orange/Blue days.  Its assumes
     *    six holidays a year: (New Yrs, Mem, 4th, Labor, Thanks, Xmas)
     *    With this assumption I reverse computed my base year (1600)
     *    From this base year I can establish the color a year starts.
     *
     *    I use 1600 as my base, because the algorythm only works for
     *    dates after the base, and because the base year needed to be
     *    divisible by 400. Year, 2000, was an option, but 1600 allows
     *    us to create some 20th century calendars.  1600 is only a base
     *    for calcultations, and should not imply the accuracy or
     *    existance of holidays, paydays, or the use of Orange/Blue
     *    days prior to 1950. (1950 was chosen out of thin air)
     *
     *    The pattern: (1st tip) Ignore weekends initialy, and apply
     *    orange/blue to them. You will see that the color a year starts
     *    with alternates every year. Except after a leap year, which is
     *    the same as the previous year.  Then apply minor adjustments.
     *    If the year starts on a Saturday. Change color, since new years
     *    holiday will be observed in previous year.  If the year starts
     *    on a Sunday, now account for the fact that weekends aren't
     *    orange or blue. Change color.
     *
     *    The day of the week a year starts with advances 1 day every
     *    year except the year after a leap year which advances 2 days.
     *
     *
     *    My Variables
     *    	ty   - Is the target year.
     *    	Yrs  - The years from base.
     *    	LYrs - The number of leap years from (including) the base year.
     *    	N    - Viewed as even or odd (Blue / Orange).
     *    	DoW  - Day of week (0-6)
     *    	DoY  - Day of year (0-365)
     * @param ty - The target year
     * @return Day[] - The array of days representing the year
     */
    public static Day[] getDayArray(int ty)
    {
        int Yrs = ty - 1600;

        double LYrs = Math.ceil(Yrs / 4.000) - Math.ceil(Yrs / 100.000) + Math.ceil(Yrs / 400.000);

        double DoW = (Yrs + LYrs + 6) % 7;
        Day[] allDays = new Day[366];
        int[] monthDays = new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
        int DoY = 0;
        double N = Yrs + LYrs; // even-Blue, odd-Orange
        // years that start on these days need to be adjusted.
        if (DoW == 0 || DoW == 6)
            N++;
        if (ty % 4 == 0 && (ty % 100 != 0 || ty % 400 == 0))
            monthDays[1] = 29;
        for (int m = 0; m <= 11; m++)
        {
            for (int d = 1; d <= monthDays[m]; d++)
            {
                allDays[DoY] = new Day(m, d, ((DoW + DoY) % 7));
                if ((DoY + DoW + 1) % 7 > 1 && !allDays[DoY].isHoliday)
                    allDays[DoY].isBlue = (N++ % 2 == 0);

                DoY++;
            }
        }

        // In 1600, if there was a payday, it was on the 10th.  Calculate forward from there.
        double fstPD = 14 - (10 + (LYrs * 366) + ((ty - LYrs - 1600) * 365)) % 14;

        //Now apply the payday to every 14th day in the allDays array
        for (int payDay = new Float(fstPD).intValue(); payDay < allDays.length; payDay = payDay + 14)
        {
            allDays[payDay - 1].isPayDay = true;  //convert from 1 based to 0 based
        }

        return allDays;
    }

    /**
     * Class to represent a day.  It's toString method will tell you if it is Blue, orange, a holiday
     * or a payday
     */

    public static class Day
    {
        private boolean isHoliday;
        private boolean isBlue;
        private boolean isPayDay;
        private int MoY;
        private int DoM;
        /**
         * Method Day.
         * @param month - The month of the year (0 based)
         * @param day - The day of the month
         * @param dow - The day of the week
         */
        public Day(int month, int day, double dow) // This is an object constructor
        {
            this.isHoliday = false;
            this.isBlue = true;
            this.isPayDay = false;
            this.MoY = month;
            this.DoM = day;
            switch (month)
            {
                case 0 :
                    if ((day == 1 && (dow + 1) % 7 > 1) || (day == 2 && dow == 1))
                        this.isHoliday = true;
                    break;
                case 4 :
                    if (day > 24 && dow == 1)
                        this.isHoliday = true;
                    break;
                case 6 :
                    if ((day == 3 && dow == 5) || (day == 4 && (dow + 1) % 7 > 1) || (day == 5 && dow == 1))
                        this.isHoliday = true;
                    break;
                case 8 :
                    if (day < 8 && dow == 1)
                        this.isHoliday = true;
                    break;
                case 10 :
                    if (dow == 4 && day > 21 && day < 29)
                        this.isHoliday = true;
                    break;
                case 11 :
                    if ((day == 24 && dow == 5) || (day == 25 && (dow + 1) % 7 > 1) || (day == 26 && dow == 1))
                        this.isHoliday = true;
                    if (day == 31 && dow == 5)
                        this.isHoliday = true;
                    break;
            }
        }

        /**
         * @return - A string representation of the days status aka "HOLIDAY|PAYDAY|ORANGE"
         */
        public String toString()
        {
            return (isHoliday ? HOLIDAY : "") + "|" + (isPayDay ? PAYDAY : "") + "|" + (isBlue ? BLUE : ORANGE);
        }
    }
}
