package org.osivia.demo.scheduler.portlet.service;

import static org.osivia.demo.scheduler.portlet.util.SchedulerConstant.SCHEDULER_SESSION_DATA;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.jboss.portal.theme.impl.render.dynamic.DynaRenderOptions;
import org.nuxeo.ecm.automation.client.model.Document;
import org.osivia.demo.scheduler.portlet.model.Event;
import org.osivia.demo.scheduler.portlet.model.Reservation;
import org.osivia.demo.scheduler.portlet.model.SchedulerEvent;
import org.osivia.demo.scheduler.portlet.model.SchedulerForm;
import org.osivia.demo.scheduler.portlet.model.SessionInformations;
import org.osivia.demo.scheduler.portlet.model.Technician;
import org.osivia.demo.scheduler.portlet.repository.SchedulerRepository;
import org.osivia.portal.api.Constants;
import org.osivia.portal.api.PortalException;
import org.osivia.portal.api.context.PortalControllerContext;
import org.osivia.portal.api.directory.v2.model.Person;
import org.osivia.portal.api.urls.IPortalUrlFactory;
import org.osivia.portal.core.cms.CMSException;
import org.osivia.portal.core.cms.CMSServiceCtx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import fr.toutatice.portail.cms.nuxeo.api.NuxeoController;
import fr.toutatice.portail.cms.nuxeo.api.services.INuxeoCustomizer;
import fr.toutatice.portail.cms.nuxeo.api.services.INuxeoService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class SchedulerServiceImpl implements SchedulerService {

    /** Application context. */
    @Autowired
    private ApplicationContext applicationContext;
	
	@Autowired
	private SchedulerRepository repository;
	
	@Autowired
	private IPortalUrlFactory portalUrlFactory;
	
    /** Nuxeo service. */
    @Autowired
    private INuxeoService nuxeoService;
	
	private static final String WEBID = "procedure_demande-intervention";
	
	private static final String PROCEDURE_PORTLET_INSTANCE = "osivia-services-procedure-portletInstance";
	
	private static final long TWELVE_HOURS = (12*60*60*1000);
	
	private static final long ONE_DAY = (24*60*60*1000);
	
	private static final long ONE_HOUR = (60*60*1000);
	
	
	/**
     * {@inheritDoc}
     */
    @Override
	public SchedulerForm getForm(PortalControllerContext portalControllerContext)
	{
    	SchedulerForm form = new SchedulerForm();
    	
		//Current user
		String currentUser = portalControllerContext.getHttpServletRequest().getUserPrincipal().getName();

        SessionInformations sessionInformations;
        Object sessionInformationsAttribute = portalControllerContext.getHttpServletRequest().getSession().getAttribute(SCHEDULER_SESSION_DATA);
        if ((sessionInformationsAttribute != null) && (sessionInformationsAttribute instanceof SessionInformations)) {
            sessionInformations = (SessionInformations) sessionInformationsAttribute;
        } else {
			sessionInformations = this.applicationContext.getBean(SessionInformations.class);
			
			//Set technicians and customerUsers in form
	    	this.repository.setCustomerInformation(portalControllerContext, sessionInformations, currentUser);
		}

		form.setSessionInformations(sessionInformations);
		sessionInformations.setNeedTimeSlotLoad((sessionInformations.getSelectedContributor() != null));
		return form;
	}
	
    public List<Technician> loadContributors()
    {
    	List<Person> listPerson = this.repository.searchPerson("");
    	List<Technician> listContributor = new ArrayList<Technician>();
    	for (Person person : listPerson)
    	{
    		Technician contributor = new Technician(person);
    		listContributor.add(contributor);
    	}
    	return listContributor;
    }
    
	/**
     * {@inheritDoc}
     */
    @Override
	public JSONArray searchContributors(String filter)
	{
        // JSON objects
        List<JSONObject> objects = new ArrayList<>();
    	
		List<Person> list = repository.searchPerson(filter);
		for (Person person : list)
		{
			// Search result
			objects.add(personToJSon(person));
		}
        
        // Items JSON array
        JSONArray items = new JSONArray();
        items.addAll(objects);
//        
//        results.put("items", items);
		return items;
	}

    /**
     * Get search result JSON Object.
     * 
     * @param person person
     * @param alreadyMember already member indicator
     * @param existingRequest existing request indicator
     * @param bundle internationalization bundle
     * @return JSON object
     */
    private JSONObject personToJSon(Person person) {
        JSONObject object = new JSONObject();
        object.put("id", person.getUid());

        // Display name
        String displayName;
        // Extra
        String extra;

        if (StringUtils.isEmpty(person.getDisplayName())) {
            displayName = person.getUid();
            extra = null;
        } else {
            displayName = person.getDisplayName();

            extra = person.getUid();
            if (StringUtils.isNotBlank(person.getMail()) && !StringUtils.equals(person.getUid(), person.getMail())) {
                extra += " – " + person.getMail();
            }
        }

        object.put("text", displayName);
        object.put("extra", extra);

        object.put("avatar", person.getAvatar().getUrl());
        object.put("uid", person.getUid());

        return object;
    }
       
    /**
     * {@inheritDoc}
     * @throws PortletException 
     */
	@Override
	public void loadScheduler(PortalControllerContext portalControllerContext, SchedulerForm form) throws PortletException {
		// CMS customizer
        INuxeoCustomizer cmsCustomizer = this.nuxeoService.getCMSCustomizer();
        
        // Nuxeo controller
        NuxeoController nuxeoController = new NuxeoController(portalControllerContext);
        
        // CMS context
        CMSServiceCtx cmsContext = nuxeoController.getCMSCtx();
        
        //Reset time slot availability 
        resetAvailability(form);
        
        Calendar mondayMorning = getCalendar(form.getMonday(), 0);
		final long milliMondayMorning = mondayMorning.getTimeInMillis();		

        Calendar calFridayNight = getCalendar(form.getFriday(), 0);
        calFridayNight.add(Calendar.DAY_OF_MONTH, 1);
        long milliFridayNight = calFridayNight.getTimeInMillis();
        
        //Start date
		String startDate = DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT.format(mondayMorning.getTime());
		//End date
        String endDate = DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT.format(calFridayNight.getTime());
        
        Map<String, SchedulerEvent> mapDay = new HashMap<>();
        
        //Events from the user workspaces
        try {
			List<Document> listWorkspaces = cmsCustomizer.getUserWorkspaces(cmsContext, form.getSelectedContributor());

	        int start = 0;
	        long milliStart;
	        long milliEnd;
			for (Document workspace: listWorkspaces)
			{
				List<Event> events = this.repository.getEvents(portalControllerContext, startDate, endDate, workspace.getPath());
				for (Event event : events)
				{
					milliStart = event.getStartDate().getTime();
					milliStart = Math.max(milliStart, milliMondayMorning);

					milliEnd = event.getEndDate().getTime();
					milliEnd = Math.min(milliFridayNight, milliEnd);
					
					float nb = (float) (milliStart - milliMondayMorning)/TWELVE_HOURS;
					start = new BigDecimal(nb).setScale(0, RoundingMode.DOWN).intValue();
					
					timeSlot(start, milliMondayMorning, milliEnd, mapDay);
				}
			}
			
		} catch (CMSException e) {
			throw new PortletException(e);
		}
        
        //Pour corriger le bug des dates qui sont enregistrées via les procédures avec une heure en moins
        mondayMorning.setTimeInMillis(mondayMorning.getTimeInMillis()-ONE_HOUR);
        //Start date
      	startDate = DateFormatUtils.ISO_DATE_FORMAT.format(mondayMorning.getTime());
      	//End date
        endDate = DateFormatUtils.ISO_DATE_FORMAT.format(calFridayNight.getTime());
        
        //Reservations
        List<Reservation> reservations = this.repository.getReservations(portalControllerContext, startDate, endDate, form.getSelectedContributor(), form.getCustomerUsers());
        for (Reservation reservation: reservations)
		{
        	float nbMillis = Math.abs((float) (reservation.getDay().getTime() - milliMondayMorning));
        	int nbDays = new BigDecimal((float) (nbMillis/ONE_DAY)).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
        	int nbHalfDays = nbDays*2 + ("PM".equals(reservation.getTimeSlot())? 1 : 0);
        	SchedulerEvent schedulerEvent = new SchedulerEvent(true);
    		schedulerEvent.setTitle(reservation.getTitle());
    		schedulerEvent.setComment(reservation.getComment());
    		schedulerEvent.setAccepted(reservation.isAccepted());
    		schedulerEvent.setCreator(reservation.getCreatorName());
    		schedulerEvent.setDateCreationReservation(reservation.getDateCreationReservation());
        	if ((form.getCustomerUsers() != null) && form.getCustomerUsers().contains(reservation.getCreatorId()))
        	{
        		mapDay.put(Integer.toString(nbHalfDays), schedulerEvent);
        	} else
        	{
        		schedulerEvent.setReservation(false);
        		mapDay.put(Integer.toString(nbHalfDays), schedulerEvent);
        	}
		}
        
        //Set busy time slot
		setBusyTimeSlot(form, mapDay);
	}
	
	/**
	 * Update mapDay with new busy time slots
	 * @param start the beginning time slot (0 is monday morning and 9 is friday afternoon)
	 * @param milliMondayMorning number of milliseconds of monday morning
	 * @param milliEnd number of milliseconds of the end of the event
	 * @param mapDay
	 */
	private void timeSlot(int start, long milliMondayMorning, long milliEnd, Map<String, SchedulerEvent> mapDay)
	{
		float nb = (float) (milliEnd - milliMondayMorning)/TWELVE_HOURS;
		BigDecimal halfDay = new BigDecimal(nb).setScale(0, RoundingMode.UP);
		int nbHalfDay = halfDay.intValue() - start;
		SchedulerEvent schedulerEvent = new SchedulerEvent(false);
		for (int i=0; i <nbHalfDay; i++)
		{
			mapDay.put(Integer.toString(i+start), schedulerEvent);
		}
	}
	
	/**
	 * Get calendar from startDate and hourOfDay parameters
	 * @param startDate start date
	 * @param hourOfDay
	 * @return
	 */
	private Calendar getCalendar(Date startDate, int hourOfDay)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar;
	}
	
	/**
	 * Set form busy time slots from a map of busy time slots
	 * @param form scheduler form
	 * @param mapDay map of busy time slots
	 */
	private void setBusyTimeSlot(SchedulerForm form, Map<String, SchedulerEvent> mapDay)
	{
		//First set morning
		if (mapDay.get("0")!= null) form.getTimeSlots()[0] = mapDay.get("0");
		if (mapDay.get("2")!= null) form.getTimeSlots()[1] = mapDay.get("2");
		if (mapDay.get("4")!= null) form.getTimeSlots()[2] = mapDay.get("4");
		if (mapDay.get("6")!= null) form.getTimeSlots()[3] = mapDay.get("6");
		if (mapDay.get("8")!= null) form.getTimeSlots()[4] = mapDay.get("8");
		//Then set afternoon
		if (mapDay.get("1")!= null) form.getTimeSlots()[5] = mapDay.get("1");
		if (mapDay.get("3")!= null) form.getTimeSlots()[6] = mapDay.get("3");
		if (mapDay.get("5")!= null) form.getTimeSlots()[7] = mapDay.get("5");
		if (mapDay.get("7")!= null) form.getTimeSlots()[8] = mapDay.get("7");
		if (mapDay.get("9")!= null) form.getTimeSlots()[9] = mapDay.get("9");
	}
	
	/**
	 * Set all time slots of the form to available
	 * @param form scheduler form
	 */
	private void resetAvailability(SchedulerForm form)
	{
		form.setTimeSlots(new SchedulerEvent[10]);
	}

	@Override
	public void startContribution(PortalControllerContext portalControllerContext, String selectedContributor,
			String selectedDay, String selectedHalfDay) throws PortletException, IOException {

        final Map<String, String> windowProperties = new HashMap<String, String>();
        
        windowProperties.put("osivia.services.procedure.webid", WEBID);
        windowProperties.put("osivia.doctype", "ProcedureModel");
        windowProperties.put("osivia.hideDecorators", "1");
        windowProperties.put(DynaRenderOptions.PARTIAL_REFRESH_ENABLED, Constants.PORTLET_VALUE_ACTIVATE);
        windowProperties.put("osivia.ajaxLink", "1");

        JSONObject variables = new JSONObject();
        variables.put("intervenant", selectedContributor);
        variables.put("date", selectedDay);
        variables.put("plage", selectedHalfDay);
        windowProperties.put("osivia.services.procedure.variables", variables.toString());
		
		String url;
		try {
			url = this.portalUrlFactory.getStartPortletUrl(portalControllerContext, PROCEDURE_PORTLET_INSTANCE, windowProperties);
		} catch (PortalException e) {
			throw new PortletException(e);
		}
		ActionResponse response  = (ActionResponse) portalControllerContext.getResponse();
		response.sendRedirect(url);
		
	}
	
}
