package com.resumeai.feedback;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeminiClient {

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    private final RestTemplate restTemplate;

    public GeminiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getFeedback(String resumeText) {
        // Placeholder for calling Gemini API for feedback
        // In a real scenario, you\'d construct a request body and send it to the Gemini API
        // For now, returning a dummy response
        return "{\"feedback\": \"This is a dummy feedback for your resume.\"}";
    }

    public String chatWithGemini(String resumeText, String chatHistory, String userQuestion) {
        // Placeholder for calling Gemini API for chat
        // In a real scenario, you\'d construct a request body with resumeText, chatHistory, and userQuestion
        // For now, returning a dummy response
        return "{\"response\": \"This is a dummy chat response from Gemini.\"}";
    }
}
