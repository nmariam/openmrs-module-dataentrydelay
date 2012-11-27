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

import java.util.ArrayList;
import java.util.Collection;
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

import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.dataentrydelay.advice.DateOperations;
import org.openmrs.module.dataentrydelay.service.DataEntryService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

/**
 *This controller is used to get the the Users statistics between Range of date(startDate and
 * endDate) to specific location
 */
public class DataEntryUsersController extends ParameterizableViewController {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	/**
	 * @see org.springframework.web.servlet.mvc.AbstractController#handleRequestInternal(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		
		// Default from date is two weeks ago from last midnight
		DateOperations dateOperation = new DateOperations();
		Date lastMidnight = dateOperation.getPreviousMidnight();
		Date twoWeekAgo = dateOperation.addDaysToDate(lastMidnight, -14);
		
		String locationIdStr = request.getParameter("locationId");
		int locationId = 0;
		Location location = null;
		if (locationIdStr != null && locationIdStr.length() > 0) {
			locationId = Integer.parseInt(locationIdStr);
			location = Context.getLocationService().getLocation(locationId);
			model.put("location", location);
		}
		
		// Get from and until request parameters
		Date startdate = dateOperation.getRightDate(request, "startDate", twoWeekAgo);
		Date enddate = dateOperation.getRightDate(request, "endDate", lastMidnight);
		
		//collection of allLocationsMap and allLocationsModel
		Map<User, Map<String, Object>> allUsers = new HashMap<User, Map<String, Object>>();
		List<User> users = Context.getUserService().getAllUsers();
		for (User user : users) {
			Map<String, Object> userStats = new HashMap<String, Object>();
			int[] delayStatistics = new int[14];
			DataEntryService service = Context.getService(DataEntryService.class);
			Collection<Encounter> encounters = service.getDataEntryStat(user, startdate, enddate, location);
			int totalDelay = 0;
			if (encounters.size() == 0) {
				
				continue;
			} else {
				for (Encounter encounter : encounters) {
					Date encounterdate = encounter.getEncounterDatetime();
					Date createddate = encounter.getDateCreated();
					
					// Calculate difference in days between encounterdate and createddate
					dateOperation = new DateOperations();
					int diffDays = dateOperation.calculateDelay(encounterdate, createddate);
					
					if (diffDays < 0) {
						delayStatistics[13]++;
					} else if (diffDays == 0) {
						continue;
					} else if (diffDays < 12) {
						delayStatistics[diffDays]++;
					} else {
						delayStatistics[12]++;
					}
					totalDelay += 1;
				}
				if(totalDelay!=0){
				userStats.put("delayStatistics", delayStatistics);
				userStats.put("totalDalay", totalDelay);
				allUsers.put(user, userStats);
				}
			}
		}
		model.put("allUsers", allUsers);
		model.put("startDate", startdate);
		model.put("endDate", enddate);
		return new ModelAndView(getViewName(), model);
	}
	
}
