package org.osivia.demo.customizer.plugin;

import org.osivia.portal.api.theming.TemplateAdapter;

/**
 * Demo template adapter.
 *
 * @author Cédric Krommenhoek
 * @see TemplateAdapter
 */
public class DemoTemplateAdapter implements TemplateAdapter {

    /**
     * Constructor.
     */
    public DemoTemplateAdapter() {
        super();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String adapt(String spaceTemplate, String targetTemplate) {
        // Adapted template
        String template = null;

        if ("/default/templates/workspace-taskbar-large".equals(spaceTemplate)) {
            // Statistics
            if ("/default/templates/statistics".equals(targetTemplate)) {
                template = "/default/templates/statistics-taskbar-large";
            }
        }

        return template;
    }

}
