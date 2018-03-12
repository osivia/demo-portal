package org.osivia.demo.customizer.plugin.fragment;

import javax.portlet.ActionRequest;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.lang.StringUtils;
import org.nuxeo.ecm.automation.client.model.Document;
import org.nuxeo.ecm.automation.client.model.PropertyMap;
import org.osivia.demo.customizer.plugin.DemoUtils;
import org.osivia.portal.api.Constants;
import org.osivia.portal.api.context.PortalControllerContext;
import org.osivia.portal.api.windows.PortalWindow;
import org.osivia.portal.api.windows.WindowFactory;

import fr.toutatice.portail.cms.nuxeo.api.NuxeoController;
import fr.toutatice.portail.cms.nuxeo.api.cms.NuxeoDocumentContext;
import fr.toutatice.portail.cms.nuxeo.api.fragment.FragmentModule;

/**
 * @author Dorian Licois
 */
public class ProduitRecordFragmentModule extends FragmentModule {

    /** Record property fragment identifier. */
    public static final String ID = "record_property";

    /** Nuxeo path window property name. */
    public static final String NUXEO_PATH_WINDOW_PROPERTY = Constants.WINDOW_PROP_URI;

    /** JSP name. */
    private static final String JSP_NAME = "product";

    public ProduitRecordFragmentModule(PortletContext portletContext) {
        super(portletContext);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doView(PortalControllerContext portalControllerContext) throws PortletException {
        // Request
        PortletRequest request = portalControllerContext.getRequest();
        // Response
        RenderResponse response = (RenderResponse) portalControllerContext.getResponse();
        // Nuxeo controller
        NuxeoController nuxeoController = new NuxeoController(request, response, portalControllerContext.getPortletCtx());

        // Current window
        PortalWindow window = WindowFactory.getWindow(request);
        // Nuxeo path
        String nuxeoPath = window.getProperty(NUXEO_PATH_WINDOW_PROPERTY);

        if (StringUtils.isNotEmpty(nuxeoPath)) {
            // Computed path
            nuxeoPath = nuxeoController.getComputedPath(nuxeoPath);

            // Nuxeo document
            NuxeoDocumentContext documentContext = nuxeoController.getDocumentContext(nuxeoPath);
            Document document = documentContext.getDenormalizedDocument();
            nuxeoController.setCurrentDoc(document);

            PropertyMap properties = document.getProperties();
            PropertyMap dataMap = properties.getMap(DemoUtils.RECORD_PROPERTY_DATA);
            if (dataMap != null) {
                // title
                request.setAttribute("title", dataMap.getString(DemoUtils.RECORD_PROPERTY_TITLE));

                // visuel
                PropertyMap visuelMap = dataMap.getMap(DemoUtils.PRODUCT_PROPERTY_VISUEL);
                request.setAttribute("visuelUrl", DemoUtils.getFileUrl(visuelMap, nuxeoController));
                request.setAttribute("visuelFilename", visuelMap.getString("fileName"));

                // description
                request.setAttribute("description", dataMap.getString(DemoUtils.PRODUCT_PROPERTY_DESCRIPTION));
            }
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
