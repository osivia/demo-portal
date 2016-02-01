package org.osivia.demo.customizer.attributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jboss.portal.core.controller.ControllerContext;
import org.jboss.portal.core.controller.ControllerException;
import org.jboss.portal.core.model.portal.command.render.RenderPageCommand;
import org.jboss.portal.core.theme.PageRendition;
import org.osivia.portal.api.PortalException;
import org.osivia.portal.api.context.PortalControllerContext;
import org.osivia.portal.api.locator.Locator;
import org.osivia.portal.api.theming.IAttributesBundle;
import org.osivia.portal.api.urls.IPortalUrlFactory;

import fr.toutatice.portail.cms.nuxeo.api.services.NuxeoConnectionProperties;

/**
 * Customized attributes bundle.
 *
 * @author Cédric Krommenhoek
 * @see IAttributesBundle
 */
public class CustomizedAttributesBundle implements IAttributesBundle {

    /** SSO applications attribute name. */
    private static final String APPLICATIONS = "osivia.sso.applications";
    /** Advanced search URL attribute name. */
    private static final String ADVANCED_SEARCH_URL = "demo.advancedSearch.url";

    /** Singleton instance. */
    private static final IAttributesBundle INSTANCE = new CustomizedAttributesBundle();


    /** Attribute names. */
    private final Set<String> names;

    /** URL factory. */
    private final IPortalUrlFactory urlFactory;

    /** SSO applications. */
    private final List<String> applications;


    /**
     * Constructor.
     */
    private CustomizedAttributesBundle() {
        super();

        // Attributes names
        this.names = new HashSet<String>();
        this.names.add(APPLICATIONS);
        this.names.add(ADVANCED_SEARCH_URL);

        // URL factory
        this.urlFactory = Locator.findMBean(IPortalUrlFactory.class, IPortalUrlFactory.MBEAN_NAME);

        // SSO applications
        this.applications = new ArrayList<String>();
        this.applications.add(NuxeoConnectionProperties.getPublicBaseUri().toString().concat("/logout"));
        this.applications.add(System.getProperty("cas.logout"));
    }


    /**
     * Get singleton instance.
     *
     * @return instance
     */
    public static IAttributesBundle getInstance() {
        return INSTANCE;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void fill(RenderPageCommand renderPageCommand, PageRendition pageRendition, Map<String, Object> attributes) throws ControllerException {
        // Controller context
        ControllerContext controllerContext = renderPageCommand.getControllerContext();
        // Portal controller context
        PortalControllerContext portalControllerContext = new PortalControllerContext(controllerContext);


        // SSO applications
        attributes.put(APPLICATIONS, this.applications);


        // Advanced search URL
        Map<String, String> properties = new HashMap<String, String>();
        Map<String, String> parameters = new HashMap<String, String>();
        String advancedSearchUrl;
        try {
            advancedSearchUrl = this.urlFactory.getStartPageUrl(portalControllerContext, null, "/default/templates/advancedSearch", properties,
                    parameters);
        } catch (PortalException e) {
            advancedSearchUrl = "#";
        }
        attributes.put(ADVANCED_SEARCH_URL, advancedSearchUrl);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Set<String> getAttributeNames() {
        return this.names;
    }

}
