package org.osivia.demo.scheduler.plugin.service;

import java.util.Map;

import javax.portlet.PortletContext;

import org.osivia.portal.api.customization.CustomizationContext;

import fr.toutatice.portail.cms.nuxeo.api.domain.ListTemplate;

/**
 * Scheduler plugin service interface.
 * 
 * @author Cédric Krommenhoek
 */
public interface SchedulerPluginService {

    /**
     * Get plugin name.
     * 
     * @return plugin name
     */
    String getPluginName();


    /**
     * Customize list templates.
     * 
     * @param portletContext portlet context
     * @param customizationContext customization context
     * @param listTemplates list templates
     */
    void customizeListTemplates(PortletContext portletContext, CustomizationContext customizationContext, Map<String, ListTemplate> listTemplates);

}
