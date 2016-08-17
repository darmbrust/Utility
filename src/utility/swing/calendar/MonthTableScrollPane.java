package utility.swing.calendar;

import javax.swing.*;
import java.awt.*;

/**
 * <pre>
 * Title:        MonthTableScrollPane
 * Description:  ScrollPane to put the calendar table in, so the headers appear.
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
public class MonthTableScrollPane extends JScrollPane
{
	private MonthTable table;

    public MonthTableScrollPane(Component view, int vsbPolicy, int hsbPolicy)
	{
		super(view, vsbPolicy, hsbPolicy);
		table = (MonthTable)view;
    }

	//Have to override this, so that the scroll pane height can be sent to the custom height method in MonthTable
	public void repaint(long a, int b, int c, int d, int e)
	{
		if (table != null)
		{
		    table.repaint(this.getHeight() - 25, a, b, c, d, e);  //25 is the space it is counting for the scroll bars
		}
		super.repaint(a, b, c, d, e);
	}

}