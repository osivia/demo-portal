package org.osivia.demo.customizer.plugin;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jboss.portal.theme.impl.render.dynamic.DynaRenderOptions;
import org.nuxeo.ecm.automation.client.model.Document;
import org.nuxeo.ecm.automation.client.model.PropertyList;
import org.nuxeo.ecm.automation.client.model.PropertyMap;
import org.osivia.portal.api.Constants;
import org.osivia.portal.api.PortalException;

import fr.toutatice.portail.cms.nuxeo.api.NuxeoController;
import net.sf.json.JSONObject;

/**
 * @author Dorian Licois
 */
public class DemoUtils {

    public static final String RECORD_PROPERTY_DATA = "rcd:data";

    public static final String RECORD_PROPERTY_TITLE = "_title";

    public static final String PRODUCT_PROPERTY_DESCRIPTION = "description";

    public static final String ACCESSORY_PROPERTY_DESCRIPTION = PRODUCT_PROPERTY_DESCRIPTION;

    public static final String PRODUCT_PROPERTY_VISUEL = "visuel";

    public static final String ACCESSORY_PROPERTY_VISUEL = PRODUCT_PROPERTY_VISUEL;

    public static final String ACCESSORY_PROPERTY_PRIX = "prixht";

    private static final String PROCEDURE_MAP_PROPERTY = "osivia.services.procedure.variables";

    private static final String PROCEDURE_PORTLET_INSTANCE = "osivia-services-procedure-portletInstance";

    private DemoUtils() {
    }

    /**
     * creates the URL to download the file in the propertyMap
     *
     * @param propertyMap
     * @param nuxeoController
     * @return
     */
    public static String getFileUrl(PropertyMap propertyMap, NuxeoController nuxeoController) {
        String fileDigest = propertyMap.getString("digest");

        return GetFileUrl(nuxeoController, fileDigest);
    }

    /**
     * creates the URL to download the file in the map
     *
     * @param map
     * @param nuxeoController
     * @return
     */
    public static String getFileUrl(Map<String, Object> map, NuxeoController nuxeoController) {
        String fileDigest = (String) map.get("digest");

        return GetFileUrl(nuxeoController, fileDigest);
    }

    /**
     * creates the URL to download the file corresponding to digest
     *
     * @param nuxeoController
     * @param fileDigest
     * @return
     */
    private static String GetFileUrl(NuxeoController nuxeoController, String fileDigest) {
        Document currentDoc = nuxeoController.getCurrentDoc();
        PropertyMap properties = currentDoc.getProperties();
        PropertyList files = properties.getList("files:files");

        return nuxeoController.createAttachedFileLink("files:files", String.valueOf(getFileIndex(fileDigest, files)));
    }

    /**
     * Retrieves the index corresponding to the supplied digest
     *
     * @param fileDigest
     * @param files
     * @return
     */
    private static int getFileIndex(String fileDigest, PropertyList files) {
        int fileIndex = 0;
        if ((files != null) && (files.size() > 1)) {
            for (int i = 0; i < files.size(); i++) {
                PropertyMap filesMap = files.getMap(i);
                if (filesMap != null) {
                    PropertyMap fileMap = filesMap.getMap("file");
                    if (fileMap != null) {
                        String digest = fileMap.getString("digest");
                        if (StringUtils.equals(fileDigest, digest)) {
                            fileIndex = i;
                            break;
                        }
                    }
                }
            }
        }
        return fileIndex;
    }


    /**
     * Creates the URL to launch a procedure
     *
     * @param nuxeoController
     * @return
     */
    public static String getLaunchProcedureUrl(NuxeoController nuxeoController, JSONObject variables, String procedureWebid) {

        Map<String, String> windowProperties = new HashMap<>();
        if (variables != null) {
            windowProperties.put(PROCEDURE_MAP_PROPERTY, variables.toString());
        }
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
        return launchProcedureUrl;
    }
}
