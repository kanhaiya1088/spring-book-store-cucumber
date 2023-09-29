# Book Store REST API CRUD Tests Using Cucumber BDD Intergration

This repository contains a collection of Cucumber tests for a simple REST API CRUD application. The tests are designed to validate the functionality of the REST API by covering the Create, Read, Update, and Delete operations.

## Problem Statement
1.	Build a microservice with CRUD function for a simple library. (add book, retrieve book, update book, delete book)
2.	Expose these CRUD function as REST services.
3.	Implement BDD for integration tests.
4.	Implement a database trigger that will add record to book_audit table whenever there are changes to main table (i.e. book table).

## Overview

The purpose of this project is to demonstrate automated testing using Cucumber for a RESTful API. The tests are written in Gherkin syntax, which provides a human-readable format for describing the desired behavior of the API. By utilizing Cucumber, the tests can be easily understood and maintained by both technical and non-technical team members.

## Features

- End-to-end tests for the REST API CRUD operations.
- Test scenarios covering various scenarios and edge cases.
- Integration with popular Java testing frameworks, such as JUnit and Spring Boot Test.
- Integration with TestRestTemplate for making HTTP requests and asserting responses.
- OpenAPI Specification 3.0 version also added for exposing Book Store Management Rest EndPoints

## Getting Started

To get started with running the tests, follow these steps:

1. Clone the repository: `git clone https://github.com/kanhaiya1088/spring-book-store-cucumber.git`
2. Install the necessary dependencies: `mvn install`
3. Configure the REST API endpoint in the test configuration file.
4. Run the Cucumber tests: `mvn test`
5. Once application up and running you can the URL: http://localhost:9090/swagger-ui/index.html

## Contact

For any inquiries or feedback, please contact [kanhaiya1088@gmail.com](mailto:kanhaiya1088@gmail.com).