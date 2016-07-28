package org.osivia.demo.customizer.project;

import java.security.Principal;
import java.util.Arrays;
import java.util.Map;

import javax.portlet.GenericPortlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.BooleanUtils;
import org.jboss.portal.core.model.portal.Page;
import org.jboss.portal.core.model.portal.Portal;
import org.osivia.portal.api.context.PortalControllerContext;
import org.osivia.portal.api.customization.CustomizationContext;
import org.osivia.portal.api.customization.CustomizationModuleMetadatas;
import org.osivia.portal.api.customization.ICustomizationModule;
import org.osivia.portal.api.customization.ICustomizationModulesRepository;
import org.osivia.portal.api.customization.IProjectCustomizationConfiguration;
import org.osivia.portal.api.directory.IDirectoryServiceLocator;
import org.osivia.portal.api.directory.entity.DirectoryPerson;
import org.osivia.portal.api.locator.Locator;
import org.osivia.portal.api.urls.IPortalUrlFactory;

import fr.toutatice.portail.cms.nuxeo.api.NuxeoController;

/**
 * Project customizer.
 *
 * @author Cédric Krommenhoek
 * @see GenericPortlet
 * @see ICustomizationModule
 */
public class ProjectCustomizer extends GenericPortlet implements ICustomizationModule {

    /** Customizer name. */
    private static final String CUSTOMIZER_NAME = "cartoun.customizer.project";
    /** Customization modules repository attribute name. */
    private static final String ATTRIBUTE_CUSTOMIZATION_MODULES_REPOSITORY = "CustomizationModulesRepository";


    /** Customization modules repository. */
    private ICustomizationModulesRepository repository;

    /** Customization module metadatas. */
    private final CustomizationModuleMetadatas metadatas;

    /** Portal URL factory. */
    private final IPortalUrlFactory portalURLFactory;

    /** directoryServiceLocator */
    private final IDirectoryServiceLocator directoryServiceLocator;

    /**
     * Constructor.
     */
    public ProjectCustomizer() {
        super();
        this.metadatas = this.generateMetadatas();

        // Portal URL factory
        this.portalURLFactory = Locator.findMBean(IPortalUrlFactory.class, IPortalUrlFactory.MBEAN_NAME);

        this.directoryServiceLocator = Locator.findMBean(IDirectoryServiceLocator.class, IDirectoryServiceLocator.MBEAN_NAME);
    }


    /**
     * Generate customization module metadatas.
     *
     * @return metadatas
     */
    private CustomizationModuleMetadatas generateMetadatas() {
        final CustomizationModuleMetadatas metadatas = new CustomizationModuleMetadatas();
        metadatas.setName(CUSTOMIZER_NAME);
        metadatas.setModule(this);
        metadatas.setCustomizationIDs(Arrays.asList(IProjectCustomizationConfiguration.CUSTOMIZER_ID));
        return metadatas;
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public void init(PortletConfig portletConfig) throws PortletException {
        super.init(portletConfig);
        this.repository = (ICustomizationModulesRepository) this.getPortletContext().getAttribute(ATTRIBUTE_CUSTOMIZATION_MODULES_REPOSITORY);
        this.repository.register(this.metadatas);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void destroy() {
        super.destroy();
        this.repository.unregister(this.metadatas);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void customize(String customizationID, CustomizationContext context) {
        final Map<String, Object> attributes = context.getAttributes();

        // Project customization configuration
        final IProjectCustomizationConfiguration configuration = (IProjectCustomizationConfiguration) attributes
                .get(IProjectCustomizationConfiguration.CUSTOMIZER_ATTRIBUTE_CONFIGURATION);

        if (configuration.isBeforeInvocation() && !configuration.isAdministrator()) {
            final HttpServletRequest request = configuration.getHttpServletRequest();
            final boolean redirect = BooleanUtils.toBoolean(request.getParameter("redirect"));
            final Principal principal = request.getUserPrincipal();

            if (!redirect && (principal != null)) {
                final Page page = configuration.getPage();
                final Portal portal = page.getPortal();
                if (page.equals(portal.getDefaultPage())) {
                    // Portal controller context
                    final PortalControllerContext portalControllerContext = context.getPortalControllerContext();
                    final HttpServletRequest httpServletRequest = portalControllerContext.getHttpServletRequest();

                    String webIdACA = (String) httpServletRequest.getSession().getAttribute("webIdACA");
                    if (webIdACA == null) {
                        // get codeACA
                        final String uid = httpServletRequest.getUserPrincipal().getName();
                        final DirectoryPerson person = directoryServiceLocator.getDirectoryService().getPerson(uid);
                        String academy = null;
                        if( person == null){
                            // Pad de personne dans le LDAP
                            configuration.setRedirectionURL("/portail/portal/cartoun/loginpage"); 
                            return;
                        }
                        
                        if( person.getExtraProperties() != null)  {
                            academy = (String) person.getExtraProperties().get("codeAcademie");
                                                  
                        }
                       
                        // build webId
                        webIdACA = "sun_" + academy;
                        // set webIdACA in session attribute
                        httpServletRequest.getSession().setAttribute("webIdACA", webIdACA);
                    }
                    final String sunPath = NuxeoController.webIdToCmsPath(webIdACA);


                    final String url = this.portalURLFactory.getCMSUrl(portalControllerContext, null, sunPath, null, null, null, null, null, null, null);
                    configuration.setRedirectionURL(url);
                }
            }
        }
    }

}
