package org.eci.stocks.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.eci.stocks.ia.IAAdapter;
import org.springframework.stereotype.Service;

@Service
public class AnalyzerService {
    private final IAAdapter iaAdapter;

    public AnalyzerService(IAAdapter iaAdapter) {
        this.iaAdapter = iaAdapter;
    }

    public String analyze(AnalyzeRequest request) throws JsonProcessingException {
        String question = "I want you to analyze this two stocks and give recommendations about in what of those two i should invest";
        question += request.getObject1().toString();
        question += request.getObject2().toString();

        return iaAdapter.generateResponse(question);
    }
}
