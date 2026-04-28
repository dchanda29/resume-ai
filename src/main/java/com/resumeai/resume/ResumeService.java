package com.resumeai.resume;

import com.resumeai.user.User;
import com.resumeai.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.Loader;

@Service
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;

    public ResumeService(ResumeRepository resumeRepository, UserRepository userRepository) {
        this.resumeRepository = resumeRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    // @Transactional — wraps the entire upload in one DB transaction.
    // Required for PostgreSQL to correctly write bytea columns without
    // the "Large Objects in auto-commit mode" error.
    public Resume uploadResume(MultipartFile file, Long userId) throws IOException {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return null;
        }

        User user = userOptional.get();
        Resume resume = new Resume();
        resume.setUser(user);
        resume.setFileName(file.getOriginalFilename());
        resume.setFileContent(file.getBytes());
        resume.setFileType(file.getContentType());

        if (file.getContentType() != null && file.getContentType().equals("application/pdf")) {
            resume.setExtractedText(extractTextFromPdf(file.getBytes()));
        }

        return resumeRepository.save(resume);
    }

    public String extractTextFromPdf(byte[] pdfBytes) throws IOException {
        try (PDDocument document = Loader.loadPDF(pdfBytes)) {
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            return pdfTextStripper.getText(document);
        }
    }

    @Transactional(readOnly = true)
    // readOnly = true — opens a real transaction for the SELECT query.
    // Without this, Hibernate reads bytea outside a transaction, which
    // causes PostgreSQL to throw "Large Objects in auto-commit mode".
    public List<Resume> getResumeHistory(Long userId) {
        return resumeRepository.findByUserId(userId);
    }
}
