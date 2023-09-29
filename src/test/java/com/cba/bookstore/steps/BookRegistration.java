package com.cba.bookstore.steps;

import com.cba.bookstore.dto.BookResponse;
import com.cba.bookstore.utils.FeatureUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookRegistration {
    @Autowired
    private TestRestTemplate restTemplate;
    private ResponseEntity<BookResponse> response;
    private BookResponse mockedBook;

    @Given("the user provides valid Book details")
    public void theUserProvidesValidBookDetails() {
        mockedBook = FeatureUtils.getMockBookResponse();
    }

    @When("the user sends a POST request to {string}")
    public void theUserSendsAPOSTRequestTo(String path) {
        response = this.restTemplate.postForEntity(FeatureUtils.URL + path, mockedBook, BookResponse.class);
    }

    @Then("the response status code of book registration should be {int}")
    public void theResponseStatusCodeOfBookRegistrationShouldBe(int statusCode) {
        assertEquals(HttpStatus.valueOf(statusCode), response.getStatusCode());
    }

    @And("the response body should contain the title {string}")
    public void theResponseBodyShouldContainTheTitle(String title) {
        assertEquals(title, response.getBody().getTitle());
    }

}
