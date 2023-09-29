package com.cba.bookstore.service;

import com.cba.bookstore.dto.BookRequest;
import com.cba.bookstore.dto.BookResponse;

import java.util.List;

public interface BookService {

    List<BookResponse> findAllBooks(final String title);
    BookResponse findById(final long id);
    BookResponse createNewBookRecord(final BookRequest bookRequest);
    BookResponse updateBookRecord(final long id, final BookRequest bookRequest);
    void deleteById(final long id);
}
