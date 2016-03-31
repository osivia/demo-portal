package org.osivia.demo.customizer.plugin;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletException;

import org.osivia.portal.api.customization.CustomizationContext;
import org.osivia.portal.api.internationalization.Bundle;
import org.osivia.portal.api.internationalization.IBundleFactory;
import org.osivia.portal.api.internationalization.IInternationalizationService;
import org.osivia.portal.api.locator.Locator;
import org.osivia.portal.core.cms.DomainContextualization;

import fr.toutatice.portail.cms.nuxeo.api.domain.AbstractPluginPortlet;
import fr.toutatice.portail.cms.nuxeo.api.domain.ListTemplate;

/**
 * Demo plugin.
 *
 * @author Cédric Krommenhoek
 * @see AbstractPluginPortlet
 */
public class DemoPlugin extends AbstractPluginPortlet {

    /** Plugin name. */
    private static final String PLUGIN_NAME = "demo.plugin";

    /** Search results list schemas. */
    private static final String SEARCH_RESULTS_SCHEMAS = "dublincore, toutatice";


    /** Internationalization bundle factory. */
    private IBundleFactory bundleFactory;


    /**
     * Constructor.
     */
    public DemoPlugin() {
        super();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void init() throws PortletException {
        super.init();

        // Internationalization bundle factory
        IInternationalizationService internationalizationService = Locator.findMBean(IInternationalizationService.class,
                IInternationalizationService.MBEAN_NAME);
        this.bundleFactory = internationalizationService.getBundleFactory(this.getClass().getClassLoader());
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected String getPluginName() {
        return PLUGIN_NAME;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected void customizeCMSProperties(String customizationID, CustomizationContext context) {
        // List templates
        this.customizeListTemplates(context);
        // Domain contextualization
        this.customizeDomainContextualizations(context);
    }


    /**
     * Customize list templates.
     *
     * @param context customization context
     */
    private void customizeListTemplates(CustomizationContext context) {
        // Internationalization bundle
        Bundle bundle = this.bundleFactory.getBundle(context.getLocale());

        // List templates
        Map<String, ListTemplate> templates = this.getListTemplates(context);

        // Search results
        ListTemplate searchResults = new ListTemplate("search-results", bundle.getString("LIST_TEMPLATE_SEARCH_RESULTS"), SEARCH_RESULTS_SCHEMAS);
        templates.put(searchResults.getKey(), searchResults);
    }


    private void customizeDomainContextualizations(CustomizationContext context) {
        List<DomainContextualization> domainContextualizations = this.getDomainContextualizations(context);

        // Etablissement
        EtablissementDomainContextualization etablissement = new EtablissementDomainContextualization();
        domainContextualizations.add(etablissement);
    }

}
