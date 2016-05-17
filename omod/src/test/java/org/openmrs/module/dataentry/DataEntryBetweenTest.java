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
package org.openmrs.module.dataentry;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import junit.framework.Assert;
import org.junit.Test;
import org.openmrs.Encounter;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.dataentrydelay.service.DataEntryService;
import org.openmrs.test.BaseModuleContextSensitiveTest;

/**
 *
 */
public class DataEntryBetweenTest extends BaseModuleContextSensitiveTest {
	
	static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	@Override
	public Boolean useInMemoryDatabase() {
		
		return false;
		
	};
	
	public static void main(String[] args) {
		
		System.out.println(System.getenv("OPENMRS_RUNTIME_PROPERTIES_FILE"));
	}
	
	@Test
	public void shouldReturnEncountersBetweenDates() throws Exception {
		authenticate();
		DataEntryService service = (DataEntryService) Context.getService(DataEntryService.class);
		User user = Context.getUserService().getUser(7);
		Calendar start = Calendar.getInstance();
		Calendar enddate=Calendar.getInstance();
		start.set(2009,7,07);
	
		enddate.set(2009,7,12);
		//start.add(Calendar.YEAR, -10);
		
		Date startDate = start.getTime();
		Date endDate = enddate.getTime();
		
		System.out.println(Context.getEncounterService().getEncounter(1));
		System.out.println(Context.getEncounterService().getEncounter(2));
		System.out.println(Context.getEncounterService().getEncounter(3));
		System.out.println(Context.getEncounterService().getEncounter(4));
		
		//System.out.println(Context.getEncounterService().getEncounter(i).getProvider().getUserId());
		Collection<Encounter> encounters = service.getDataEntryByProviderDate(user, startDate, endDate);
		Assert.assertNotNull(encounters);
		int size = encounters.size();
		
		System.out.println("size is " + size + " between " + startDate + " " + endDate);
		for (Encounter encounter : encounters) {
	        System.out.println(encounter.getEncounterDatetime());
        }
	}
}
