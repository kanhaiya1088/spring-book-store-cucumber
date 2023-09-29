package com.cba.bookstore.utils;

import com.cba.bookstore.model.Book;

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
}
