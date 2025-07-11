package com.example.BookInfo.service.impl;

import com.example.BookInfo.dto.AccessDto;
import com.example.BookInfo.entity.Access;
import com.example.BookInfo.repository.AccessRepository;
import com.example.BookInfo.service.AccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccessServiceImpl implements AccessService {

    @Autowired
    private AccessRepository accessRepository;

    @Override
    public Access createAccess(AccessDto accessDto) {
        Access access = new Access();
        access.setEbookId(accessDto.getEbookId());
        access.setStudentId(accessDto.getStudentId());
        return accessRepository.save(access);
    }

    @Override
    public void deleteAccess(Long ebookId, Long studentId) {
        Access.AccessKey key = new Access.AccessKey(ebookId, studentId);
        accessRepository.deleteById(key);
    }

    @Override
    public List<Access> getAllAccessRecords() {
        return accessRepository.findAll();
    }
}
