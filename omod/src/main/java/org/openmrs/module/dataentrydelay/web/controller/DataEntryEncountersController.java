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

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * This controller is used to display the encounters between date(startDate and endDate) related to
 * specific delay which is done by any user to specific provider
 */
public class DataEntryEncountersController extends AbstractController {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	/**
	 * @see org.springframework.web.servlet.mvc.AbstractController#handleRequestInternal(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse arg1) throws Exception {
		
		// if delay is not a parameter then delay = -2
		//getting the attributes from jsp page
		int delay = ServletRequestUtils.getIntParameter(request, "delay", -2);
		String userIdStr = request.getParameter("userId");
		String locationIdStr = request.getParameter("locationId");
		
		//int userId = userIdStr != null ? Integer.parseInt(userIdStr) : 0;
		int userId = (userIdStr != null && userIdStr.length() > 0) ? Integer.parseInt(userIdStr) : 0;
		int locationId = (locationIdStr != null && locationIdStr.length() > 0) ? Integer.parseInt(locationIdStr) : 0;
		Location location = Context.getLocationService().getLocation(locationId);
		
		// Default  date from date is two weeks ago from last midnight
		DateOperations dateOperation = new DateOperations();
		
		Date lastMidnight = dateOperation.getPreviousMidnight();
		Date twoWeekAgo = dateOperation.addDaysToDate(lastMidnight, -14);
		
		// Get from and until request parameters
		Date startdate = dateOperation.getRightDate(request, "startDate", twoWeekAgo);
		Date enddate = dateOperation.getRightDate(request, "endDate", lastMidnight);
		
		//collection of allEncountersMap and model 
		Map<Encounter, Integer> allEncountersMap = new HashMap<Encounter, Integer>();
		HashMap<String, Object> model = new HashMap<String, Object>();
		User provider = Context.getUserService().getUser(userId);
		
		DataEntryService dataEntryservice = Context.getService(DataEntryService.class);
		
		//get encounters by provider between start date and end date and location
		Collection<Encounter> encounters = dataEntryservice.getDataEntryStat(provider, startdate, enddate, location);
		
		for (Encounter encounter : encounters) {
			Date encounterdate = encounter.getEncounterDatetime();
			Date createddate = encounter.getDateCreated();
//			if (encounters.size() == 100) {
//				break;
//			}
			// Calculate difference in days 
			dateOperation = new DateOperations();
			int diffDays = dateOperation.calculateDelay(encounterdate, createddate);
			if (diffDays == 0) {
				continue;
			} else if (delay == -2 || (delay == 12 && diffDays > 12) || (delay == -1 && diffDays < 0) || delay == diffDays) {
				allEncountersMap.put(encounter, diffDays);
			}
			
		}
		model.put("encounters", allEncountersMap);
		model.put("startdate", startdate);
		model.put("enddate", enddate);
		model.put("user", provider);
		model.put("location", location);
		model.put("delay", delay);
		//To send all encounters for any delay
		return new ModelAndView("/module/dataentrydelay/encounters", model);
	}
}
