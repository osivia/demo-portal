package org.osivia.demo.scheduler.plugin.configuration;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;

import org.osivia.portal.api.Constants;
import org.osivia.portal.api.internationalization.IBundleFactory;
import org.osivia.portal.api.internationalization.IInternationalizationService;
import org.osivia.portal.api.locator.Locator;
import org.osivia.portal.api.portlet.PortletAppUtils;
import org.osivia.portal.api.urls.IPortalUrlFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.portlet.context.PortletConfigAware;
import org.springframework.web.portlet.context.PortletContextAware;

/**
 * Scheduler plugin configuration.
 * 
 * @author Cédric Krommenhoek
 * @see PortletContextAware
 */
@Configuration
@ComponentScan(basePackages = "org.osivia.demo.scheduler.plugin")
public class SchedulerPluginConfiguration implements PortletConfigAware {

    /** Application context . */
    @Autowired
    private ApplicationContext applicationContext;


    /**
     * Constructor.
     */
    public SchedulerPluginConfiguration() {
        super();
    }





    /**
     * Get message source.
     *
     * @return message source
     */
    @Bean(name = "messageSource")
    public ResourceBundleMessageSource getMessageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("plugin");
        return messageSource;
    }


    /**
     * Get portal URL factory.
     * 
     * @return portal URL factory
     */
    @Bean
    public IPortalUrlFactory getPortalUrlFactory() {
        return Locator.findMBean(IPortalUrlFactory.class, IPortalUrlFactory.MBEAN_NAME);
    }


    /**
     * Get internationalization bundle factory.
     * 
     * @return internationalization bundle factory
     */
    @Bean
    public IBundleFactory getBundleFactory() {
        IInternationalizationService internationalizationService = Locator.findMBean(IInternationalizationService.class,
                IInternationalizationService.MBEAN_NAME);
        return internationalizationService.getBundleFactory(this.getClass().getClassLoader(), this.applicationContext);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void setPortletConfig(PortletConfig portletConfig) {
            PortletAppUtils.registerApplication(portletConfig, applicationContext);            

    }
}
