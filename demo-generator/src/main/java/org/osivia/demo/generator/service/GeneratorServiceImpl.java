package org.osivia.demo.generator.service;

import javax.naming.NamingException;
import javax.portlet.PortletException;

import org.osivia.demo.generator.model.Configuration;
import org.osivia.demo.generator.repository.GeneratorRepository;
import org.osivia.portal.api.context.PortalControllerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Generator service implementation.
 *
 * @author Cédric Krommenhoek
 * @see GeneratorService
 */
@Service
public class GeneratorServiceImpl implements GeneratorService {

    /** Generator repository. */
    @Autowired
    private GeneratorRepository repository;


    /**
     * Constructor.
     *
     * @throws NamingException
     */
    public GeneratorServiceImpl() throws NamingException {
        super();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Configuration getConfiguration(PortalControllerContext portalControllerContext) throws PortletException {
        return this.repository.getConfiguration(portalControllerContext);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void saveConfiguration(PortalControllerContext portalControllerContext, Configuration configuration) throws PortletException {
        this.repository.setConfiguration(portalControllerContext, configuration);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void generate(PortalControllerContext portalControllerContext) throws PortletException {
        this.repository.generate(portalControllerContext);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void purge(PortalControllerContext portalControllerContext) throws PortletException {
        this.repository.purge(portalControllerContext);
    }

}
