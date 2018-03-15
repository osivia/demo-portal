package org.osivia.demo.scheduler.portlet.repository;

import java.util.List;

import javax.portlet.PortletException;

import org.osivia.demo.scheduler.portlet.model.Event;
import org.osivia.demo.scheduler.portlet.model.Reservation;
import org.osivia.demo.scheduler.portlet.model.SchedulerForm;
import org.osivia.portal.api.context.PortalControllerContext;
import org.osivia.portal.api.directory.v2.model.Person;

public interface SchedulerRepository {

    //Constants of VEVENT
    /** Calendar event document type name. */
    String DOCUMENT_TYPE_EVENEMENT = "VEVENT";
    /** Start date Nuxeo property. */
    String START_DATE_PROPERTY = "vevent:dtstart";
    /** End date Nuxeo property. */
    String END_DATE_PROPERTY = "vevent:dtend";
    /** All day Nuxeo property. */
    String ALL_DAY_PROPERTY = "vevent:allDay";
    /** Background color */
    String BCKG_COLOR = "vevent:color";
    /** Title */
    String TITLE_PROPERTY = "dc:title";
    /** Description */
    String DESCRIPTION_PROPERTY = "dc:description";
    
    //Constants of Agenda
    /** Calendar document type name. */
    String DOCUMENT_TYPE_AGENDA = "Agenda";
    /** URL to synchronize agenda */
    String URL_SYNCHRONIZATION = "url";
    /** Source ID */
    String SOURCEID_SYNCHRONIZATION = "sourceId";
    /** Default color of synchronized events */
    String COLOR_SYNCHRONIZATION = "color";
    /** Default color of synchronized events */
    String DISPLAYNAME_SYNCHRONIZATION = "displayName";
    /** List of synchronization sources */
    String LIST_SOURCE_SYNCHRO = "cal:sources";
    /** Color of the primary calendar */
    String PRIMARY_CALENDAR_COLOR = "cal:color";
	
    /**
     * List all events in a user workspace in the week starting from the start date
     * @param portalControllerContext
     * @param startDate
     * @param endDate
     * @param workspacePath
     * @return
     * @throws PortletException
     */
    List<Event> getEvents(PortalControllerContext portalControllerContext, String startDate, String endDate, String workspacePath) throws PortletException;
    
    /**
     * List all reservations between start date and end date with that intervenant
     * @param portalControllerContext
     * @param startDate
     * @param endDate
     * @param intervenant
     * @param customerUsers
     * @return
     */
    List<Reservation> getReservations(PortalControllerContext portalControllerContext, String startDate, String endDate, String intervenant, List<String> customerUsers);
    
    /**
     * List all persons
     * @param portalControllerContext
     * @return
     */
    List<Person> searchPerson(String filter);
    
    /**
     * List all users id of a client
     * @param portalControllerContext
     * @param schedulerForm
     * @param user
     */
    void setCustomerInformation(PortalControllerContext portalControllerContext, SchedulerForm form, String user);
}
