package org.osivia.demo.scheduler.portlet.model;

import java.util.Date;

public class Reservation {

	private Date day;
	
	private String timeSlot;
	
	private String object;
	
	private String idClient;
	
	private boolean accepted;
	
    /** id of the creator */
    private String creatorId;
    /** Display name of the creator */
    private String creatorName;
    /** Date of the reservation's creation */
    private Date dateCreationReservation;

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

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creator) {
		this.creatorId = creator;
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

	public Date getDateCreationReservation() {
		return dateCreationReservation;
	}

	public void setDateCreationReservation(Date dateCreationReservation) {
		this.dateCreationReservation = dateCreationReservation;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	
}
