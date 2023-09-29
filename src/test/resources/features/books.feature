Feature: Book Management

  Scenario: Add a new book
    Given the user provides valid Book details
    When the user sends a POST request to "/api/books"
    Then the response status code of book registration should be 201
    And the response body should contain the title "Head First Java"

  Scenario: Get an existing book
    Given an existing book with ID 1
    When the user sends a GET request to "/api/books"
    Then the response status code of getting a book should be 200
    And the response body should contain the book details
