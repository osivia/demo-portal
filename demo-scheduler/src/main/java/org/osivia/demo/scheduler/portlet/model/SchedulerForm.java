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
	
	private List<Contributor> contributorsList;
	
	private Calendar calendar;
	

	
	private boolean busyMondayMorning;
	
	private boolean busyMondayAfternoon;

	private boolean busyTuesdayMorning;
	
	private boolean busyTuesdayAfternoon;
	
	private boolean busyWednesdayMorning;
	
	private boolean busyWednesdayAfternoon;
	
	private boolean busyThursdayMorning;
	
	private boolean busyThursdayAfternoon;
	
	private boolean busyFridayMorning;
	
	private boolean busyFridayAfternoon;

	public SchedulerForm() {
		super();
		calendar = Calendar.getInstance();
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

	public boolean isBusyMondayMorning() {
		return busyMondayMorning;
	}

	public void setBusyMondayMorning(boolean busyMondayMorning) {
		this.busyMondayMorning = busyMondayMorning;
	}

	public boolean isBusyMondayAfternoon() {
		return busyMondayAfternoon;
	}

	public void setBusyMondayAfternoon(boolean busyMondayAfternoon) {
		this.busyMondayAfternoon = busyMondayAfternoon;
	}

	public boolean isBusyTuesdayMorning() {
		return busyTuesdayMorning;
	}

	public void setBusyTuesdayMorning(boolean busyTuesdayMorning) {
		this.busyTuesdayMorning = busyTuesdayMorning;
	}

	public boolean isBusyTuesdayAfternoon() {
		return busyTuesdayAfternoon;
	}

	public void setBusyTuesdayAfternoon(boolean busyTuesdayAfternoon) {
		this.busyTuesdayAfternoon = busyTuesdayAfternoon;
	}

	public boolean isBusyWednesdayMorning() {
		return busyWednesdayMorning;
	}

	public void setBusyWednesdayMorning(boolean busyWednesdayMorning) {
		this.busyWednesdayMorning = busyWednesdayMorning;
	}

	public boolean isBusyWednesdayAfternoon() {
		return busyWednesdayAfternoon;
	}

	public void setBusyWednesdayAfternoon(boolean busyWednesdayAfternoon) {
		this.busyWednesdayAfternoon = busyWednesdayAfternoon;
	}

	public boolean isBusyThursdayMorning() {
		return busyThursdayMorning;
	}

	public void setBusyThursdayMorning(boolean busyThursdayMorning) {
		this.busyThursdayMorning = busyThursdayMorning;
	}

	public boolean isBusyThursdayAfternoon() {
		return busyThursdayAfternoon;
	}

	public void setBusyThursdayAfternoon(boolean busyThursdayAfternoon) {
		this.busyThursdayAfternoon = busyThursdayAfternoon;
	}

	public boolean isBusyFridayMorning() {
		return busyFridayMorning;
	}

	public void setBusyFridayMorning(boolean busyFridayMorning) {
		this.busyFridayMorning = busyFridayMorning;
	}

	public boolean isBusyFridayAfternoon() {
		return busyFridayAfternoon;
	}

	public void setBusyFridayAfternoon(boolean busyFridayAfternoon) {
		this.busyFridayAfternoon = busyFridayAfternoon;
	}

	public List<Contributor> getContributorsList() {
		return contributorsList;
	}

	public void setContributorsList(List<Contributor> contributorsList) {
		this.contributorsList = contributorsList;
	}

}
