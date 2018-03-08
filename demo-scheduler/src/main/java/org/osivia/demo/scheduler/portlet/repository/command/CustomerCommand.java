package org.osivia.demo.scheduler.portlet.repository.command;

import java.util.UUID;

import org.nuxeo.ecm.automation.client.Constants;
import org.nuxeo.ecm.automation.client.OperationRequest;
import org.nuxeo.ecm.automation.client.Session;

import fr.toutatice.portail.cms.nuxeo.api.INuxeoCommand;
import fr.toutatice.portail.cms.nuxeo.api.NuxeoQueryFilter;
import fr.toutatice.portail.cms.nuxeo.api.NuxeoQueryFilterContext;

/**
 * Load customer information.
 *
 * @author Julien Barberet
 * @see INuxeoCommand
 */
public class CustomerCommand implements INuxeoCommand {

	private static final String CUSTOMER_WEBID = "rec_client";
	
    /** Nuxeo query filter context. */
    private NuxeoQueryFilterContext queryContext;
    /** Start date. */
    private final String user;


    /**
     * Constructor.
     *
     * @param queryContext Nuxeo query filter context
     * @param contextPath context path
     * @param startDate start date
     * @param endDate end date
     */
    public CustomerCommand(NuxeoQueryFilterContext queryContext, String user) {
        super();
        this.queryContext = queryContext;
        this.user = user; 
        		
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Object execute(Session nuxeoSession) throws Exception {

        // Clause
        StringBuilder clause = new StringBuilder();
        clause.append("ecm:primaryType = 'Record' ");
        clause.append("and rcd:procedureModelWebId = '").append(CUSTOMER_WEBID).append("' ");
        clause.append("and rcd:data.customerusers.user = '").append(user).append("' ");

        // Filter on published documents
        String filteredRequest = NuxeoQueryFilter.addPublicationFilter(this.queryContext, clause.toString());

        // Request
        OperationRequest request = nuxeoSession.newRequest("Document.QueryES");
        request.set(Constants.HEADER_NX_SCHEMAS, "*");

        request.set("query", "SELECT * FROM Document WHERE " + filteredRequest);

        return request.execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() {
        return "CustomerUsers/" + UUID.randomUUID();
    }

}
