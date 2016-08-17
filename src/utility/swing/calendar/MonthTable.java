package utility.swing.calendar;

import javax.swing.*;
import javax.swing.table.*;

/**
 * <pre>
 * Title:        MonthTable
 * Description:  The table used for building the calendar.
 * Copyright:    Copyright (c) 2002
 * Company:      <A HREF="http://www.mayo.edu">Mayo Clinic</A>
 *
 * Last update by $Author: armbrust $
 * On $Date: 2002/10/04 17:47:40 $
 * </pre>
 *
 * @author <A HREF="mailto:armbrust.daniel@mayo.edu">Dan Armbrust</A>
 * @version 1.0 $Revision: 1.4 $
 * @see MonthPanel
 * @see CalendarDialog
 */
public class MonthTable extends JTable {

    public MonthTable(boolean shortNames, int month, int year, DefaultTableCellRenderer renderer)
	{
		super(new MonthTableModel(shortNames, month, year));
		if (renderer == null)
			setDefaultRenderer(getColumnClass(0), new MonthTableCellRenderer());
		else
			setDefaultRenderer(getColumnClass(0), renderer);
		this.setRowSelectionAllowed(false);
		this.setCellSelectionEnabled(true);
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.getTableHeader().setReorderingAllowed(false);
		this.getTableHeader().setResizingAllowed(false);

	}

	public void repaint(int realHeight, long tm, int x, int y, int width, int height)  //I'm bringing in the real height, because the MonthTable.getHeight reports the wrong size when it is inside of a JScrollPane.
	{
		try
		{
			if((realHeight > ((MonthTableModel)this.getModel()).numRows_) && (this.getRowHeight() != (realHeight / ((MonthTableModel)this.getModel()).numRows_)))
			{
				int numRows = ((MonthTableModel)this.getModel()).numRows_;
				int newHeight = realHeight / numRows;
				setRowHeight(newHeight);
				setRowHeight(numRows - 1, (realHeight - (newHeight * numRows) + newHeight));  //set the height of the last row to take up the slack
			}
		}
		catch (NullPointerException e)
		{}  //it tries to paint before numRows has been set, which is bad.
		super.repaint(tm, x, y, width, height);
	}
}
