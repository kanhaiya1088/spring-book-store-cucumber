package com.cba.bookstore.steps;

import com.cba.bookstore.dto.BookResponse;
import com.cba.bookstore.model.Book;
import com.cba.bookstore.repository.BookRepository;
import com.cba.bookstore.utils.FeatureUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookManagement {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private TestRestTemplate restTemplate;
    private ResponseEntity<BookResponse> response;
    private BookResponse mockBook;

    @Given("an existing book with ID {long}")
    public void anExistingBookWithID(long id) {
        // use the mock account saved
        this.mockBook = FeatureUtils.getMockBookResponse();
        Book book = mapper.map(this.mockBook, Book.class);
        this.bookRepository.save(book);
    }

    @When("the user sends a GET request to {string}")
    public void theUserSendsAGETRequestTo(String path) {
        String url = String.format("%s/%s/%d", FeatureUtils.URL, path, mockBook.getId());
        response = this.restTemplate.getForEntity(url, BookResponse.class);
    }

    @Then("the response status code of getting a book should be {int}")
    public void theResponseStatusCodeOfGettingAnAccountShouldBe(int statusCode) {
        assertEquals(HttpStatus.valueOf(statusCode), response.getStatusCode());
    }

    @And("the response body should contain the book details")
    public void theResponseBodyShouldContainTheBookDetails() {
        BookResponse resBook = response.getBody();
        assert resBook != null;
        assertEquals(mockBook.getId(), resBook.getId());
        assertEquals(mockBook.getTitle(), resBook.getTitle());
        assertEquals(mockBook.getAuthor(), resBook.getAuthor());
        assertEquals(mockBook.getPrice(), resBook.getPrice());
        assertEquals(mockBook.getPublisher(), resBook.getPublisher());
    }
}
