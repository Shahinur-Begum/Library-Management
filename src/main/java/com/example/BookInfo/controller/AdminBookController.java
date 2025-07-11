package com.example.BookInfo.controller;

import com.example.BookInfo.entity.AdminBook;
import com.example.BookInfo.service.AdminBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AdminBookController {

    @Autowired
    private AdminBookService adminBookService;

    // Endpoint to add an admin-book relationship
    @PostMapping("/add")
    public void addAdminBook(@RequestBody AdminBook adminBook) {
        adminBookService.saveAdminBook(adminBook);
    }

    // Endpoint to remove an admin-book relationship
    @DeleteMapping
    public void removeAdminBook(@RequestBody AdminBook adminBook) {
        adminBookService.deleteAdminBook(adminBook);
    }
}
