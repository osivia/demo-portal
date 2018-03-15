package org.osivia.demo.scheduler.portlet.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osivia.demo.scheduler.portlet.model.SchedulerForm;
import org.osivia.demo.scheduler.portlet.model.SessionInformations;
import org.osivia.demo.scheduler.portlet.service.SchedulerService;
import org.osivia.portal.api.context.PortalControllerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import net.sf.json.JSONArray;

/**
 * Demo scheduler view portlet controller
 * @author jbarberet
 *
 */
@Controller
@RequestMapping(value = "VIEW")
@SessionAttributes("schedulerForm")
public class ViewSchedulerController {

	
	/** Portlet service. */
    @Autowired
    private SchedulerService service;
    /** Portlet context */
    @Autowired
    private PortletContext portletContext;
	
    /**
     * Constructor
     */
    public ViewSchedulerController() {
        super();
    }

    /**
     * View demo scheduler render mapping
     * @param request
     * @param response
     * @param form
     * @return
     */
    @RenderMapping
    public String view(RenderRequest request, RenderResponse response, @ModelAttribute("schedulerForm") SchedulerForm form)
    {
        return "view";
    }
    
    /**
     * Action called to load contributor's scheduler
     * @param request
     * @param response
     * @param form
     * @throws PortletException
     */
    @ActionMapping(name = "loadScheduler")
    public void loadScheduler(ActionRequest request, ActionResponse response, 
    		@ModelAttribute("schedulerForm") SchedulerForm form)
            throws PortletException {
    	
    	PortalControllerContext portalControllerContext = new PortalControllerContext(this.portletContext, request, response);
    	this.service.loadScheduler(portalControllerContext, form);
    	
    	//Stay on the edit page
        response.setRenderParameter("view","view");
    	
    }
    
    /**
     * Action to call the startContribution procedure
     * @param request
     * @param response
     * @param form
     * @param halfDay
     * @param day
     * @throws PortletException
     * @throws IOException
     */
    @ActionMapping(name = "startContribution")
    public void startContribution(ActionRequest request, ActionResponse response,
    		@ModelAttribute("schedulerForm") SchedulerForm form,
    		@RequestParam("halfDay") String halfDay,
    		@RequestParam("day") String day)
            throws PortletException, IOException {
    	PortalControllerContext portalControllerContext = new PortalControllerContext(this.portletContext, request, response);

    	if (form.getSelectedContributor() != null)  
    	{
    		this.service.startContribution(portalControllerContext, form.getSelectedContributor(), day, halfDay);
    	}

    }
    
    /**
     * Action called to move back a week's agenda
     * @param request
     * @param response
     * @param form
     * @throws PortletException
     * @throws IOException
     */
    @ActionMapping(name = "previousWeek")
    public void previousWeek(ActionRequest request, ActionResponse response,
    		@ModelAttribute("schedulerForm") SchedulerForm form)
            throws PortletException, IOException {

    	form.previousWeek();
    	
    	loadScheduler(request, response, form);
    }
    
    /**
     * Action called to advance the agenda of a week
     * @param request
     * @param response
     * @param form
     * @throws PortletException
     * @throws IOException
     */
    @ActionMapping(name = "nextWeek")
    public void nextWeek(ActionRequest request, ActionResponse response,
    		@ModelAttribute("schedulerForm") SchedulerForm form)
            throws PortletException, IOException {

    	form.nextWeek();
    	
    	loadScheduler(request, response, form);
    }
    
    /**
     * Search persons resource mapping.
     *
     * @param request resource request
     * @param response resource response
     * @param options options model attribute
     * @param filter search filter request parameter
     * @param page pagination page number request parameter
     * @param tokenizer tokenizer indicator request parameter
     * @throws PortletException
     * @throws IOException
     */
    @ResourceMapping("searchContributor")
    public void searchContributor(ResourceRequest request, ResourceResponse response,
            @RequestParam(value = "filter", required = false) String filter) throws PortletException, IOException {
    	
    	// Search results
    	JSONArray results = this.service.searchContributors(filter);

        // Content type
        response.setContentType("application/json");

        // Content
        PrintWriter printWriter = new PrintWriter(response.getPortletOutputStream());
        printWriter.write(results.toString());
        printWriter.close();
    }
    
    /**
     * Get scheduler form model attribute.
     * 
     * @param request portlet request
     * @param response portlet response
     * @return form
     * @throws PortletException
     */
    @ModelAttribute(value = "schedulerForm")
    public SchedulerForm getForm(PortletRequest request, PortletResponse response) throws PortletException {

    	PortalControllerContext portalControllerContext = new PortalControllerContext(this.portletContext, request, response);

    	return this.service.getForm(portalControllerContext);
    }
    
}
