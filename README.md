# SmartPark
"SmartPark" is a technology company developing an intelligent parking management system for
urban areas. The company is aiming to optimize the use of parking spaces and facilitate easy
navigation for drivers.

## Requirements
Runs in Java 17, Springboot version 3.5.3

## Start/Build Project
1. Start the application by executing this command `mvn spring-boot:run`
2. Run `mvn test` to execute unit testing

## Endpoint
1. Get authentication token`localhost:8080/auth/token`
2. Get all registered vehicle `localhost:8080/vehicle/all`
3. Get all registered parking lot `localhost:8080/parkinglot/all`
4. Get all park cars in a parking lot by id `localhost:8080/parkinglot/details?id=PXGzPuzw7BF10mRWP5qUhRIvzc3ko1uZB6FFzVC1HSRE0LNJn8`
5. Get parking lot details and slot availability `localhost:8080/parkinglot/availability?id=PXGzPuzw7BF10mRWP5qUhRIvzc3ko1uZB6FFzVC1HSRE0LNJn8`
6. Register a Vehicle `localhost:8080/vehicle/register`
7. Register a Parking lot `localhost:8080/parkinglot/register`
8. Check in vehicle to a parking lot `localhost:8080/parkingrecord/checkin`
9. Check out vehicle to a parking lot `localhost:8080/parkingrecord/checkout`

## Scheduler Settings
Scheduler is running every 1 min to check vehicles that parked for 15 minutes and up

## Testing Notes
make sure to get auth token and update all value of Authentication Headers of each endpoint