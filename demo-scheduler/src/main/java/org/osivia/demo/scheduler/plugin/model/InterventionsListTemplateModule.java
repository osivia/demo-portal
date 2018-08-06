package org.osivia.demo.scheduler.plugin.model;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.lang.time.DateUtils;
import org.jboss.portal.theme.impl.render.dynamic.DynaRenderOptions;
import org.osivia.portal.api.Constants;
import org.osivia.portal.api.PortalException;
import org.osivia.portal.api.context.PortalControllerContext;
import org.osivia.portal.api.internationalization.Bundle;
import org.osivia.portal.api.internationalization.IBundleFactory;
import org.osivia.portal.api.portlet.PortletAppUtils;
import org.osivia.portal.api.urls.IPortalUrlFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import fr.toutatice.portail.cms.nuxeo.api.portlet.PrivilegedPortletModule;
import fr.toutatice.portail.cms.nuxeo.api.services.NuxeoCommandContext;

/**
 * Interventions list template module.
 * 
 * @author Cédric Krommenhoek
 * @see PrivilegedPortletModule
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class InterventionsListTemplateModule extends PrivilegedPortletModule {

    /** Timestamp pattern. */
    private static final String TIMESTAMP_PATTERN = "yyyy-MM-dd HH:mm:ss";


    /** Timestamp format. */
    private final DateFormat timestampFormat;


    /** Application context. */
    @Autowired
    private ApplicationContext applicationContext;

    /** Portal URL factory. */
    @Autowired
    private IPortalUrlFactory portalUrlFactory;

    /** Internationalization bundle factory. */
    @Autowired
    private IBundleFactory bundleFactory;
    
    @Autowired
    private PortletConfig portletConfig;


    /**
     * Constructor.
     * 
     * @param portletContext portlet context
     */
    public InterventionsListTemplateModule(PortletContext portletContext) {
        super(portletContext);

        // Timestamp format
        this.timestampFormat = new SimpleDateFormat(TIMESTAMP_PATTERN);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void doView(RenderRequest request, RenderResponse response, PortletContext portletContext) throws PortletException, IOException {
        // Portal controller context
        PortalControllerContext portalControllerContext = new PortalControllerContext(portletContext, request, response);
        // Internationalization bundle
        Bundle bundle = this.bundleFactory.getBundle(request.getLocale());
        
        // Application context
        request.setAttribute(Constants.PORTLET_ATTR_WEBAPP_CONTEXT, applicationContext);
        
        // Intervention request URL
        String url;
        try {
            // Portlet instance
            String instance = "osivia-demo-scheduler-instance";

            // Window properties
            Map<String, String> properties = new HashMap<String, String>();
            properties.put("osivia.title", bundle.getString("PLANNING_TITLE"));
            properties.put(DynaRenderOptions.PARTIAL_REFRESH_ENABLED, String.valueOf(true));
            properties.put("osivia.ajaxLink", "1");

            url = this.portalUrlFactory.getStartPortletUrl(portalControllerContext, instance, properties);
        } catch (PortalException e) {
            throw new PortletException(e);
        }
        request.setAttribute("interventionRequestUrl", url);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public int getAuthType() {
        return NuxeoCommandContext.AUTH_TYPE_SUPERUSER;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String getFilter(PortalControllerContext portalControllerContext) {
        // User
        String user = portalControllerContext.getHttpServletRequest().getRemoteUser();
        // Timestamp
        Date currentDate = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
        String timestamp = this.timestampFormat.format(currentDate);

        StringBuilder filter = new StringBuilder();
        filter.append("ecm:primaryType = 'ProcedureInstance' ");
        filter.append("AND pi:procedureModelWebId = 'procedure_demande-intervention' ");
        filter.append("AND pi:procedureInitiator = '").append(user).append("' ");
        filter.append("AND (pi:currentStep in ('1', '2') OR pi:data/accepted = 'true') ");
        filter.append("AND pi:data/date >= TIMESTAMP '").append(timestamp).append("' ");

        return filter.toString();
    }

}
