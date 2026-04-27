package com.resumeai.resume;

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
    public Object uploadResume(@RequestParam("file") MultipartFile file, @RequestParam("userId") Long userId) throws IOException {
        String result = resumeService.uploadResume(file, userId); // Calls service
        return Map.of("message", result, "resumeId", 1, "fileName", file.getOriginalFilename());
    }


    @GetMapping("/history")
    public List<String> getResumeHistory() {
        // Logic for retrieving user's resume upload history
        return List.of("resume1.pdf", "resume2.docx");
    }
}
