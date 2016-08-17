package utility.swing.calendar;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;


/**
 * <pre>
 * Title:        MonthPanel
 * Description:  This class builds a JPanel with a very configurable calendar for a given year/month in it.
 * Copyright:    Copyright (c) 2002
 * Company:      <A HREF="http://www.mayo.edu">Mayo Clinic</A>
 *
 * Last update by $Author: armbrust $
 * On $Date: 2002/10/04 17:47:42 $
 * </pre>
 *
 * <H5> Notes</H5>
 * <UL>
 *      <LI>
 *          Months are 1 based (1-12) (which is different than the gregorian calendar 0 based months)
 *      </LI>
 * </UL>
 *
 * @author <A HREF="mailto:armbrust.daniel@mayo.edu">Dan Armbrust</A>
 * @version 1.0 $Revision: 1.6 $
 * @see CalendarDialog
 */
public class MonthPanel extends JPanel
{
	private MonthTable calendar_;
	private JButton monthBack;
	private JButton monthForward;
	private JComboBox monthSelection;
	private JButton yearBack;
	private JButton yearForward;
	private JComboBox yearSelection;
	private int month_;
	private int year_;
	private boolean longDayTitles_;
	private MonthTableScrollPane calendarScrollPane_;
	private boolean displayMonthChooser_;
	private HashMap actionListeners_ = new HashMap();
	private boolean monthYearListenersEnabled_ = true;
	private MonthPanel mp_;

	public Day selectedDay;
	public static final String DAY_SELECTED = "DAY_SELECTED";
	public static final String DAY_RIGHT_CLICKED = "DAY_RIGHT_CLICKED";
	public static final String DAY_DOUBLE_CLICKED = "DAY_DOUBLE_CLICKED";

	/**
     * The constructor for the MonthPanel class.  This will create a panel with 0 insets, which contains a fully
     * functional calendar.  The calendar has configurable colors, and if you attach an action listener to this panel
     * it will fire ActionEvents for single click, right click, and double click on a certain day.
     * The day is then accessable from the public variable selectedDay.
     * Example code:
     * <pre>
     * calendarPanel.addActionListener(new ActionListener()
    	{
    		public void actionPerformed(ActionEvent evt)
    		{
    			System.out.println(evt.getActionCommand());
    			System.out.println(calendarPanel.selectedDay.getMonth() + " " + calendarPanel.selectedDay.getDay() + " " + calendarPanel.selectedDay.getYear());
    		}
    	});
     * </pre>
     *
     * You may also choose 1 and 2 letter day of the week abbreviations, or the full names, and also
     * whether or not to display drop down lists of the months and years.  The years in the dropdown box
     * are by default +- 2 years from the year the MonthPanel is constructed with.  There is a method that
     * takes an array of Integers if you wish to supply your own list of years.  You may pass null as the 
     * renderer, and it will choose a default calendar type.  Pass in a preconstructed renderer - for example
     * BlueOrangeMonthTableCellRenderer, or the MonthTableCellRenderer if you want to further customize the 
     * colors used in display.
     *
     */
    public MonthPanel(int month, int year, boolean displayLongDayTitles, boolean displayMonthChooser, DefaultTableCellRenderer renderer)
    {
    	super();
    	mp_ = this;
    	month_ = month - 1;
    	year_ = year;
    	longDayTitles_ = displayLongDayTitles;
    	calendar_ = new MonthTable(longDayTitles_, month_, year_, renderer);
    	displayMonthChooser_ = displayMonthChooser;
    	init();
    }

	/**
	 * This method is used to populate the year dropdown box with your choice of years.
	 */
	public void setYearChoices(Integer[] years)
	{
		if (displayMonthChooser_)
		{
			monthYearListenersEnabled_ = false;
		    yearSelection.setModel(new DefaultComboBoxModel(years));
			monthYearListenersEnabled_ = true;
			setDateComboBoxes();
		}
	}

	/**
	 * This method is meant to be used with the setYearChoices method.  It returns an array of Integers from begin to end.
	 */
	public static Integer[] createDateRange(int begin, int end)
	{
		Integer[] result = new Integer[end - begin + 1];
		for (int i = 0; begin <= end; i++)
		{
			result[i] = new Integer(begin++);
		}
		return result;
	}

	/**
	 * This method is used to change the month that the calendar is displaying.
	 * It is 1 based (months 1-12)
	 */
	public void setDate(int month, int year)
	{
		setMonthInternal(month - 1, year);
	}

	public void addActionListener(java.awt.event.ActionListener a)
	{
	    actionListeners_.put(a, a);
	}

	private void init()
	{
		calendarScrollPane_ = new MonthTableScrollPane(calendar_, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		if (displayMonthChooser_)
		{
			ImageIcon leftIcon = new ImageIcon(this.getClass().getResource("images/left.gif"));
			ImageIcon rightIcon = new ImageIcon(this.getClass().getResource("images/right.gif"));

			Dimension buttonSize = new Dimension(26, 21);

			monthBack  = new JButton(null, leftIcon);
			monthBack.setMinimumSize(buttonSize);
			monthBack.setPreferredSize(buttonSize);
			monthBack.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent evt)
				{
				  if(month_ > 0)
				  {
					setMonthInternal(month_ - 1, year_);
				  }
				  else
				  {
					setMonthInternal(11, year_ - 1);
				  }
				}
			});

			monthForward = new JButton(null, rightIcon);
			monthForward.setMinimumSize(buttonSize);
			monthForward.setPreferredSize(buttonSize);
			monthForward.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent evt)
				{
				  if(month_ < 11)
				  {
					setMonthInternal(month_ + 1, year_);
				  }
				  else
				  {
					setMonthInternal(0, year_ + 1);
				  }
				}
			  });


			yearBack  = new JButton(null, leftIcon);
			yearBack.setMinimumSize(buttonSize);
			yearBack.setPreferredSize(buttonSize);
			yearBack.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent evt)
				{
					setMonthInternal(month_, year_ - 1);
				}
			  });


			yearForward = new JButton(null, rightIcon);
			yearForward.setMinimumSize(buttonSize);
			yearForward.setPreferredSize(buttonSize);
			yearForward.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent evt)
				{
					setMonthInternal(month_, year_ + 1);
				}
			  });

			Dimension size = new Dimension(120, 21);

			monthSelection = new JComboBox(new String[]{"January", "February", "March", "April",
				   "May", "June", "July", "August", "September",
				   "October", "November", "December"});
			monthSelection.setMinimumSize(size);
			monthSelection.setPreferredSize(size);
			monthSelection.setMaximumRowCount(12);
			monthSelection.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent evt)
				{
					if (mp_.monthYearListenersEnabled_)
					    setMonthInternal(monthSelection.getSelectedIndex(), year_);
				}
			  });

			yearSelection = new JComboBox(new Integer[]{new Integer(year_ -2), new Integer(year_ -1),
						new Integer(year_), new Integer(year_ +1), new Integer(year_ +2)});
			yearSelection.setMinimumSize(size);
			yearSelection.setPreferredSize(size);
			yearSelection.setMaximumRowCount(20);
			yearSelection.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent evt)
				{
					if (mp_.monthYearListenersEnabled_)
					    setMonthInternal(month_, ((Integer)yearSelection.getSelectedItem()).intValue());
				}
			  });
		}

		/* Setting up the GridBagLayout stuff... */
		GridBagLayout gbl = new GridBagLayout();
		this.setLayout(gbl);
		GridBagConstraints gbc = new GridBagConstraints();

		/* Actually adding the graphical elements to the GUI... */


		if (displayMonthChooser_)
		{
			gbc.insets = new Insets(0, 0, 5, 0);
			guiAdd(this, monthBack, gbc,            0, 0, 1, 1, 1.0, 0.0, gbc.NONE, gbc.WEST);
			guiAdd(this, monthSelection, gbc,       1, 0, 1, 1, 0.0, 0.0, gbc.NONE, gbc.CENTER);
			guiAdd(this, monthForward, gbc,         2, 0, 1, 1, 1.0, 0.0, gbc.NONE, gbc.EAST);
			guiAdd(this, yearBack, gbc,             0, 1, 1, 1, 1.0, 0.0, gbc.NONE, gbc.WEST);
			guiAdd(this, yearSelection, gbc,        1, 1, 1, 1, 0.0, 0.0, gbc.NONE, gbc.CENTER);
			guiAdd(this, yearForward, gbc,          2, 1, 1, 1, 1.0, 0.0, gbc.NONE, gbc.EAST);
		}
		gbc.insets = new Insets(0, 0, 0, 0);
		guiAdd(this, calendarScrollPane_, gbc,    0, 2, 3, 1, 1.0, 1.0, gbc.BOTH, gbc.CENTER);

		calendar_.addMouseListener(new MouseAdapter()
		{
		    public void mouseClicked(MouseEvent evt)
			{
			    int row = calendar_.rowAtPoint(evt.getPoint());
			    int column = calendar_.columnAtPoint(evt.getPoint());

				if ((row >= 0) && (column >= 0))
				{
					calendar_.setRowSelectionInterval(row, row);
					calendar_.setColumnSelectionInterval(column, column);

					if (SwingUtilities.isRightMouseButton(evt))
					{
						mp_.selectedDay = ((Day) calendar_.getValueAt(row, column));
						mp_.fireActionEvent(new ActionEvent(mp_, 3, mp_.DAY_RIGHT_CLICKED));
					}
					else if (evt.getClickCount() == 2)
					{
						mp_.selectedDay = ((Day) calendar_.getValueAt(row, column));
						mp_.fireActionEvent(new ActionEvent(mp_, 2, mp_.DAY_DOUBLE_CLICKED));
					}
					else if (evt.getClickCount() == 1)
					{
						mp_.selectedDay = ((Day) calendar_.getValueAt(row, column));
						mp_.fireActionEvent(new ActionEvent(mp_, 1, mp_.DAY_SELECTED));
					}

				}
			  }
		  });

		if (displayMonthChooser_)
		{
		    setDateComboBoxes();
		}
	  }

	private void setDateComboBoxes()
	{
		this.monthYearListenersEnabled_ = false;
		this.monthSelection.setSelectedIndex(month_);
		DefaultComboBoxModel model = (DefaultComboBoxModel)this.yearSelection.getModel();

		model.setSelectedItem(new Integer(year_));
		if (((Integer)model.getSelectedItem()).intValue() != year_) //If it didn't select the one we want, it needs to be added
		{
			model.addElement(new Integer(year_));

			Integer[] temp = new Integer[model.getSize()];
			for (int i = 0; i < temp.length; i++)
			{
				temp[i] = (Integer)model.getElementAt(i);
			}
			Arrays.sort(temp);
			yearSelection.setModel(new DefaultComboBoxModel(temp));

			this.yearSelection.setSelectedItem(new Integer(year_));
		}
		this.monthYearListenersEnabled_ = true;
	}


	private void fireActionEvent(ActionEvent actionEvent)
	{
		Iterator temp = actionListeners_.values().iterator();
		while(temp.hasNext())
		{
		    ((ActionListener)temp.next()).actionPerformed(actionEvent);
		}
	}

	private void setMonthInternal(int month, int year)
	{
		year_ = year;
		month_ = month;
		calendar_.setModel(new MonthTableModel(longDayTitles_, month_, year_));
		if (displayMonthChooser_)
		{
		    setDateComboBoxes();
		}
		calendarScrollPane_.repaint();
	}

	private void guiAdd(Container container, Component c, GridBagConstraints gbc, int x, int y, int w, int h, double weightx, double weighty, int fill, int anchor)
	{
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = w;
		gbc.gridheight = h;
		gbc.weightx = weightx;
		gbc.weighty = weighty;
		gbc.fill = fill;
		gbc.anchor = anchor;
		container.add(c, gbc);
	}
	
//	public static void main(String[] args) throws Exception
//    {
//    	UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//    	JFrame temp = new JFrame();
//    	temp.setSize(300, 300);
//        final MonthPanel calendarPanel = new MonthPanel(3, 2002, false, true, null);
//    	temp.getContentPane().add(calendarPanel);
//    	temp.setVisible(true);
//    
//    	calendarPanel.addActionListener(new ActionListener()
//    	{
//    		public void actionPerformed(ActionEvent evt)
//    		{
//    			System.out.println(evt.getActionCommand());
//    			System.out.println(calendarPanel.selectedDay.getMonth() + " " + calendarPanel.selectedDay.getDay() + " " + calendarPanel.selectedDay.getYear());
//    		}
//    	});
//    
//    	calendarPanel.setYearChoices(new Integer[]{new Integer(1950), new Integer(1951),
//    					new Integer(1952), new Integer(1953), new Integer(1954)});
//    }
}
