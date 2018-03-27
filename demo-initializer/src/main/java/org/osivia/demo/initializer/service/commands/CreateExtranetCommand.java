package org.osivia.demo.initializer.service.commands;

import org.nuxeo.ecm.automation.client.Session;
import org.nuxeo.ecm.automation.client.adapters.DocumentService;
import org.nuxeo.ecm.automation.client.model.Document;
import org.nuxeo.ecm.automation.client.model.Documents;
import org.nuxeo.ecm.automation.client.model.PathRef;
import org.nuxeo.ecm.automation.client.model.PropertyMap;

import fr.toutatice.portail.cms.nuxeo.api.INuxeoCommand;

public class CreateExtranetCommand implements INuxeoCommand {

	@Override
	public Object execute(Session nuxeoSession) throws Exception {
		
		DocumentService documentService = nuxeoSession.getAdapter(DocumentService.class);
		
		Document repository = documentService.getDocument(new PathRef("/"));
		
		Documents extranetDomain = documentService.query("SELECT * FROM Domain WHERE ecm:path STARTSWITH '/extranet'");
		Document domain;
		if(extranetDomain.size() < 1 ) {
			// Procedure container
			PropertyMap properties = new PropertyMap();
			properties.set("dc:title", "Extranet");
			domain = documentService.createDocument(repository, "Domain", "extranet", properties);
		}
		else {
			domain = extranetDomain.get(0);
		}
		
		Documents homePortalSite = documentService.query("SELECT * FROM PortalSite WHERE ecm:path STARTSWITH '"+domain.getPath()+"'");
		Document portalSite;
		if(homePortalSite.size() < 1 ) {
			// Procedure container
			PropertyMap properties = new PropertyMap();
			properties.set("dc:title", "Accueil");
			portalSite = documentService.createDocument(domain, "PortalSite", "home", properties);
		}
		else {
			portalSite = homePortalSite.get(0);
		}
		
		
		return null;
	}

	@Override
	public String getId() {
		return this.getClass().getSimpleName();

	}

}
