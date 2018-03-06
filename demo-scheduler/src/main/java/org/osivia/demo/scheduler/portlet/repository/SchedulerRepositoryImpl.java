package org.osivia.demo.scheduler.portlet.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.portlet.PortletException;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.nuxeo.ecm.automation.client.model.Document;
import org.nuxeo.ecm.automation.client.model.Documents;
import org.osivia.demo.scheduler.portlet.model.Event;
import org.osivia.demo.scheduler.portlet.repository.command.EventListCommand;
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
    public List<Person> searchContributor(String filter)
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
