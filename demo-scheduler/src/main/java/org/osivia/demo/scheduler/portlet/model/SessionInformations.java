package org.osivia.demo.scheduler.portlet.model;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SessionInformations {

	private String selectedContributor;
	
	private Calendar calendar;
	
	private List<String> customerUsers;
	
	private List<Technician> technicians;

	public String getSelectedContributor() {
		return selectedContributor;
	}

	public void setSelectedContributor(String selectedContributor) {
		this.selectedContributor = selectedContributor;
	}

	public Calendar getCalendar() {
		return calendar;
	}

	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}

	public List<String> getCustomerUsers() {
		return customerUsers;
	}

	public void setCustomerUsers(List<String> customerUsers) {
		this.customerUsers = customerUsers;
	}

	public List<Technician> getTechnicians() {
		return technicians;
	}

	public void setTechnicians(List<Technician> technicians) {
		this.technicians = technicians;
	}
	
}
