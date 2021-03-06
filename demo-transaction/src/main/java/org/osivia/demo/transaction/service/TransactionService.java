package org.osivia.demo.transaction.service;

import javax.portlet.PortletException;

import org.osivia.demo.transaction.model.CommandNotification;
import org.osivia.demo.transaction.model.Configuration;
import org.osivia.portal.api.context.PortalControllerContext;

/**
 * Transaction service interface.
 *
 * @author Julien Barberet
 */
public interface TransactionService {

    /**
     * Get transaction configuration.
     *
     * @param portalControllerContext portal controller context
     * @return configuration
     * @throws PortletException
     */
    Configuration getConfiguration(PortalControllerContext portalControllerContext) throws PortletException;


    /**
     * Save transaction configuration.
     *
     * @param portalControllerContext portal controller context
     * @param configuration transaction configuration
     * @throws PortletException
     */
    void saveConfiguration(PortalControllerContext portalControllerContext, Configuration configuration) throws PortletException;

    /**
     * Create 1 document 
     * @param portalControllerContext
     * @throws PortletException
     */
    CommandNotification createOne(PortalControllerContext portalControllerContext) throws PortletException;
    
    /**
     * Create several documents
     * 
     * @param portalControllerContext
     * @throws PortletException
     */
    CommandNotification createSeveral(PortalControllerContext portalControllerContext) throws PortletException;
    
    /**
     * Create and update document
     * 
     * @param portalControllerContext
     * @throws PortletException
     */
    CommandNotification createAndUpdate(PortalControllerContext portalControllerContext) throws PortletException;
    
    
    /**
     * Create and update document
     * 
     * @param portalControllerContext
     * @throws PortletException
     */
    CommandNotification createAndUpdateTx2(PortalControllerContext portalControllerContext) throws PortletException;
    
    /**
     * Create and rollback
     * 
     * @param portalControllerContext
     * @throws PortletException
     */
    CommandNotification createAndRollback(PortalControllerContext portalControllerContext) throws PortletException;
    
    /**
     * Delete and rollback
     * 
     * @param portalControllerContext
     * @throws PortletException
     */
    CommandNotification deleteAndRollback(PortalControllerContext portalControllerContext) throws PortletException;
    
    /**
     * Create blob
     * 
     * @param portalControllerContext
     * @throws PortletException
     */
    CommandNotification createBlob(PortalControllerContext portalControllerContext) throws PortletException;
    
    /**
     * Create blobs
     * 
     * @param portalControllerContext
     * @throws PortletException
     */
    CommandNotification createBlobs(PortalControllerContext portalControllerContext) throws PortletException;
    
    
    /**
     * FetchPublicationInfo
     * 
     * @param portalControllerContext
     * @throws PortletException
     */
    CommandNotification fetchPublicationInfo(PortalControllerContext portalControllerContext) throws PortletException;
    
    
    /**
     * Update and Rollback
     * 
     * @param portalControllerContext
     * @throws PortletException
     */
    CommandNotification updateAndRollback(PortalControllerContext portalControllerContext) throws PortletException;
    
}
