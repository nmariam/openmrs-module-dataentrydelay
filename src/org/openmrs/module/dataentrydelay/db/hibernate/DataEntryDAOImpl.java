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
package org.openmrs.module.dataentrydelay.db.hibernate;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.openmrs.Encounter;
import org.openmrs.Location;
import org.openmrs.User;
import org.openmrs.module.dataentrydelay.db.DataEntryDAO;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

/**
 * this class is is help to implement the dataentryService class
 */
public class DataEntryDAOImpl implements DataEntryDAO {
	
	private SessionFactory sessionFactory;
	
	/**
	 * this is used to get dataentryDAO
	 * 
	 * @return dataentryDAO
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	/**
	 * is used to set dataentryDAO
	 * 
	 * @param dataentryDAO
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	/**
	 * @see org.openmrs.module.dataentrydelay.db.DataEntryDAO#getDataEntryByProvider(org.openmrs.User)
	 */
	@SuppressWarnings("unchecked")
	public Collection<Encounter> getDataEntryByProvider(User provider) {
		Session session = getSessionFactory().getCurrentSession();
		List<Encounter> dataentries = session.createCriteria(Encounter.class).add(Restrictions.eq("provider", provider))
		        .list();
		return dataentries;
		
	}
	
	/**
	 * @see org.openmrs.module.dataentrydelay.db.DataEntryDAO#getDataEntryByProviderDate(org.openmrs.User,
	 *      java.util.Date, java.util.Date, java.lang.Boolean)
	 */
	@SuppressWarnings("unchecked")
	public Collection<Encounter> getDataEntryByProviderDate(User provider, Date startDate, Date endDate) {
		Session session = getSessionFactory().getCurrentSession();
		Criteria query = session.createCriteria(Encounter.class);
		if (provider != null)
			query.add(Restrictions.eq("provider", provider));
		
		List<Encounter> listOfEncounters = query.add(
		            Restrictions.between("encounterDatetime", startDate, endDate)).list();
		
		return listOfEncounters;
	}
	
	/**
	 * @see org.openmrs.module.dataentrydelay.db.DataEntryDAO#getDataEntryByLocationDate(org.openmrs.Location,
	 *      java.util.Date, java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	public Collection<Encounter> getDataEntryByLocationDate(Location location, Date startdate, Date enddate) {
		
		Session session = getSessionFactory().getCurrentSession();
		List<Encounter> listOfEncounters = session.createCriteria(Encounter.class)
		        .add(Restrictions.eq("location", location)).add(
		            Restrictions.between("encounterDatetime", startdate, enddate)).list();
		
		return listOfEncounters;
	}
	
	/**
	 * @see org.openmrs.module.dataentrydelay.db.DataEntryDAO#getDataEntryByLocation()
	 */
	@SuppressWarnings("unchecked")
	public Collection<Encounter> getDataEntryByLocation(Location location) {
		Session session = getSessionFactory().getCurrentSession();
		List<Encounter> listOfEncounters = session.createCriteria(Encounter.class)
		        .add(Restrictions.eq("location", location)).list();
		return listOfEncounters;
	}
	
	/**
	 * @see org.openmrs.module.dataentrydelay.db.DataEntryDAO#getDataEntryStat(org.openmrs.User,
	 *      java.util.Date, java.util.Date, org.openmrs.Location)
	 */
	@SuppressWarnings("unchecked")
	public Collection<Encounter> getDataEntryStat(User user, Date startdate, Date enddate, Location location) {
		Session session = getSessionFactory().getCurrentSession();
		
		Criteria query = session.createCriteria(Encounter.class);
		
		if (user != null)
			query.add(Restrictions.eq("provider", user));
		if (location != null)
			query.add(Restrictions.eq("location", location));
		List<Encounter> listOfEncounters = query.add(Restrictions.between("encounterDatetime", startdate, enddate)).list();
		return listOfEncounters;
		
	}
	
}
