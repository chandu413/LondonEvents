package com.example.londonevents;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.londonevents.data.Event;
import com.example.londonevents.service.EventService;


/**
 * Not start the server at all, but test only the layer below that, 
 * where Spring handles the incoming HTTP request and hands it off to your controller. 
 * That way, almost the full stack is used, and 
 * your code will be called exactly the same way as if it was processing 
 * a real HTTP request, but without the cost of starting the server.  
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LondonEventsAppControllerTests {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private EventService eventServiceMock;

	private int wantedNumberOfInvocations = 1;

	/*
	 * full Spring application context is started, but without the server
	 */
	@Test
	public void shuoldDisplayHomePage() throws Exception {
		
		MvcResult result = mockMvc.perform(get("/")
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(new MediaType("text", "html", Charset.forName("UTF8"))))
				.andReturn();
		assertThat(result.getResponse().getContentAsString(), containsString("Get the list of Events"));
		
		verifyZeroInteractions(eventServiceMock);
		verify(eventServiceMock, never()).getEvents();
	}
	
	@Test
	public void shouldReturn2FakeEventsFromMockService() throws Exception {
		
		when(eventServiceMock.getEvents()).thenReturn(get2TestEvents());
		
		mockMvc.perform(get("/events")
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(new MediaType("text", "html", Charset.forName("UTF8"))))
				.andExpect(view().name("eventlist"))
				.andExpect(model().attribute("events", hasSize(2)))
				.andExpect(model().attribute("events", hasItem(
						allOf(
								hasProperty("title", is("title1")),
								hasProperty("venue_name", is("venueName1")),
								hasProperty("venue_address", is("venuwAddress1")),
								hasProperty("start_time", is("startTime1"))
						)
				)))
				.andExpect(model().attribute("events", hasItem(
						allOf(
								hasProperty("title", is("title2")),
								hasProperty("venue_name", is("venueName2")),
								hasProperty("venue_address", is("venuwAddress2")),
								hasProperty("start_time", is("startTime2"))
						)
				)));
		
		verify(eventServiceMock, times(wantedNumberOfInvocations)).setCategory("science");
		verify(eventServiceMock, times(wantedNumberOfInvocations)).getEvents();
		verifyNoMoreInteractions(eventServiceMock);
	}
	
	private List<Event> get2TestEvents() {
		
		List<Event> l = new ArrayList<>();
		l.add(new Event("title1", "venueName1", "venuwAddress1", "startTime1"));
		l.add(new Event("title2", "venueName2", "venuwAddress2", "startTime2"));
		return l;
	}
	
	@Test
	public void shouldReturnZeroEventsWithoutMockServiceResponse() throws Exception {
		mockMvc.perform(get("/events")
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(new MediaType("text", "html", Charset.forName("UTF8"))))
				.andExpect(view().name("eventlist"))
				.andExpect(model().attribute("events", hasSize(0)));
	}
}
