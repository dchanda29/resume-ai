package com.resumeai.resume;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/resume")
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @PostMapping("/upload")
    public String uploadResume(@RequestParam("file") MultipartFile file) {
        // Logic for uploading and storing resume
        return "Resume uploaded successfully";
    }

    @GetMapping("/history")
    public List<String> getResumeHistory() {
        // Logic for retrieving user's resume upload history
        return List.of("resume1.pdf", "resume2.docx");
    }
}
