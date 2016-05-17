package org.openmrs.module.dataentrydelay.advice;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;

public class DateOperations {
	
	protected static final Log log = LogFactory.getLog(DateOperations.class);
	
	/**
	 * Utility method to get a parsed date parameter
	 * 
	 * @param request the HTTP request object
	 * @param name the name of the date parameter
	 * @param def the default value if parameter doesn't exist or is invalid
	 * @return the date
	 */
	public  Date getRightDate(HttpServletRequest request,String name,Date def) {
		
		String strDate = request.getParameter(name);
		if (strDate != null) {
			try {
				return Context.getDateFormat().parse(strDate);
			}
			catch (Exception ex) {
				//log.warn("Invalid date format: " + strDate);
			}
		}
		return def;
	}
	
	/**
	 * Utility method to add days to an existing date
	 * 
	 * @param date (may be null to use today's date)
	 * @param days the number of days to add (negative to subtract days)
	 * @return the new date
	 */
	public  Date addDaysToDate(Date date, int days) {
		// Initialize with date if it was specified
		Calendar cal = new GregorianCalendar();
		if (date != null)
			cal.setTime(date);
		cal.add(Calendar.DAY_OF_WEEK, days);
		return cal.getTime();
	}
	
	/**
	 * Calculates the time of the last midnight before an existing date
	 * 
	 * @param date (may be null to use today's date)
	 * @return the new date
	 */
	public  Date getPreviousMidnight() {
		// Initialize with date if it was specified
		
		Calendar cal = new GregorianCalendar();
		//========if (date != null)
		Date date=new Date();
			cal.setTime(date);
		
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	public  int calculateDelay(Date startDate, Date endDate) {
		Calendar startCal = Calendar.getInstance();
		Calendar endCal = Calendar.getInstance();
		startCal.setTime(startDate);
		endCal.setTime(endDate);
		long milisStart = startCal.getTimeInMillis();
		long milisEnd = endCal.getTimeInMillis();
		long diff = milisEnd - milisStart;
		long diffDays = diff / (24 * 60 * 60 * 1000);
		return (int)diffDays;
	}
}
