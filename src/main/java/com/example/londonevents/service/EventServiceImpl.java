package com.example.londonevents.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.londonevents.data.Event;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

@Service("eventService")
public class EventServiceImpl implements EventService {
	
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(EventServiceImpl.class);
	
	private String categori = "";
	
	private String eventUrl = "http://api.eventful.com/json/events/search?app_key=qqXCtrkGhjSK5JjS" 
			+ "&location=London"
			+ "&categories=" ;

	ObjectMapper mapper = new ObjectMapper();

	@Override
	public List<Event> getEvents() {
		
		List<Event> eventsList = new ArrayList<>();
		
		RestTemplate restTemplate = new RestTemplate();
		
		// Response from the API has got text/javascript content type. Add it to supported types
		addServerResponseContentType(restTemplate);
		
		// Have to create a complete POJO for the whole response if this method is used
//		log.info(restTemplate.getForObject(eventUrl, Event.class));
		
		// Getting response JSON string
		ResponseEntity<String> resp = restTemplate.getForEntity(getEventUrl(), String.class);
		
		// Parse response JSON string and get the relevant Event nodes in a list
		try {
			JsonNode root = mapper.readTree(resp.getBody());
			JsonNode eventsNode = root.path("events");
			
			ArrayNode eventArrayNode = (ArrayNode) eventsNode.get("event");
			Iterator<JsonNode> eventsIter = eventArrayNode.elements();
			
			while(eventsIter.hasNext()) {
				JsonNode eventNode = eventsIter.next();
				// Map it to our simple reduced POJO of Event
				Event event = mapper.treeToValue(eventNode, Event.class);
				// Add to the list to be returned
				eventsList.add(event);
			}
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return eventsList;
	}

	private void addServerResponseContentType(RestTemplate restTemplate) {
		
		 for (HttpMessageConverter<?> msgConverter : restTemplate.getMessageConverters ()) {
		     if (msgConverter instanceof MappingJackson2HttpMessageConverter) {
		        List<MediaType> mediaTypes = new ArrayList<MediaType> ();
		        mediaTypes.addAll (msgConverter.getSupportedMediaTypes ());
		        mediaTypes.add (MediaType.parseMediaType ("text/javascript; charset=utf-8"));
		        ((MappingJackson2HttpMessageConverter) msgConverter).setSupportedMediaTypes (mediaTypes);
		     }
		  }		
	}

	@Override
	public void setCategory(String category) {
		this.categori = category;
	}
	

	private String getCategory() {
		return categori;
	}
	
	public String getEventUrl() {
		return eventUrl + getCategory() 
				+ "&date=Future"
				+ "&sort_order=date"
//				+ "&sort_direction=descending"
				+ "&page_size=50"
				;
	}

}
