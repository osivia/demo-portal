package org.osivia.demo.customizer.plugin;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import javax.portlet.PortletException;

import org.osivia.demo.customizer.plugin.fragment.LaunchSupportPortletModule;
import org.osivia.demo.customizer.plugin.fragment.ProduitRecordFragmentModule;
import org.osivia.demo.customizer.plugin.list.AccessoriesListTemplateModule;
import org.osivia.demo.customizer.plugin.menubar.DemoMenubarModule;
import org.osivia.portal.api.customization.CustomizationContext;
import org.osivia.portal.api.internationalization.Bundle;
import org.osivia.portal.api.internationalization.IBundleFactory;
import org.osivia.portal.api.internationalization.IInternationalizationService;
import org.osivia.portal.api.locator.Locator;
import org.osivia.portal.api.menubar.MenubarModule;
import org.osivia.portal.api.theming.TemplateAdapter;

import fr.toutatice.portail.cms.nuxeo.api.domain.AbstractPluginPortlet;
import fr.toutatice.portail.cms.nuxeo.api.domain.FragmentType;
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

    private static final String LAUNCH_PROCEDURE_LIST_SCHEMAS = "dublincore, common, toutatice, procedureInstance";


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
    public int getOrder() {
        return Integer.MAX_VALUE;
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
    protected void customizeCMSProperties(CustomizationContext customizationContext) {
        // List templates
        this.customizeListTemplates(customizationContext);
        // Menu templates
        this.customizeMenuTemplates(customizationContext);
        // Template adapters
        this.customizeTemplateAdapters(customizationContext);
        // Menubar modules
        this.customizeMenubarModules(customizationContext);
        // Fragments
        this.customizeFragments(customizationContext);
    }


    /**
     * Customize list templates.
     *
     * @param customizationContext customization context
     */
    private void customizeListTemplates(CustomizationContext customizationContext) {
        // Internationalization bundle
        Bundle bundle = this.bundleFactory.getBundle(customizationContext.getLocale());

        // List templates
        Map<String, ListTemplate> templates = this.getListTemplates(customizationContext);

        // Search results
        ListTemplate searchResults = new ListTemplate("search-results", bundle.getString("LIST_TEMPLATE_SEARCH_RESULTS"), SEARCH_RESULTS_SCHEMAS);
        templates.put(searchResults.getKey(), searchResults);

        // Workspace member requests
        ListTemplate workspaceMemberRequests = templates.get("workspace-member-requests");

        if (workspaceMemberRequests != null) {
            // Workspace member requests tiles
            ListTemplate workspaceMemberRequestsTiles = new ListTemplate("workspace-member-requests-tiles",
                    bundle.getString("LIST_TEMPLATE_WORKSPACE_MEMBER_REQUESTS_TILES"), workspaceMemberRequests.getSchemas());
            workspaceMemberRequestsTiles.setModule(workspaceMemberRequests.getModule());
            templates.put(workspaceMemberRequestsTiles.getKey(), workspaceMemberRequestsTiles);
        }

        // Workspace tiles
        ListTemplate workspaceTiles = new ListTemplate("workspace-tiles", bundle.getString("LIST_TEMPLATE_WORKSPACE_TILES"), "dublincore, common, toutatice");
        templates.put(workspaceTiles.getKey(), workspaceTiles);


        // accessory list
        ListTemplate accessoryList = new ListTemplate("accessories", bundle.getString("LIST_TEMPLATE_ACCESSORIES"), "dublincore, common, toutatice, record");
        accessoryList.setModule(new AccessoriesListTemplateModule(getPortletContext()));
        templates.put(accessoryList.getKey(), accessoryList);
    }


    /**
     * Customize menu templates.
     *
     * @param customizationContext customization context
     */
    private void customizeMenuTemplates(CustomizationContext customizationContext) {
        // Internationalization bundle
        Bundle bundle = this.bundleFactory.getBundle(customizationContext.getLocale());

        // Menu templates
        SortedMap<String, String> templates = this.getMenuTemplates(customizationContext);

        // Extranet
        templates.put("extranet", bundle.getString("MENU_TEMPLATE_EXTRANET"));
    }


    /**
     * Customize template adapters.
     *
     * @param customizationContext customization context
     */
    private void customizeTemplateAdapters(CustomizationContext customizationContext) {
        // Template adapters
        List<TemplateAdapter> adapters = this.getTemplateAdapters(customizationContext);

        // Demo adapter
        TemplateAdapter demo = new DemoTemplateAdapter();
        adapters.add(demo);
    }


    /**
     * Customize menubar modules.
     *
     * @param customizationContext customization context
     */
    private void customizeMenubarModules(CustomizationContext customizationContext) {
        // Menubar modules
        List<MenubarModule> modules = this.getMenubarModules(customizationContext);

        // Customized menubar module
        MenubarModule module = new DemoMenubarModule();
        modules.add(module);
    }


    /**
     * Customize fragments
     *
     * @param customizationContext
     */
    private void customizeFragments(CustomizationContext customizationContext) {
        List<FragmentType> fragmentTypes = getFragmentTypes(customizationContext);

        Bundle bundle = this.bundleFactory.getBundle(customizationContext.getLocale());

        ProduitRecordFragmentModule RecordPropertyModule = new ProduitRecordFragmentModule(getPortletContext());
        FragmentType recordPropertyFragment = new FragmentType(ProduitRecordFragmentModule.ID, bundle.getString("FRAGMENT_PRODUCT_RECORD"),
                RecordPropertyModule);
        fragmentTypes.add(recordPropertyFragment);


        // Launch procedure
        LaunchSupportPortletModule launchSupportPortletModule = new LaunchSupportPortletModule(getPortletContext());
        FragmentType launchSupportFragment = new FragmentType(LaunchSupportPortletModule.ID, bundle.getString("FRAGMENT_LAUNCH_SUPPORT"),
                launchSupportPortletModule);
        fragmentTypes.add(launchSupportFragment);
    }
}
