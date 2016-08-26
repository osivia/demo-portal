package org.osivia.portal.demo.customizer;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nuxeo.ecm.automation.client.Constants;
import org.nuxeo.ecm.automation.client.OperationRequest;
import org.nuxeo.ecm.automation.client.Session;
import org.nuxeo.ecm.automation.client.model.Document;

import fr.toutatice.portail.cms.nuxeo.api.INuxeoCommand;

public class GetProfileCommand implements INuxeoCommand {

    protected static final Log logger = LogFactory.getLog(GetProfileCommand.class);

    private final String userName;



    public GetProfileCommand(String userName) {
        super();
        this.userName = userName;


    }


    @Override
    public Object execute(Session automationSession) throws Exception {

        OperationRequest newRequest = automationSession.newRequest("Services.GetToutaticeUserProfile");
        newRequest.set("username", this.userName);
        Document userProfile = (Document) newRequest.execute();

        // Get Full doc
        Document doc = (Document) automationSession.newRequest("Document.Fetch").setHeader(Constants.HEADER_NX_SCHEMAS, "*").set("value", userProfile.getPath()).execute();
        
        return doc;

    }

    @Override
    public String getId() {
        return "GetProfileCommand";
    }
}
