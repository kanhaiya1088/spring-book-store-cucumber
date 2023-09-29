package com.cba.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Response DTO Class
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponse {

    private Long id;
    private String title;
    private String author;
    private Integer price;
    private String publisher;
    private LocalDateTime createDateTime;
    private LocalDateTime updateDateTime;
}
