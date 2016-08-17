package utility.swing.sortedTable;

import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

/**
 * <pre>
 * Title:        JSortTable
 * Description:  This class extends a standard JTable, but has sort arrow indicators on the header.
 * Copyright:    Copyright (c) 2002
 * Company:      <A HREF="http://www.mayo.edu">Mayo Clinic</A>
 *
 * Last update by $Author: ARMBRUST $
 * On $Date: 2002/07/16 17:03:18 $
 * </pre>
 *
 * Simply use this class instead of a standard JTable if you want to implement a sorted JTable that has
 * directional arrows.  You handle the actual sorting of the model.  To find out when and what column and
 * direction to sort on, simply attach an action listener.
 *
 * Here is an example of how to catch a sort command:
 * <pre>
 *       table.addActionListener(new ActionListener()
 *       {
 *           public void actionPerformed(ActionEvent evt)
 *           {
 *               evt.getActionCommand();
 *               tableModel.sort(evt.getModifiers(), evt.getActionCommand().equals(JSortTable.COLUMN_SORTED_ASCENDING));
 *           }
 *       });
 *</pre>
 * @author <A HREF="mailto:armbrust.daniel@mayo.edu">Dan Armbrust</A>
 * @version 1.0 $Revision: 1.2 $
 */

public class JSortTable extends JTable implements MouseListener
{
    protected int sortedColumnIndex = -1;
    protected boolean sortedColumnAscending = true;
    private HashMap actionListeners_ = new HashMap();
    public static final String COLUMN_SORTED_ASCENDING = "COLUMN_SORTED_ASCENDING";
    public static final String COLUMN_SORTED_DESCENDING = "COLUMN_SORTED_DESCENDING";

    public JSortTable()
    {
        super();
        initSortHeader();
    }

    public JSortTable(int rows, int cols)
    {
        super(rows, cols);
        initSortHeader();
    }

    public JSortTable(Object[][] data, Object[] names)
    {
        super(data, names);
        initSortHeader();
    }

    public JSortTable(Vector data, Vector names)
    {
        super(data, names);
        initSortHeader();
    }

    public JSortTable(TableModel model)
    {
        super(model);
        initSortHeader();
    }

    public JSortTable(TableModel model, TableColumnModel colModel)
    {
        super(model, colModel);
        initSortHeader();
    }

    public JSortTable(TableModel model, TableColumnModel colModel, ListSelectionModel selModel)
    {
        super(model, colModel, selModel);
        initSortHeader();
    }

    public int getSortedColumnIndex()
    {
        return sortedColumnIndex;
    }

    public boolean isSortedColumnAscending()
    {
        return sortedColumnAscending;
    }

    public void mouseReleased(MouseEvent event)
    {
        TableColumnModel colModel = getColumnModel();
        int index = colModel.getColumnIndexAtX(event.getX());
        int modelIndex = colModel.getColumn(index).getModelIndex();

        // toggle ascension, if already sorted
        if (sortedColumnIndex == index)
        {
            sortedColumnAscending = !sortedColumnAscending;
        }
        sortedColumnIndex = index;

        ActionEvent actionEvent;
        if (sortedColumnAscending)
            actionEvent = new ActionEvent(this, 0, this.COLUMN_SORTED_ASCENDING, modelIndex);
        else
            actionEvent = new ActionEvent(this, 1, this.COLUMN_SORTED_DESCENDING, modelIndex);

        fireActionEvent(actionEvent);
    }

    public void mousePressed(MouseEvent event) {}
    public void mouseClicked(MouseEvent event) {}
    public void mouseEntered(MouseEvent event) {}
    public void mouseExited(MouseEvent event) {}

    public void addActionListener(java.awt.event.ActionListener a)
    {
        actionListeners_.put(a, a);
    }

    private void initSortHeader()
    {
        JTableHeader header = getTableHeader();
        header.setDefaultRenderer(new SortHeaderRenderer());
        header.addMouseListener(this);
    }

    private void fireActionEvent(ActionEvent actionEvent)
    {
        Iterator temp = actionListeners_.values().iterator();
        while(temp.hasNext())
        {
            ((ActionListener)temp.next()).actionPerformed(actionEvent);
        }
    }
}

