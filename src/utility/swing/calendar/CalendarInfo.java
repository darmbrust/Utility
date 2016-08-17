package utility.swing.calendar;

import java.util.GregorianCalendar;

/**
 * <pre>
 * Title:        CalendarInfo
 * Description:  This class generates info about a month in a given year, useful for drawing a calendar.
 * Copyright:    Copyright (c) 2002
 * Company:      <A HREF="http://www.mayo.edu">Mayo Clinic</A>
 *
 * Last update by $Author: ARMBRUST $
 * On $Date: 2002/03/21 21:13:04 $
 * </pre>
 *
 * @author <A HREF="mailto:armbrust.daniel@mayo.edu">Dan Armbrust</A>
 * @version 1.0 $Revision: 1.2 $
 * @see MonthPanel
 * @see CalendarDialog
 */
public class CalendarInfo
{
	public final int dayOfWeek; //number (1-7) coresponding to what day of the week the first of the month falls on in this month
	public final int numberOfDaysInMonth;
	public final int numberOfDaysInPreviousMonth;

    public CalendarInfo(int month, int year)
	{
		GregorianCalendar monthView = new GregorianCalendar(year, month, 1);
		GregorianCalendar previousMonth = new GregorianCalendar(year, month - 1, 1);
		dayOfWeek = monthView.get(monthView.DAY_OF_WEEK);
		numberOfDaysInMonth = monthView.getActualMaximum(monthView.DAY_OF_MONTH);
		numberOfDaysInPreviousMonth = previousMonth.getActualMaximum(previousMonth.DAY_OF_MONTH);
    }
}
