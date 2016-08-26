/*
 * (C) Copyright 2014 OSIVIA (http://www.osivia.com)
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-2.1.html
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 */
package org.osivia.portal.demo.customizer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletException;

import org.apache.commons.lang.StringUtils;
import org.jboss.portal.core.model.portal.Window;
import org.nuxeo.ecm.automation.client.model.Document;
import org.osivia.portal.api.customization.CustomizationContext;
import org.osivia.portal.api.customization.CustomizationModuleMetadatas;
import org.osivia.portal.api.customization.ICustomizationModule;
import org.osivia.portal.api.customization.ICustomizationModulesRepository;
import org.osivia.portal.api.customization.IProjectCustomizationConfiguration;
import org.osivia.portal.api.urls.IPortalUrlFactory;
import org.osivia.portal.core.cms.CMSException;
import org.osivia.portal.core.cms.CMSItem;
import org.osivia.portal.core.cms.CMSServiceCtx;

import fr.toutatice.portail.cms.nuxeo.api.CMSPortlet;
import fr.toutatice.portail.cms.nuxeo.api.NuxeoController;


/**
 * Redirection for CGU acceptation
 * 
 * @author Jean-Sébastien Steux
 * @see CMSPortlet
 * @see ICustomizationModule
 */
public class CGUCustomizerPortlet extends CMSPortlet implements ICustomizationModule {


    /** Customizer name. */
    private static final String CUSTOMIZER_NAME = "osivia.demo.customizer.cms.cgu";
    /** Customization modules repository attribute name. */
    private static final String ATTRIBUTE_CUSTOMIZATION_MODULES_REPOSITORY = "CustomizationModulesRepository";

    /** Customization modules repository. */
    private ICustomizationModulesRepository repository;
    /** Internationalization customization module metadatas. */
    private final CustomizationModuleMetadatas metadatas;

    private IPortalUrlFactory portalUrlFactory;


    /**
     * Constructor.
     */
    public CGUCustomizerPortlet() {
        super();
        this.metadatas = this.generateMetadatas();

    }


    /**
     * Utility method used to generate attributes bundles customization module metadatas.
     * 
     * @return metadatas
     */
    private CustomizationModuleMetadatas generateMetadatas() {
        CustomizationModuleMetadatas metadatas = new CustomizationModuleMetadatas();
        metadatas.setName(CUSTOMIZER_NAME);
        metadatas.setModule(this);
        metadatas.setCustomizationIDs(Arrays.asList(IProjectCustomizationConfiguration.CUSTOMIZER_ID));
        return metadatas;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void init() throws PortletException {
        super.init();
        this.repository = (ICustomizationModulesRepository) this.getPortletContext().getAttribute(ATTRIBUTE_CUSTOMIZATION_MODULES_REPOSITORY);
        this.repository.register(this.metadatas);

        // Portal URL factory
        this.portalUrlFactory = (IPortalUrlFactory) this.getPortletContext().getAttribute("UrlService");

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
     * Check level.
     *
     * @param context the context
     * @param configuration the configuration
     * @return the string
     * @throws IllegalArgumentException the illegal argument exception
     * @throws CMSException the CMS exception
     */

    public boolean mustCheckCGU(CustomizationContext context, IProjectCustomizationConfiguration configuration) throws IllegalArgumentException, CMSException {

        /*
      
        // Test spécifique à Rennes (à enrichir des RG spécifiques sux workspace v2)

        String path = configuration.getPage().getProperty("osivia.cms.basePath");

        if (path != null) {

            CMSServiceCtx cmsReadItemContext = new CMSServiceCtx();
            cmsReadItemContext.setPortalControllerContext(context.getPortalControllerContext());

            CMSItem publishSpaceConfig = getCMSService().getSpaceConfig(cmsReadItemContext, configuration.getPage().getProperty("osivia.cms.basePath"));
            Document space = (Document) publishSpaceConfig.getNativeItem();


            // TODO : RG RENNES
            if (space.getTitle().contains("Intranet")) {
                return true;
            }
        }

        return false;
*/
        
        return true;
    }

 

    /**
     * {@inheritDoc}
     */
    @Override
    public void customize(String customizationID, CustomizationContext context) {


        Map<String, Object> attributes = context.getAttributes();
        IProjectCustomizationConfiguration configuration = (IProjectCustomizationConfiguration) attributes
                .get(IProjectCustomizationConfiguration.CUSTOMIZER_ATTRIBUTE_CONFIGURATION);

        if (configuration.getHttpServletRequest().getUserPrincipal() != null) {


            if (configuration.isBeforeInvocation()) {


                try {

                    String cguPortalPath = configuration.getPage().getProperty("osivia.services.cgu.path");
                    String cguPortalLevel = configuration.getPage().getProperty("osivia.services.cgu.level");

                    // Is CGU defined ?
                    if (cguPortalLevel == null || cguPortalPath == null)
                        return;

                    // CGU already checked (in session) ?

                    Integer checkedLevel = (Integer) configuration.getHttpServletRequest().getSession().getAttribute("osivia.services.cgu.level");
                    if (checkedLevel != null && checkedLevel.toString().equals(cguPortalLevel))
                        return;


                    NuxeoController ctx = new NuxeoController(getPortletContext());
                    ctx.setServletRequest(configuration.getHttpServletRequest());

                    // No CGU request on CGU !!!
                    Window window = (Window) configuration.getPage().getChild("virtual");
                    if (window != null) {
                        if (window.getDeclaredProperty("osivia.services.cgu.path") != null)
                            return;
                    }


                    if (mustCheckCGU(context, configuration)) {

                        // Get userProfile
                        Document userProfile = (Document) ctx.executeNuxeoCommand(new GetProfileCommand(configuration.getHttpServletRequest()
                                .getUserPrincipal().getName()));


                        String level = userProfile.getProperties().getString("userprofile:terms_of_use_agreement");

                        if (!cguPortalLevel.equals(level)) {

                            Map<String, String> properties = new HashMap<String, String>();
                            Map<String, String> parameters = new HashMap<String, String>();


                            properties.put("osivia.services.cgu.path", cguPortalPath);
                            properties.put("osivia.services.cgu.level", cguPortalLevel);


                            configuration.getHttpServletRequest().getSession()
                                    .setAttribute("osivia.services.cgu.pathToRedirect", configuration.buildRestorableURL());


                            String CguUrl = this.portalUrlFactory.getStartPortletInNewPage(context.getPortalControllerContext(), "CGU", "Validation CGU",
                                    "osivia-services-cgu-portailPortletInstance", properties, parameters);


                            configuration.setRedirectionURL(CguUrl);
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
