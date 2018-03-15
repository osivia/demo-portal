package org.osivia.demo.scheduler.calendar.view.portlet.controller;

import org.osivia.services.calendar.view.portlet.controller.ViewCalendarController;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

/**
 * 
 * @author Julien Barberet
 *
 */
@Controller
@RequestMapping(value = "VIEW")
@Primary
public class ViewCalendarCustomController extends ViewCalendarController{
	
}
