package org.osivia.demo.scheduler.portlet.model;

import org.osivia.portal.api.directory.v2.model.Person;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Contributor bean
 * @author Julien Barberet
 *
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CustomerUser implements Comparable<CustomerUser>{

    /** Identifier. */
    private String id;
    
    private String text;
    
    private String givenName;
    
    private String avatarUrl;
    
    private String extra;
    
    public CustomerUser() {
	}
    
    public CustomerUser(Person person) {
        super();
        this.id = person.getUid();
        this.text = StringUtils.isEmpty(person.getDisplayName())? person.getUid() : person.getDisplayName();
        this.givenName = person.getGivenName();
        if (person.getAvatar() != null) this.avatarUrl = person.getAvatar().getUrl();
    }
    
    /**
     * Constructor used with select2 when contributor is selected
     * 
     * @param id person ID
     */
    public CustomerUser(String id) {
        super();
        this.id = id;
    }

    
    
    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(CustomerUser arg0) {
        if (this.givenName == null)
        {
            if (arg0 == null || arg0.givenName == null)
            {
                return 0;
            } else
            {
                return -1;
            }
        } else
        {
            if (arg0 == null || arg0.givenName == null)
            {
                return 1;
            } else
            {
                return this.getGivenName().compareTo(arg0.getGivenName());
            }
        }
    }

    /**
     * Getter for displayName.
     * @return the displayName
     */
    public String getText() {
        return text;
    }

    
    /**
     * Setter for displayName.
     * @param displayName the displayName to set
     */
    public void setText(String displayName) {
        this.text = displayName;
    }

    
    
    /**
     * Getter for avatarUrl.
     * @return the avatarUrl
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }


    
    /**
     * Setter for avatarUrl.
     * @param avatarUrl the avatarUrl to set
     */
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }


    /**
     * Getter for extra.
     * @return the extra
     */
    public String getExtra() {
        return extra;
    }


    
    /**
     * Setter for extra.
     * @param extra the extra to set
     */
    public void setExtra(String extra) {
        this.extra = extra;
    }

    
    /**
     * Getter for id.
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Getter for givenName.
     * @return the givenName
     */
    public String getGivenName() {
        return givenName;
    }

    
    /**
     * Setter for givenName.
     * @param givenName the givenName to set
     */
    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }
}
