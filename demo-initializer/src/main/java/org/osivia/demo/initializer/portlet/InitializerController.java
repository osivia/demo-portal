package org.osivia.demo.initializer.portlet;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;

import org.osivia.demo.initializer.service.InitializerService;
import org.osivia.portal.api.Constants;
import org.osivia.portal.api.PortalException;
import org.osivia.portal.api.context.PortalControllerContext;
import org.osivia.portal.api.urls.IPortalUrlFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

/**
 * 
 * @author Loïc Billon
 *
 */
@Controller
@RequestMapping(value = "VIEW")
public class InitializerController {

	private static final String DEFAULT_VIEW = "view";

	@Autowired
	private InitializerService service;
	
    /** Portlet context. */
	@Autowired
    private PortletContext portletContext;
	
	@Autowired
	private IPortalUrlFactory urlFactory;
	
	@RenderMapping
    public String view(RenderRequest request, RenderResponse response) throws PortletException {
		
		return DEFAULT_VIEW;
	}
	
    @ActionMapping(params = "action=checkInit")
    public void checkInit(ActionRequest request, ActionResponse response) throws PortalException, IOException {
    	
    	PortalControllerContext portalControllerContext = new PortalControllerContext(portletContext, request, response);
		service.initialize(portalControllerContext);
    	
    	// Get redirect url from session
        HttpServletRequest servletRequest = (HttpServletRequest) request.getAttribute(Constants.PORTLET_ATTR_HTTP_REQUEST);
        String redirectUrl = (String) servletRequest.getSession().getAttribute("osivia.platform.init.pathToRedirect");
        
        if( redirectUrl != null)    {
            String closeURL = this.urlFactory.getDestroyCurrentPageUrl(portalControllerContext, redirectUrl);
            response.sendRedirect(closeURL);
        }
    }

}
