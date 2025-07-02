package org.eci.stocks.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class AlphaVantageMarketProvider implements StockMarketProvider{
    private final WebClient webClient;

    @Value("${ALPHA_KEY}")
    private String ALPHA_KEY;
    public AlphaVantageMarketProvider() {
        this.webClient = WebClient.builder()
                .baseUrl("https://www.alphavantage.co/")
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    private Mono<Map<String, Object>> consultAlphaApiDaily(String stock){
        System.out.println(ALPHA_KEY);
        return webClient.get()
                .uri(String.format("/query?function=TIME_SERIES_DAILY&symbol=%s&apikey=%s", stock, ALPHA_KEY))
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    System.out.println("response = " + response);
                    Map<String, Object> data = (Map<String, Object>) response.get("Time Series (Daily)");
                    return data;
                });

    }

    private Mono<Map<String, Object>> consultAlphaApiIntraDay(String stock){
        System.out.println(ALPHA_KEY);
        return webClient.get()
                .uri(String.format("/query?function=TIME_SERIES_INTRADAY&symbol=%s&interval=5min&apikey=%s", stock, ALPHA_KEY))
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    System.out.println("response = " + response);
                    Map<String, Object> data = (Map<String, Object>) response.get("Time Series (5min)");
                    return data;
                });

    }

    private Mono<Map<String, Object>> consultAlphaMonthly(String stock){
        System.out.println(ALPHA_KEY);
        return webClient.get()
                .uri(String.format("/query?function=TIME_SERIES_MONTHLY&symbol=%s&apikey=%s", stock, ALPHA_KEY))
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    System.out.println("response = " + response);
                    Map<String, Object> data = (Map<String, Object>) response.get("Time Series (Daily)");
                    return data;
                });

    }

    private Mono<Map<String, Object>> consultAlphaApiWeekly(String stock){
        System.out.println(ALPHA_KEY);
        return webClient.get()
                .uri(String.format("/query?function=TIME_SERIES_WEEKLY&symbol=%s&apikey=%s", stock, ALPHA_KEY))
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    System.out.println("response = " + response);
                    Map<String, Object> data = (Map<String, Object>) response.get("Time Series (Daily)");
                    return data;
                });

    }


    @Override
    public Map<String, Object> getStockInfo(String stock, StockFrequence type) {
        switch (type){
            case DAILY -> {
                return consultAlphaApiDaily(stock).block();
            }
            case INTRADAY -> {
                return consultAlphaApiIntraDay(stock).block();
            }
            case WEEKLY -> {
                return consultAlphaApiWeekly(stock).block();
            }
            case MONTHLY -> {
                return consultAlphaMonthly(stock).block();
            }
        }

        return null;
    }
}
