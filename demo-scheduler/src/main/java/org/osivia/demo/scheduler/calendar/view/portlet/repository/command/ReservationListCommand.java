package org.osivia.demo.scheduler.calendar.view.portlet.repository.command;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang.time.DateFormatUtils;
import org.nuxeo.ecm.automation.client.Constants;
import org.nuxeo.ecm.automation.client.OperationRequest;
import org.nuxeo.ecm.automation.client.Session;

import fr.toutatice.portail.cms.nuxeo.api.INuxeoCommand;
import fr.toutatice.portail.cms.nuxeo.api.NuxeoQueryFilter;
import fr.toutatice.portail.cms.nuxeo.api.NuxeoQueryFilterContext;

/**
 * List procedure instances command.
 *
 * @author Julien Barberet
 * @see INuxeoCommand
 */
public class ReservationListCommand implements INuxeoCommand {

	private static final String PROCEDURE_REQUEST_FOR_INTERVENTION = "procedure_demande-intervention";
    /** Nuxeo query filter context. */
    private NuxeoQueryFilterContext queryContext;
    /** Start date. */
    private final Date startDate;
    /** End date. */
    private final Date endDate;
    
    private final String contributor;


    /**oui
     * Constructor.
     *
     * @param queryContext Nuxeo query filter context
     * @param contextPath context path
     * @param startDate start date
     * @param endDate end date
     */
    public ReservationListCommand(NuxeoQueryFilterContext queryContext, Date startDate, Date endDate, String contributor) {
        super();
        this.queryContext = queryContext;
        this.startDate = startDate;
        this.endDate = endDate;
        this.contributor = contributor;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Object execute(Session nuxeoSession) throws Exception {
    	//Pour corriger le bug de création des procédures instance avec une heure en moins
    	this.startDate.setTime(this.startDate.getTime()-(60*60*1000));
        String start = DateFormatUtils.ISO_DATE_FORMAT.format(this.startDate);

        String end = DateFormatUtils.ISO_DATE_FORMAT.format(this.endDate);

        // Clause
        StringBuilder clause = new StringBuilder();
        clause.append("ecm:primaryType = 'ProcedureInstance' ");
        clause.append("and pi:procedureModelWebId = '").append(PROCEDURE_REQUEST_FOR_INTERVENTION).append("' ");
        clause.append("AND (pi:data/date between DATE '").append(start).append("' and DATE '").append(end).append("') ");
        clause.append("and pi:data/intervenant = '").append(contributor).append("' ");
        clause.append("AND (pi:currentStep in ('1','2') OR pi:data/accepted = 'true') ");
        clause.append("ORDER BY pi:data/date ");
        
        // Filter on published documents
        String filteredRequest = NuxeoQueryFilter.addPublicationFilter(this.queryContext, clause.toString());

        // Request
        OperationRequest request;
        request = nuxeoSession.newRequest("Document.QueryES");
        request.set(Constants.HEADER_NX_SCHEMAS, "*");
        request.set("query", "SELECT * FROM Document WHERE " + filteredRequest);

        return request.execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() {
        return "Calendar/" + UUID.randomUUID();
    }

}
