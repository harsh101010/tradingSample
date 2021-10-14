package com.acme.mytrader.strategy;

import com.acme.mytrader.domain.PriceActionList;
import com.acme.mytrader.execution.ExecutionService;

import java.util.Map;
import java.util.TreeMap;

/**
 * <pre>
 * User Story: As a trader I want to be able to monitor stock prices such
 * that when they breach a trigger level orders can be executed automatically
 * </pre>
 */
public class TradingStrategy {

    // trading strategy buy or sell triggers are stored in map with format <securityName
    //                                                                          -> price
    //                                                                                -> List of actions(BUY,100; sell,200)>
    private final Map<String,TreeMap<Double, PriceActionList>> strategyData;
    ExecutionService executionService;

    /**
     * load the security trading strategy here
     * The data contains security name as key , value is a map of pricePoint and PriceAction(BUY,SELL)
     */
    public TradingStrategy(Map<String, TreeMap<Double, PriceActionList>> strategyData, ExecutionService executionService){
        this.strategyData = strategyData;
        this.executionService = executionService;
    }

    public void listenTransaction(String ibm, double v) {


    }
}
