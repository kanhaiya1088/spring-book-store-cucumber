package com.cba.bookstore.service;

import com.cba.bookstore.dto.BookRequest;
import com.cba.bookstore.dto.BookResponse;
import com.cba.bookstore.model.Book;
import com.cba.bookstore.repository.BookRepository;
import com.cba.bookstore.service.impl.BookServiceImpl;
import com.cba.bookstore.utils.TestDataPreparation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private BookServiceImpl bookService;


    @Test
    public void findByIdTest(){
        Book book = TestDataPreparation.getBook();

        Mockito.when(bookRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(book));

        BookResponse result = bookService.findById(book.getId());
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Head First Java", result.getTitle());
        assertEquals("Kathy Sierra", result.getAuthor());
        assertEquals(248, result.getPrice());
        assertEquals("J. K. Publisher", result.getPublisher());
    }

    @Test
    public void findAllBooksWhenTitleIsNull(){
        List<Book> books = TestDataPreparation.getBooks();
        Mockito.when(bookRepository.findAll()).thenReturn(books);
        List<BookResponse> result = bookService.findAllBooks(null);
        assertNotNull(result);
        assertTrue(result.size()>0);
        assertEquals(1, result.get(0).getId());
        assertEquals("Head First Java", result.get(0).getTitle());
        assertEquals("Kathy Sierra", result.get(0).getAuthor());
        assertEquals(248, result.get(0).getPrice());
        assertEquals("J. K. Publisher", result.get(0).getPublisher());
    }

    @Test
    public void findAllBooksWhenTitleIsMached(){
        List<Book> books = TestDataPreparation.getBooks();
        Mockito.when(bookRepository.findByTitle(Mockito.anyString())).thenReturn(books);
        List<BookResponse> result = bookService.findAllBooks("Head First Java");
        assertNotNull(result);
        assertTrue(result.size()>0);
        assertEquals(1, result.get(0).getId());
        assertEquals("Head First Java", result.get(0).getTitle());
        assertEquals("Kathy Sierra", result.get(0).getAuthor());
        assertEquals(248, result.get(0).getPrice());
        assertEquals("J. K. Publisher", result.get(0).getPublisher());
    }

    @Test
    public void findAllBooksWhenTitleIsContaining(){
        List<Book> books = TestDataPreparation.getBooks();
        Mockito.when(bookRepository.findByTitle(Mockito.anyString())).thenReturn(Collections.emptyList());
        Mockito.when(bookRepository.findByTitleContaining(Mockito.anyString())).thenReturn(books);
        List<BookResponse> result = bookService.findAllBooks("Head First Java");
        assertNotNull(result);
        assertTrue(result.size()>0);
        assertEquals(1, result.get(0).getId());
        assertEquals("Head First Java", result.get(0).getTitle());
        assertEquals("Kathy Sierra", result.get(0).getAuthor());
        assertEquals(248, result.get(0).getPrice());
        assertEquals("J. K. Publisher", result.get(0).getPublisher());
    }

    @Test
    void createNewBookRecordTest(){
        Book book = TestDataPreparation.getBook();
        Mockito.when(bookRepository.save(Mockito.any())).thenReturn(book);

        BookRequest bookRequest = BookRequest.builder().title("Head First Java").price(248)
                .author("Kathy Sierra").publisher("J. K. Publisher").build();
        BookResponse result = bookService.createNewBookRecord(bookRequest);
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Head First Java", result.getTitle());
        assertEquals("Kathy Sierra", result.getAuthor());
        assertEquals(248, result.getPrice());
        assertEquals("J. K. Publisher", result.getPublisher());
    }

    @Test
    void updateBookRecordTest(){
        Book book = TestDataPreparation.getBook();
        Mockito.when(bookRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(book));

        Mockito.when(bookRepository.save(Mockito.any())).thenReturn(book);

        BookRequest bookRequest = BookRequest.builder().title("Head First Java").price(248)
                .author("Kathy Sierra").publisher("J. K. Publisher").build();
        BookResponse result = bookService.updateBookRecord(book.getId(), bookRequest);
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Head First Java", result.getTitle());
        assertEquals("Kathy Sierra", result.getAuthor());
        assertEquals(248, result.getPrice());
        assertEquals("J. K. Publisher", result.getPublisher());
    }

    @Test
    public void deleteByIdTest(){
        long bookId = 1L;
        willDoNothing().given(bookRepository).deleteById(bookId);
        bookService.deleteById(bookId);
        verify(bookRepository, times(1)).deleteById(bookId);
    }

}