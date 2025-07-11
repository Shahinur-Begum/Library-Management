package com.example.BookInfo.service;

import com.example.BookInfo.dto.EbookDto;

import java.util.List;

public interface EbookService {

    EbookDto createEbook(EbookDto ebookDto);

    EbookDto getEbookById(Long id);

    EbookDto getEbookByTitle(String title);

    List<EbookDto> getAllEbooks();

    EbookDto updateEbook(Long id, EbookDto updateEbook);

    void deleteEbook(Long id);
}
