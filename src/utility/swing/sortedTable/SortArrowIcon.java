package utility.swing.sortedTable;

import java.awt.*;
import javax.swing.*;

/**
 * <pre>
 * Title:        SortArrowIcon
 * Description:  This class draws the actual arrow in the header of a JTable.
 * Copyright:    Copyright (c) 2002
 * Company:      <A HREF="http://www.mayo.edu">Mayo Clinic</A>
 *
 * Last update by $Author: ARMBRUST $
 * On $Date: 2002/07/16 17:03:18 $
 * </pre>
 *
 * Modifed from origional source from Claude Duguay
 *
 * @author <A HREF="mailto:armbrust.daniel@mayo.edu">Dan Armbrust</A>
 * @version 1.0 $Revision: 1.2 $
 */

public class SortArrowIcon implements Icon
{
    public static final int NONE = 0;
    public static final int DECENDING = 1;
    public static final int ASCENDING = 2;

    protected int direction;
    protected int width = 8;
    protected int height = 8;

    public SortArrowIcon(int direction)
    {
        this.direction = direction;
    }

    public int getIconWidth()
    {
        return width;
    }

    public int getIconHeight()
    {
        return height;
    }

    public void paintIcon(Component c, Graphics g, int x, int y)
    {
        Color bg = c.getBackground();
        Color light = bg.brighter();
        Color shade = bg.darker();

        int w = width;
        int h = height;
        int m = w / 2;
        if (direction == ASCENDING)
        {
            g.setColor(shade);
            g.drawLine(x, y, x + w, y);
            g.drawLine(x, y, x + m, y + h);
            g.setColor(light);
            g.drawLine(x + w, y, x + m, y + h);
        }
        if (direction == DECENDING)
        {
            g.setColor(shade);
            g.drawLine(x + m, y, x, y + h);
            g.setColor(light);
            g.drawLine(x, y + h, x + w, y + h);
            g.drawLine(x + m, y, x + w, y + h);
        }
    }
}


