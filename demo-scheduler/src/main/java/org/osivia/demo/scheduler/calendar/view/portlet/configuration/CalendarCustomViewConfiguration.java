package org.osivia.demo.scheduler.calendar.view.portlet.configuration;

import org.osivia.services.calendar.view.portlet.configuration.CalendarViewConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.portlet.context.PortletContextAware;

import fr.toutatice.portail.cms.nuxeo.api.CMSPortlet;

/**
 * Calendar view portlet configuration.
 *
 * @author Julien Barberet
 * @see CMSPortlet
 * @see PortletContextAware
 */
@Configuration
@ComponentScan(basePackages = {"org.osivia.demo.scheduler.calendar.view.portlet"})
public class CalendarCustomViewConfiguration extends CalendarViewConfiguration {

    /**
     * Constructor.
     */
    public CalendarCustomViewConfiguration() {
        super();
    }
    
    /**
     * {@inheritDoc}
     */
    @Bean(name = "messageSource")
    @Override
    public ResourceBundleMessageSource getMessageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("Resource", "Resource-custom");
        return messageSource;
    }
}
