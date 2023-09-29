package com.cba.bookstore.steps;

import com.cba.bookstore.utils.FeatureUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationVersion {
    @Autowired
    private TestRestTemplate restTemplate;
    private ResponseEntity<String> response;

    @When("I request the endpoint {string}")
    public void iRequestTheEndpoint(String path) {
        response  = this.restTemplate.getForEntity(FeatureUtils.URL + path, String.class);
    }

    @Then("the response should be {string}")
    public void theResponseShouldBe(String version) {
        assertEquals(version, response.getBody());
    }

    @And("the status code is {int}")
    public void theStatusCodeIs(int statusCode) {
        assertEquals(HttpStatus.valueOf(statusCode), response.getStatusCode());
    }
}
