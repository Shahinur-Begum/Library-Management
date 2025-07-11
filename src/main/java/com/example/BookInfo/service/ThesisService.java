package com.example.BookInfo.service;

import com.example.BookInfo.dto.ThesisDto;
import com.example.BookInfo.entity.Thesis;
import com.example.BookInfo.exception.ResourceNotFoundException;
import com.example.BookInfo.repository.ThesisRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ThesisService {

    private final ThesisRepository thesisRepository;

    public ThesisDto createThesis(ThesisDto thesisDto) {
        Thesis thesis = new Thesis();
        thesis.setTitle(thesisDto.getTitle());
        thesis.setAuthor(thesisDto.getAuthor());
        thesis.setYear(thesisDto.getYear());
        thesis.setTopic(thesisDto.getTopic());
        thesis.setFileUrl(thesisDto.getFileUrl());
        thesis.setDownloadUrl(thesisDto.getDownloadUrl());  // NEW

        Thesis savedThesis = thesisRepository.save(thesis);

        ThesisDto savedDto = new ThesisDto();
        savedDto.setThesisId(savedThesis.getThesisId());
        savedDto.setTitle(savedThesis.getTitle());
        savedDto.setAuthor(savedThesis.getAuthor());
        savedDto.setYear(savedThesis.getYear());
        savedDto.setTopic(savedThesis.getTopic());
        savedDto.setFileUrl(savedThesis.getFileUrl());
        savedDto.setDownloadUrl(savedThesis.getDownloadUrl());  // NEW

        return savedDto;
    }

    public ThesisDto getThesisById(Long thesisId) {
        Thesis thesis = thesisRepository.findById(thesisId)
                .orElseThrow(() -> new ResourceNotFoundException("Thesis not found with ID: " + thesisId));

        ThesisDto dto = new ThesisDto();
        dto.setThesisId(thesis.getThesisId());
        dto.setTitle(thesis.getTitle());
        dto.setAuthor(thesis.getAuthor());
        dto.setYear(thesis.getYear());
        dto.setTopic(thesis.getTopic());
        dto.setFileUrl(thesis.getFileUrl());
        dto.setDownloadUrl(thesis.getDownloadUrl());  // NEW

        return dto;
    }

    public ThesisDto getThesisByTitle(String title) {
        Thesis thesis = thesisRepository.findByTitleIgnoreCase(title)
                .orElseThrow(() -> new ResourceNotFoundException("Thesis with title \"" + title + "\" not found"));

        ThesisDto dto = new ThesisDto();
        dto.setThesisId(thesis.getThesisId());
        dto.setTitle(thesis.getTitle());
        dto.setAuthor(thesis.getAuthor());
        dto.setYear(thesis.getYear());
        dto.setTopic(thesis.getTopic());
        dto.setFileUrl(thesis.getFileUrl());
        dto.setDownloadUrl(thesis.getDownloadUrl());  // NEW

        return dto;
    }

    public List<ThesisDto> getAllTheses() {
        return thesisRepository.findAll().stream().map(thesis -> {
            ThesisDto dto = new ThesisDto();
            dto.setThesisId(thesis.getThesisId());
            dto.setTitle(thesis.getTitle());
            dto.setAuthor(thesis.getAuthor());
            dto.setYear(thesis.getYear());
            dto.setTopic(thesis.getTopic());
            dto.setFileUrl(thesis.getFileUrl());
            dto.setDownloadUrl(thesis.getDownloadUrl());  // NEW
            return dto;
        }).collect(Collectors.toList());
    }

    public List<ThesisDto> getThesesByTopic(String topic) {
        return thesisRepository.findByTopic(topic).stream().map(thesis -> {
            ThesisDto dto = new ThesisDto();
            dto.setThesisId(thesis.getThesisId());
            dto.setTitle(thesis.getTitle());
            dto.setAuthor(thesis.getAuthor());
            dto.setYear(thesis.getYear());
            dto.setTopic(thesis.getTopic());
            dto.setFileUrl(thesis.getFileUrl());
            dto.setDownloadUrl(thesis.getDownloadUrl());  // NEW
            return dto;
        }).collect(Collectors.toList());
    }

    public ThesisDto updateThesis(Long thesisId, ThesisDto updatedThesis) {
        Thesis thesis = thesisRepository.findById(thesisId)
                .orElseThrow(() -> new ResourceNotFoundException("Thesis not found with ID: " + thesisId));

        thesis.setTitle(updatedThesis.getTitle());
        thesis.setAuthor(updatedThesis.getAuthor());
        thesis.setYear(updatedThesis.getYear());
        thesis.setTopic(updatedThesis.getTopic());
        thesis.setFileUrl(updatedThesis.getFileUrl());
        thesis.setDownloadUrl(updatedThesis.getDownloadUrl());  // NEW

        Thesis saved = thesisRepository.save(thesis);

        ThesisDto dto = new ThesisDto();
        dto.setThesisId(saved.getThesisId());
        dto.setTitle(saved.getTitle());
        dto.setAuthor(saved.getAuthor());
        dto.setYear(saved.getYear());
        dto.setTopic(saved.getTopic());
        dto.setFileUrl(saved.getFileUrl());
        dto.setDownloadUrl(saved.getDownloadUrl());  // NEW

        return dto;
    }

    public void deleteThesis(Long thesisId) {
        Thesis thesis = thesisRepository.findById(thesisId)
                .orElseThrow(() -> new ResourceNotFoundException("Thesis not found with ID: " + thesisId));

        thesisRepository.delete(thesis);
    }
}
