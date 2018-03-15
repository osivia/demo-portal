package org.osivia.demo.scheduler.portlet.model;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Scheduler form
 * @author Julien Barberet
 *
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SchedulerForm {

	private String selectedContributor;
	
	private List<Technician> technicians;
	
	private Calendar calendar;
	
	private SchedulerEvent[] timeSlots;
	
	private List<String> customerUsers;

	public SchedulerForm() {
		super();
		calendar = Calendar.getInstance();
		timeSlots = new SchedulerEvent[10];
	}
	
	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}

	public void previousWeek()
	{
		calendar.add(Calendar.WEEK_OF_YEAR, -1);
	}
	
	public void nextWeek()
	{
		calendar.add(Calendar.WEEK_OF_YEAR, 1);
	}

	public String getSelectedContributor() {
		return selectedContributor;
	}

	public void setSelectedContributor(String selectedContributor) {
		this.selectedContributor = selectedContributor;
	}
	
	public Date getMonday()
	{
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return calendar.getTime();
	}
	
	public Date getTuesday()
	{
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
		return calendar.getTime();
	}
	
	public Date getWednesday()
	{
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
		return calendar.getTime();
	}
	
	public Date getThursday()
	{
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
		return calendar.getTime();
	}
	
	public Date getFriday()
	{
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		return calendar.getTime();
	}

	public List<Technician> getTechnicians() {
		return technicians;
	}

	public void setTechnicians(List<Technician> technicians) {
		this.technicians = technicians;
	}

	public List<String> getCustomerUsers() {
		return customerUsers;
	}

	public void setCustomerUsers(List<String> customerUsers) {
		this.customerUsers = customerUsers;
	}

	public SchedulerEvent[] getTimeSlots() {
		return timeSlots;
	}

	public void setTimeSlots(SchedulerEvent[] timeSlots) {
		this.timeSlots = timeSlots;
	}
	
	public boolean isShowPreviousButton() {
		Calendar currentCal = Calendar.getInstance();
		boolean showPreviousButton = currentCal.get(Calendar.WEEK_OF_YEAR) < calendar.get(Calendar.WEEK_OF_YEAR);
		return showPreviousButton;
	}
}
