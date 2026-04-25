package com.resumeai.feedback;

import com.resumeai.resume.Resume;
import com.resumeai.resume.ResumeRepository;
import com.resumeai.resume.ResumeService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FeedbackService {

    private final GeminiClient geminiClient;
    private final ResumeRepository resumeRepository;
    private final ResumeService resumeService;

    public FeedbackService(GeminiClient geminiClient, ResumeRepository resumeRepository, ResumeService resumeService) {
        this.geminiClient = geminiClient;
        this.resumeRepository = resumeRepository;
        this.resumeService = resumeService;
    }

    public String getFeedbackForResume(Long resumeId) {
        Optional<Resume> resumeOptional = resumeRepository.findById(resumeId);
        if (resumeOptional.isPresent()) {
            Resume resume = resumeOptional.get();
            // In a real application, you would extract text from the resume file
            String resumeText = resume.getExtractedText();
            if (resumeText == null || resumeText.isEmpty()) {
                // If text not extracted during upload, try to extract now
                try {
                    resumeText = resumeService.extractTextFromPdf(resume.getFileContent());
                } catch (Exception e) {
                    return "Error extracting text from resume: " + e.getMessage();
                }
            }            return geminiClient.getFeedback(resumeText);
        } else {
            return "Resume not found";
        }
    }

    public String chatWithGemini(Long resumeId, String chatHistory, String userQuestion) {
        Optional<Resume> resumeOptional = resumeRepository.findById(resumeId);
        if (resumeOptional.isPresent()) {
            Resume resume = resumeOptional.get();
            String resumeText = resume.getExtractedText();
            if (resumeText == null || resumeText.isEmpty()) {
                // If text not extracted during upload, try to extract now
                try {
                    resumeText = resumeService.extractTextFromPdf(resume.getFileContent());
                } catch (Exception e) {
                    return "Error extracting text from resume: " + e.getMessage();
                }
            }
            return geminiClient.chatWithGemini(resumeText, chatHistory, userQuestion);
        } else {
            return "Resume not found";
        }
    }
}
