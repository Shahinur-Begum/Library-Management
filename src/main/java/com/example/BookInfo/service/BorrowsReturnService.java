package com.example.BookInfo.service;

import com.example.BookInfo.dto.BorrowsReturnDto;
import com.example.BookInfo.entity.BorrowsReturn;
import com.example.BookInfo.entity.Book;
import com.example.BookInfo.entity.Student;
import com.example.BookInfo.repository.BorrowsReturnRepository;
import com.example.BookInfo.repository.BookRepository;
import com.example.BookInfo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BorrowsReturnService {

    @Autowired
    private BorrowsReturnRepository borrowsReturnRepository;

    @Autowired
    private ReportService reportService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private StudentRepository studentRepository;

    // Scheduled to run at midnight every day
    @Scheduled(cron = "0 0 0 * * ?")
    public void updateDues() {
        List<BorrowsReturn> overdueRecords = borrowsReturnRepository.findAll()
                .stream()
                .filter(borrow -> borrow.getReturnDate() != null && borrow.getReturnDate().before(new Date()))
                .toList();

        for (BorrowsReturn borrow : overdueRecords) {
            borrow.setDue(borrow.getDue() + 1);
            borrowsReturnRepository.save(borrow);
        }
    }

    public BorrowsReturnDto createBorrowsReturn(Long studentId, Long bookId, Date issuedDate, Date returnDate) {
        validateDates(issuedDate, returnDate);

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        BorrowsReturn borrowsReturn = new BorrowsReturn();
        borrowsReturn.setStudent(student);
        borrowsReturn.setBook(book);
        borrowsReturn.setStudentId(studentId);
        borrowsReturn.setBookId(bookId);
        borrowsReturn.setIssuedDate(issuedDate);
        borrowsReturn.setReturnDate(returnDate);

        // Calculate initial due value
        int due = calculateDue(returnDate);
        borrowsReturn.setDue(due);

        BorrowsReturn saved = borrowsReturnRepository.save(borrowsReturn);
        reportService.generateOrUpdateReport(studentId);
        return convertToDto(saved);
    }

    public BorrowsReturnDto updateBorrowsReturn(Long studentId, Long bookId, Date issuedDate, Date returnDate) {
        validateDates(issuedDate, returnDate);

        BorrowsReturn borrowsReturn = borrowsReturnRepository.findById(new BorrowsReturn.BorrowsReturnKey(studentId, bookId))
                .orElseThrow(() -> new IllegalArgumentException("Record not found"));

        borrowsReturn.setIssuedDate(issuedDate);
        borrowsReturn.setReturnDate(returnDate);

        // Recalculate due value
        int due = calculateDue(returnDate);
        borrowsReturn.setDue(due);
        reportService.generateOrUpdateReport(studentId);
        BorrowsReturn updated = borrowsReturnRepository.save(borrowsReturn);

        return convertToDto(updated);
    }

    public void deleteBorrowsReturn(Long studentId, Long bookId) {
        BorrowsReturn borrowsReturn = borrowsReturnRepository.findById(new BorrowsReturn.BorrowsReturnKey(studentId, bookId))
                .orElseThrow(() -> new IllegalArgumentException("Record not found"));

        borrowsReturnRepository.delete(borrowsReturn);
    }

    private BorrowsReturnDto convertToDto(BorrowsReturn borrowsReturn) {
        return new BorrowsReturnDto(
                borrowsReturn.getStudentId(),
                borrowsReturn.getBookId(),
                borrowsReturn.getIssuedDate(),
                borrowsReturn.getReturnDate(),
                borrowsReturn.getDue()
        );
    }

    public Map<String, Object> getBooksAndDetailsByStudentId(Long studentId) {
        List<BorrowsReturn> borrows = borrowsReturnRepository.findByStudentId(studentId);
        Long count = (long) borrows.size();

        // Map to hold the simplified book details including cover image
        List<Map<String, Object>> bookDetails = borrows.stream().map(borrow -> {
            Book book = bookRepository.findById(borrow.getBookId())
                    .orElseThrow(() -> new IllegalArgumentException("Book not found for ID: " + borrow.getBookId()));

            // Create a simplified book details map including cover
            Map<String, Object> details = new HashMap<>();
            details.put("title", book.getTitle());
            details.put("author", book.getAuthor());
            details.put("category", book.getCategory()); // Assuming 'category' exists in the Book entity
            details.put("cover", book.getCover()); // Assuming 'cover' is a URL or file path in Book entity

            return details;
        }).collect(Collectors.toList());

        Map<String, Object> result = new HashMap<>();
        result.put("count", count); // Return the count of books issued
        result.put("books", bookDetails); // Return the simplified book details including cover

        return result;
    }

    public List<BorrowsReturnDto> getAllBorrowsReturns() {
        List<BorrowsReturn> borrowsReturnList = borrowsReturnRepository.findAll();

        return borrowsReturnList.stream()
                .map(borrowsReturn -> new BorrowsReturnDto(
                        borrowsReturn.getStudentId(),
                        borrowsReturn.getBookId(),
                        borrowsReturn.getIssuedDate(),
                        borrowsReturn.getReturnDate(),
                        borrowsReturn.getDue()))
                .collect(Collectors.toList());
    }

    public Map<String, Object> getDueBooksByStudentId(Long studentId) {
        // Fetch the due borrows for the student
        List<BorrowsReturn> dueBorrows = borrowsReturnRepository.findDueBooksByStudentId(studentId);
        Long count = (long) dueBorrows.size();

        // Map to hold the simplified book details including cover image and due value
        List<Map<String, Object>> dueBookDetails = dueBorrows.stream().map(borrow -> {
            // Fetch book details for each borrow entry
            Book book = bookRepository.findById(borrow.getBookId())
                    .orElseThrow(() -> new IllegalArgumentException("Book not found for ID: " + borrow.getBookId()));

            // Create a simplified book details map including cover, due value, and other details
            Map<String, Object> details = new HashMap<>();
            details.put("title", book.getTitle());
            details.put("author", book.getAuthor());
            details.put("category", book.getCategory()); // Assuming 'category' exists in the Book entity
            details.put("cover", book.getCover()); // Assuming 'cover' is a URL or file path in Book entity
            details.put("due", borrow.getDue()); // Directly use the due value stored in the BorrowsReturn entity

            return details;
        }).collect(Collectors.toList());

        // Create a response map to return the count and due book details
        Map<String, Object> result = new HashMap<>();
        result.put("count", count); // Return the count of due books
        result.put("dueBooks", dueBookDetails); // Return the simplified due book details including cover and due value

        return result;
    }

    // Updated method to calculate overdue days without time zone issues
    private int calculateDue(Date returnDate) {
        if (returnDate == null || !returnDate.before(new Date())) {
            return 0;
        }

        // Convert returnDate to LocalDate to ignore the time portion
        LocalDate currentDate = LocalDate.now();
        LocalDate returnLocalDate = returnDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();

        // Check if the return date is before the current date
        if (returnLocalDate.isBefore(currentDate)) {
            // Calculate the number of days overdue
            return (int) ChronoUnit.DAYS.between(returnLocalDate, currentDate);
        }

        return 0;
    }

    private void validateDates(Date issuedDate, Date returnDate) {
        if (issuedDate == null) {
            throw new IllegalArgumentException("Issued date cannot be null");
        }
        if (returnDate != null && returnDate.before(issuedDate)) {
            throw new IllegalArgumentException("Return date cannot be before issued date");
        }
    }
}
