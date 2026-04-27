package com.resumeai.resume;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "resume")
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private com.resumeai.user.User user;

    private String fileName;
    private String fileType;

    @Column(columnDefinition = "TEXT")
    private String extractedText;

    @JsonIgnore
    @Column(columnDefinition = "bytea")
    private byte[] fileContent;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public com.resumeai.user.User getUser() { return user; }
    public void setUser(com.resumeai.user.User user) { this.user = user; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getFileType() { return fileType; }
    public void setFileType(String fileType) { this.fileType = fileType; }

    public String getExtractedText() { return extractedText; }
    public void setExtractedText(String extractedText) { this.extractedText = extractedText; }

    public byte[] getFileContent() { return fileContent; }
    public void setFileContent(byte[] fileContent) { this.fileContent = fileContent; }
}