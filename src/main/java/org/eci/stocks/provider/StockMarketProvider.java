package org.eci.stocks.provider;

import java.util.Map;

public interface StockMarketProvider {
    public Map<String, Object> getStockInfo(String stock, StockFrequence type);
}
