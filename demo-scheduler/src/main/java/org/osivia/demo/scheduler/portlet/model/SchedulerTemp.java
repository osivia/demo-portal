package org.osivia.demo.scheduler.portlet.model;

import org.osivia.portal.api.portlet.Refreshable;
import org.springframework.stereotype.Component;

@Component
@Refreshable
public class SchedulerTemp {

	private SchedulerEvent[] timeSlots;

	public SchedulerTemp() {
		super();
		this.timeSlots = new SchedulerEvent[10];
	}

	public SchedulerEvent[] getTimeSlots() {
		return timeSlots;
	}

	public void setTimeSlots(SchedulerEvent[] timeSlots) {
		this.timeSlots = timeSlots;
	}
}
