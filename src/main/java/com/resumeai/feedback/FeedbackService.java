package com.resumeai.feedback;

import com.resumeai.resume.Resume;
import com.resumeai.resume.ResumeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FeedbackService {

    private final GeminiClient geminiClient;
    private final ResumeRepository resumeRepository;

    public FeedbackService(GeminiClient geminiClient, ResumeRepository resumeRepository) {
        this.geminiClient = geminiClient;
        this.resumeRepository = resumeRepository;
    }

    public String getFeedbackForResume(Long resumeId) {
        Optional<Resume> resumeOptional = resumeRepository.findById(resumeId);
        if (resumeOptional.isPresent()) {
            Resume resume = resumeOptional.get();
            // In a real application, you would extract text from the resume file
            // For now, we\'ll use a placeholder or assume text extraction is done elsewhere
            String resumeText = "Placeholder resume text for " + resume.getFileName();
            return geminiClient.getFeedback(resumeText);
        } else {
            return "Resume not found";
        }
    }

    public String chatWithGemini(Long resumeId, String chatHistory, String userQuestion) {
        Optional<Resume> resumeOptional = resumeRepository.findById(resumeId);
        if (resumeOptional.isPresent()) {
            Resume resume = resumeOptional.get();
            String resumeText = "Placeholder resume text for " + resume.getFileName();
            return geminiClient.chatWithGemini(resumeText, chatHistory, userQuestion);
        } else {
            return "Resume not found";
        }
    }
}
