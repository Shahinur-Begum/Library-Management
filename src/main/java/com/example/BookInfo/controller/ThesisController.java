package com.example.BookInfo.controller;

import com.example.BookInfo.dto.ThesisDto;
import com.example.BookInfo.service.ThesisService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/thesis")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class ThesisController {

    private final ThesisService thesisService;

    @PostMapping
    public ThesisDto createThesis(@RequestBody ThesisDto thesisDto) {
        return thesisService.createThesis(thesisDto);
    }

    @GetMapping("/{id}")
    public ThesisDto getThesisById(@PathVariable Long id) {
        return thesisService.getThesisById(id);
    }

    @GetMapping("/title/{title}")
    public ThesisDto getThesisByTitle(@PathVariable String title) {
        return thesisService.getThesisByTitle(title);
    }

    @GetMapping("/topic/{topic}")
    public List<ThesisDto> getThesesByTopic(@PathVariable String topic) {
        return thesisService.getThesesByTopic(topic);
    }

    @GetMapping
    public List<ThesisDto> getAllTheses() {
        return thesisService.getAllTheses();
    }

    @PutMapping("/{id}")
    public ThesisDto updateThesis(@PathVariable Long id, @RequestBody ThesisDto thesisDto) {
        return thesisService.updateThesis(id, thesisDto);
    }

    @DeleteMapping("/{id}")
    public void deleteThesis(@PathVariable Long id) {
        thesisService.deleteThesis(id);
    }

    // Download endpoint using downloadUrl for direct file download
    @GetMapping("/download/{id}")
    public ResponseEntity<Void> redirectToDownloadFile(@PathVariable Long id) {
        ThesisDto thesis = thesisService.getThesisById(id);
        String downloadUrl = thesis.getDownloadUrl();

        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, downloadUrl)
                .build();
    }
}
