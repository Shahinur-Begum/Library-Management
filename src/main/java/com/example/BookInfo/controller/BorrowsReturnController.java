package com.example.BookInfo.controller;

import com.example.BookInfo.dto.BorrowsReturnDto;
import com.example.BookInfo.service.BorrowsReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/borrows-return")
public class BorrowsReturnController {

    @Autowired
    private BorrowsReturnService borrowsReturnService;

    @PostMapping("/create")
    public ResponseEntity<BorrowsReturnDto> createBorrowsReturn(@RequestBody BorrowsReturnDto borrowsReturnDto) {
        BorrowsReturnDto result = borrowsReturnService.createBorrowsReturn(
                borrowsReturnDto.getStudentId(),
                borrowsReturnDto.getBookId(),
                borrowsReturnDto.getIssuedDate(),
                borrowsReturnDto.getReturnDate()
        );
        return ResponseEntity.ok(result);
    }

    @GetMapping("/details/{studentId}")
    public ResponseEntity<Map<String, Object>> getBooksAndDetailsByStudent(@PathVariable Long studentId) {
        Map<String, Object> result = borrowsReturnService.getBooksAndDetailsByStudentId(studentId);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/update/{studentId}/{bookId}")
    public ResponseEntity<BorrowsReturnDto> updateBorrowsReturn(
            @PathVariable Long studentId,
            @PathVariable Long bookId,
            @RequestBody BorrowsReturnDto borrowsReturnDto) {

        BorrowsReturnDto result = borrowsReturnService.updateBorrowsReturn(
                studentId,
                bookId,
                borrowsReturnDto.getIssuedDate(),
                borrowsReturnDto.getReturnDate()
        );
        return ResponseEntity.ok(result);
    }

    @GetMapping("/all")
    public ResponseEntity<List<BorrowsReturnDto>> getAllBorrowsReturns() {
        // Fetch all records from the service
        List<BorrowsReturnDto> result = borrowsReturnService.getAllBorrowsReturns();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/due-books/{studentId}")
    public ResponseEntity<Map<String, Object>> getDueBooksByStudent(@PathVariable Long studentId) {
        // Get the due books with count and details from the service layer
        Map<String, Object> dueBooksData = borrowsReturnService.getDueBooksByStudentId(studentId);

        // Return the response with the count and the due books details
        return ResponseEntity.ok(dueBooksData);
    }



    @DeleteMapping("/delete/{studentId}/{bookId}")
    public ResponseEntity<String> deleteBorrowsReturn(
            @PathVariable Long studentId,
            @PathVariable Long bookId) {

        borrowsReturnService.deleteBorrowsReturn(studentId, bookId);
        return ResponseEntity.ok("BorrowsReturn successfully deleted");
    }
}
