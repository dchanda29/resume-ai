package com.resumeai.resume;

import com.resumeai.user.User;
import com.resumeai.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.ByteArrayInputStream;

@Service
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;

    public ResumeService(ResumeRepository resumeRepository, UserRepository userRepository) {
        this.resumeRepository = resumeRepository;
        this.userRepository = userRepository;
    }

    public String uploadResume(MultipartFile file, Long userId) throws IOException {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Resume resume = new Resume();
            resume.setUser(user);
            resume.setFileName(file.getOriginalFilename());
            resume.setFileContent(file.getBytes());
            if (file.getContentType() != null && file.getContentType().equals("application/pdf")) {
                resume.setExtractedText(extractTextFromPdf(file.getBytes()));
            }
            resume.setFileType(file.getContentType());
            resumeRepository.save(resume);
            return "Resume uploaded successfully for user: " + userId;
        } else {
            return "User not found";
        }
    }

    public String extractTextFromPdf(byte[] pdfBytes) throws IOException {
        try (PDDocument document = PDDocument.load(new ByteArrayInputStream(pdfBytes))) {
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            return pdfTextStripper.getText(document);
        }
    }

    public List<Resume> getResumeHistory(Long userId) {
        return resumeRepository.findByUserId(userId);
    }
}
