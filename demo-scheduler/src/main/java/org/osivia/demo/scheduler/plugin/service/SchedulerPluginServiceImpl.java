package org.osivia.demo.scheduler.plugin.service;

import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletContext;

import org.osivia.demo.scheduler.plugin.model.InterventionsListTemplateModule;
import org.osivia.portal.api.customization.CustomizationContext;
import org.osivia.portal.api.internationalization.Bundle;
import org.osivia.portal.api.internationalization.IBundleFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import fr.toutatice.portail.cms.nuxeo.api.domain.ListTemplate;
import fr.toutatice.portail.cms.nuxeo.api.portlet.IPortletModule;

/**
 * Scheduler plugin service implementation.
 * 
 * @author Cédric Krommenhoek
 * @see SchedulerPluginService
 */
@Service
public class SchedulerPluginServiceImpl implements SchedulerPluginService {

    /** Plugin name. */
    private static final String PLUGIN_NAME = "scheduler.plugin";

    /** Interventions list identifier. */
    private static final String LIST_INTERVENTIONS_ID = "interventions";
    /** Interventions list schemas. */
    private static final String LIST_INTERVENTIONS_SCHEMAS = "dublincore, common, toutatice, procedureInstance";


    /** Application context. */
    @Autowired
    private ApplicationContext applicationContext;

    /** Portlet context. */
    @Autowired
    private PortletContext portletContext;

    /** Internationalization bundle factory. */
    @Autowired
    private IBundleFactory bundleFactory;


    /**
     * Constructor.
     */
    public SchedulerPluginServiceImpl() {
        super();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String getPluginName() {
        return PLUGIN_NAME;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void customizeListTemplates(PortletContext portletContext, CustomizationContext customizationContext, Map<String, ListTemplate> listTemplates) {
        // Locale
        Locale locale = customizationContext.getLocale();
        // Internationalization bundle
        Bundle bundle = this.bundleFactory.getBundle(locale);

        // Interventions
        String label = bundle.getString("LIST_TEMPLATE_INTERVENTIONS");
        IPortletModule module = this.applicationContext.getBean(InterventionsListTemplateModule.class, this.portletContext);
        ListTemplate interventions = new ListTemplate(LIST_INTERVENTIONS_ID, label, LIST_INTERVENTIONS_SCHEMAS);
        interventions.setModule(module);
        listTemplates.put(interventions.getKey(), interventions);
    }

}
