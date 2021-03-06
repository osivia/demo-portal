package org.osivia.demo.transaction.repository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.UUID;

import javax.naming.NamingException;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.osivia.demo.transaction.model.CommandNotification;
import org.osivia.demo.transaction.model.Configuration;
import org.osivia.demo.transaction.repository.command.CreateAndRollbackCommand;
import org.osivia.demo.transaction.repository.command.CreateAndTxUpdateCommand;
import org.osivia.demo.transaction.repository.command.CreateAndUpdateCommand;
import org.osivia.demo.transaction.repository.command.CreateBlobCommand;
import org.osivia.demo.transaction.repository.command.CreateBlobsCommand;
import org.osivia.demo.transaction.repository.command.DeleteAndRollbackCommand;
import org.osivia.demo.transaction.repository.command.FetchPublicationInfoCommand;
import org.osivia.demo.transaction.repository.command.OneCreationCommand;
import org.osivia.demo.transaction.repository.command.SeveralCreationCommand;
import org.osivia.demo.transaction.repository.command.UpdateAndRollbackCommand;
import org.osivia.portal.api.cache.services.CacheInfo;
import org.osivia.portal.api.context.PortalControllerContext;
import org.osivia.portal.api.windows.PortalWindow;
import org.osivia.portal.api.windows.WindowFactory;
import org.springframework.stereotype.Repository;

import fr.toutatice.portail.cms.nuxeo.api.NuxeoController;
import fr.toutatice.portail.cms.nuxeo.api.services.NuxeoCommandContext;

/**
 * Transaction repository implementation.
 *
 * @author Julien Barberet
 * @see TransactionRepository
 */
@Repository
public class TransactionRepositoryImpl implements TransactionRepository {

	private static Logger LOGGER = Logger.getLogger(TransactionRepositoryImpl.class);

    private static final String PORTLET_TRANSACTION_CONFIGURATION = "portlet.transaction.configuration";
    /** Generator properties file name. */
    private static final String PROPERTIES_FILE_NAME = "transaction.properties";
	/** Path. */
	private static final String PATH_PROPERTY = "transaction.path";
	/** Number of spaces. */
    private static final String WEBID = "transaction.webid";

    /** Generator properties. */
    private final Properties properties;
    
    /**
     * Constructor.
     *
     * @throws IOException
     * @throws NamingException
     */
    public TransactionRepositoryImpl() throws IOException, NamingException {
        super();

        // Generator properties
        this.properties = new Properties();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME);
        if (inputStream != null) {
            this.properties.load(inputStream);
        } else {
            throw new FileNotFoundException(PROPERTIES_FILE_NAME);
        }


    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Configuration getConfiguration(PortalControllerContext portalControllerContext) throws PortletException {
        // Window
        PortalWindow window = WindowFactory.getWindow(portalControllerContext.getRequest());

        //Configuration
        Configuration configuration = (Configuration) portalControllerContext.getRequest().getPortletSession().getAttribute(PORTLET_TRANSACTION_CONFIGURATION);
        if (configuration == null)
        {
            configuration = new Configuration();

            //int nbOfworkspaces = NumberUtils.toInt(StringUtils.defaultIfEmpty(window.getProperty(NB_WORKSPACES_PROPERTY), "10");
            String path = StringUtils.defaultIfEmpty(window.getProperty(PATH_PROPERTY),
                    this.properties.getProperty(PATH_PROPERTY));

            String webid = StringUtils.defaultIfEmpty(window.getProperty(WEBID),
                    this.properties.getProperty(WEBID));


            configuration.setPath(path);
            configuration.setWebid(webid);
            portalControllerContext.getRequest().getPortletSession().setAttribute(PORTLET_TRANSACTION_CONFIGURATION, configuration);
        }

        return configuration;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void setConfiguration(PortalControllerContext portalControllerContext, Configuration configuration) throws PortletException {
    	
        
        // Window
      PortalWindow window = WindowFactory.getWindow(portalControllerContext.getRequest());

      if (configuration.getPath() == null) {
    	  configuration.setPath(this.properties.getProperty(PATH_PROPERTY));
      }
      if (configuration.getWebid() == null) {
    	  configuration.setWebid(this.properties.getProperty(WEBID));
      }
      
      // Nuxeo request
      window.setProperty(PATH_PROPERTY, configuration.getPath());

      // BeanShell
      window.setProperty(WEBID,configuration.getWebid());

    }

    
    
    /** 
     * {@inheritDoc}
     * @throws PortletException 
     */
    @Override
    public CommandNotification createOne(PortalControllerContext portalControllerContext) throws PortletException {
        
        // Configuration
        Configuration configuration = this.getConfiguration(portalControllerContext);
        
        // Nuxeo controller
        NuxeoController nuxeoController = this.getNuxeoController(portalControllerContext);
        nuxeoController.setAuthType(NuxeoCommandContext.AUTH_TYPE_SUPERUSER);
        nuxeoController.setCacheType(CacheInfo.CACHE_SCOPE_PORTLET_CONTEXT);
        nuxeoController.setAsynchronousCommand(false);
        
        CommandNotification commandNotification = (CommandNotification) nuxeoController.executeNuxeoCommand(new OneCreationCommand(configuration,UUID.randomUUID().toString()));
        return commandNotification;
        
    }
    
    /** 
     * {@inheritDoc}
     * @throws PortletException 
     */
    @Override
    public CommandNotification createSeveral(PortalControllerContext portalControllerContext) throws PortletException {
        
        // Configuration
        Configuration configuration = this.getConfiguration(portalControllerContext);
        
        // Nuxeo controller
        NuxeoController nuxeoController = this.getNuxeoController(portalControllerContext);
        nuxeoController.setAuthType(NuxeoCommandContext.AUTH_TYPE_SUPERUSER);
        nuxeoController.setCacheType(CacheInfo.CACHE_SCOPE_PORTLET_CONTEXT);
        nuxeoController.setAsynchronousCommand(false);
        
        CommandNotification commandNotification = (CommandNotification) nuxeoController.executeNuxeoCommand(new SeveralCreationCommand(configuration,UUID.randomUUID().toString()));
        return commandNotification;
        
    }
    
    /** 
     * {@inheritDoc}
     * @throws PortletException 
     */
    @Override
    public CommandNotification createAndUpdate(PortalControllerContext portalControllerContext) throws PortletException {
        
        // Configuration
        Configuration configuration = this.getConfiguration(portalControllerContext);
        
        // Nuxeo controller
        NuxeoController nuxeoController = this.getNuxeoController(portalControllerContext);
        nuxeoController.setAuthType(NuxeoCommandContext.AUTH_TYPE_SUPERUSER);
        nuxeoController.setCacheType(CacheInfo.CACHE_SCOPE_PORTLET_CONTEXT);
        nuxeoController.setAsynchronousCommand(false);
        
        CommandNotification commandNotification = (CommandNotification) nuxeoController.executeNuxeoCommand(new CreateAndUpdateCommand(configuration,UUID.randomUUID().toString()));
        return commandNotification;
    }
    
    
    /** 
     * {@inheritDoc}
     * @throws PortletException 
     */
    @Override
    public CommandNotification createAndUpdateTx2(PortalControllerContext portalControllerContext) throws PortletException {
        
        // Configuration
        Configuration configuration = this.getConfiguration(portalControllerContext);
        
        // Nuxeo controller
        NuxeoController nuxeoController = this.getNuxeoController(portalControllerContext);
        nuxeoController.setAuthType(NuxeoCommandContext.AUTH_TYPE_SUPERUSER);
        nuxeoController.setCacheType(CacheInfo.CACHE_SCOPE_PORTLET_CONTEXT);
        nuxeoController.setAsynchronousCommand(false);
        
        CommandNotification commandNotification = (CommandNotification) nuxeoController.executeNuxeoCommand(new CreateAndTxUpdateCommand(configuration,UUID.randomUUID().toString()));
        return commandNotification;
    }
    
    /** 
     * {@inheritDoc}
     * @throws PortletException 
     */
    @Override
    public CommandNotification createAndRollback(PortalControllerContext portalControllerContext) throws PortletException {
        
        // Configuration
        Configuration configuration = this.getConfiguration(portalControllerContext);
        
        // Nuxeo controller
        NuxeoController nuxeoController = this.getNuxeoController(portalControllerContext);
        nuxeoController.setAuthType(NuxeoCommandContext.AUTH_TYPE_SUPERUSER);
        nuxeoController.setCacheType(CacheInfo.CACHE_SCOPE_PORTLET_CONTEXT);
        nuxeoController.setAsynchronousCommand(false);
        
        CommandNotification commandNotification = (CommandNotification) nuxeoController.executeNuxeoCommand(new CreateAndRollbackCommand(configuration, UUID.randomUUID().toString()));
        return commandNotification;
    }
    
    /** 
     * {@inheritDoc}
     * @throws PortletException 
     */
    @Override
    public CommandNotification deleteAndRollback(PortalControllerContext portalControllerContext) throws PortletException {
        
        // Configuration
        Configuration configuration = this.getConfiguration(portalControllerContext);
        
        // Nuxeo controller
        NuxeoController nuxeoController = this.getNuxeoController(portalControllerContext);
        nuxeoController.setAuthType(NuxeoCommandContext.AUTH_TYPE_SUPERUSER);
        nuxeoController.setCacheType(CacheInfo.CACHE_SCOPE_PORTLET_CONTEXT);
        nuxeoController.setAsynchronousCommand(false);
        
        CommandNotification commandNotification = (CommandNotification) nuxeoController.executeNuxeoCommand(new DeleteAndRollbackCommand(configuration,UUID.randomUUID().toString()));
        return commandNotification;
    }
    
    /** 
     * {@inheritDoc}
     * @throws PortletException 
     */
    @Override
    public CommandNotification createBlob(PortalControllerContext portalControllerContext) throws PortletException {
        
        // Configuration
        Configuration configuration = this.getConfiguration(portalControllerContext);
        
        // Nuxeo controller
        NuxeoController nuxeoController = this.getNuxeoController(portalControllerContext);
        nuxeoController.setAuthType(NuxeoCommandContext.AUTH_TYPE_SUPERUSER);
        nuxeoController.setCacheType(CacheInfo.CACHE_SCOPE_PORTLET_CONTEXT);
        nuxeoController.setAsynchronousCommand(false);
        
        CommandNotification commandNotification = (CommandNotification) nuxeoController.executeNuxeoCommand(new CreateBlobCommand(configuration,UUID.randomUUID().toString()));
        return commandNotification;
    }
    
    /** 
     * {@inheritDoc}
     * @throws PortletException 
     */
    @Override
    public CommandNotification createBlobs(PortalControllerContext portalControllerContext) throws PortletException {
        
        // Configuration
        Configuration configuration = this.getConfiguration(portalControllerContext);
        
        // Nuxeo controller
        NuxeoController nuxeoController = this.getNuxeoController(portalControllerContext);
        nuxeoController.setAuthType(NuxeoCommandContext.AUTH_TYPE_SUPERUSER);
        nuxeoController.setCacheType(CacheInfo.CACHE_SCOPE_PORTLET_CONTEXT);
        nuxeoController.setAsynchronousCommand(false);
        
        CommandNotification commandNotification = (CommandNotification) nuxeoController.executeNuxeoCommand(new CreateBlobsCommand(configuration,UUID.randomUUID().toString()));
        return commandNotification;
    }

    /** 
     * {@inheritDoc}
     * @throws PortletException 
     */
    @Override
    public CommandNotification fetchPublicationInfo(PortalControllerContext portalControllerContext) throws PortletException {
        
        // Configuration
        Configuration configuration = this.getConfiguration(portalControllerContext);
        
        // Nuxeo controller
        NuxeoController nuxeoController = this.getNuxeoController(portalControllerContext);
        nuxeoController.setAuthType(NuxeoCommandContext.AUTH_TYPE_SUPERUSER);
        nuxeoController.setCacheType(CacheInfo.CACHE_SCOPE_PORTLET_CONTEXT);
        nuxeoController.setAsynchronousCommand(false);
        
        CommandNotification commandNotification = (CommandNotification) nuxeoController.executeNuxeoCommand(new FetchPublicationInfoCommand(configuration,UUID.randomUUID().toString()));
        return commandNotification;
    }
    
    /** 
     * {@inheritDoc}
     * @throws PortletException 
     */
    @Override
    public CommandNotification updateAndRollback(PortalControllerContext portalControllerContext) throws PortletException {
        
        // Configuration
        Configuration configuration = this.getConfiguration(portalControllerContext);
        
        // Nuxeo controller
        NuxeoController nuxeoController = this.getNuxeoController(portalControllerContext);
        nuxeoController.setAuthType(NuxeoCommandContext.AUTH_TYPE_SUPERUSER);
        nuxeoController.setCacheType(CacheInfo.CACHE_SCOPE_PORTLET_CONTEXT);
        nuxeoController.setAsynchronousCommand(false);
        
        CommandNotification commandNotification = (CommandNotification) nuxeoController.executeNuxeoCommand(new UpdateAndRollbackCommand(configuration,UUID.randomUUID().toString()));
        return commandNotification;
    }
    
    /**
     * Get Nuxeo controller
     *
     * @param portalControllerContext portal controller context
     * @return Nuxeo controller
     */
    private NuxeoController getNuxeoController(PortalControllerContext portalControllerContext) {
        PortletRequest request = portalControllerContext.getRequest();
        PortletResponse response = portalControllerContext.getResponse();
        PortletContext portletContext = portalControllerContext.getPortletCtx();
        return new NuxeoController(request, response, portletContext);
    }

}
