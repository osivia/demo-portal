package org.osivia.demo.customizer.plugin;

import java.util.List;

import javax.portlet.PortletContext;

import org.osivia.portal.api.cms.EcmDocument;
import org.osivia.portal.api.context.PortalControllerContext;
import org.osivia.portal.core.cms.CMSException;

import fr.toutatice.portail.cms.nuxeo.api.domain.INavigationAdapterModule;
import fr.toutatice.portail.cms.nuxeo.api.domain.Symlink;

/**
 * Extranet navigation adapter module.
 * 
 * @author Cédric Krommenhoek
 * @see INavigationAdapterModule
 */
public class ExtranetNavigationAdapterModule implements INavigationAdapterModule {

    /** Portlet context. */
    private PortletContext portletContext;


    /**
     * Constructor.
     * 
     * @param portletContext portlet context
     */
    public ExtranetNavigationAdapterModule(PortletContext portletContext) {
        super();
        this.portletContext = portletContext;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String adaptNavigationPath(PortalControllerContext portalControllerContext, EcmDocument document) throws CMSException {
        // TODO Auto-generated method stub
        return null;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Symlink> getSymlinks(PortalControllerContext portalControllerContext) throws CMSException {
        // TODO Auto-generated method stub
        return null;
    }

}
