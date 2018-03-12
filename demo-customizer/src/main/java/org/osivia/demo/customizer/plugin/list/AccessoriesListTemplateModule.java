package org.osivia.demo.customizer.plugin.list;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.collections.CollectionUtils;
import org.osivia.demo.customizer.plugin.DemoUtils;

import fr.toutatice.portail.cms.nuxeo.api.NuxeoController;
import fr.toutatice.portail.cms.nuxeo.api.domain.DocumentDTO;
import fr.toutatice.portail.cms.nuxeo.api.portlet.PortletModule;
import net.sf.json.JSONObject;


/**
 * @author Dorian Licois
 */
public class AccessoriesListTemplateModule extends PortletModule {

    private static final String PROCEDURE_COMMANDE_WEBID = "procedure_commande_produit";

    public AccessoriesListTemplateModule(PortletContext portletContext) {
        super(portletContext);
    }

    @Override
    protected void doView(RenderRequest request, RenderResponse response, PortletContext portletContext) throws PortletException, IOException {
        // Documents
        List<DocumentDTO> documents = (List<DocumentDTO>) request.getAttribute("documents");

        if (CollectionUtils.isNotEmpty(documents)) {

            // Nuxeo controller
            NuxeoController nuxeoController = new NuxeoController(request, response, portletContext);

            for (DocumentDTO documentDTO : documents) {

                nuxeoController.setCurrentDoc(documentDTO.getDocument());

                Map<String, Object> properties = documentDTO.getProperties();
                Map<String, Object> dataMap = (Map<String, Object>) properties.get(DemoUtils.RECORD_PROPERTY_DATA);

                if (dataMap != null) {
                    // title
                    properties.put("title", dataMap.get(DemoUtils.RECORD_PROPERTY_TITLE));

                    // visuel
                    Map<String, Object> visuelMap = (Map<String, Object>) dataMap.get(DemoUtils.PRODUCT_PROPERTY_VISUEL);
                    properties.put("visuelUrl", DemoUtils.getFileUrl(visuelMap, nuxeoController));
                    properties.put("visuelFilename", visuelMap.get("fileName"));

                    // description
                    properties.put("description", dataMap.get(DemoUtils.PRODUCT_PROPERTY_DESCRIPTION));

                    // prix
                    properties.put("prixht", dataMap.get(DemoUtils.ACCESSORY_PROPERTY_PRIX));

                    // orderUrl
                    properties.put("orderUrl", getOrderUrl(PROCEDURE_COMMANDE_WEBID, nuxeoController, documentDTO, (String) properties.get("ttc:webid")));
                }
            }
        }
    }

    /**
     * Creates the URL to launch the order procedure
     *
     * @param procedureWebid
     * @param nuxeoController
     * @param documentDTO
     * @param object
     * @return
     */
    private String getOrderUrl(String procedureWebid, NuxeoController nuxeoController, DocumentDTO documentDTO, String object) {
        JSONObject variables = new JSONObject();
        variables.put("accessoryWebid", object);
        variables.put("accessory", documentDTO.getTitle());
        return DemoUtils.getLaunchProcedureUrl(nuxeoController, variables, procedureWebid);
    }

}
