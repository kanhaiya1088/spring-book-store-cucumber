package com.cba.bookstore.repository;

import com.cba.bookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
  List<Book> findByTitleContaining(final String title);

  List<Book> findByTitle(final String title);
}