package org.osivia.demo.initializer.service;

import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.nuxeo.ecm.automation.client.model.Document;
import org.osivia.demo.initializer.service.commands.CreateExtranetCommand;
import org.osivia.demo.initializer.service.commands.CreateProcedureContainerCommand;
import org.osivia.demo.initializer.service.commands.CreateProcedureModelsCommand;
import org.osivia.demo.initializer.service.commands.LoadRecordsCommand;
import org.osivia.portal.api.cache.services.CacheInfo;
import org.osivia.portal.api.context.PortalControllerContext;
import org.osivia.portal.api.directory.v2.model.Person;
import org.osivia.portal.api.directory.v2.service.PersonService;
import org.osivia.services.workspace.portlet.model.WorkspaceCreationForm;
import org.osivia.services.workspace.portlet.service.WorkspaceCreationService;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private WorkspaceCreationService workspaceCreationService;
	
	@Autowired
	private PersonService personService;
	
	public void initialize(PortalControllerContext portalControllerContext) throws PortletException {
		
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
        
        // Create first workspace
        String workspaceId = "Espace de test";
    	WorkspaceCreationForm form = new WorkspaceCreationForm();
    	form.setTitle(workspaceId);
    	form.setDescription(workspaceId);
    	
    	Person owner = personService.getPerson("demo");
    	
    	form.setOwner(owner.getUid());
    	
    	workspaceCreationService.create(portalControllerContext, form);
    	
    	// Load some records
        nuxeoController.executeNuxeoCommand(new LoadRecordsCommand());


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
