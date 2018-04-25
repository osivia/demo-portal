package org.osivia.demo.initializer.service.commands;

import java.io.File;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nuxeo.ecm.automation.client.OperationRequest;
import org.nuxeo.ecm.automation.client.Session;
import org.nuxeo.ecm.automation.client.adapters.DocumentService;
import org.nuxeo.ecm.automation.client.model.Blob;
import org.nuxeo.ecm.automation.client.model.Document;
import org.nuxeo.ecm.automation.client.model.Documents;
import org.nuxeo.ecm.automation.client.model.FileBlob;
import org.nuxeo.ecm.automation.client.model.PathRef;
import org.nuxeo.ecm.automation.client.model.PropertyMap;

import fr.toutatice.portail.cms.nuxeo.api.INuxeoCommand;

public class CreateExtranetCommand implements INuxeoCommand {

	private Log logger = LogFactory.getLog(CreateExtranetCommand.class);

	
	@Override
	public Object execute(Session nuxeoSession) throws Exception {
		
		DocumentService documentService = nuxeoSession.getAdapter(DocumentService.class);
		
		Document repository = documentService.getDocument(new PathRef("/"));
		
		Documents extranetDomain = documentService.query("SELECT * FROM Domain WHERE ecm:path STARTSWITH '/extranet'");
		Document domain;
		if(extranetDomain.size() < 1 ) {
			// Domain extranet
			PropertyMap properties = new PropertyMap();
			properties.set("dc:title", "Extranet");
			domain = documentService.createDocument(repository, "Domain", "extranet", properties);
		}
		else {
			domain = extranetDomain.get(0);
		}
		

		// Web configurations
		URL webconfigurationUrl = this.getClass().getResource("/docs/extranet/export-webconfiguration.zip");
		File webconfiguration = new File(webconfigurationUrl.getFile());
		Blob blob = new FileBlob(webconfiguration);
		
		OperationRequest operationRequest = nuxeoSession.newRequest("FileManager.Import").setInput(blob);
        operationRequest.setContextProperty("currentDocument", domain);
        operationRequest.set("overwite", "true");

        operationRequest.execute();
        
        // Extranet
		URL extranetUrl = this.getClass().getResource("/docs/extranet/export-pages-extranet.zip");
		File extranet = new File(extranetUrl.getFile());
		blob = new FileBlob(extranet);
		
		operationRequest = nuxeoSession.newRequest("FileManager.Import").setInput(blob);
        operationRequest.setContextProperty("currentDocument", domain);
        operationRequest.set("overwite", "true");

        operationRequest.execute();        
        
        // Mass publication
        Documents extranetPages = documentService.query("SELECT * FROM Document WHERE ecm:path STARTSWITH '/extranet/home'");
        for(Document page : extranetPages) {
        	
			logger.info("Publish page : " + page.getPath());
        	
        	operationRequest = nuxeoSession.newRequest("Document.SetOnLineOperation").setInput(page);
        	operationRequest.execute();
        }
        
		
		return null;
	}

	@Override
	public String getId() {
		return this.getClass().getSimpleName();

	}

}
