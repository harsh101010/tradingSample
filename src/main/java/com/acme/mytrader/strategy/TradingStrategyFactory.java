package com.acme.mytrader.strategy;

import com.acme.mytrader.domain.PriceActionList;
import com.acme.mytrader.execution.ExecutionService;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class TradingStrategyFactory {

    public static TradingStrategy createEmptyStrategy(ExecutionService executionService) {

        Map<String, TreeMap<Double, PriceActionList>> map = new HashMap<>();

        return new TradingStrategy(map, executionService);
    }

}