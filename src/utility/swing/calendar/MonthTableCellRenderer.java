package utility.swing.calendar;

import java.awt.*;
import javax.swing.*;
import java.util.*;
import javax.swing.table.*;

/**
 * <pre>
 * Title:        MonthTableCellRenderer
 * Description:  Cell renderer for the calendar.
 * Copyright:    Copyright (c) 2002
 * Company:      <A HREF="http://www.mayo.edu">Mayo Clinic</A>
 *
 * Last update by $Author: armbrust $
 * On $Date: 2002/10/04 17:47:41 $
 * </pre>
 *
 * @author <A HREF="mailto:armbrust.daniel@mayo.edu">Dan Armbrust</A>
 * @version 1.0 $Revision: 1.3 $
 * @see MonthPanel
 * @see CalendarDialog
 */
public class MonthTableCellRenderer extends DefaultTableCellRenderer
{
    private Color todaysForegroundColor;
    private Color todaysBackgroundColor;
    private Color normalDayForegroundColor;
    private Color normalDayBackgroundColor;
    private Color nonCurrentMonthBackgroundColor;
    private Color nonCurrentMonthForegroundColor;

    private int fontSize = 12;

    private GregorianCalendar gc = new GregorianCalendar();

    /**
     * Constructor for the MonthTableCellRenderer.
     */
    public MonthTableCellRenderer()
    {
        super();
        setDefaultColors();
    }

    /**
     * Method setColors.  Sets the colors to what you want.  Null's are ignored.  (just pass in 
     * the colors you want to change, leave the rest null)
     * @param todaysForegroundColor
     * @param todaysBackgroundColor
     * @param normalDayForegroundColor
     * @param normalDayBackgroundColor
     * @param nonCurrentMonthBackgroundColor
     * @param nonCurrentMonthForegroundColor
     */
    public void setColors(
        Color todaysForegroundColor,
        Color todaysBackgroundColor,
        Color normalDayForegroundColor,
        Color normalDayBackgroundColor,
        Color nonCurrentMonthBackgroundColor,
        Color nonCurrentMonthForegroundColor)
    {
        if (todaysForegroundColor != null)
            this.todaysForegroundColor = todaysForegroundColor;
        if (todaysBackgroundColor != null)
            this.todaysBackgroundColor = todaysBackgroundColor;
        if (normalDayForegroundColor != null)
            this.normalDayForegroundColor = normalDayForegroundColor;
        if (normalDayBackgroundColor != null)
            this.normalDayBackgroundColor = normalDayBackgroundColor;
        if (nonCurrentMonthBackgroundColor != null)
            this.nonCurrentMonthBackgroundColor = nonCurrentMonthBackgroundColor;
        if (nonCurrentMonthForegroundColor != null)
            this.nonCurrentMonthForegroundColor = nonCurrentMonthForegroundColor;
    }

    /**
     * Method setDefaultColors - Sets the colors to their defaults.
     */
    public void setDefaultColors()
    {
        todaysForegroundColor = Color.red;
        todaysBackgroundColor = Color.white;
        normalDayForegroundColor = Color.black;
        normalDayBackgroundColor = Color.white;
        nonCurrentMonthBackgroundColor = Color.lightGray;
        nonCurrentMonthForegroundColor = Color.darkGray;
    }

    /**
     * Overrides this method to make the table pretty.
     * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
     */
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col)
    {
        JLabel jl = new JLabel();
        jl.setOpaque(true);
        jl.setHorizontalAlignment(JLabel.RIGHT);
        jl.setVerticalAlignment(JLabel.TOP);
        jl.setFont(new Font(jl.getFont().getName(), Font.BOLD, fontSize));

        GregorianCalendar vgc = ((Day) value).getDate();
        jl.setText(value.toString());

        if ((vgc.get(GregorianCalendar.DATE) == gc.get(GregorianCalendar.DATE))
            && (vgc.get(GregorianCalendar.YEAR) == gc.get(GregorianCalendar.YEAR))
            && (vgc.get(GregorianCalendar.MONTH) == gc.get(GregorianCalendar.MONTH)))
        {
            jl.setForeground(todaysForegroundColor);
            if (((Day) value).currentMonth())
                jl.setBackground(todaysBackgroundColor);
            else
                jl.setBackground(this.nonCurrentMonthBackgroundColor);
        }

        else if (((Day) value).currentMonth())
        {
            jl.setForeground(normalDayForegroundColor);
            jl.setBackground(normalDayBackgroundColor);
        }
        else //not current month
            {
            jl.setForeground(this.nonCurrentMonthForegroundColor);
            jl.setBackground(this.nonCurrentMonthBackgroundColor);
        }

        if (isSelected)
        {
            Color originalB = jl.getBackground();
            Color inverseB =
                new Color(255 - originalB.getRed(), 255 - originalB.getGreen(), 255 - originalB.getBlue(), 255 - originalB.getAlpha() + 85);
            jl.setBackground(inverseB);

            Color originalF = jl.getForeground();
            Color inverseF =
                new Color(255 - originalF.getRed(), 255 - originalF.getGreen(), 255 - originalF.getBlue(), 255 - originalF.getAlpha() + 85);
            jl.setForeground(inverseF);
        }

        return (jl);
    }
    /**
     * Returns the fontSize.
     * @return int
     */
    public int getFontSize()
    {
        return fontSize;
    }

    /**
     * Sets the fontSize.
     * @param fontSize The fontSize to set
     */
    public void setFontSize(int fontSize)
    {
        this.fontSize = fontSize;
    }

}