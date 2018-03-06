package org.osivia.demo.scheduler.portlet.repository.command;

import org.nuxeo.ecm.automation.client.Constants;
import org.nuxeo.ecm.automation.client.OperationRequest;
import org.nuxeo.ecm.automation.client.Session;

import fr.toutatice.portail.cms.nuxeo.api.INuxeoCommand;
import fr.toutatice.portail.cms.nuxeo.api.NuxeoCompatibility;
import fr.toutatice.portail.cms.nuxeo.api.NuxeoQueryFilter;
import fr.toutatice.portail.cms.nuxeo.api.NuxeoQueryFilterContext;

/**
 * List Nuxeo events command.
 *
 * @author Julien Barberet
 * @see INuxeoCommand
 */
public class EventListCommand implements INuxeoCommand {

    /** Nuxeo query filter context. */
    private NuxeoQueryFilterContext queryContext;
    /** Context path. */
    private final String contextPath;
    /** Start date. */
    private final String startDate;
    /** End date */
    private final String endDate;


    /**
     * Constructor.
     *
     * @param queryContext Nuxeo query filter context
     * @param contextPath context path
     * @param startDate start date
     * @param endDate end date
     */
    public EventListCommand(NuxeoQueryFilterContext queryContext, String contextPath, String startDate, String endDate) {
        super();
        this.queryContext = queryContext;
        this.contextPath = contextPath;
        this.startDate = startDate;
        this.endDate = endDate;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Object execute(Session nuxeoSession) throws Exception {

        // Clause
        StringBuilder clause = new StringBuilder();
        clause.append("ecm:mixinType = 'Schedulable' ");
        clause.append("AND ecm:path STARTSWITH '").append(this.contextPath).append("' ");
        clause.append("AND ((vevent:dtstart < TIMESTAMP '").append(endDate).append("') ");
        clause.append("AND (vevent:dtend > TIMESTAMP '").append(startDate).append("')) ");
        clause.append(" ORDER BY vevent:dtstart");

        // Filter on published documents
        String filteredRequest = NuxeoQueryFilter.addPublicationFilter(this.queryContext, clause.toString());

        // Request
        OperationRequest request;
        if (NuxeoCompatibility.canUseES()) {
            request = nuxeoSession.newRequest("Document.QueryES");
            request.set(Constants.HEADER_NX_SCHEMAS, "dublincore, common, toutatice, vevent, synchronization");
        } else {
            request = nuxeoSession.newRequest("Document.Query");
            request.setHeader(Constants.HEADER_NX_SCHEMAS, "dublincore, common, toutatice, vevent, synchronization");
        }
        request.set("query", "SELECT * FROM Document WHERE " + filteredRequest);

        return request.execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() {
        return "Calendar/" + this.contextPath;
    }

}
