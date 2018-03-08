package org.osivia.demo.scheduler.portlet.model;

import java.util.Date;

public class Reservation {

	private Date day;
	
	private String timeSlot;
	
	private String object;
	
	private String idClient;
	
	private boolean accepted;
	
	//TODO Julien: a supprimer une fois que j'aurai alimenté le champ idClient
	private String creator;

	public Reservation() {
		super();
	}

	public Reservation(Date day, String timeSlot) {
		super();
		this.day = day;
		this.timeSlot = timeSlot;
	}

	public Date getDay() {
		return day;
	}

	public void setDay(Date day) {
		this.day = day;
	}

	public String getTimeSlot() {
		return timeSlot;
	}

	public void setTimeSlot(String timeSlot) {
		this.timeSlot = timeSlot;
	}

	public String getIdClient() {
		return idClient;
	}

	public void setIdClient(String idClient) {
		this.idClient = idClient;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public boolean isAccepted() {
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}
	
}
