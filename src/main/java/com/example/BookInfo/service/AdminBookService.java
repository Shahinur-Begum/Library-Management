package com.example.BookInfo.service;

import com.example.BookInfo.entity.AdminBook;
import com.example.BookInfo.repository.AdminBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminBookService {

    @Autowired
    private AdminBookRepository adminBookRepository;

    // Method to save AdminBook relationship
    public void saveAdminBook(AdminBook adminBook) {
        adminBookRepository.save(adminBook);
    }

    // Method to delete AdminBook relationship
    public void deleteAdminBook(AdminBook adminBook) {
        adminBookRepository.delete(adminBook);
    }
}
