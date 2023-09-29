package com.cba.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *  Request DTO Class
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRequest {

    @NotEmpty
    @Size(min = 2, message = "Title should have at least 2 characters")
    private String title;

    @NotEmpty
    @Size(min = 2, message = "Author should have at least 2 characters")
    private String author;

    private Integer price;

    private String publisher;
}
