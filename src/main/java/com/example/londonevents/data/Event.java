package com.example.londonevents.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Event {

	private String id;
	private String title;
	private String venue_name;
	private String venue_address;
	private String start_time;
	
	public Event() {
		id = "";
		title = "";
		venue_name = "";
		venue_address = "";
		start_time = "";
	}
	
	public Event(String t, String vn, String va, String st) {
		this.title = t;
		this.venue_name = vn;
		this.venue_address = va;
		this.start_time = st;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getVenue_name() {
		return venue_name;
	}
	public void setVenue_name(String venue_name) {
		this.venue_name = venue_name;
	}
	public String getVenue_address() {
		return venue_address;
	}
	public void setVenue_address(String venue_address) {
		this.venue_address = venue_address;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	@Override
	public String toString() {
		return "Event[title=" + title 
				+ ", venue_name=" + venue_name
				+ ", venue_address=" + venue_address
				+ "start_time=" + start_time
				+ "]";
	}
}
