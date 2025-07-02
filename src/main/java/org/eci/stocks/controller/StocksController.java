package org.eci.stocks.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.eci.stocks.ia.IAAdapter;
import org.eci.stocks.provider.StockFrequence;
import org.eci.stocks.provider.StockMarketProvider;
import org.eci.stocks.service.AnalyzeRequest;
import org.eci.stocks.service.AnalyzerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/")
public class StocksController {
    private final StockMarketProvider stockMarketProvider;
    private final AnalyzerService service;

    public StocksController(StockMarketProvider stockMarketProvider, AnalyzerService service) {
        this.stockMarketProvider = stockMarketProvider;
        this.service = service;
    }

    @GetMapping("/stocks")
    public ResponseEntity<Map<String, Object>> getStockInfo(@RequestParam String stock, @RequestParam StockFrequence type){
        return ResponseEntity.ok(stockMarketProvider.getStockInfo(stock, type));
    }

    @PostMapping("/ia-recommendation")
    public ResponseEntity<String> askIa(@RequestBody AnalyzeRequest request) throws JsonProcessingException {
        return ResponseEntity.ok(service.analyze(request));
    }
}
