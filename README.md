## Conference Track Management

### Prerequisites for Executing the Program
* Java 1.8 or above

### Design

#### ConferenceService

* The `ConferenceService` class provides a method called `schedule()` as an API to create a
  `Conference` object representing a scheduled conference with tracks for the provided input
* Example use of the API:
```java
BufferedReader reader = new BufferedReader(new FileReader("path\input_file"));
ConferenceService conferenceService = new ConferenceService();
Response<Conference> conference = conferenceService.scheduler(reader);
if (EnumResponse.ERROR.equals(conference.getResultado())) {
	LOG.error("Errors found:");
	LOG.error("{}", conference.getMensajes());
} else {
	LOG.info("{}", conference);
}
```

#### Conference

* A `Conference` object currently only returns the schedule as a string via its `toString()`
  method. This also can be easily extended to return the schedule as a list of `Track`s

#### Track

* An object representation of a single track of a conference. Each `Track` object contains one
  or more `Slot`s

#### Slot

* A `Slot` represents a group of events in a `Track`.

#### Event

* An event of the conference


