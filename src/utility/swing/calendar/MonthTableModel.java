package utility.swing.calendar;

import java.util.*;
import javax.swing.table.*;

/**
 * <pre>
 * Title:        MonthTableModel
 * Description:  MonthTableModel for the calendar.
 * Copyright:    Copyright (c) 2002
 * Company:      <A HREF="http://www.mayo.edu">Mayo Clinic</A>
 *
 * Last update by $Author: ARMBRUST $
 * On $Date: 2002/03/21 21:13:05 $
 * </pre>
 *
 * @author <A HREF="mailto:armbrust.daniel@mayo.edu">Dan Armbrust</A>
 * @version 1.0 $Revision: 1.2 $
 * @see MonthPanel
 * @see CalendarDialog
 */
public class MonthTableModel extends AbstractTableModel {

	private final int numCols_ = 7;
	public final int numRows_; //this isn't known yet
	private String[] columnNames = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
	private int month_;
	private int year_;
	private int dayOfFirst_;
	private int length_;
	private int daysInPreviousMonth_;

	private Day[][] daysOfMonth_ = new Day[7][6];

	public MonthTableModel(boolean longColumnNames, int month, int year)
	{
		if (!longColumnNames)
			columnNames = new String[]{"Su", "M", "Tu", "W", "Th", "F", "Sa"};
		CalendarInfo calendarInfo = new CalendarInfo(month, year);
		dayOfFirst_ = calendarInfo.dayOfWeek;
		length_ = calendarInfo.numberOfDaysInMonth;
		daysInPreviousMonth_ = calendarInfo.numberOfDaysInPreviousMonth;
		month_ = month;
		year_ = year;


		int day = 1;
		int i = 0;
		int j = 0;

		for(; (i < 6); i++)
		{
			if (i == 0)
				j = dayOfFirst_ - 1;
			else
				j = 0;
			for (; ((j < 7) && (day <= length_)); j++)
			{
				GregorianCalendar tempGC = new GregorianCalendar(year_, month_, day++);
				daysOfMonth_[j][i] = new Day(tempGC);
			}
			if (day > length_)
				break;
		}
		numRows_ = i + 1;

		//Fill in the trailing days (first days of the next month)
		for (int k = 1; j < 7; j++)
		{
			GregorianCalendar tempGC = new GregorianCalendar(year_, month_ + 1, k++);
			daysOfMonth_[j][i] = new Day(tempGC, true);
		}

		//Fill in the leading days of the previous month
		for (int k = this.dayOfFirst_ - 2; k >=0; k--)
		{
			GregorianCalendar tempGC = new GregorianCalendar(year_, month_ - 1, daysInPreviousMonth_--);
			daysOfMonth_[k][0] = new Day(tempGC, true);
		}
	}

	public Object getValueAt(int rowIndex, int columnIndex)
	{
		return daysOfMonth_[columnIndex][rowIndex];
	}

	public int getColumnCount()
	{
	    return(numCols_);
	}

	public int getRowCount()
	{
	    return(numRows_);
	}

	public String getColumnName(int col)
	{
	    return(columnNames[col]);
	}
}
