package com.resumeai.feedback;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import java.util.Collections;
import java.util.List;

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
        String prompt = "Provide structured feedback for the following resume in JSON format, highlighting strengths, weaknesses, and suggestions for improvement:\n\n" + resumeText;
        return callGeminiApi(prompt);
    }

    public String chatWithGemini(String resumeText, String chatHistory, String userQuestion) {
        String prompt = "Given the following resume:\n" + resumeText + "\n\nAnd the chat history:\n" + chatHistory + "\n\nUser asks: " + userQuestion + "\n\nProvide a concise answer.";
        return callGeminiApi(prompt);
    }

    private String callGeminiApi(String prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        Part part = new Part(prompt);
        Content content = new Content(Collections.singletonList(part));
        GeminiRequest request = new GeminiRequest(Collections.singletonList(content));

        HttpEntity<GeminiRequest> entity = new HttpEntity<>(request, headers);

        GeminiResponse response = restTemplate.postForObject(geminiApiUrl + geminiApiKey, entity, GeminiResponse.class);

        if (response != null && !response.getCandidates().isEmpty()) {
            return response.getCandidates().get(0).getContent().getParts().get(0).getText();
        }
        return "No response from Gemini API.";
    }
}
