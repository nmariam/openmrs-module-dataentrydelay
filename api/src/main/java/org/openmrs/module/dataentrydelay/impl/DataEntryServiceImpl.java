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
package org.openmrs.module.dataentrydelay.impl;

import java.util.Collection;
import java.util.Date;

import org.openmrs.Encounter;
import org.openmrs.Location;
import org.openmrs.User;
import org.openmrs.module.dataentrydelay.db.DataEntryDAO;
import org.openmrs.module.dataentrydelay.service.DataEntryService;

/**
 *
 */
public class DataEntryServiceImpl implements DataEntryService {
	
	private DataEntryDAO dataentryDAO;
	
	public DataEntryDAO getDataentryDAO() {
		return dataentryDAO;
	}
	
	public void setDataentryDAO(DataEntryDAO dataentryDAO) {
		this.dataentryDAO = dataentryDAO;
	}
	
	public Collection<Encounter> getDataEntryByProvider(User provider) {
		
		return dataentryDAO.getDataEntryByProvider(provider);
	}
	
	/**
	 * @see org.openmrs.module.dataentrydelay.service.DataEntryService#getDataEntryByProviderDate(org.openmrs.User,
	 *      java.util.Date, java.util.Date, java.lang.Boolean)
	 */
	public Collection<Encounter> getDataEntryByProviderDate(User user, Date startdate, Date enddate) {
		// TODO Auto-generated method stub
		return dataentryDAO.getDataEntryByProviderDate(user, startdate, enddate);
	}
	
	/**
     * @see org.openmrs.module.dataentrydelay.service.DataEntryService#getDataEntryByLocation()
     */
    public Collection<Encounter> getDataEntryByLocation(Location location) {
	    // TODO Auto-generated method stub
	    return dataentryDAO.getDataEntryByLocation(location);
    }

	/**
     * @see org.openmrs.module.dataentrydelay.service.DataEntryService#getDataEntryByLocationDate(org.openmrs.Location, java.util.Date, java.util.Date)
     */
    public Collection<Encounter> getDataEntryByLocationDate(Location location, Date startdate, Date enddate) {
	    // TODO Auto-generated method stub
	    return dataentryDAO.getDataEntryByLocationDate(location, startdate, enddate);
    }

	/**
     * @see org.openmrs.module.dataentrydelay.service.DataEntryService#getDataEntryStat(org.openmrs.User, java.util.Date, java.util.Date, org.openmrs.Location)
     */
    public Collection<Encounter> getDataEntryStat(User user, Date startdate, Date enddate, Location location) {
	    // TODO Auto-generated method stub
	    return dataentryDAO.getDataEntryStat(user, startdate, enddate, location);
    }	
}
