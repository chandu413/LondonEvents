# LondonEvents
Displays Events for London for different categories in a web page pulled from Eventful API. Use query params to retrieve events for a specific category. Retrieves a max of 50 events for a category in a request.

## Getting Started

Download the project to run

### Prerequisites

```
Java 1.8
Maven 3.3
```

### Installing

* Change to the project directory
* mvn package
* Change to target directory
* Run "Java -jar LondonEvents 0.0.1-SNAPSHOT.jar"

or

* mvn spring-boot:run


## Running the tests

Run the tests

```
mvn test
```

### tests

* Controller tests using MockMvc
* Integration tests using Spring TestRestTemplate


## Authors

* **Chandra Dhulipala**


## Notes

### Approach

* Understand the requirements
* Create an account and request an API key from Eventful site
* Created a demo project using Spring boot initializer with Web and test dependencies
* Created some initial unit tests for the LondonEvents Rest API
* Consumed Eventful API
* Created more tests - Mocked service tests and full integration tests


### Features

* Home page with basic info and a link to one of the category of events by default
* Home page with some specific links to different other categories
* Default category link displays London events for sceience category with basic info in a tabular format
* User can provide category as a query parameter to retrieve specific category events
* Events are displayed in teh order of the date and a maximum of 50 per category in a single request
* Provision for the user to go back to Home page from the lsit of evetns page
* Browser displays the user selected category in the webpage tab title
