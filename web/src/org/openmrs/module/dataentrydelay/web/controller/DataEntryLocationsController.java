/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.dataentrydelay.web.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Encounter;
import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.module.dataentrydelay.advice.DateOperations;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

/**
 *This controller is used to get the the location statistics between Range of date(startDate and
 * endDate)
 */
public class DataEntryLocationsController extends ParameterizableViewController {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	/**
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse, java.lang.Object,
	 *      org.springframework.validation.BindException)
	 */
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		
		// Default from date is one week ago from last midnight
		DateOperations dateOperation=new DateOperations();
		Date lastMidnight = dateOperation.getPreviousMidnight();
		Date twoWeekAgo = dateOperation.addDaysToDate(lastMidnight, -14);
		
		// Get from and until request parameters
		Date startdate = dateOperation.getRightDate(request, "startDate", twoWeekAgo);
		Date enddate = dateOperation.getRightDate(request, "endDate", lastMidnight);
		
		//colleLocationction of allLocationsMap and allLocationsModel
		Map<Location, Map<String, Object>> allLocations = new HashMap<Location, Map<String, Object>>();
		
		List<Location> locations = Context.getLocationService().getAllLocations();
		//for each location in the database:
		//do this:
		for (Location location : locations) {
			Map<String, Object> locationStats = new HashMap<String, Object>();
			int[] delayStatisticsTable = new int[14];
			
			List<Encounter> encounters = Context.getEncounterService().getEncounters(null, location, startdate, enddate,
			    null, null, true);
			int totalDelay=0;
			if (encounters.size() == 0) {
				continue;
			} else {
				
				for (Encounter encounter : encounters) {
					Date encounterdate = encounter.getEncounterDatetime();
					Date createddate = encounter.getDateCreated();
					
					// Calculate difference in days
					dateOperation=new DateOperations();
					int diffDays = dateOperation.calculateDelay(encounterdate, createddate);
					if (diffDays < 0) {
						delayStatisticsTable[13]++;
					}
					else if(diffDays==0){
						continue;
					}
					else if (diffDays < 12) {
						delayStatisticsTable[diffDays]++;
					} else {
						delayStatisticsTable[12]++;
					}
					totalDelay+=1;
				}
			}
			locationStats.put("delayStatistics", delayStatisticsTable);
			locationStats.put("totalDelay", totalDelay);
			allLocations.put(location, locationStats);
			
		}
		model.put("allLocations", allLocations);
		model.put("startDate", startdate);
		model.put("endDate", enddate);
		
		return new ModelAndView(getViewName(), model);
		
	}
}
