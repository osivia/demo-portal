package org.osivia.demo.scheduler.calendar.view.portlet.repository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.nuxeo.ecm.automation.client.model.Document;
import org.nuxeo.ecm.automation.client.model.Documents;
import org.nuxeo.ecm.automation.client.model.PropertyMap;
import org.osivia.demo.scheduler.calendar.view.portlet.repository.command.ListWorkspaceCommand;
import org.osivia.demo.scheduler.calendar.view.portlet.repository.command.ReservationListCommand;
import org.osivia.portal.api.PortalException;
import org.osivia.portal.api.context.PortalControllerContext;
import org.osivia.portal.api.urls.IPortalUrlFactory;
import org.osivia.portal.api.windows.PortalWindow;
import org.osivia.portal.api.windows.WindowFactory;
import org.osivia.portal.core.constants.InternalConstants;
import org.osivia.services.calendar.common.repository.CalendarRepositoryImpl;
import org.osivia.services.calendar.common.repository.command.EventRemoveCommand;
import org.osivia.services.calendar.edition.portlet.model.CalendarSynchronizationSource;
import org.osivia.services.calendar.view.portlet.model.CalendarOptions;
import org.osivia.services.calendar.view.portlet.model.CalendarViewForm;
import org.osivia.services.calendar.view.portlet.model.events.Event;
import org.osivia.services.calendar.view.portlet.repository.CalendarViewRepository;
import org.osivia.services.calendar.view.portlet.repository.CalendarViewRepositoryImpl;
import org.osivia.services.calendar.view.portlet.repository.command.EventGetCommand;
import org.osivia.services.calendar.view.portlet.repository.command.EventListCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import fr.toutatice.portail.cms.nuxeo.api.INuxeoCommand;
import fr.toutatice.portail.cms.nuxeo.api.NuxeoController;
import fr.toutatice.portail.cms.nuxeo.api.NuxeoQueryFilterContext;
import fr.toutatice.portail.cms.nuxeo.api.cms.NuxeoDocumentContext;

import static org.osivia.demo.scheduler.portlet.util.SchedulerConstant.CALENDAR_CMS_SUFFIX;

/**
 * Calendar repository implementation.
 *
 * @author Cédric Krommenhoek
 * @author Julien Barberet
 * @see CalendarRepositoryImpl
 * @see CalendarViewRepository
 */
@Repository
@Primary
public class CalendarViewCustomRepositoryImpl extends CalendarViewRepositoryImpl {
    
	private static final String DATA_MAP = "pi:data";
	
	private static final String TASK_MAP = "pi:task";
	
	private static final String TASK_PATH = "ecm:path";
	
	private static final String UUID_RESERVATION_PROPERTY = "uuid";
	
	private static final String DATE_RESERVATION_PROPERTY = "date";
	
	private static final String TIME_SLOT_RESERVATION_PROPERTY = "plage";
	
	private static final String TITLE_RESERVATION_PROPERTY = "titre";
	
	private static final String TIME_SLOT_MORNING = "AM";
	
	private static final String TIME_SLOT_AFTERNOON = "PM";
	
    /** User workspaces default path. */
    private static final String USER_WORKSPACES_DEFAULT_PATH = "/default-domain/UserWorkspaces";
    /** User workspaces default type. */
    private static final String USER_WORKSPACES_DEFAULT_TYPE = "Workspace";
	
    /** Portal URL factory. */
    @Autowired
    private IPortalUrlFactory urlFactory;
	
    /**
     * Constructor.
     */
    public CalendarViewCustomRepositoryImpl() {
        super();

    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String getColorIdAgenda(PortalControllerContext portalControllerContext) throws PortletException {
        // Nuxeo controller
        NuxeoController nuxeoController = new NuxeoController(portalControllerContext.getRequest(), portalControllerContext.getResponse(),
                portalControllerContext.getPortletCtx());

        Document document = this.getDocument(nuxeoController);
        if (document != null)
        {
        	return document.getString(PRIMARY_CALENDAR_COLOR);
        } else
        {
        	return "";
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Event> getEvents(PortalControllerContext portalControllerContext, Date start, Date end) throws PortletException {
        // Nuxeo controller
        NuxeoController nuxeoController = new NuxeoController(portalControllerContext.getRequest(), portalControllerContext.getResponse(),
                portalControllerContext.getPortletCtx());

        // CMS path
        String cmsPath = this.getCMSPath(nuxeoController);

        List<Event> events;
        if (StringUtils.isEmpty(cmsPath)) {
            events = null;
        } else {
            List<CalendarSynchronizationSource> listSource = getSynchronizationSources(portalControllerContext);

            // Nuxeo command
            INuxeoCommand nuxeoCommand = new EventListCommand(NuxeoQueryFilterContext.CONTEXT_LIVE_N_PUBLISHED, cmsPath, start, end, listSource);
            Documents documents = (Documents) nuxeoController.executeNuxeoCommand(nuxeoCommand);

            // Events
            events = new ArrayList<Event>();

            for (Document document : documents) {
                if ((document.getDate(START_DATE_PROPERTY) != null) && (document.getDate(END_DATE_PROPERTY) != null)) {
                    // Event
                    Event event = fillEvent(document, nuxeoController);
                    events.add(event);
                }
            }
            
            //Current user
    		String currentUser = portalControllerContext.getHttpServletRequest().getUserPrincipal().getName();
            
    		List<String> userWorkspacePath = getUserWorkspaces(portalControllerContext, currentUser);
    		boolean isUserWorkspace = false;
    		for (String workspacePath : userWorkspacePath)
    		{
    			if ((cmsPath).equals(workspacePath+CALENDAR_CMS_SUFFIX))
    			{
    				isUserWorkspace = true;
    				break;
    			}
    		}
    		if (isUserWorkspace)
    		{
	            // Nuxeo command
	            nuxeoCommand = new ReservationListCommand(NuxeoQueryFilterContext.CONTEXT_LIVE_N_PUBLISHED, start, end, currentUser);
	            documents = (Documents) nuxeoController.executeNuxeoCommand(nuxeoCommand);
	            
	            for (Document document : documents) {
	                // Event
	                Event event = fillReservation(portalControllerContext, document, nuxeoController);
	                if (event != null) events.add(event);
	            }
    		}
        }

        return events;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Event getEvent(PortalControllerContext portalControllerContext, String docid) throws PortletException {
        // Nuxeo controller
        NuxeoController nuxeoController = new NuxeoController(portalControllerContext.getRequest(), portalControllerContext.getResponse(),
                portalControllerContext.getPortletCtx());
        // CMS path
        String cmsPath = this.getCMSPath(nuxeoController);
        INuxeoCommand nuxeoCommand = new EventGetCommand(cmsPath, docid);
        Document document = (Document) nuxeoController.executeNuxeoCommand(nuxeoCommand);

        return fillEvent(document, nuxeoController);
    }

    private List<String> getUserWorkspaces(PortalControllerContext portalControllerContext, String userName) throws PortletException {
    	
    	// Nuxeo controller
        NuxeoController nuxeoController = new NuxeoController(portalControllerContext.getRequest(), portalControllerContext.getResponse(),
                portalControllerContext.getPortletCtx());
        // Query
        String query = this.getUserWorkspacesQuery(userName);
        // Schemas
        String schemas = "dublincore";
        // Portal policy filter
        String filter = InternalConstants.PORTAL_CMS_REQUEST_FILTERING_POLICY_NO_FILTER;

        // User workspaces
        List<String> workspaces;
        try {
            String liveStatus = Integer.toString(NuxeoQueryFilterContext.STATE_LIVE);
            INuxeoCommand nuxeoCommand = new ListWorkspaceCommand(query, liveStatus, 0, -1, schemas, filter);
            Documents documents = (Documents) nuxeoController.executeNuxeoCommand(nuxeoCommand);

            workspaces = new ArrayList<String>(documents.size());
            for (Document document : documents.list()) {
                String path  = document.getPath();
                workspaces.add(path);
            }
        } catch (Exception e) {
            throw new PortletException(e);
        }

        return workspaces;
    }
    
    /**
     * Get user workspaces query.
     *
     * @param userName user name, may be empty for getting all user workspaces
     * @return query
     */
    private String getUserWorkspacesQuery(String userName) {
        StringBuilder query = new StringBuilder();
        query.append("ecm:primaryType = '");
        query.append(USER_WORKSPACES_DEFAULT_TYPE);
        query.append("' AND ecm:path STARTSWITH '");
        query.append(USER_WORKSPACES_DEFAULT_PATH);
        query.append("/'");
        if (StringUtils.isNotEmpty(userName)) {
            query.append(" AND dc:creator = '");
            query.append(userName);
            query.append("'");
        }
        return query.toString();
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
        String bckgcolor = document.getString(BCKG_COLOR);
        boolean allDay = BooleanUtils.isTrue(document.getProperties().getBoolean(ALL_DAY_PROPERTY));
        String viewURL = nuxeoController.getLink(document).getUrl();
        String idEventSrc;
        String idParentSrc;
        idEventSrc = document.getString(ID_SOURCE_PROPERTY);
        idParentSrc = document.getString(ID_PARENT_SOURCE_PROPERTY);
        return new Event(id, title, startDate, endDate, allDay, bckgcolor, viewURL, idEventSrc, idParentSrc);
    }
    
    private Event fillReservation(PortalControllerContext portalControllerContext, Document document, NuxeoController nuxeoController) throws PortletException
    {
    	
    	PropertyMap map = document.getProperties().getMap(DATA_MAP);
    	String id = map.getString(UUID_RESERVATION_PROPERTY);
        String title = map.getString(TITLE_RESERVATION_PROPERTY);
        Date date = map.getDate(DATE_RESERVATION_PROPERTY);
        //Correction du bug des procédures qui enregistre les dates avec une heure en moins
        date.setTime(date.getTime()+(60*60*1000));
        String timeSlot = map.getString(TIME_SLOT_RESERVATION_PROPERTY);
        Date startDate = null;
        Date endDate = null;
        Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
        if (TIME_SLOT_MORNING.equals(timeSlot))
        {
        	cal.set(Calendar.HOUR_OF_DAY, 8);
        	startDate = cal.getTime();
        	cal.set(Calendar.HOUR_OF_DAY,12);
        	endDate = cal.getTime();
        } else if (TIME_SLOT_AFTERNOON.equals(timeSlot))
        {
        	cal.set(Calendar.HOUR_OF_DAY, 14);
        	startDate = cal.getTime();
        	cal.set(Calendar.HOUR_OF_DAY,18);
        	endDate = cal.getTime();
        }
        
        String url = null;
        map = document.getProperties().getMap(TASK_MAP);
        if (map == null)
        {
        	url = nuxeoController.getLink(document).getUrl();
        } else
        {
	        String path = map.getString(TASK_PATH);
			try {
				url = this.urlFactory.getPermaLink(portalControllerContext, null, null, path, IPortalUrlFactory.PERM_LINK_TYPE_CMS);
			} catch (PortalException e) {
				throw new PortletException(e);
			}
        }
        
        return (startDate == null)? null : new Event(id, title, startDate, endDate, false, null, url, null, null);
    }



    /**
     * Get CMS path.
     *
     * @param nuxeoController Nuxeo controller
     * @return CMS path
     * @throws PortletException
     */
    private String getCMSPath(NuxeoController nuxeoController) throws PortletException {
        // Portlet configuration
        CalendarOptions configuration = this.getConfiguration(nuxeoController.getPortalCtx());

        // Context path
        String cmsPath;
        if (StringUtils.isNotBlank(configuration.getCmsPath())) {
            cmsPath = configuration.getCmsPath();
        } else {
            // Current window
            PortalWindow window = WindowFactory.getWindow(nuxeoController.getRequest());

            cmsPath = window.getPageProperty("osivia.cms.basePath");
        }

        return nuxeoController.getComputedPath(cmsPath);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEventEditable(PortalControllerContext portalControllerContext, String docid) throws PortletException {
        // Request
        PortletRequest request = portalControllerContext.getRequest();
        // Nuxeo controller
        NuxeoController nuxeoController = new NuxeoController(request, portalControllerContext.getResponse(), portalControllerContext.getPortletCtx());
        NuxeoDocumentContext documentContext = nuxeoController.getCurrentDocumentContext();
        return documentContext.getPermissions().isEditable();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(PortalControllerContext portalControllerContext, CalendarViewForm form) throws PortletException {
        // Nuxeo controller
        NuxeoController nuxeoController = new NuxeoController(portalControllerContext);

        INuxeoCommand command = new EventRemoveCommand(form.getDocId());
        nuxeoController.executeNuxeoCommand(command);

    }
}
