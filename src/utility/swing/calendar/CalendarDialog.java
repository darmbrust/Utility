package utility.swing.calendar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * <pre>
 * Title:        CalendarDialog
 * Description:  This class displays a pop up modal calendar, and returns the day selected.
 * Copyright:    Copyright (c) 2002
 * Company:      <A HREF="http://www.mayo.edu">Mayo Clinic</A>
 *
 * Last update by $Author: armbrust $
 * On $Date: 2002/10/04 19:23:10 $
 * </pre>
 *
 * <H5> Notes</H5>
 * <UL>
 *      <LI>
 *          Months are 1 based (1-12) (which is different than the gregorian calendar 0 based months)
 *      </LI>
  *      <LI>
 *          See MonthPanel for a more configuable calendar solution.
 *      </LI>
 * </UL>
 *
 * @author <A HREF="mailto:armbrust.daniel@mayo.edu">Dan Armbrust</A>
 * @version 1.0 $Revision: 1.6 $
 * @see MonthPanel
 */

 public class CalendarDialog
{
    private static Day valueToReturn = null;
    private static MonthPanel monthPanel = null;
    private static JDialog dialog = null;

    /**
     * Method displayCalendarDialog.
     * This method works like the JOptionPane dialogs.  Simply call it,
     * it pops up the dialog, and returns you the result.
     * @param month - The month
     * @param year - The year
     * @param yearChoices - The choices to display in the years drop down list
     * @param owner - The owner frame
     * @param title - The title
     * @param renderer - The renderer to use (may be null) - Pass in your own to set
     * different colors, font sizes.
     * @see BlueOrangeMonthTableCellRenderer
     * @see MonthTableCellRenderer
     * @return Day - The day that was selected
     */
    public static Day displayCalendarDialog(int month, int year, Integer[] yearChoices, Frame owner, String title, DefaultTableCellRenderer renderer)
    {
        JDialog dialog = new JDialog(owner, title, true);
        CalendarDialog calendarDialog = new CalendarDialog(dialog, month, year, yearChoices, renderer);
        dialog.setVisible(true);
        monthPanel = null;
        dialog = null;
        return calendarDialog.valueToReturn;
    }

    /**
     * Method displayCalendarDialog.
     * This method works like the JOptionPane dialogs.  Simply call it,
     * it pops up the dialog, and returns you the result.
     * @param month - The month
     * @param year - The year
     * @param yearChoices - The choices to display in the years drop down list
     * @param owner - The owner dialog
     * @param title - The title
     * @param renderer - The renderer to use (may be null) - Pass in your own to set
     * different colors, font sizes.
     * @see BlueOrangeMonthTableCellRenderer
     * @see MonthTableCellRenderer
     * @return Day - The day that was selected
     */
    public static Day displayCalendarDialog(int month, int year, Integer[] yearChoices, Dialog owner, String title, DefaultTableCellRenderer renderer)
    {
        JDialog dialog = new JDialog(owner, title, true);
        CalendarDialog calendarDialog = new CalendarDialog(dialog, month, year, yearChoices, renderer);
        dialog.setVisible(true);
        monthPanel = null;
        dialog = null;
        return calendarDialog.valueToReturn;
    }

    /**
     * Method displayCalendarDialog.
     * This method is for getting at that CalendarDialog class, so you can load it up before you need it.
     * This way it loads quicker when you say display it.
     * @param month - The month
     * @param year - The year
     * @param yearChoices - The choices to display in the years drop down list
     * @param owner - The owner frame
     * @param title - The title
     * @param renderer - The renderer to use (may be null) - Pass in your own to set
     * different colors, font sizes.
     * @see BlueOrangeMonthTableCellRenderer
     * @see MonthTableCellRenderer
     * @return Day - The day that was selected
     */
    public static CalendarDialog preloadCalendarDialog(int month, int year, Integer[] yearChoices, Frame owner, String title, DefaultTableCellRenderer renderer)
    {
        JDialog dialog = new JDialog(owner, title, true);
        dialog.hide();
        CalendarDialog calendarDialog = new CalendarDialog(dialog, month, year, yearChoices, renderer);
        return calendarDialog;
    }

    /**
     * Method displayCalendarDialog.
     * This method is for getting at that CalendarDialog class, so you can load it up before you need it.
     * This way it loads quicker when you say display it.
     * @param month - The month
     * @param year - The year
     * @param yearChoices - The choices to display in the years drop down list
     * @param owner - The owner dialog
     * @param title - The title
     * @param renderer - The renderer to use (may be null) - Pass in your own to set
     * different colors, font sizes.
     * @see BlueOrangeMonthTableCellRenderer
     * @see MonthTableCellRenderer
     * @return Day - The day that was selected
     */
    public static CalendarDialog preloadCalendarDialog(int month, int year, Integer[] yearChoices, Dialog owner, String title, DefaultTableCellRenderer renderer)
    {
        JDialog dialog = new JDialog(owner, title, true);
        dialog.hide();
        CalendarDialog calendarDialog = new CalendarDialog(dialog, month, year, yearChoices, renderer);
        return calendarDialog;
    }

    /**
     * Call this method to change the date of the preloaded calendar.
     * See the preloadCalendarDialog method
     */
    public void setDate(int month, int year)
    {
        this.monthPanel.setDate(month, year);
    }

    /**
     * This method is what you use to display a calendar which you preloaded.
     * See the preloadCalendarDialog method
     */
    public Day displayPreloadedCalendarDialog()
    {
        dialog.setVisible(true);
        return this.valueToReturn;
    }



    public static Integer[] createDateRange(int begin, int end)
    {
        return MonthPanel.createDateRange(begin, end);
    }

    private CalendarDialog(JDialog dialog2, int month, int year, Integer[] yearChoices, DefaultTableCellRenderer renderer)
    {
        dialog = dialog2;
        init(month, year, yearChoices, renderer);
    }

    private void init(int month, int year, Integer[] yearChoices, DefaultTableCellRenderer renderer)
    {
        monthPanel = new MonthPanel(month, year, false, true, renderer);
        if (yearChoices != null)
            monthPanel.setYearChoices(yearChoices);

        monthPanel.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                valueToReturn = monthPanel.selectedDay;
                dialog.setVisible(false);
            }
        });

        dialog.getContentPane().setLayout(new GridBagLayout());
        dialog.getContentPane().add(monthPanel, new GridBagConstraints(
            0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

        dialog.setSize(300, 330);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        dialog.setLocation(screenSize.width/2 - 330/2, screenSize.height/2 - 300/2);
    }

    public static void main(String[] args)
    {
        Day result = displayCalendarDialog(4, 2002, null, new Frame(), "Choose a day", null);
        if (result == null)
            System.out.println("null");
        else
            System.out.println(result.getMonth() + "/" + result.getDay() + "/" + result.getYear());

        BlueOrangeMonthTableCellRenderer foo = new BlueOrangeMonthTableCellRenderer();
        foo.setFontSize(20);
        CalendarDialog temp = preloadCalendarDialog(3, 2002, null, new Frame(), "hi", foo);

        temp.setDate(4, 2003);
        System.out.println(temp.displayPreloadedCalendarDialog());
    }
}