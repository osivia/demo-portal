package org.osivia.demo.customizer.plugin.fragment;

import java.io.IOException;
import java.io.StringWriter;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.activation.MimeType;
import javax.activation.MimeTypeParseException;
import javax.portlet.ActionRequest;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.dom4j.Element;
import org.nuxeo.ecm.automation.client.model.Document;
import org.nuxeo.ecm.automation.client.model.PropertyList;
import org.nuxeo.ecm.automation.client.model.PropertyMap;
import org.osivia.portal.api.Constants;
import org.osivia.portal.api.cms.FileDocumentType;
import org.osivia.portal.api.context.PortalControllerContext;
import org.osivia.portal.api.html.DOM4JUtils;
import org.osivia.portal.api.html.HTMLConstants;
import org.osivia.portal.api.internationalization.Bundle;
import org.osivia.portal.api.windows.PortalWindow;
import org.osivia.portal.api.windows.WindowFactory;

import fr.toutatice.portail.cms.nuxeo.api.NuxeoController;
import fr.toutatice.portail.cms.nuxeo.api.cms.NuxeoDocumentContext;
import fr.toutatice.portail.cms.nuxeo.api.fragment.FragmentModule;
import fr.toutatice.portail.cms.nuxeo.api.services.INuxeoCustomizer;

public class RecordPropertyFragmentModule extends FragmentModule {

    /** Record property fragment identifier. */
    public static final String ID = "record_property";

    /** Nuxeo path window property name. */
    public static final String NUXEO_PATH_WINDOW_PROPERTY = Constants.WINDOW_PROP_URI;
    /** Property name window property name. */
    public static final String PROPERTY_NAME_WINDOW_PROPERTY = "osivia.propertyName";

    /** JSP name. */
    private static final String JSP_NAME = "property";

    public RecordPropertyFragmentModule(PortletContext portletContext) {
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
        // Property name
        String propertyName = window.getProperty(PROPERTY_NAME_WINDOW_PROPERTY);

        String content = null;

        if (StringUtils.isNotEmpty(nuxeoPath)) {
            // Computed path
            nuxeoPath = nuxeoController.getComputedPath(nuxeoPath);

            // Nuxeo document
            NuxeoDocumentContext documentContext = nuxeoController.getDocumentContext(nuxeoPath);
            Document document = documentContext.getDenormalizedDocument();
            nuxeoController.setCurrentDoc(document);

            if (StringUtils.isNotEmpty(propertyName)) {
                String[] propertyPathTab = StringUtils.split(propertyName, "/");
                PropertyMap currentMap = document.getProperties();
                for (int i = 0; i < propertyPathTab.length; i++) {
                    Object propertyValueO = currentMap.get(propertyPathTab[i]);
                    if (i == (propertyPathTab.length - 1)) {
                        content = buildContent(document, propertyName, propertyValueO, request, nuxeoController);
                    } else if (propertyValueO instanceof PropertyMap) {
                        currentMap = (PropertyMap) propertyValueO;
                    } else if (propertyValueO instanceof PropertyList) {
                        break;
                    } else {
                        break;
                    }
                }
            }
        }

        request.setAttribute("content", content);
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

        // Property name
        String propertyName = window.getProperty(PROPERTY_NAME_WINDOW_PROPERTY);
        request.setAttribute("propertyName", propertyName);
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

            // Property name
            window.setProperty(PROPERTY_NAME_WINDOW_PROPERTY, StringUtils.trimToNull(request.getParameter("propertyName")));
        }
    }

    private String buildContent(Document document, String propertyName, Object propertyValueO, PortletRequest request, NuxeoController nuxeoController)
            throws PortletException {
        String content = null;
        if (propertyValueO instanceof String) {
            content = (String) propertyValueO;
        } else if (propertyValueO instanceof Date) {
            // content = DateParser.formatW3CDateTime((Date) propertyValueO);
            content = formatRelativeDate((Date) propertyValueO, request);
        } else if (propertyValueO instanceof PropertyMap) {
            content = formatMap((PropertyMap) propertyValueO, nuxeoController);
        } else if (propertyValueO instanceof PropertyList) {
            content = formatList(document, propertyName, request, (PropertyList) propertyValueO, nuxeoController);
        }
        return content;
    }

    private String formatMap(PropertyMap propertyMap, NuxeoController nuxeoController) {

        String content = null;

        String type = propertyMap.getString("type");
        if (StringUtils.equals(type, "PICTURE")) {
            content = formatPicture(propertyMap, nuxeoController);
        } else if (StringUtils.equals(type, "FILE")) {
            content = formatFile(propertyMap, nuxeoController);
        }

        return content;
    }

    private String formatFile(PropertyMap propertyMap, NuxeoController nuxeoController) {

        // file properties
        Document currentDoc = nuxeoController.getCurrentDoc();
        PropertyMap properties = currentDoc.getProperties();
        PropertyList files = properties.getList("files:files");
        int fileIndex = getFileIndex(propertyMap.getString("digest"), files);
        PropertyMap fileMap = files.getMap(fileIndex);
        String fileName = fileMap.getString("filename");
        PropertyMap fileFileMap = fileMap.getMap("file");

        // file icon
        String mimeType = fileFileMap.getString("mime-type");
        MimeType mimeTypeO;
        try {
            mimeTypeO = new MimeType(mimeType);
        } catch (MimeTypeParseException e) {
            mimeTypeO = new MimeType();
        }
        String icon = getIcon(mimeTypeO, nuxeoController);

        // file link
        String fileLink = nuxeoController.createAttachedFileLink("files:files", String.valueOf(getFileIndex(fileFileMap.getString("digest"), files)));

        // HTML
        Element divElement = DOM4JUtils.generateDivElement(null);
        Element iElement = DOM4JUtils.generateElement(HTMLConstants.I, icon, StringUtils.EMPTY);
        Element aElement = DOM4JUtils.generateLinkElement(fileLink, "_blank", null, "no-ajax-link", null);
        Element spanElement = DOM4JUtils.generateElement(HTMLConstants.SPAN, null, fileName);
        aElement.add(spanElement);
        divElement.add(iElement);
        divElement.add(aElement);

        return DOM4JUtils.write(divElement);
    }

    public String getIcon(MimeType mimeType, NuxeoController nuxeoController) {
        INuxeoCustomizer cmsCustomizer = nuxeoController.getNuxeoCMSService().getCMSCustomizer();

        // Icon
        String icon;

        if (mimeType == null) {
            icon = null;
        } else {
            // File document types
            List<FileDocumentType> types = cmsCustomizer.getFileDocumentTypes();

            icon = null;
            for (FileDocumentType type : types) {
                if (StringUtils.equals(mimeType.getPrimaryType(), type.getMimePrimaryType())) {
                    if (type.getMimeSubTypes().isEmpty()) {
                        icon = type.getIcon();
                    } else if (type.getMimeSubTypes().contains(mimeType.getSubType())) {
                        icon = type.getIcon();
                        break;
                    }
                }
            }
        }

        return icon;
    }

    private String getFileLink(PropertyMap propertyMap, NuxeoController nuxeoController) {
        String fileDigest = propertyMap.getString("digest");

        Document currentDoc = nuxeoController.getCurrentDoc();
        PropertyMap properties = currentDoc.getProperties();
        PropertyList files = properties.getList("files:files");

        return nuxeoController.createAttachedFileLink("files:files", String.valueOf(getFileIndex(fileDigest, files)));
    }

    private int getFileIndex(String fileDigest, PropertyList files) {
        int fileIndex = 0;
        if ((files != null) && (files.size() > 1)) {
            for (int i = 0; i < files.size(); i++) {
                PropertyMap filesMap = files.getMap(i);
                if(filesMap!=null) {
                    PropertyMap fileMap = filesMap.getMap("file");
                    if(fileMap!=null) {
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

    private String formatPicture(PropertyMap propertyMap, NuxeoController nuxeoController) {

        // imageLink
        String imageLink = getFileLink(propertyMap, nuxeoController);

        // image file name
        String filename = propertyMap.getString("fileName");

        // HTML
        Element aElement = DOM4JUtils.generateLinkElement(imageLink, null, null, "thumbnail no-margin-bottom no-ajax-link", null);
        DOM4JUtils.addDataAttribute(aElement, "fancybox", "gallery");
        DOM4JUtils.addDataAttribute(aElement, "caption", filename);
        DOM4JUtils.addDataAttribute(aElement, "type", "image");
        Element imgElement = DOM4JUtils.generateElement(HTMLConstants.IMG, null, null);
        imgElement.addAttribute(HTMLConstants.SRC, imageLink);
        imgElement.addAttribute(HTMLConstants.ALT, filename);

        aElement.add(imgElement);

        return DOM4JUtils.write(aElement);
    }

    private String formatList(Document document, String propertyName, PortletRequest request, PropertyList propertyList, NuxeoController nuxeoController)
            throws PortletException {
        String content = null;
        List<Object> list = propertyList.getList();
        List<String> contentList = new ArrayList<>(list.size());
        for (Object propertyValueO : list) {
            contentList.add(buildContent(document, propertyName, propertyValueO, request, nuxeoController));
        }

        // TODO display nicely in HTML
        content = StringUtils.join(contentList, "/n");

        return content;
    }

    private String formatRelativeDate(Date date, PortletRequest request) throws PortletException {
        // Current date
        Date currentDate = new Date();
        // Current calendar
        Calendar currentCalendar = DateUtils.toCalendar(currentDate);


        // Calendar fields
        Integer[] fields = new Integer[]{Calendar.YEAR, Calendar.MONTH, Calendar.WEEK_OF_YEAR, Calendar.DAY_OF_YEAR, Calendar.HOUR_OF_DAY, Calendar.MINUTE};

        // Current field
        Integer currentField = null;
        // Amount
        int amount = 0;

        for (Integer field : fields) {
            // Calendar
            Calendar calendar = DateUtils.toCalendar(date);
            calendar.add(field, 1);

            if (calendar.before(currentCalendar)) {
                currentField = field;

                while (calendar.before(currentCalendar)) {
                    calendar.add(field, 1);
                    amount++;
                }
                break;
            }
        }

        // Internationalization key
        String key;
        if (currentField == null) {
            key = "RELATIVE_DATE_JUST_NOW";
        } else {
            String fragment;
            switch (currentField) {
                case Calendar.YEAR:
                    fragment = "YEAR";
                    break;

                case Calendar.MONTH:
                    fragment = "MONTH";
                    break;

                case Calendar.WEEK_OF_YEAR:
                    fragment = "WEEK";
                    break;

                case Calendar.DAY_OF_YEAR:
                    fragment = "DAY";
                    break;

                case Calendar.HOUR_OF_DAY:
                    fragment = "HOUR";
                    break;

                case Calendar.MINUTE:
                    fragment = "MINUTE";
                    break;

                default:
                    fragment = null;
            }

            if (fragment == null) {
                key = "RELATIVE_DATE_JUST_NOW";
            } else if (amount == 1) {
                key = "RELATIVE_DATE_ONE_" + fragment + "_AGO";
            } else {
                key = "RELATIVE_DATE_N_" + fragment + "S_AGO";
            }
        }

        Bundle bundle = getBundleFactory().getBundle(request.getLocale());

        // Text
        String text = bundle.getString(key, amount);
        text = StringUtils.capitalize(text);

        // Container
        Element container = DOM4JUtils.generateElement("abbr", null, text);
        // Date format
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, request.getLocale());

        DOM4JUtils.addAttribute(container, "title", dateFormat.format(date));

        StringWriter sw = new StringWriter();
        try {
            container.write(sw);
        } catch (IOException e) {
            throw new PortletException(e);
        }
        return sw.toString();
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
