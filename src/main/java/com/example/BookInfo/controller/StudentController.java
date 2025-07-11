package com.example.BookInfo.controller;

import com.example.BookInfo.dto.BookDto;
import com.example.BookInfo.dto.EbookDto;
import com.example.BookInfo.service.BookService;
import com.example.BookInfo.service.EbookService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/student")
public class StudentController {

    private final BookService bookService;
    private final EbookService ebookService;

    // Endpoint to get a book by author
    @GetMapping("/books/author/{author}")
    public ResponseEntity<BookDto> getBookByAuthor(@PathVariable("author") String author) {
        BookDto bookDto = bookService.getBookByAuthor(author);
        return ResponseEntity.ok(bookDto);
    }

    // Endpoint to get a book by title
    @GetMapping("/books/title/{title}")
    public ResponseEntity<BookDto> getBookByTitle(@PathVariable("title") String title) {
        BookDto bookDto = bookService.getBookByTitle(title);
        return ResponseEntity.ok(bookDto);
    }

    @GetMapping("/ebooks/title/{title}")
    public ResponseEntity<EbookDto> getEbookByTitle(@PathVariable String title) {
        EbookDto ebookDto = ebookService.getEbookByTitle(title);
        return ResponseEntity.ok(ebookDto);
    }
}
