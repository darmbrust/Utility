package utility.swing.calendar;

import java.awt.*;
import javax.swing.*;

import java.util.*;
import javax.swing.table.*;

/**
 * <pre>
 * Title:        BlueOrangeMonthTableCellRenderer
 * Description:  Cell renderer for the calendar.
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
public class BlueOrangeMonthTableCellRenderer extends DefaultTableCellRenderer
{
    private int fontSize = 12;
    private MayoDayTypeDeterminer mdtd;
    private GregorianCalendar gc = new GregorianCalendar();

    private Color todaysBackgroundColor;
    private Color normalDayBackgroundColor;
    private Color nonCurrentMonthBackgroundColor;
    private Color weekendBackgroundColor;
    private Color holidayBackgroundColor;

    private Color paydayBorderColor;

    private Color blueDayForegroundColor;
    private Color orangeDayForegroundColor;
    

    /**
     * Constructor for BlueOrangeMonthTableCellRenderer.  Uses the default colors.
     */
    public BlueOrangeMonthTableCellRenderer()
    {
        super();
        setDefaultColors();
        mdtd = new MayoDayTypeDeterminer();

    }
    

    /**
     * Method setColors.  Resets any colors you desire to whatever you want.  
     * null inputs will be ignored.  (So if you just want to change one color, leave the 
     * rest null)
     * @param todaysBackgroundColor
     * @param normalDayBackgroundColor
     * @param nonCurrentMonthBackgroundColor
     * @param weekendBackgroundColor
     * @param holidayBackgroundColor
     * @param paydayBorderColor
     * @param blueDayForegroundColor
     * @param orangeDayForegroundColor
     */
    public void setColors(Color todaysBackgroundColor, 
    						Color normalDayBackgroundColor, 
    						Color nonCurrentMonthBackgroundColor,
    						Color weekendBackgroundColor,
    						Color holidayBackgroundColor,
    						Color paydayBorderColor,
    						Color blueDayForegroundColor,
    						Color orangeDayForegroundColor)
    {
        if (todaysBackgroundColor != null)
	        this.todaysBackgroundColor = todaysBackgroundColor;
        if (normalDayBackgroundColor != null)
        	this.normalDayBackgroundColor = normalDayBackgroundColor;
        if (nonCurrentMonthBackgroundColor != null)
	        this.nonCurrentMonthBackgroundColor = nonCurrentMonthBackgroundColor;
    	if (weekendBackgroundColor != null)
	        this.weekendBackgroundColor = weekendBackgroundColor;
        if (holidayBackgroundColor != null)
	        this.holidayBackgroundColor = holidayBackgroundColor;

    	if (paydayBorderColor != null)
	        this.paydayBorderColor = paydayBorderColor;
		
		if (blueDayForegroundColor != null)
			this.blueDayForegroundColor = blueDayForegroundColor;

		if (orangeDayForegroundColor != null)
	        this.orangeDayForegroundColor = orangeDayForegroundColor;
    }

    /**
     * Method setDefaultColors.
     * Sets up the calendar to look like the mayo calendar.  light blue holidays, weekend, 
     * Blue days, orange days, blue square pay days.
     */
    public void setDefaultColors()
    {
        todaysBackgroundColor = Color.cyan;
        normalDayBackgroundColor = Color.white;
        nonCurrentMonthBackgroundColor = Color.lightGray;
        float[] color = Color.RGBtoHSB(0, 153, 255, null);
        weekendBackgroundColor = Color.getHSBColor(color[0], color[1], color[2]);
        holidayBackgroundColor = Color.getHSBColor(color[0], color[1], color[2]);

        paydayBorderColor = Color.getHSBColor(color[0], color[1], color[2]);

        color = Color.RGBtoHSB(0, 0, 187, null);
        blueDayForegroundColor = Color.getHSBColor(color[0], color[1], color[2]);

        color = Color.RGBtoHSB(255, 153, 0, null);

        orangeDayForegroundColor = Color.getHSBColor(color[0], color[1], color[2]);
    }

    /**
     * The method that is overridden to make the table pretty.
     * 
     * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
     */
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col)
    {
        JLabel jl = new JLabel();
        jl.setOpaque(true);
        jl.setHorizontalAlignment(JLabel.RIGHT);
        jl.setVerticalAlignment(JLabel.TOP);
        jl.setFont(new Font(jl.getFont().getName(), Font.BOLD, fontSize));
        jl.setText(value.toString());

        GregorianCalendar vgc = ((Day) value).getDate();
        String type = mdtd.getType(vgc.get(vgc.YEAR), vgc.get(vgc.DAY_OF_YEAR));

        //set foreground
        if (type.indexOf(mdtd.ORANGE) != -1)
        {
            jl.setForeground(orangeDayForegroundColor);
        }
        else
        {
            jl.setForeground(blueDayForegroundColor);
        }

        // set background

        if (!((Day) value).currentMonth())
        {
            jl.setBackground(nonCurrentMonthBackgroundColor);
        }

        else if ((vgc.get(GregorianCalendar.DATE) == gc.get(GregorianCalendar.DATE))
                && (vgc.get(GregorianCalendar.YEAR) == gc.get(GregorianCalendar.YEAR))
                && (vgc.get(GregorianCalendar.MONTH) == gc.get(GregorianCalendar.MONTH)))
        {
            jl.setBackground(todaysBackgroundColor);
        }

        else if (type.indexOf(mdtd.HOLIDAY) != -1)
        {
            jl.setBackground(holidayBackgroundColor);
        }
        else if (vgc.get(GregorianCalendar.DAY_OF_WEEK) == 1 || vgc.get(GregorianCalendar.DAY_OF_WEEK) == 7)
        {
            jl.setBackground(weekendBackgroundColor);
        }
        else
        {
            jl.setBackground(normalDayBackgroundColor);
        }
        
        //Set the border color

        if (type.indexOf(mdtd.PAYDAY) != -1)
        {
            jl.setBorder(BorderFactory.createLineBorder(paydayBorderColor, 2));
        }
        else
        {
        	jl.setBorder(BorderFactory.createLineBorder(jl.getBackground(), 2));
        }
        
        if(isSelected)
		{
			Color originalB = jl.getBackground();
			Color inverseB = new Color(255 - originalB.getRed(),
					255 - originalB.getGreen(),
					255 - originalB.getBlue(),
					255 - originalB.getAlpha() + 85);
			jl.setBackground(inverseB);

			Color originalF = jl.getForeground();
			Color inverseF = new Color(255 - originalF.getRed(),
					 255 - originalF.getGreen(),
					 255 - originalF.getBlue(),
					 255 - originalF.getAlpha() + 85);
			jl.setForeground(inverseF);
		}

	    return(jl);
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
