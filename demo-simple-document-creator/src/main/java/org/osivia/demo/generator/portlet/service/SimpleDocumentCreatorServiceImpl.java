package org.osivia.demo.generator.portlet.service;

import java.beans.PropertyDescriptor;

import javax.portlet.PortletException;

import org.apache.commons.beanutils.PropertyUtils;
import org.osivia.demo.generator.portlet.model.CreationForm;
import org.osivia.demo.generator.portlet.repository.SimpleDocumentCreatorRepository;
import org.osivia.portal.api.context.PortalControllerContext;
import org.osivia.portal.api.internationalization.Bundle;
import org.osivia.portal.api.internationalization.IBundleFactory;
import org.osivia.portal.api.notifications.INotificationsService;
import org.osivia.portal.api.notifications.NotificationsType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * Portlet service implementation.
 * 
 * @author Cédric Krommenhoek
 * @see SimpleDocumentCreatorService
 */
@Service
public class SimpleDocumentCreatorServiceImpl implements SimpleDocumentCreatorService {

    /** Application context. */
    @Autowired
    private ApplicationContext applicationContext;

    /** Portlet repository. */
    @Autowired
    private SimpleDocumentCreatorRepository repository;

    /** Notifications service. */
    @Autowired
    private INotificationsService notificationsService;

    /** Internationalization bundle factory. */
    @Autowired
    private IBundleFactory bundleFactory;


    /**
     * Constructor.
     */
    public SimpleDocumentCreatorServiceImpl() {
        super();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void create(PortalControllerContext portalControllerContext, CreationForm form) throws PortletException {
        // Internationalization bundle
        Bundle bundle = this.bundleFactory.getBundle(portalControllerContext.getRequest().getLocale());

        this.repository.create(portalControllerContext, form);

        // Notification
        this.notificationsService.addSimpleNotification(portalControllerContext, bundle.getString("SIMPLE_DOCUMENT_CREATION_MESSAGE_SUCCESS", form.getTitle()),
                NotificationsType.SUCCESS);

        // Update model
        try {
            for (PropertyDescriptor propertyDescriptor : PropertyUtils.getPropertyDescriptors(form.getClass())) {
                if (propertyDescriptor.getWriteMethod() != null) {
                    PropertyUtils.setProperty(form, propertyDescriptor.getName(), null);
                }
            }
        } catch (ReflectiveOperationException e) {
            throw new PortletException(e);
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public CreationForm getCreationForm(PortalControllerContext portalControllerContext) throws PortletException {
        return this.applicationContext.getBean(CreationForm.class);
    }

}
