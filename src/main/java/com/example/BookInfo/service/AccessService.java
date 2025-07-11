package com.example.BookInfo.service;

import com.example.BookInfo.dto.AccessDto;
import com.example.BookInfo.entity.Access;

import java.util.List;

public interface AccessService {
    Access createAccess(AccessDto accessDto);
    void deleteAccess(Long ebookId, Long studentId);
    List<Access> getAllAccessRecords();
}
