package com.example.londonevents;

import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Starts the application up and 
 * listen for a connection like it would do in production, and 
 * then send an HTTP request and 
 * assert the response.
 * 
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LondonEventsAppIntegrationTests {

	@LocalServerPort
	private int port;
	
	private URL baseUrl;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Before
	public void setUp() throws Exception {
		this.baseUrl = new URL("http://localhost:" + port + "/");
	}
	
	@Test
	public void testLandingPageResponse() throws Exception {
		ResponseEntity<String> response = restTemplate.getForEntity(baseUrl.toString(), String.class);
		String titleTags = "<title>London Events</title>";
		assertThat(response.getBody(), not(isEmptyString()));
		assertEquals(true, response.getBody().contains(titleTags));
	}
	
	@Test
	public void testDefaultCategoryEventsPageResponse() throws Exception {
		String evetntsUrlStr = baseUrl.toString() + "events";
		ResponseEntity<String> response = restTemplate.getForEntity(evetntsUrlStr, String.class);
		String titleTags = "<title>London science Events</title>";
		assertThat(response.getBody(), not(isEmptyString()));
		assertEquals(true, response.getBody().contains(titleTags));
	}

	@Test
	public void testSelectedCategoryEventsPageResponse() throws Exception {
		String category = "sales";
		String evetntsUrlStr = baseUrl.toString() + "events?category=" + category;
		String titleTags = "<title>London " + category + " Events</title>";
		String eventsHeading = "<h2>List of Events</h2>";
		ResponseEntity<String> response = restTemplate.getForEntity(evetntsUrlStr, String.class);
		assertThat(response.getBody(), not(isEmptyString()));
		assertEquals(true, response.getBody().contains(titleTags));
		assertEquals(true, response.getBody().contains(eventsHeading));
	}
	
}
