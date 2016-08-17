package utility.swing.calendar;

import java.util.GregorianCalendar;

/**
 * <pre>
 * Title:        Day
 * Description:  This class is what is held in each day of a calendar.
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
public class Day
{

	private GregorianCalendar date_;
	private boolean nonCurrentMonth_;

	public Day(GregorianCalendar date)
	{
	    date_ = date;
		nonCurrentMonth_ = false;
	}

	public Day(GregorianCalendar date, boolean nonCurrentMonth)
	{
	    date_ = date;
		nonCurrentMonth_ = nonCurrentMonth;
	}


	public GregorianCalendar getDate()
	{
	    return(date_);
	}

	public String getDay()
	{
		return date_.get(date_.DAY_OF_MONTH) + "";
	}

	public String getMonth()
	{
		return (date_.get(date_.MONTH) + 1) + "";
	}

	public String getYear()
	{
		return date_.get(date_.YEAR) + "";
	}

	public boolean currentMonth()
	{
		return !nonCurrentMonth_;
	}

	public String toString()
	{
	    String s = new String();
	    if(date_ != null)
		{
	      s = String.valueOf(date_.get(GregorianCalendar.DATE));
	    }
	    return(s);
	}
}