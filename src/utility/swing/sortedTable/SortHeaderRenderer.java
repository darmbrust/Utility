package utility.swing.sortedTable;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

/**
 * <pre>
 * Title:        SortHeaderRenderer
 * Description:  Renderes the arrow icon onto the JTable.
 * Copyright:    Copyright (c) 2002
 * Company:      <A HREF="http://www.mayo.edu">Mayo Clinic</A>
 *
 * Last update by $Author: ARMBRUST $
 * On $Date: 2002/07/16 17:03:19 $
 * </pre>
 *
 * Modifed from origional source from Claude Duguay
 *
 * @author <A HREF="mailto:armbrust.daniel@mayo.edu">Dan Armbrust</A>
 * @version 1.0 $Revision: 1.2 $
 */

public class SortHeaderRenderer extends DefaultTableCellRenderer
{
    public static Icon NONSORTED = new SortArrowIcon(SortArrowIcon.NONE);
    public static Icon ASCENDING = new SortArrowIcon(SortArrowIcon.ASCENDING);
    public static Icon DECENDING = new SortArrowIcon(SortArrowIcon.DECENDING);

    public SortHeaderRenderer()
    {
        setHorizontalTextPosition(LEFT);
        setHorizontalAlignment(CENTER);
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int col)
    {
        int index = -1;
        boolean ascending = true;
        if (table instanceof JSortTable)
        {
            JSortTable sortTable = (JSortTable)table;
            index = sortTable.getSortedColumnIndex();
            ascending = sortTable.isSortedColumnAscending();
        }
        if (table != null)
        {
            JTableHeader header = table.getTableHeader();
            if (header != null)
            {
                setForeground(header.getForeground());
                setBackground(header.getBackground());
                setFont(header.getFont());
            }
        }
        Icon icon = ascending ? ASCENDING : DECENDING;
        setIcon(col == index ? icon : NONSORTED);
        setText((value == null) ? "" : value.toString());
        setBorder(UIManager.getBorder("TableHeader.cellBorder"));
        return this;
    }
}