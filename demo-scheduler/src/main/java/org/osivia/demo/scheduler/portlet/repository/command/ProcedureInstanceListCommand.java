package org.osivia.demo.scheduler.portlet.repository.command;

import java.util.List;
import java.util.UUID;

import org.nuxeo.ecm.automation.client.Constants;
import org.nuxeo.ecm.automation.client.OperationRequest;
import org.nuxeo.ecm.automation.client.Session;

import fr.toutatice.portail.cms.nuxeo.api.INuxeoCommand;
import fr.toutatice.portail.cms.nuxeo.api.NuxeoQueryFilter;
import fr.toutatice.portail.cms.nuxeo.api.NuxeoQueryFilterContext;

/**
 * List Nuxeo events command.
 *
 * @author Julien Barberet
 * @see INuxeoCommand
 */
public class ProcedureInstanceListCommand implements INuxeoCommand {

	private static final String PROCEDURE_REQUEST_FOR_INTERVENTION = "procedure_demande-intervention";
    /** Nuxeo query filter context. */
    private NuxeoQueryFilterContext queryContext;
    /** Start date. */
    private final String startDate;
    /** End date */
    private final String endDate;
    
    private final String intervenant;
    
    private final List<String> customerUsers;


    /**
     * Constructor.
     *
     * @param queryContext Nuxeo query filter context
     * @param contextPath context path
     * @param startDate start date
     * @param endDate end date
     */
    public ProcedureInstanceListCommand(NuxeoQueryFilterContext queryContext, String startDate, String endDate, String intervenant, List<String> customerUsers) {
        super();
        this.queryContext = queryContext;
        this.startDate = startDate;
        this.endDate = endDate;
        this.intervenant = intervenant;
        this.customerUsers = customerUsers;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Object execute(Session nuxeoSession) throws Exception {

        // Clause
        StringBuilder clause = new StringBuilder();
        clause.append("ecm:primaryType = 'ProcedureInstance' ");
        clause.append("and pi:procedureModelWebId = '").append(PROCEDURE_REQUEST_FOR_INTERVENTION).append("' ");
        clause.append("AND (pi:data/date between DATE '").append(startDate).append("' and DATE '").append(endDate).append("') ");
        clause.append("and pi:data/intervenant = '").append(intervenant).append("' ");
        clause.append("AND (pi:currentStep = '1' OR pi:data/accepted = 'true') ");
        clause.append("and dc:creator in ('");
        boolean first = true;
        for (String user : customerUsers)
        {
        	if (!first) clause.append("','");
        	clause.append(user);
        	first = false;
        }
        clause.append("') ");

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
        return "ProcedureInstanceListCommand :" + UUID.randomUUID();
    }

}
