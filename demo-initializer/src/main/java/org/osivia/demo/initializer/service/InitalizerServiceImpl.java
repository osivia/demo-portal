package org.osivia.demo.initializer.service;

import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.nuxeo.ecm.automation.client.model.Document;
import org.osivia.demo.initializer.service.commands.CreateExtranetCommand;
import org.osivia.demo.initializer.service.commands.CreateProcedureContainerCommand;
import org.osivia.demo.initializer.service.commands.CreateProcedureModelsCommand;
import org.osivia.portal.api.cache.services.CacheInfo;
import org.osivia.portal.api.context.PortalControllerContext;
import org.springframework.stereotype.Service;

import fr.toutatice.portail.cms.nuxeo.api.NuxeoController;
import fr.toutatice.portail.cms.nuxeo.api.services.NuxeoCommandContext;

/**
 * 
 * @author Loïc Billon
 *
 */
@Service
public class InitalizerServiceImpl implements InitializerService {

	public void initialize(PortalControllerContext portalControllerContext) {
		
		// Nuxeo controller
        NuxeoController nuxeoController = this.getNuxeoController(portalControllerContext);
        nuxeoController.setAuthType(NuxeoCommandContext.AUTH_TYPE_SUPERUSER);
        nuxeoController.setCacheType(CacheInfo.CACHE_SCOPE_PORTLET_CONTEXT);
        nuxeoController.setAsynchronousCommand(false);
		
        // Containers
        Document modelsContainer = (Document) nuxeoController.executeNuxeoCommand(new CreateProcedureContainerCommand());
        
        // Models
        nuxeoController.executeNuxeoCommand(new CreateProcedureModelsCommand(modelsContainer));
        
        // Extranet
        nuxeoController.executeNuxeoCommand(new CreateExtranetCommand());

	}
	
	/**
     * Get Nuxeo controller
     *
     * @param portalControllerContext portal controller context
     * @return Nuxeo controller
     */
    private NuxeoController getNuxeoController(PortalControllerContext portalControllerContext) {
        PortletRequest request = portalControllerContext.getRequest();
        PortletResponse response = portalControllerContext.getResponse();
        PortletContext portletContext = portalControllerContext.getPortletCtx();
        return new NuxeoController(request, response, portletContext);
    }

}
