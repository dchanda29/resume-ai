package com.resumeai.feedback;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @GetMapping("/{resumeId}")
    public String getFeedback(@PathVariable Long resumeId) {
        // Logic to get feedback for a specific resume
        return feedbackService.getFeedbackForResume(resumeId);
    }

    @PostMapping("/chat/{resumeId}")
    public String chatWithGemini(@PathVariable Long resumeId, @RequestBody ChatRequest chatRequest) {
        // Logic to chat with Gemini about a specific resume
        return feedbackService.chatWithGemini(resumeId, chatRequest.getChatHistory(), chatRequest.getUserQuestion());
    }
}

class ChatRequest {
    private String chatHistory;
    private String userQuestion;

    public String getChatHistory() {
        return chatHistory;
    }

    public void setChatHistory(String chatHistory) {
        this.chatHistory = chatHistory;
    }

    public String getUserQuestion() {
        return userQuestion;
    }

    public void setUserQuestion(String userQuestion) {
        this.userQuestion = userQuestion;
    }
}
