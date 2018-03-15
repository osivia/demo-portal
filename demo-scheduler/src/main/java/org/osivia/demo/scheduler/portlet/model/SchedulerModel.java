package org.osivia.demo.scheduler.portlet.model;

import org.springframework.stereotype.Component;

@Component
public class SchedulerModel {

	private SessionInformations sessionInformations;
	
	private SchedulerTemp schedulerTemp;

	public SessionInformations getSessionInformations() {
		return sessionInformations;
	}

	public void setSessionInformations(SessionInformations sessionInformations) {
		this.sessionInformations = sessionInformations;
	}

	public SchedulerTemp getSchedulerTemp() {
		return schedulerTemp;
	}

	public void setSchedulerTemp(SchedulerTemp schedulerTemp) {
		this.schedulerTemp = schedulerTemp;
	}
	
	
}
