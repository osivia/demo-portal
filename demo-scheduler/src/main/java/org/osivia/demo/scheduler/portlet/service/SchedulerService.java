package org.osivia.demo.scheduler.portlet.service;

import java.io.IOException;

import javax.portlet.PortletException;

import org.osivia.demo.scheduler.portlet.model.SchedulerForm;
import org.osivia.portal.api.context.PortalControllerContext;

import net.sf.json.JSONArray;

/**
 * Scheduler service interface
 * @author jbarberet
 *
 */
public interface SchedulerService {

	SchedulerForm getForm(PortalControllerContext portalControllerContext);
	
	/**
	 * List of contributors
	 * @return
	 */
	JSONArray searchContributors(String filter) throws PortletException;
	
	/**
	 * Load scheduler
	 * @param portalControllerContext
	 * @param form
	 * @throws PortletException
	 */
	void loadScheduler(PortalControllerContext portalControllerContext, SchedulerForm form) throws PortletException;
	
	/**
	 * Call startContribution's procedure
	 * @param portalControllerContext
	 * @param selectedContributor
	 * @param selectedDay
	 * @param selectedHalfDay
	 * @throws PortletException
	 * @throws IOException
	 */
	void startContribution(PortalControllerContext portalControllerContext,String selectedContributor, String selectedDay, String selectedHalfDay) throws PortletException, IOException;
	
}
