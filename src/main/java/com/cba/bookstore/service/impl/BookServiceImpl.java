package com.cba.bookstore.service.impl;

import com.cba.bookstore.dto.BookRequest;
import com.cba.bookstore.dto.BookResponse;
import com.cba.bookstore.model.Book;
import com.cba.bookstore.repository.BookRepository;
import com.cba.bookstore.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ModelMapper mapper;

    /**
     * Find All Books for book_store Table if Title is coming as null or Empty
     * If book Title is come then Find by Title will give response if further Title is not Matched with exact title column
     * Then find By Title Containing will be applicable.
     * @param title
     * @return
     */
    @Override
    public List<BookResponse> findAllBooks(final String title) {
        final List<Book> books;
        if(StringUtils.isBlank(title)){
            log.info("------- Find All Library's Books ------");
            books = bookRepository.findAll();
        } else {
            log.info("------- Find Books By Title : {} ------", title);
            List<Book> booksByTitle = bookRepository.findByTitle(title);
            if(booksByTitle.isEmpty()){
                log.info("------- Now Find Books By Containing the Title : {} ------", title);
                books = bookRepository.findByTitleContaining(title);
            } else {
                books = booksByTitle;
            }
        }
        List<BookResponse> response = books.stream().map(book -> mapper.map(book, BookResponse.class))
                .collect(Collectors.toList());
        return response;
    }

    /**
     * Find By BookId
     * @param id
     * @return
     */
    @Override
    public BookResponse findById(final long id) {
        log.info("------- Find Book By bookId : {}", id);
        Optional<Book> bookOptional = bookRepository.findById(id);
        if(bookOptional.isPresent()){
            return mapper.map(bookOptional.get(), BookResponse.class);
        }
        return null;
    }

    /**
     * Adding a New Book Record into book_store Table by Given bookRequest Object
     * @param bookRequest
     * @return
     */
    @Override
    public BookResponse createNewBookRecord(final BookRequest bookRequest) {
        log.info("------- Create New Book Record and Request Object : {}", bookRequest);
        Book book = Book.builder().title(bookRequest.getTitle()).price(bookRequest.getPrice())
                .author(bookRequest.getAuthor()).publisher(bookRequest.getPublisher()).build();
        Book bookResponse = bookRepository.save(book);
        return mapper.map(bookResponse, BookResponse.class);
    }

    /**
     * Updating a Book Record by bookId and as per given bookRequest object
     * @param bookRequest
     * @return
     */
    @Override
    public BookResponse updateBookRecord(final long id, final BookRequest bookRequest) {
        log.info("------- Updating Book Record based on BookId : {} and bookRequest : {}", id, bookRequest);
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent()) {
            log.info("------- Book Record is preset----- ");
            Book book = bookOptional.get();
            book.setTitle(StringUtils.isNoneBlank(bookRequest.getTitle()) ? bookRequest.getTitle() : book.getTitle());
            book.setAuthor(StringUtils.isNoneBlank(bookRequest.getAuthor())? bookRequest.getAuthor() : book.getAuthor());
            book.setPrice(bookRequest.getPrice() != null ? bookRequest.getPrice() : book.getPrice());
            book.setPublisher(StringUtils.isNoneBlank(bookRequest.getPublisher())? bookRequest.getPublisher() : book.getPublisher());
            Book save = bookRepository.save(book);
            return mapper.map(save, BookResponse.class);
        }
        return null;
    }

    /**
     * Delete Book Record by Id
     * @param id
     */
    @Override
    public void deleteById(long id) {
        log.info("------- delete Book Record by BookId : {} ", id);
        try {
            bookRepository.deleteById(id);
        } catch (Exception ex){
            log.error("Server Error found during deleteById and Error followed By : {} ", ex.getCause());
            throw new RuntimeException("Server Error !!!");
        }
    }
}
