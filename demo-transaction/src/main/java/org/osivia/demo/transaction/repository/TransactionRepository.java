package org.osivia.demo.transaction.repository;

import javax.portlet.PortletException;

import org.osivia.demo.transaction.model.CommandNotification;
import org.osivia.demo.transaction.model.Configuration;
import org.osivia.portal.api.context.PortalControllerContext;

/**
 * Transaction repository interface.
 *
 * @author Julien Barberet
 */
public interface TransactionRepository {

    /**
     * Get transaction configuration.
     *
     * @param portalControllerContext portal controller context
     * @return configuration
     * @throws PortletException
     */
    Configuration getConfiguration(PortalControllerContext portalControllerContext) throws PortletException;


    /**
     * Set transaction configuration.
     *
     * @param portalControllerContext portal controller context
     * @param configuration transaction configuration
     * @throws PortletException
     */
    void setConfiguration(PortalControllerContext portalControllerContext, Configuration configuration) throws PortletException;


    CommandNotification createOne(PortalControllerContext portalControllerContext) throws PortletException;
    
    CommandNotification createSeveral(PortalControllerContext portalControllerContext) throws PortletException;
    
    CommandNotification createAndUpdate(PortalControllerContext portalControllerContext) throws PortletException;
  
    CommandNotification createAndUpdateTx2(PortalControllerContext portalControllerContext) throws PortletException;

    CommandNotification createAndRollback(PortalControllerContext portalControllerContext) throws PortletException;
    
    CommandNotification deleteAndRollback(PortalControllerContext portalControllerContext) throws PortletException;
    
    CommandNotification createBlob(PortalControllerContext portalControllerContext) throws PortletException;
    
    CommandNotification createBlobs(PortalControllerContext portalControllerContext) throws PortletException;
    
    CommandNotification fetchPublicationInfo(PortalControllerContext portalControllerContext) throws PortletException;
    
    CommandNotification updateAndRollback(PortalControllerContext portalControllerContext) throws PortletException;
}
