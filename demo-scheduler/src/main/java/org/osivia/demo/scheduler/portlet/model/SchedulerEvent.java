package org.osivia.demo.scheduler.portlet.model;

import java.util.Date;

public class SchedulerEvent {

	private boolean reservation;
	
	/** if true accepted state, else if waiting state */
	private boolean accepted;
	
	private String object;
	
	private String client;
	
	private String creator;
	
	private Date dateCreationReservation;

	public SchedulerEvent(boolean reservation) {
		super();
		this.reservation = reservation;
	}

	public boolean isReservation() {
		return reservation;
	}

	public void setReservation(boolean reservation) {
		this.reservation = reservation;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public boolean isAccepted() {
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getDateCreationReservation() {
		return dateCreationReservation;
	}

	public void setDateCreationReservation(Date dateCreationReservation) {
		this.dateCreationReservation = dateCreationReservation;
	}
	
}
