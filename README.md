# Virgin Atlantic ~ Flight Information Display

Here is an example of a flight times application for Virgin Atlantic. This project represents what we're looking for in
a candidate and our current technology choices.

Please spend up to 2 hours improving this application. What you improve is up to you: Perhaps it needs more tests,
additional functionality, or some bug fixing.

## Rules

1. The code must be your own work. If you have a strong case to use a small code snippet of someone else's work, e.g. a
   boilerplate function, it must be clearly commented and attributed to the original author.
2. The flight data cannot be changed, and must be loaded from the CSV file, so it can easily be replaced with another file.
3. You must include any unit tests you think are appropriate.
4. You are not allowed to add any additional dependencies to the project - make use of what's been provided.
5. Identify intentional gaps in the code by looking for `//FIXME - applicant to complete` and provide either solutions or improvements.

## What it should do

The application should allow the user to select or input any date, of any year, resulting in the display of flights on
that day, displayed in chronological order -- a Flight Information Display.

## Supplying your code

Please **create and commit your code into a public Github repository** and supply the link to the recruiter for review. Your code should compile and run in one step.

## Supporting Data

The [flight data](./src/main/resources/flights.csv) is a simple comma-separated file containing the following:

| Departure Time | Destination | Destination Airport IATA | Flight No | Sun | Mon | Tue | Wed | Thu | Fri | Sat |
| :------------- | :---------- | :----------------------- | :-------- | :-: | :-: | :-: | :-: | :-: | :-: | :-: |
| 09:00          | Antigua     | ANU                      | VS033     |     |     | `x` |     |     |     |
| 10:00          | Antigua     | ANU                      | VS033     |     |     |     |     | `x` |     | `x` |
| 11:05          | Barbados    | BGI                      | VS029     | `x` | `x` | `x` | `x` | `x` | `x` | `x` |
| 12:20          | Cancun      | CUN                      | VS093     |     |     | `x` |     |     |     |
| 09:00          | Grenada     | GND                      | VS089     |     | `x` |     |     |     |     |
| 10:10          | Grenada     | GND                      | VS089     |     |     |     |     |     | `x` |
| 10:15          | Havana      | HAV                      | VS063     |     |     | `x` |     |     |     |
| 10:15          | Havana      | HAV                      | VS063     |     | `x` |     |     | `x` |     |
| 10:15          | Las Vegas   | LAS                      | VS043     | `x` |     |     |     |     | `x` | `x` |
| 10:25          | Las Vegas   | LAS                      | VS043     |     |     |     |     | `x` |     |
| 10:35          | Las Vegas   | LAS                      | VS043     |     | `x` | `x` | `x` |     |     |
| 15:35          | Las Vegas   | LAS                      | VS044     | `x` | `x` | `x` | `x` | `x` | `x` | `x` |
| 12:25          | Montego Bay | MBJ                      | VS065     |     |     |     | `x` |     |     |
| 12:40          | Montego Bay | MBJ                      | VS065     | `x` |     |     |     |     |     |
| 10:10          | Orlando     | MCO                      | VS049     | `x` |     |     |     |     |     |
| 10:15          | Orlando     | MCO                      | VS027     |     |     |     | `x` |     |     |
| 11:00          | Orlando     | MCO                      | VS027     |     | `x` |     |     |     |     |
| 11:10          | Orlando     | MCO                      | VS049     |     | `x` |     |     |     |     |
| 11:20          | Orlando     | MCO                      | VS027     |     |     |     |     |     | `x` | `x` |
| 11:35          | Orlando     | MCO                      | VS027     |     |     |     |     | `x` |     |
| 11:45          | Orlando     | MCO                      | VS027     | `x` |     | `x` |     |     |     |
| 11:45          | Orlando     | MCO                      | VS049     |     |     |     | `x` |     |     |
| 13:00          | Orlando     | MCO                      | VS015     | `x` | `x` | `x` | `x` | `x` | `x` | `x` |
| 09:00          | St Lucia    | UVF                      | VS089     |     | `x` |     |     |     |     |
| 09:00          | St Lucia    | UVF                      | VS097     | `x` |     |     |     |     |     |
| 10:10          | St Lucia    | UVF                      | VS089     |     |     |     |     |     | `x` |
| 09:00          | Tobago      | TAB                      | VS097     | `x` |     |     |     |     |     |

The `x` denotes days that the flight operates.

## Running the Application and Tests

### Run the Application

Make sure the desired port is free before running the app. The default port is **8080**.

To run the application on the default port (8080):

```bash
mvn spring-boot:run
```

To run the application on a custom port without modifying any files, pass the port as a command-line argument:

```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=YOUR_DESIRED_PORT"
```

For example, to run on port 9090:

```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=9090"
```

### Run Tests

To run all tests:

```bash
mvn test
```

### Troubleshooting

To check if a port is in use:

1. Linux/macOS: lsof -i :PORT_NUMBER

2. Windows: netstat -ano | findstr :PORT_NUMBER

Ensure Java 16 is installed.
