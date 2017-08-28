package com.example.londonevents;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.londonevents.service.EventService;

@Controller
public class LondonEventsAppController {
	
	@Autowired
	private EventService eventService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String atRoot() {
		return "home";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/events")
	public String getEvents(@RequestParam(value="category", required=false, defaultValue="science") String category, Model model) {
		eventService.setCategory(category);
		model.addAttribute("cat", category);
		model.addAttribute("events", eventService.getEvents());
		return "eventlist";
	}
}
