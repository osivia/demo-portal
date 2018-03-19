package org.osivia.demo.scheduler.portlet.model;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

/**
 * Scheduler form
 * @author Julien Barberet
 *
 */
@Component
public class SchedulerForm {

	private SessionInformations sessionInformations;
	
	private SchedulerEvent[] timeSlots;
	
	
	
	public SchedulerForm() {
		super();
		this.timeSlots = new SchedulerEvent[10];
		
		
	}
	
	public SessionInformations getSessionInformations() {
		return sessionInformations;
	}

	public void setSessionInformations(SessionInformations sessionInformations) {
		this.sessionInformations = sessionInformations;
	}

	public void previousWeek()
	{
		sessionInformations.getCalendar().add(Calendar.WEEK_OF_YEAR, -1);
	}
	
	public void nextWeek()
	{
		sessionInformations.getCalendar().add(Calendar.WEEK_OF_YEAR, 1);
	}

	public String getSelectedContributor() {
		return this.sessionInformations.getSelectedContributor();
	}

	public void setSelectedContributor(String selectedContributor) {
		this.sessionInformations.setSelectedContributor(selectedContributor);
	}
	
	public Date getMonday()
	{
		sessionInformations.getCalendar().set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return sessionInformations.getCalendar().getTime();
	}
	
	public Date getTuesday()
	{
		sessionInformations.getCalendar().set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
		return sessionInformations.getCalendar().getTime();
	}
	
	public Date getWednesday()
	{
		sessionInformations.getCalendar().set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
		return sessionInformations.getCalendar().getTime();
	}
	
	public Date getThursday()
	{
		sessionInformations.getCalendar().set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
		return sessionInformations.getCalendar().getTime();
	}
	
	public Date getFriday()
	{
		sessionInformations.getCalendar().set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		return sessionInformations.getCalendar().getTime();
	}

	public List<Technician> getTechnicians() {
		return sessionInformations.getTechnicians();
	}

	public void setTechnicians(List<Technician> technicians) {
		this.sessionInformations.setTechnicians(technicians);
	}

	public List<String> getCustomerUsers() {
		return this.sessionInformations.getCustomerUsers();
	}

	public void setCustomerUsers(List<String> customerUsers) {
		this.sessionInformations.setCustomerUsers(customerUsers);
	}

	public SchedulerEvent[] getTimeSlots() {
		return timeSlots;
	}

	public void setTimeSlots(SchedulerEvent[] timeSlots) {
			this.timeSlots = timeSlots;
	}

	public boolean isShowPreviousButton() {
		Calendar currentCal = Calendar.getInstance();
		boolean showPreviousButton = currentCal.get(Calendar.WEEK_OF_YEAR) < sessionInformations.getCalendar().get(Calendar.WEEK_OF_YEAR);
		return showPreviousButton;
	}
}
