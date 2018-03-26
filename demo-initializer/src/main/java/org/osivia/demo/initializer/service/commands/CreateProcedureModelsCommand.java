package org.osivia.demo.initializer.service.commands;

import java.io.File;
import java.net.URL;

import org.nuxeo.ecm.automation.client.OperationRequest;
import org.nuxeo.ecm.automation.client.Session;
import org.nuxeo.ecm.automation.client.model.Blob;
import org.nuxeo.ecm.automation.client.model.Document;
import org.nuxeo.ecm.automation.client.model.FileBlob;

import fr.toutatice.portail.cms.nuxeo.api.INuxeoCommand;

public class CreateProcedureModelsCommand implements INuxeoCommand {

	private Document modelsContainer;
	
	public CreateProcedureModelsCommand(Document modelsContainer) {
		this.modelsContainer = modelsContainer;
	}

	@Override
	public Object execute(Session nuxeoSession) throws Exception {
		
		URL procedureUrl = this.getClass().getResource("/docs/models/export-model-invitation.zip");
		Blob blob = new FileBlob(new File(procedureUrl.getFile()));
		
		OperationRequest operationRequest = nuxeoSession.newRequest("FileManager.Import").setInput(blob);
        operationRequest.setContextProperty("currentDocument", this.modelsContainer.getId());
        operationRequest.set("overwite", "true");

        return operationRequest.execute();
		
	}

	@Override
	public String getId() {
		return this.getClass().getSimpleName();
	}

}
