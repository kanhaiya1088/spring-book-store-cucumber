package com.cba.bookstore.controller;

import com.cba.bookstore.dto.BookRequest;
import com.cba.bookstore.dto.BookResponse;
import com.cba.bookstore.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
@Tag(name = "Bookstore", description = "Endpoints for managing bookstore")
public class BookController {

	@Autowired
	private BookService bookService;

	@Value(value = "${app.version}")
	private String APPLICATION_VERSION;

	@GetMapping("/version")
	public String getVersion() {
		return Optional.ofNullable(APPLICATION_VERSION).orElse("N/A");
	}

	@GetMapping("/books")
	@Operation(
		summary = "Finds all Books", description = "Finds all Books if title is not present if the title is coming then first" +
			" it will check exact title matched, if still not found then it will check by containing the title",
		responses = {
				@ApiResponse(
						description = "Get All Books Information",
						responseCode = "200",
						content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = BookResponse.class)))
				),
				@ApiResponse(description = "Not found", responseCode = "404", content = @Content),
				@ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
		}
	)
	public ResponseEntity<List<BookResponse>> getBooks(@RequestParam(name = "title", required = false) String title) {
		try {
			List<BookResponse> books = bookService.findAllBooks(title);
			if (books.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(books, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/books/{id}")
	@Operation(
			summary = "Finds a Book",
			description = "Finds a book by their Id.",
//			tags = { "BookStore" },
			responses = {
					@ApiResponse(
							description = "Success",
							responseCode = "200",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookResponse.class))
					),
					@ApiResponse(description = "Not found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
			}
	)
	public ResponseEntity<BookResponse> getBookByBookId(@PathVariable("id") Long id) {
		BookResponse bookResponse = bookService.findById(id);
		if (bookResponse != null) {
			return new ResponseEntity<>(bookResponse, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/books")
	@Operation(
			summary = "Create a New Book",
			description = "Create a New Book by Given Request Body",
			responses = {
					@ApiResponse(
							description = "Success",
							responseCode = "201",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookResponse.class))
					),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
			}
	)
	public ResponseEntity<BookResponse> addNewBook(@Valid @RequestBody BookRequest bookRequest) {
		try {
			BookResponse bookResponse = bookService.createNewBookRecord(bookRequest);
			return new ResponseEntity<>(bookResponse, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/books/{id}")
	@Operation(
			summary = "Update a Book",
			description = "Update a Book by their id and given request body",
			responses = {
					@ApiResponse(
							description = "Success",
							responseCode = "200",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookResponse.class))
					),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
			}
	)
	public ResponseEntity<BookResponse> updateBook(@PathVariable("id") Long id, @RequestBody BookRequest bookRequest) {
		BookResponse bookResponse = bookService.updateBookRecord(id, bookRequest);
		if (bookResponse != null) {
			return new ResponseEntity<>(bookResponse, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/books/{id}")
	@Operation(
			summary = "Delete a Book",
			description = "Delete a Book by their id",
			responses = {
					@ApiResponse(
							description = "Success",
							responseCode = "204",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookResponse.class))
					),
					@ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
			}
	)
	public ResponseEntity<HttpStatus> deleteBookByBookId(@PathVariable("id") long id) {
		try {
			bookService.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}