package com.cba.bookstore.utils;

import com.cba.bookstore.dto.BookRequest;
import com.cba.bookstore.dto.BookResponse;
import com.cba.bookstore.model.Book;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class TestDataPreparation {
    public static Book getBook(){
        return Book.builder().id(1l).title("Head First Java").price(248)
                .author("Kathy Sierra").publisher("J. K. Publisher").build();
    }

    public static List<Book> getBooks(){
        return Arrays.asList(getBook());
    }

    public static BookRequest bookRequest(){
        return BookRequest.builder().title("Head First Java").price(248)
                .author("Kathy Sierra").publisher("J. K. Publisher").build();
    }

    public static BookResponse bookResponse(){
        LocalDateTime localDateTime = LocalDateTime.now();
        return BookResponse.builder().id(1l).title("Head First Java").price(248)
                .author("Kathy Sierra").publisher("J. K. Publisher")
                .createDateTime(localDateTime).updateDateTime(localDateTime).build();
    }

    public static List<BookResponse> bookResponseList(){
        return Arrays.asList(bookResponse());
    }
}