package com.example.BookInfo.service.impl;

import com.example.BookInfo.dto.EbookDto;
import com.example.BookInfo.entity.Ebook;
import com.example.BookInfo.exception.ResourceNotFoundException;
import com.example.BookInfo.repository.EbookRepository;
import com.example.BookInfo.service.EbookService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EbookServiceImpl implements EbookService {

    private final EbookRepository ebookRepository;

    @Override
    public EbookDto createEbook(EbookDto ebookDto) {
        // Manually mapping EbookDto to Ebook
        Ebook ebook = new Ebook();
        ebook.setTitle(ebookDto.getTitle());
        ebook.setCover(ebookDto.getCover());
        ebook.setPdf(ebookDto.getPdf());
        ebook.setAudioLink(ebookDto.getAudioLink());

        Ebook savedEbook = ebookRepository.save(ebook);

        // Manually mapping saved Ebook to EbookDto
        EbookDto savedEbookDto = new EbookDto();
        savedEbookDto.setId(savedEbook.getId());
        savedEbookDto.setTitle(savedEbook.getTitle());
        savedEbookDto.setCover(savedEbook.getCover());
        savedEbookDto.setPdf(savedEbook.getPdf());
        savedEbookDto.setAudioLink(savedEbook.getAudioLink());

        return savedEbookDto;
    }

    @Override
    public EbookDto getEbookById(Long ebookId) {
        Ebook ebook = ebookRepository.findById(ebookId)
                .orElseThrow(() -> new ResourceNotFoundException("Ebook doesn't exist with given id: " + ebookId));

        // Manually mapping Ebook to EbookDto
        EbookDto ebookDto = new EbookDto();
        ebookDto.setId(ebook.getId());
        ebookDto.setTitle(ebook.getTitle());
        ebookDto.setCover(ebook.getCover());
        ebookDto.setPdf(ebook.getPdf());
        ebookDto.setAudioLink(ebook.getAudioLink());

        return ebookDto;
    }

    @Override
    public EbookDto getEbookByTitle(String title) {
        Ebook ebook = ebookRepository.findByTitleIgnoreCase(title)
                .orElseThrow(() -> new ResourceNotFoundException("Ebook with title \"" + title + "\" not found"));

        // Manually mapping Ebook to EbookDto
        EbookDto ebookDto = new EbookDto();
        ebookDto.setId(ebook.getId());
        ebookDto.setTitle(ebook.getTitle());
        ebookDto.setCover(ebook.getCover());
        ebookDto.setPdf(ebook.getPdf());
        ebookDto.setAudioLink(ebook.getAudioLink());

        return ebookDto;
    }

    @Override
    public List<EbookDto> getAllEbooks() {
        List<Ebook> ebooks = ebookRepository.findAll();
        // Manually mapping List<Ebook> to List<EbookDto>
        return ebooks.stream().map(ebook -> {
            EbookDto ebookDto = new EbookDto();
            ebookDto.setId(ebook.getId());
            ebookDto.setTitle(ebook.getTitle());
            ebookDto.setCover(ebook.getCover());
            ebookDto.setPdf(ebook.getPdf());
            ebookDto.setAudioLink(ebook.getAudioLink());
            return ebookDto;
        }).collect(Collectors.toList());
    }

    @Override
    public EbookDto updateEbook(Long ebookId, EbookDto updateEbook) {
        Ebook ebook = ebookRepository.findById(ebookId)
                .orElseThrow(() -> new ResourceNotFoundException("Ebook doesn't exist with given id: " + ebookId));

        // Updating fields manually
        ebook.setCover(updateEbook.getCover());
        ebook.setTitle(updateEbook.getTitle());
        ebook.setPdf(updateEbook.getPdf());
        ebook.setAudioLink(updateEbook.getAudioLink());

        Ebook updatedEbook = ebookRepository.save(ebook);

        // Manually mapping updated Ebook to EbookDto
        EbookDto updatedEbookDto = new EbookDto();
        updatedEbookDto.setId(updatedEbook.getId());
        updatedEbookDto.setTitle(updatedEbook.getTitle());
        updatedEbookDto.setCover(updatedEbook.getCover());
        updatedEbookDto.setPdf(updatedEbook.getPdf());
        updatedEbookDto.setAudioLink(updatedEbook.getAudioLink());

        return updatedEbookDto;
    }

    @Override
    public void deleteEbook(Long ebookId) {
        Ebook ebook = ebookRepository.findById(ebookId)
                .orElseThrow(() -> new ResourceNotFoundException("Ebook doesn't exist with given id: " + ebookId));

        ebookRepository.deleteById(ebookId);
    }
}
