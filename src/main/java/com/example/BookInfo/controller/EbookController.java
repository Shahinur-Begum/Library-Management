package com.example.BookInfo.controller;

import com.example.BookInfo.dto.EbookDto;
import com.example.BookInfo.service.EbookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class EbookController {

    private final EbookService ebookService;

    @PostMapping("/ebooks")
    public ResponseEntity<EbookDto> createEbook(@RequestBody EbookDto ebookDto) {
        EbookDto savedEbook = ebookService.createEbook(ebookDto);
        return new ResponseEntity<>(savedEbook, HttpStatus.CREATED);
    }

    @GetMapping("/ebooks/{id}")
    public ResponseEntity<EbookDto> getEbookById(@PathVariable("id") Long ebookId) {
        EbookDto ebookDto = ebookService.getEbookById(ebookId);
        return ResponseEntity.ok(ebookDto);
    }

    @GetMapping("/ebooks/title/{title}")
    public ResponseEntity<EbookDto> getEbookByTitle(@PathVariable("title") String title) {
        EbookDto ebookDto = ebookService.getEbookByTitle(title);
        return ResponseEntity.ok(ebookDto);
    }

    @GetMapping("/ebooks")
    public ResponseEntity<List<EbookDto>> getAllEbooks() {
        List<EbookDto> ebooks = ebookService.getAllEbooks();
        return ResponseEntity.ok(ebooks);
    }

    @PutMapping("/ebooks/{id}")
    public ResponseEntity<EbookDto> updateEbook(@PathVariable("id") Long ebookId,
                                                @RequestBody EbookDto updatedEbook) {
        EbookDto ebookDto = ebookService.updateEbook(ebookId, updatedEbook);
        return ResponseEntity.ok(ebookDto);
    }

    @DeleteMapping("/ebooks/{id}")
    public ResponseEntity<String> deleteEbook(@PathVariable("id") Long ebookId) {
        ebookService.deleteEbook(ebookId);
        return ResponseEntity.ok("Ebook successfully deleted");
    }
}
