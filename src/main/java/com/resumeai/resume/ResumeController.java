package com.resumeai.resume;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/resume")
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadResume(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") Long userId) throws IOException {

        Resume resume = resumeService.uploadResume(file, userId);

        if (resume == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "User not found"));
        }

        return ResponseEntity.ok(Map.of(
                "message", "Resume uploaded successfully",
                "resumeId", resume.getId(),
                "fileName", resume.getFileName(),
                "extractedText", resume.getExtractedText() != null ? resume.getExtractedText() : ""
        ));
    }

    @GetMapping("/history")
    public ResponseEntity<List<Resume>> getResumeHistory(@RequestParam("userId") Long userId) {
        List<Resume> history = resumeService.getResumeHistory(userId);
        return ResponseEntity.ok(history);
    }
}