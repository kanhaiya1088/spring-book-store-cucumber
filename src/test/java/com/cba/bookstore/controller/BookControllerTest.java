package com.cba.bookstore.controller;

import com.cba.bookstore.dto.BookRequest;
import com.cba.bookstore.dto.BookResponse;
import com.cba.bookstore.service.impl.BookServiceImpl;
import com.cba.bookstore.utils.TestDataPreparation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookServiceImpl bookService;

    @Test
    public void getVersionTest() {
        String result = bookController.getVersion();
        assertThat(result.equals("0.0.1"));
    }

    @Test
    public void createNewBookTest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        BookResponse bookResponse = TestDataPreparation.bookResponse();

        when(bookService.createNewBookRecord(any(BookRequest.class))).thenReturn(bookResponse);

        ResponseEntity<BookResponse> responseEntity = bookController.addNewBook(TestDataPreparation.bookRequest());

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
        assertThat(responseEntity.getBody().getId().equals(1l));
        assertThat(responseEntity.getBody().getTitle().equals("Head First Java"));
        assertThat(responseEntity.getBody().getPrice().equals(248));
        assertThat(responseEntity.getBody().getPublisher().equals("J. K. Publisher"));
    }

    @Test
    public void creatNewBookWhenExceptionOccur() {
        when(bookService.createNewBookRecord(any(BookRequest.class))).thenThrow(new RuntimeException("Internal Server Error!!"));
        ResponseEntity<BookResponse> responseEntity = bookController.addNewBook(TestDataPreparation.bookRequest());
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(500);
    }

    @Test
    public void getBooksTest() {
        List<BookResponse> bookResponses = TestDataPreparation.bookResponseList();

        when(bookService.findAllBooks(Mockito.anyString())).thenReturn(bookResponses);

        ResponseEntity<List<BookResponse>> responseEntity = bookController.getBooks("Head First Java");
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody().size()).isEqualTo(1);
        assertThat(responseEntity.getBody().get(0).getId().equals(1l));
        assertThat(responseEntity.getBody().get(0).getTitle().equals("Head First Java"));
        assertThat(responseEntity.getBody().get(0).getPrice().equals(248));
        assertThat(responseEntity.getBody().get(0).getPublisher().equals("J. K. Publisher"));
    }

    @Test
    public void getBooksTestWhenExceptionOccur() {
        when(bookService.findAllBooks(Mockito.anyString())).thenThrow(RuntimeException.class);
        ResponseEntity<List<BookResponse>> responseEntity = bookController.getBooks("Head First Java");
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(500);
    }

    @Test
    public void getAllBooksWhenResponseIsEmpty() {
        when(bookService.findAllBooks(Mockito.anyString())).thenReturn(Collections.emptyList());
        ResponseEntity<List<BookResponse>> responseEntity = bookController.getBooks("Head First Java");
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(204);
    }

    @Test
    public void getBookByBookIdTest() {
        when(bookService.findById(Mockito.anyLong())).thenReturn(TestDataPreparation.bookResponse());
        ResponseEntity<BookResponse> responseEntity = bookController.getBookByBookId(1l);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody().getId().equals(1l));
        assertThat(responseEntity.getBody().getTitle().equals("Head First Java"));
        assertThat(responseEntity.getBody().getPrice().equals(248));
        assertThat(responseEntity.getBody().getPublisher().equals("J. K. Publisher"));
    }

    @Test
    public void getBookByBookIdWhenNoDataFound() {
        when(bookService.findById(Mockito.anyLong())).thenReturn(null);
        ResponseEntity<BookResponse> responseEntity = bookController.getBookByBookId(1l);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void updateBookTest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        BookResponse bookResponse = TestDataPreparation.bookResponse();

        when(bookService.updateBookRecord(anyLong(), any(BookRequest.class))).thenReturn(bookResponse);

        ResponseEntity<BookResponse> responseEntity = bookController.updateBook(1l, TestDataPreparation.bookRequest());

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody().getId().equals(1l));
        assertThat(responseEntity.getBody().getTitle().equals("Head First Java"));
        assertThat(responseEntity.getBody().getPrice().equals(248));
        assertThat(responseEntity.getBody().getPublisher().equals("J. K. Publisher"));

    }

    @Test
    public void updateBookTestWhenUpdateBookResponseIsNull(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(bookService.updateBookRecord(anyLong(), any(BookRequest.class))).thenReturn(null);

        ResponseEntity<BookResponse> responseEntity = bookController.updateBook(1l, TestDataPreparation.bookRequest());

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void deleteBookByBookIdTest(){
        long bookId = 1L;
        willDoNothing().given(bookService).deleteById(bookId);
        bookController.deleteBookByBookId(bookId);
        verify(bookService, times(1)).deleteById(bookId);
    }


}