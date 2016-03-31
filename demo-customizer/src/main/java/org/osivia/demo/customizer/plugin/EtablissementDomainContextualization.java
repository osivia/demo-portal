package org.osivia.demo.customizer.plugin;

import java.util.Arrays;
import java.util.List;

import org.osivia.portal.api.context.PortalControllerContext;
import org.osivia.portal.core.cms.DomainContextualization;


public class EtablissementDomainContextualization implements DomainContextualization {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contextualize(PortalControllerContext portalControllerContext, String domainPath) {
        return "/etablissement".equals(domainPath);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getSites(PortalControllerContext portalControllerContext) {
        return Arrays.asList("site", "espace-de-publication");
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String getDefaultSite(PortalControllerContext portalControllerContext) {
        return null;
    }

}
