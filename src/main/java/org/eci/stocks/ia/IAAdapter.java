package org.eci.stocks.ia;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface IAAdapter {
    String generateResponse(String input) throws JsonProcessingException;
    String getStatus();
}