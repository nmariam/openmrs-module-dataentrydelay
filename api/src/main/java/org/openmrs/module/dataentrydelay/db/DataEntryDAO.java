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
package org.openmrs.module.dataentrydelay.db;

import java.util.Collection;
import java.util.Date;

import org.openmrs.Encounter;
import org.openmrs.Location;
import org.openmrs.User;

/**
 * this is interface used by dataentryImpl to connect to database
 */
public interface DataEntryDAO {
	
	/**
	 * is used to return a collection of encounter Object
	 * 
	 * @param provider
	 * @return encounters
	 */
	Collection<Encounter> getDataEntryByProvider(User provider);
	
	/**
	 * is used to get a collection of encounters from the database
	 * 
	 * @param user
	 * @param startdate
	 * @param enddate
	 * @return encounters
	 */
	public Collection<Encounter> getDataEntryByProviderDate(User user, Date startdate, Date enddate);
	
	/**
	 * is used to get all encounters by those parameters
	 * 
	 * @param location
	 * @param startdate
	 * @param enddate
	 * @return encounters
	 */
	public Collection<Encounter> getDataEntryByLocationDate(Location location, Date startdate, Date enddate);
	
	/**
	 * is used to get all encounters by locationId
	 * 
	 * @param locationId
	 * @return encounters
	 */
	public Collection<Encounter> getDataEntryByLocation(Location locationId);
	
	public Collection<Encounter>getDataEntryStat(User user,Date startdate,Date enddate,Location location);
	
}
