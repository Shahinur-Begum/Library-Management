package com.example.BookInfo.controller;

import com.example.BookInfo.dto.AccessDto;
import com.example.BookInfo.entity.Access;
import com.example.BookInfo.service.AccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/access")
public class AccessController {

    @Autowired
    private AccessService accessService;

    @PostMapping("/create")
    public ResponseEntity<Access> createAccess(@RequestBody AccessDto accessDto) {
        Access access = accessService.createAccess(accessDto);
        return ResponseEntity.ok(access);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAccess(@RequestParam Long ebookId, @RequestParam Long studentId) {
        accessService.deleteAccess(ebookId, studentId);
        return ResponseEntity.ok("Access record deleted successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Access>> getAllAccessRecords() {
        List<Access> accessRecords = accessService.getAllAccessRecords();
        return ResponseEntity.ok(accessRecords);
    }
}
