package org.osivia.demo.generator.repository;

import javax.portlet.PortletException;

import org.osivia.demo.generator.model.Configuration;
import org.osivia.portal.api.context.PortalControllerContext;

/**
 * Generator repository interface.
 *
 * @author Cédric Krommenhoek
 */
public interface GeneratorRepository {

    /**
     * Get generator configuration.
     *
     * @param portalControllerContext portal controller context
     * @return configuration
     * @throws PortletException
     */
    Configuration getConfiguration(PortalControllerContext portalControllerContext) throws PortletException;


    /**
     * Set generator configuration.
     *
     * @param portalControllerContext portal controller context
     * @param configuration generator configuration
     * @throws PortletException
     */
    void setConfiguration(PortalControllerContext portalControllerContext, Configuration configuration) throws PortletException;


    /**
     * Generate.
     *
     * @param portalControllerContext portal controller context
     * @throws PortletException
     */
    void generate(PortalControllerContext portalControllerContext) throws PortletException;


    /**
     * Purge.
     * 
     * @param portalControllerContext portal controller context
     * @throws PortletException
     */
    void purge(PortalControllerContext portalControllerContext) throws PortletException;

}
