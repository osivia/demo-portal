package org.osivia.demo.customizer.plugin.menubar;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.osivia.portal.api.PortalException;
import org.osivia.portal.api.cms.DocumentContext;
import org.osivia.portal.api.context.PortalControllerContext;
import org.osivia.portal.api.menubar.MenubarContainer;
import org.osivia.portal.api.menubar.MenubarDropdown;
import org.osivia.portal.api.menubar.MenubarItem;
import org.osivia.portal.api.menubar.MenubarModule;

public class DemoMenubarModule implements MenubarModule {

    /**
     * Constructor.
     */
    public DemoMenubarModule() {
        super();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void customizeSpace(PortalControllerContext portalControllerContext, List<MenubarItem> menubar, DocumentContext spaceDocumentContext)
            throws PortalException {
        // Base path
        String basePath;
        if (spaceDocumentContext == null) {
            basePath = null;
        } else {
            basePath = spaceDocumentContext.getPath();
        }
        
        
        if (StringUtils.startsWith(basePath, "/default-domain/UserWorkspaces/")) {
            // User workspace
            Iterator<MenubarItem> iterator = menubar.iterator();
            while (iterator.hasNext()) {
                MenubarItem item = iterator.next();
                MenubarContainer parent = item.getParent();
                
                if ((parent != null) && (parent instanceof MenubarDropdown)) {
                    MenubarDropdown dropdown = (MenubarDropdown) parent;
                    if (StringUtils.equals("CONFIGURATION", dropdown.getId())) {
                        iterator.remove();
                    }
                }
            }
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void customizeDocument(PortalControllerContext portalControllerContext, List<MenubarItem> menubar, DocumentContext documentContext)
            throws PortalException {
        // Path
        String path;
        if (documentContext == null) {
            path = null;
        } else {
            path = documentContext.getPath();
        }

        if (StringUtils.startsWith(path, "/default-domain/UserWorkspaces/")) {
            // User workspace
            List<String> identifiers = Arrays.asList(new String[]{"WORKSPACE_ACL_MANAGEMENT", "SUBSCRIBE_URL"});
            Iterator<MenubarItem> iterator = menubar.iterator();
            while (iterator.hasNext()) {
                MenubarItem item = iterator.next();
                MenubarContainer parent = item.getParent();

                if (identifiers.contains(item.getId())) {
                    iterator.remove();
                } else if ((parent != null) && (parent instanceof MenubarDropdown)) {
                    MenubarDropdown dropdown = (MenubarDropdown) parent;
                    if (StringUtils.equals("SHARE", dropdown.getId())) {
                        iterator.remove();
                    }
                }
            }
        }
    }

}
