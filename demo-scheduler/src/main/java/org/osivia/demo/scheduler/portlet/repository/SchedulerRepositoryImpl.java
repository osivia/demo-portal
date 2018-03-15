package org.osivia.demo.scheduler.portlet.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.portlet.PortletException;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.nuxeo.ecm.automation.client.model.Document;
import org.nuxeo.ecm.automation.client.model.Documents;
import org.nuxeo.ecm.automation.client.model.PropertyList;
import org.nuxeo.ecm.automation.client.model.PropertyMap;
import org.osivia.demo.scheduler.portlet.model.Event;
import org.osivia.demo.scheduler.portlet.model.Reservation;
import org.osivia.demo.scheduler.portlet.model.SchedulerForm;
import org.osivia.demo.scheduler.portlet.model.Technician;
import org.osivia.demo.scheduler.portlet.repository.command.CustomerCommand;
import org.osivia.demo.scheduler.portlet.repository.command.EventListCommand;
import org.osivia.demo.scheduler.portlet.repository.command.ProcedureInstanceListCommand;
import org.osivia.portal.api.context.PortalControllerContext;
import org.osivia.portal.api.directory.v2.model.Person;
import org.osivia.portal.api.directory.v2.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.toutatice.portail.cms.nuxeo.api.INuxeoCommand;
import fr.toutatice.portail.cms.nuxeo.api.NuxeoController;
import fr.toutatice.portail.cms.nuxeo.api.NuxeoQueryFilterContext;

/**
 * Scheduler Repository Implementation
 * @author Julien Barberet
 *
 */
@Repository
public class SchedulerRepositoryImpl implements SchedulerRepository{

	public static final String CALENDAR_PATH_SUFFIX = "/calendrier";
	
	private static final String PROCEDURE_INSTANCE_MAP = "pi:data";
	
	private static final String RESERVATION_DATE = "date";
	
	private static final String RESERVATION_TIME_SLOT = "plage";
	
	private static final String RESERVATION_OBJECT = "objet";
	
	private static final String RESERVATION_ACCEPTED = "accepted";
	
	private static final String DATA_MAP = "rcd:data";
	
	private static final String CUSTOMER_USERS_DATA = "customerusers";
	
	private static final String CUSTOMER_USER_PROPERTY = "user";
	
	private static final String TECHNICIANS_DATA = "technicianusers";
	
	private static final String TECHNICIAN_PROPERTY = "technician";
	
	private static final String CREATOR_PROPERTY = "dc:creator";
	
	private static final String CREATION_DATE_PROPERTY = "dc:created";
	
	@Autowired
	private PersonService personService;
	
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Event> getEvents(PortalControllerContext portalControllerContext, String startDate, String endDate, String workspacePath) throws PortletException {
        // Nuxeo controller
        NuxeoController nuxeoController = new NuxeoController(portalControllerContext.getRequest(), portalControllerContext.getResponse(),
                portalControllerContext.getPortletCtx());

        // CMS path
        String contextPath = workspacePath+CALENDAR_PATH_SUFFIX;

        List<Event> events;
        // Nuxeo command
        INuxeoCommand nuxeoCommand = new EventListCommand(NuxeoQueryFilterContext.CONTEXT_LIVE_N_PUBLISHED, contextPath, startDate, endDate);
        Documents documents = (Documents) nuxeoController.executeNuxeoCommand(nuxeoCommand);

        // Events
        events = new ArrayList<Event>(documents.size());

        for (Document document : documents) {
            if ((document.getDate(START_DATE_PROPERTY) != null) && (document.getDate(END_DATE_PROPERTY) != null)) {
                // Event
                Event event = fillEvent(document, nuxeoController);
                events.add(event);
            }
        }

        return events;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Reservation> getReservations(PortalControllerContext portalControllerContext, String startDate, String endDate, String intervenant, List<String> customerUsers)
    {
    	// Nuxeo controller
        NuxeoController nuxeoController = new NuxeoController(portalControllerContext.getRequest(), portalControllerContext.getResponse(),
                portalControllerContext.getPortletCtx());

        // Nuxeo command
        INuxeoCommand nuxeoCommand = new ProcedureInstanceListCommand(NuxeoQueryFilterContext.CONTEXT_LIVE_N_PUBLISHED, startDate, endDate, intervenant, customerUsers);
        Documents documents = (Documents) nuxeoController.executeNuxeoCommand(nuxeoCommand);
        List<Reservation> reservations = new ArrayList<>();
        for (Document document : documents) {
        	Reservation reservation = new Reservation();
        	PropertyMap map = document.getProperties().getMap(PROCEDURE_INSTANCE_MAP);
        	reservation.setDay(map.getDate(RESERVATION_DATE));
        	reservation.setTimeSlot(map.getString(RESERVATION_TIME_SLOT));
        	reservation.setObject(map.getString(RESERVATION_OBJECT));
        	reservation.setAccepted("true".equals(map.getString(RESERVATION_ACCEPTED)));
        	String creator = document.getString(CREATOR_PROPERTY);
        	reservation.setCreatorId(creator);
        	if (null != creator)
        	{
        		Person person = personService.getPerson(creator);
        		creator = person.getDisplayName();
        	}
        	reservation.setCreatorName(creator);
        	reservation.setDateCreationReservation(document.getDate(CREATION_DATE_PROPERTY));
        	reservations.add(reservation);
        }
        return reservations;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Person> searchPerson(String filter)
    {
    	// Criteria
        Person criteria = this.personService.getEmptyPerson();
        
        // Stripped filter
        String strippedFilter = StringUtils.strip(StringUtils.trimToEmpty(filter), "*");

        String tokenizedFilter = strippedFilter + "*";
        String tokenizedFilterSubStr;
        if (StringUtils.isEmpty(strippedFilter)) {
            tokenizedFilterSubStr = tokenizedFilter;
        } else {
            tokenizedFilterSubStr = "*" + strippedFilter + "*";
        }

        criteria.setUid(tokenizedFilter);
        criteria.setCn(tokenizedFilter);
        criteria.setSn(tokenizedFilter);
        criteria.setGivenName(tokenizedFilter);
        criteria.setMail(tokenizedFilter);

        criteria.setDisplayName(tokenizedFilterSubStr);
        
        return this.personService.findByCriteria(criteria);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setCustomerInformation(PortalControllerContext portalControllerContext, SchedulerForm form, String user)
    {
    	// Nuxeo controller
        NuxeoController nuxeoController = new NuxeoController(portalControllerContext.getRequest(), portalControllerContext.getResponse(),
                portalControllerContext.getPortletCtx());

        // Nuxeo command
        INuxeoCommand nuxeoCommand = new CustomerCommand(NuxeoQueryFilterContext.CONTEXT_LIVE_N_PUBLISHED, user);
        
        Documents documents = (Documents) nuxeoController.executeNuxeoCommand(nuxeoCommand);
        
        List<String> customerUsers = new ArrayList<>();
        List<Technician> technicians = new ArrayList<>();
        if (documents.size()>0) {
        	Document document = documents.get(0);
        	PropertyMap map = document.getProperties().getMap(DATA_MAP);
        	
        	//Customer users data
        	PropertyList list = map.getList(CUSTOMER_USERS_DATA);
        	List listUsers = list.getList();
        	for (Object object : listUsers)
        	{
        		if (object != null)
        		{
        			customerUsers.add(((PropertyMap) object).getString(CUSTOMER_USER_PROPERTY));
        		}
        	}
        	
        	//Customer technicians data
        	list = map.getList(TECHNICIANS_DATA);
        	listUsers = list.getList();
        	for (Object object : listUsers)
        	{
        		if (object != null)
        		{
        			Person person = personService.getPerson(((PropertyMap) object).getString(TECHNICIAN_PROPERTY));
        			if (person != null) technicians.add(new Technician(person));
        		}
        	}
        	form.setCustomerUsers(customerUsers);
        	form.setTechnicians(technicians);
        }        
    }
    
    /**
     * Fill event attributes
     * 
     * @param document
     * @param nuxeoController
     * @return event filled
     */
    private Event fillEvent(Document document, NuxeoController nuxeoController) {
        String id = document.getId();
        String title = document.getTitle();
        Date startDate = document.getDate(START_DATE_PROPERTY);
        Date endDate = document.getDate(END_DATE_PROPERTY);
        boolean allDay = BooleanUtils.isTrue(document.getProperties().getBoolean(ALL_DAY_PROPERTY));
        
        return new Event(id, title, startDate, endDate, allDay);
    }
	
}
