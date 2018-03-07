package org.osivia.demo.customizer.plugin.fragment;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.lang.StringUtils;
import org.jboss.portal.theme.impl.render.dynamic.DynaRenderOptions;
import org.nuxeo.ecm.automation.client.model.Document;
import org.nuxeo.ecm.automation.client.model.PropertyMap;
import org.osivia.portal.api.Constants;
import org.osivia.portal.api.PortalException;
import org.osivia.portal.api.context.PortalControllerContext;
import org.osivia.portal.api.windows.PortalWindow;
import org.osivia.portal.api.windows.WindowFactory;

import fr.toutatice.portail.cms.nuxeo.api.NuxeoController;
import fr.toutatice.portail.cms.nuxeo.api.cms.NuxeoDocumentContext;
import fr.toutatice.portail.cms.nuxeo.api.fragment.FragmentModule;
import net.sf.json.JSONObject;


/**
 * @author Dorian Licois
 */
public class LaunchSupportPortletModule extends FragmentModule {

    public static final String ID = "launch_support";

    private static final String PROCEDURE_MAP_PROPERTY = "osivia.services.procedure.variables";

    private static final String PROCEDURE_PORTLET_INSTANCE = "osivia-services-procedure-portletInstance";

    /** Nuxeo path window property name. */
    private static final String NUXEO_PATH_WINDOW_PROPERTY = Constants.WINDOW_PROP_URI;

    private static final String LAUNCH_PROCEDURE_PROPERTY = "osivia.launch.procedure.webid";

    /** JSP name. */
    private static final String JSP_NAME = "launch-support";

    public LaunchSupportPortletModule(PortletContext portletContext) {
        super(portletContext);
    }

    @Override
    protected void doView(RenderRequest request, RenderResponse response, PortletContext portletContext) throws PortletException, IOException {

        // Current window
        PortalWindow window = WindowFactory.getWindow(request);
        // Nuxeo path
        String nuxeoPath = window.getProperty(NUXEO_PATH_WINDOW_PROPERTY);

        String procedureWebid = window.getProperty(LAUNCH_PROCEDURE_PROPERTY);

        if (StringUtils.isNotEmpty(nuxeoPath) && StringUtils.isNotBlank(procedureWebid)) {

            NuxeoController nuxeoController = new NuxeoController(request, response, getPortletContext());
            // Computed path
            nuxeoPath = nuxeoController.getComputedPath(nuxeoPath);

            // Nuxeo document
            NuxeoDocumentContext documentContext = nuxeoController.getDocumentContext(nuxeoPath);
            Document document = documentContext.getDenormalizedDocument();
            nuxeoController.setCurrentDoc(document);

            PropertyMap properties = document.getProperties();
            String producteWebId = properties.getString("ttc:webid");

            // Procedure Map
            Map<String, String> windowProperties = new HashMap<>();
            JSONObject variables = new JSONObject();
            variables.put("productWebid", producteWebId);
            variables.put("produit", document.getTitle());
            windowProperties.put(PROCEDURE_MAP_PROPERTY, variables.toString());

            // launchProcedureUrl
            windowProperties.put("osivia.services.procedure.webid", procedureWebid);
            windowProperties.put("osivia.doctype", "ProcedureModel");
            windowProperties.put("osivia.hideDecorators", "1");
            windowProperties.put(DynaRenderOptions.PARTIAL_REFRESH_ENABLED, Constants.PORTLET_VALUE_ACTIVATE);
            windowProperties.put("osivia.ajaxLink", "1");
            String launchProcedureUrl = null;
            try {
                launchProcedureUrl = nuxeoController.getPortalUrlFactory().getStartPortletUrl(nuxeoController.getPortalCtx(), PROCEDURE_PORTLET_INSTANCE,
                        windowProperties);
            } catch (PortalException e) {
            }

            request.setAttribute("launchProcedureUrl", launchProcedureUrl);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doAdmin(PortalControllerContext portalControllerContext) throws PortletException {
        // Request
        PortletRequest request = portalControllerContext.getRequest();

        // Current window
        PortalWindow window = WindowFactory.getWindow(request);

        // Nuxeo path
        String nuxeoPath = window.getProperty(NUXEO_PATH_WINDOW_PROPERTY);
        request.setAttribute("nuxeoPath", nuxeoPath);

        String procedureWebid = window.getProperty(LAUNCH_PROCEDURE_PROPERTY);
        request.setAttribute("procedureWebid", procedureWebid);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void processAction(PortalControllerContext portalControllerContext) throws PortletException {
        // Request
        PortletRequest request = portalControllerContext.getRequest();

        if ("admin".equals(request.getPortletMode().toString()) && "save".equals(request.getParameter(ActionRequest.ACTION_NAME))) {
            // Current window
            PortalWindow window = WindowFactory.getWindow(request);

            // Nuxeo path
            window.setProperty(NUXEO_PATH_WINDOW_PROPERTY, StringUtils.trimToNull(request.getParameter("nuxeoPath")));

            window.setProperty(LAUNCH_PROCEDURE_PROPERTY, StringUtils.trimToNull(request.getParameter("procedureWebid")));
        }
    }

    @Override
    public String getViewJSPName() {
        return JSP_NAME;
    }

    @Override
    public String getAdminJSPName() {
        return JSP_NAME;
    }

    @Override
    public boolean isDisplayedInAdmin() {
        return true;
    }
}
