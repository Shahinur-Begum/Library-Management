package com.example.BookInfo.service;

import com.example.BookInfo.entity.KeepsTracksOf;
import com.example.BookInfo.repository.KeepsTracksOfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeepsTracksOfService {

    @Autowired
    private KeepsTracksOfRepository keepsTracksOfRepository;

    public KeepsTracksOf addTrack(KeepsTracksOf track) {
        return keepsTracksOfRepository.save(track);
    }

    public List<KeepsTracksOf> getAllTracks() {
        return keepsTracksOfRepository.findAll();
    }

    public List<KeepsTracksOf> getTracksByStudentId(Long studentId) {
        return keepsTracksOfRepository.findByStudentId(studentId);
    }

    public List<KeepsTracksOf> getTracksByAdminId(Long adminId) {
        return keepsTracksOfRepository.findByAdminId(adminId);
    }

    public void deleteTrack(Long id) {
        keepsTracksOfRepository.deleteById(id);
    }
}
