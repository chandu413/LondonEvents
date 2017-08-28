package com.example.londonevents.service;

import java.util.List;

import com.example.londonevents.data.Event;

public interface EventService {

	List<Event> getEvents();

	void setCategory(String category);
}
